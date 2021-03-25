package com.company.project.task;

import static com.company.project.Web3JHelper.surPlusCal;
import static com.company.project.configurer.BaseConf.GQL_BLOCK;
import static com.company.project.configurer.BaseConf.GQL_DEPOSITS;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.company.project.configurer.BaseConf;
import com.company.project.configurer.HttpHelper;
import com.company.project.gen.CompoundERC20Market;
import com.company.project.gen.DInterest;
import com.company.project.gen.ICERC20;
import com.company.project.gen.UniswapPair;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.web3j.contracts.eip20.generated.ERC20;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthBlockNumber;
import org.web3j.tuples.generated.Tuple3;

@Component
@Slf4j
public class MonitorTask {

  @Autowired
  Web3j web3j;
  @Autowired
  BaseConf baseConf;

  Map<String,String> cacheMap = new LinkedHashMap<>();


  private String toJSONString(Object o){
    return JSONObject.toJSONString(o);
  }


 // @Scheduled(fixedDelayString="${monitor.delay}")//30min
  public void monitor() {

    //blockNum
    EthBlockNumber ethBlockNumber = null;
    try {
      ethBlockNumber = web3j.ethBlockNumber()
          .sendAsync()
          .get();
    } catch (Exception e) {
      log.error("cannot connect to heco node",e);
    }
    String result ="";
    try {
      result = HttpHelper.post(baseConf.getGraphNodeAddr(),GQL_BLOCK);
    } catch (IOException e) {
      log.error("cannot connect to graph node",e);
    }
    JSONObject jsonObject = JSON.parseObject(result);
    BigInteger fromNode = ethBlockNumber.getBlockNumber();
    BigInteger fromGraph = jsonObject.getJSONObject("data").getJSONObject("_meta").getJSONObject("block").getBigInteger("number");
    compare(fromNode,fromGraph);

    //price
    try {
      UniswapPair uniswapPair = UniswapPair.load(baseConf.getFixPairAddress(),web3j,baseConf.me,baseConf.gasProvider);
      Tuple3<BigInteger, BigInteger, BigInteger> reserves = uniswapPair.getReserves().send();
      BigDecimal price = div(reserves.component1(),reserves.component2());
      log.info("FIX price is {}",price);
      setValue("FIX价格",price.toPlainString(),true);
    } catch (Exception e) {
      log.error("cannot get price from mdex",e);
    }

    //circle
    try {
      LinkedHashMap<String,BigInteger> balanceMap = new LinkedHashMap<>();
      ERC20 fix = ERC20.load(baseConf.getFixAddress(),web3j,BaseConf.me,BaseConf.gasProvider);
      BigInteger totalSupply = fix.totalSupply().send();
      log.info("FIX totalSupply is {}",div18(totalSupply));
      BigInteger fixInLP = fix.balanceOf(baseConf.getFixLpRewardsPool()).send();
      balanceMap.put(baseConf.getFixLpRewardsPool(),fixInLP);
      log.info("FIX in LP Pool {}",div18(fixInLP));

      BigInteger fixInRewardsPool = fix.balanceOf(baseConf.getFixrewardsPool()).send();
      balanceMap.put(baseConf.getFixrewardsPool(),fixInRewardsPool);
      log.info("FIX in Rewards Pool {}",div18(fixInRewardsPool));

      BigInteger fixInGovWallet = fix.balanceOf(baseConf.getFixGovWallet()).send();
      balanceMap.put(baseConf.getFixGovWallet(),fixInGovWallet);
      log.info("FIX in Gov Wallet {}",div18(fixInGovWallet));

      BigInteger fixInDevWallet = fix.balanceOf(baseConf.getFixDevWallet()).send();
      balanceMap.put(baseConf.getFixDevWallet(),fixInDevWallet);
      log.info("FIX in Dev Wallet {}",div18(fixInDevWallet));


      BigInteger fixInDevVesting = fix.balanceOf(baseConf.getFixVesting()).send();
      balanceMap.put(baseConf.getFixVesting(),fixInDevVesting);
      log.info("FIX in Vesting {}",div18(fixInDevVesting));

      BigInteger totalLock = sum(balanceMap);
      BigInteger totalCircle = totalSupply.subtract(totalLock);
      BigDecimal percent = new BigDecimal(totalLock).multiply(new BigDecimal("100")).divide(new BigDecimal(totalSupply),2,RoundingMode.HALF_UP);

      setValue("FIX锁仓量",div18(totalLock).toPlainString(),true);
      setValue("FIX流通量",div18(totalCircle).toPlainString(),true);
      setValue("FIX锁仓比例",percent+"%",true);
      log.info("FIX totalLock {} , total Circle {}",div18(totalLock),div18(totalCircle));

    }catch (Exception e){
      log.error("cannot get price from node",e);
    }

    //Markets

    try{
      Map<String,String> dInterestMap  = baseConf.getDInterestMap();
      for(String marketName:dInterestMap.keySet()){
        String address = dInterestMap.get(marketName);
        result = HttpHelper.post(baseConf.getGraphNodeAddr(),GQL_DEPOSITS.replaceAll("DPOOLMARK",address.toLowerCase()));
        jsonObject = JSON.parseObject(result);
        String moneyMarketIncomeIndex = jsonObject.getJSONObject("data").getJSONObject("dpool").getString("moneyMarketIncomeIndex");
        JSONArray deposits = jsonObject.getJSONObject("data").getJSONObject("dpool").getJSONArray("deposits");

        BigDecimal res = new BigDecimal("0");
        List<SurplusDetail> surplusDetailList = new ArrayList<>();

        for(int i=0;i<deposits.size();i++){
          JSONObject cube = deposits.getJSONObject(i);
          boolean active = cube.getBoolean("active");
          if(active){
            String amount = cube.getString("amount");
            String initialMoneyMarketIncomeIndex = cube.getString("initialMoneyMarketIncomeIndex");
            String interestEarned = cube.getString("interestEarned");
            BigDecimal s = surPlusCal(interestEarned,amount,moneyMarketIncomeIndex,initialMoneyMarketIncomeIndex);
            res = res.add(s);
            surplusDetailList.add(new SurplusDetail(cube.getString("nftID"),s.toPlainString()));
            log.info("Market {} nftId {} surplus is {}",marketName,cube.getString("nftID"),s.toPlainString());
          }else {
            log.info("Market {} nftId {} is closed",marketName,cube.getString("nftID"));
           // surplusDetailList.add(new SurplusDetail(cube.getString("nftID"),"closed"));
          }
        }

        String surplus = jsonObject.getJSONObject("data").getJSONObject("dpool").getString("surplus");
        log.info("Market {} total surplus is {}",marketName,surplus);

        StringBuilder stringBuilder = new StringBuilder(marketName+"市场债务 :"+surplus+" "+coin(marketName)+" 明细 :\n");
        for(SurplusDetail surplusDetail:surplusDetailList){
          stringBuilder.append("NFT ID = "+surplusDetail.nftID+" ,债务 "+surplusDetail.surplus+" "+coin(marketName)+"\n");
        }
        qiPush(stringBuilder.toString());
      }

    }catch (Exception e){
      log.error("error in graph node",e);
    }


    //Market cTokens

    try{
      Map<String,String> dInterestMap  = baseConf.getDInterestMap();
      for(String marketName:dInterestMap.keySet()){
        String address = dInterestMap.get(marketName);
        DInterest dInterest = DInterest.load(address,web3j,BaseConf.me,BaseConf.gasProvider);
        String marketAddr =  dInterest.moneyMarket().send();
        CompoundERC20Market market = CompoundERC20Market.load(marketAddr,web3j,BaseConf.me,BaseConf.gasProvider);
        ICERC20 icerc20 = ICERC20.load(market.cToken().send(),web3j,BaseConf.me,BaseConf.gasProvider);
        BigInteger currCBal = icerc20.balanceOf(marketAddr).send();
        BigInteger exRate = icerc20.exchangeRateStored().send();

        BigInteger totolDeposit = wmul(currCBal,exRate);
        log.info("Market {} total deposit value is {}",marketName,totolDeposit);
        BigInteger totolCash= icerc20.getCash().send();
        log.info("Market {} remote cash is {}",marketName,totolCash);
        String msg = marketName
            +" 本平台存款量: "+smartDiv(marketName,totolDeposit).toPlainString()+" "+coin(marketName)
            +" 第三方可取款量: "+smartDiv(marketName,totolCash).toPlainString()+" "+coin(marketName)+"\n";
        if(totolCash.compareTo(totolDeposit)>=0){
          msg += "无取款风险";
        }else {
          msg += "出现取款风险";
        }
        qiPush(msg);
      }
    }catch (Exception e){
      log.error("error in graph node",e);
    }


  }

  private void setValue(String key,String value,boolean compare){
    log.info("set {} value {}",key,value);
    String valuePre = cacheMap.get(key);

    if(valuePre==null){
      qiPush(key+": "+value);
      cacheMap.put(key,value);
      return;
    }
    if(!valuePre.equals(value) ){
      if(compare){
        qiPush(key+": "+valuePre+" => "+value);
      }else {
        qiPush(key+": "+value);
      }
      cacheMap.put(key,value);
    }
  }
  private String market(String msg){
    return msg.split("-")[0];

  }

  private String coin(String msg){
    return msg.split("-")[1];
  }

  private String qiPush(String msg){
    String result ="FAILED";
    try {
      Map<String,Object> cube = new HashMap<>();
      cube.put("msgtype","text");
      Map<String,String> tent = new HashMap<>();
      tent.put("content",msg);
      cube.put("text",tent);
      log.info("ready to push: \n{}",cube);
     // result = HttpHelper.post(baseConf.getQiPushAddr(),new JSONObject(cube).toJSONString());
     // log.info("push res:{}",result);
      //sleep for 2s
      Thread.sleep(2000);
    } catch (Exception e) {
      log.error("push to Q Error",e);
    }
    return result;
  }

  private void compare(BigInteger fromNode,BigInteger fromGraph){
    long diff =  fromNode.subtract(fromGraph).longValue();
    if(diff>=10||diff<=-10){
      log.error("block from fullNode: {}, block from graph: {} ,diff {} !",fromNode,fromGraph,diff);
      qiPush("Graph块高: "+ fromGraph+" FullNode块高: "+fromNode+" ,差距过大!");
    }else {
      log.info("block from fullNode: {}, block from graph: {} ,diff {} ",fromNode,fromGraph,diff);
    }

  }

  private static BigInteger wmul(BigInteger a,BigInteger b){
    return a.multiply(b).divide(BigInteger.TEN.pow(18));
  }

  public static BigDecimal div(BigInteger a,BigInteger b){
    return new BigDecimal(a).divide(new BigDecimal(b),6, RoundingMode.HALF_UP);
  }

  public static BigDecimal div18(BigInteger a){
    return new BigDecimal(a).divide(BigDecimal.TEN.pow(18),4, RoundingMode.HALF_UP);
  }

  public static BigDecimal smartDiv(String market,BigInteger a){
    if(market.toLowerCase().contains("husd")){
      return new BigDecimal(a).divide(BigDecimal.TEN.pow(8),4, RoundingMode.HALF_UP);
    }
    return new BigDecimal(a).divide(BigDecimal.TEN.pow(18),4, RoundingMode.HALF_UP);
  }

  public static BigInteger sum( LinkedHashMap<String,BigInteger> balanceMap ){
    BigInteger sum = new BigInteger("0");
    for(String key:balanceMap.keySet()){
      sum = sum.add(balanceMap.get(key));
    }
    return sum;
  }
  @Data
  @AllArgsConstructor
  public static class SurplusDetail{
    String nftID;
    String surplus;
  }

}
