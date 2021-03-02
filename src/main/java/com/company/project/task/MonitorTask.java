package com.company.project.task;

import static com.company.project.configurer.BaseConf.GQL_BLOCK;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.company.project.configurer.BaseConf;
import com.company.project.configurer.HttpHelper;
import com.company.project.gen.UniswapPair;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.concurrent.ExecutionException;
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

  private String toJSONString(Object o){
    return JSONObject.toJSONString(o);
  }


  @Scheduled(fixedDelay=10000L)
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
      log.info("FIX price is {}",div(reserves.component2(),reserves.component1()));
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

      log.info("FIX totalLock {} , total Circle {}",div18(totalLock),div18(totalCircle));

    }catch (Exception e){
      log.error("cannot get price from node",e);
    }


  }

  private void compare(BigInteger fromNode,BigInteger fromGraph){
    long diff =  fromNode.subtract(fromGraph).longValue();
    if(diff>=10||diff<=-10){
      log.error("block from fullNode: {}, block from graph: {} ,diff {} !",fromNode,fromGraph,diff);
    }else {
      log.info("block from fullNode: {}, block from graph: {} ,diff {} ",fromNode,fromGraph,diff);
    }

  }

  private static BigDecimal div(BigInteger a,BigInteger b){
    return new BigDecimal(a).divide(new BigDecimal(b),6, RoundingMode.HALF_UP);
  }

  private static BigDecimal div18(BigInteger a){
    return new BigDecimal(a).divide(BigDecimal.TEN.pow(18),18, RoundingMode.HALF_UP);
  }

  private static BigInteger sum( LinkedHashMap<String,BigInteger> balanceMap ){
    BigInteger sum = new BigInteger("0");
    for(String key:balanceMap.keySet()){
      sum = sum.add(balanceMap.get(key));
    }
    return sum;
  }

}
