package com.company.project.task;

import static com.company.project.task.MonitorTask.div;
import static com.company.project.task.MonitorTask.div18;
import static com.company.project.task.MonitorTask.sum;

import com.alibaba.fastjson.JSONObject;
import com.company.project.configurer.BaseConf;
import com.company.project.configurer.HttpHelper;
import com.company.project.gen.Rewards;
import com.company.project.gen.UniswapPair;
import com.google.common.base.Joiner;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.web3j.contracts.eip20.generated.ERC20;
import org.web3j.protocol.Web3j;
import org.web3j.tuples.generated.Tuple3;

@Component
@Slf4j
public class DefiBoxTask {

  @Autowired
  Web3j web3j;
  @Autowired
  BaseConf baseConf;
  Joiner joiner = Joiner.on('@');
  public static final String PROJECT_DATA="/dgg/open/report/project/data";
  public static final String PROJECT_COIN="/dgg/open/report/project/coin";
  public static final String PROJECT_POOL="/dgg/open/report/project/pool";

  public static final String SECRETK = "p2TUh0D5Hw610CKgf77LFyUrusSk0Y2SPfnsvlNF";
  public static final String ACCESSK = "fixedI5T0lAvB5lBEVG90";


  private String toJSONString(Object o){
    return JSONObject.toJSONString(o);
  }


  @Scheduled(fixedDelayString="${defibox.delay}")//30min
  public void monitor() throws Exception {

    UniswapPair uniswapPair = UniswapPair.load(baseConf.getFixPairAddress(),web3j,baseConf.me,baseConf.gasProvider);
    Tuple3<BigInteger, BigInteger, BigInteger> reserves = uniswapPair.getReserves().send();
    BigInteger lpts = uniswapPair.totalSupply().send();
    //component1 is usdt
    BigDecimal price = div(reserves.component1(),reserves.component2());
    log.info("FIX price is {}",price);
    BigDecimal lpPrice = new BigDecimal(reserves.component1()).divide(new BigDecimal(lpts),2,RoundingMode.HALF_UP).multiply(new BigDecimal("2"));


    //getPoolInfo
    Rewards rewards = Rewards.load(baseConf.getFixLpRewardsPool(),web3j,BaseConf.me,BaseConf.gasProvider);
    BigInteger rate = rewards.rewardRate().send();
    //get1yearGain
    BigDecimal yearGain= new BigDecimal(rate).multiply(new BigDecimal("31536000")).multiply(price).divide(BigDecimal.TEN.pow(18)).setScale(2,RoundingMode.HALF_UP);
    //getTotalBal
    BigInteger totalBal = rewards.totalSupply().send();
    //getTotalBalValue
    BigDecimal totalBalValue = new BigDecimal(totalBal).multiply(lpPrice).divide(BigDecimal.TEN.pow(18)).setScale(2,RoundingMode.HALF_UP);
    BigDecimal apy2 = yearGain.divide(totalBalValue,4,RoundingMode.HALF_UP).multiply(new BigDecimal("100"));

    log.info("apy2 {}",apy2.toPlainString());



    /**
     * | 属性               | 数据类型 | 是否必填 | 说明                   |
     * | :----------------- | :------- | :------- | :--------------------- |
     * | accessKey          | String   | Y        | 访问key                |
     * | timestamp          | long     | Y        | 请求时间戳             |
     * | signVersion        | String   | Y        | 版本，固定1.0          |
     * | signature          | String   | Y        | 签名，通过加签生成     |
     * | chain              | String   | Y        | 协议：HECO/ETH/BSC/OKT |
     * | tvl                | String   | N        | 锁仓量                 |
     * | address_count      | long     | N        | 总地址数               |
     * | address_active_24h | long     | N        | 24小时活跃地址数       |
     * | address_active_7d  | long     | N        | 7天活跃地址数          |
     * | trans_count_24h    | long     | N        | 24小时交易次数         |
     * | trans_count_7d     | long     | N        | 7day交易数             |
     * | trans_amount_24h   | string   | N        | 24小时交易额           |
     * | trans_amount_7d    | string   | N        | 7天交易额              |
     * |                    |          |          |                        |
     */
    LinkedHashMap<String,Object> tvlInfo = new LinkedHashMap<>();
    tvlInfo.put("access_key",ACCESSK);
    tvlInfo.put("timestamp",System.currentTimeMillis());
    tvlInfo.put("sign_version","1.0");
    tvlInfo.put("chain","HECO");
    tvlInfo.put("tvl","13657923");
    signature(tvlInfo,SECRETK);

    log.info("ready to post: {}",toJSONString(tvlInfo));
    push(tvlInfo,PROJECT_DATA);

/**
 * | 属性          | 数据类型 | 是否必填 | 说明                   |
 * | :------------ | :------- | :------- | :--------------------- |
 * | accessKey     | String   | Y        | 访问key                |
 * | timestamp     | long     | Y        | 请求时间戳             |
 * | signVersion   | String   | Y        | 版本，固定1.0          |
 * | signature     | String   | Y        | 签名，通过加签生成     |
 * | chain         | String   | Y        | 协议：HECO/ETH/BSC/OKT |
 * | name          | string   | Y        | 代币名称               |
 * | address       | string   | N        | 代币地址               |
 * | issue_amount  | string   | N        | 发行量                 |
 * | market_amount | string   | N        | 流通量                 |
 * |               |          |          |                        |
 */
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

    LinkedHashMap<String,Object> coinInfo = new LinkedHashMap<>();
    coinInfo.put("access_key",ACCESSK);
    coinInfo.put("timestamp",System.currentTimeMillis());
    coinInfo.put("sign_version","1.0");
    coinInfo.put("chain","HECO");
    coinInfo.put("name", "FIX");
    coinInfo.put("address","0xde9495de889996404b14ddbf05f66db7401f0733");
    coinInfo.put("issue_amount",div18(totalSupply).toPlainString());
    coinInfo.put("market_amount",div18(totalCircle).toPlainString());

    signature(coinInfo,SECRETK);

    log.info("ready to post: {}",toJSONString(coinInfo));
    push(coinInfo,PROJECT_COIN);

/**
 * | 属性              | 数据类型 | 是否必填 | 说明                                                         |
 * | :---------------- | :------- | :------- | :----------------------------------------------------------- |
 * | accessKey         | String   | Y        | 访问key                                                      |
 * | timestamp         | long     | Y        | 请求时间戳                                                   |
 * | signVersion       | String   | Y        | 版本，固定1.0                                                |
 * | signature         | String   | Y        | 签名，通过加签生成                                           |
 * | chain             | String   | Y        | 协议：HECO/ETH/BSC/OKT                                       |
 * | pool              | String   | Y        | 矿池，币种名，多个币种横线间隔，例：HUSD-WHT                 |
 * | type              | int      | N        | 池类型：1-流动性挖矿（单币，多币） ，2-交易挖矿 ，3-董事会，4-质押借贷，5-其它 |
 * | address_pool      | String   | N        | 矿池地址，例：0x12312312234234                               |
 * | address_token1    | String   | N        | 矿池币的地址，例：0x12312312234234                           |
 * | address_token2    | String   | N        | 矿池币的地址，例：0x12312312234234                           |
 * | apy_year          | string   | N        | 年化                                                         |
 * | apy_day           | string   | N        | 日化                                                         |
 * | lp_amount         | string   | N        | lp锁仓量                                                     |
 * | lp_depth_token1   | string   | N        | lp深度中的token1                                             |
 * | lp_depth_token2   | string   | N        | lp深度中的token2                                             |
 * | lp_total          | string   | N        | lp数量                                                       |
 * | status            | int      | N        | 状态：1-开启、2-关闭                                         |
 * | reinvest_apy_year | string   | N        | 复投年化                                                     |
 * |                   |          |          |                                                              |
 */


    LinkedHashMap<String,Object> poolInfo = new LinkedHashMap<>();
    poolInfo.put("access_key",ACCESSK);
    poolInfo.put("timestamp",System.currentTimeMillis());
    poolInfo.put("sign_version","1.0");
    poolInfo.put("chain","HECO");
    poolInfo.put("pool","FIX");
    poolInfo.put("type",3);
    poolInfo.put("address_pool",baseConf.getFixrewardsPool());
    poolInfo.put("address_token1","0xde9495de889996404b14ddbf05f66db7401f0733");
    String apy = "197.55";
    poolInfo.put("apy_year",revestAPY(apy));
    poolInfo.put("reinvest_apy_year",revestDAPY(apy));
    poolInfo.put("apy_day",new BigDecimal(apy).divide(new BigDecimal("365"),2,RoundingMode.HALF_UP).toPlainString());
    poolInfo.put("status",1);

    signature(poolInfo,SECRETK);

    log.info("ready to post: {}",toJSONString(poolInfo));
    push(poolInfo,PROJECT_POOL);



    poolInfo = new LinkedHashMap<>();
    poolInfo.put("access_key",ACCESSK);
    poolInfo.put("timestamp",System.currentTimeMillis());
    poolInfo.put("sign_version","1.0");
    poolInfo.put("chain","HECO");
    poolInfo.put("pool","FIX-USDT");
    poolInfo.put("type",1);
    poolInfo.put("address_pool",baseConf.getFixLpRewardsPool());
    poolInfo.put("address_token1","0xde9495de889996404b14ddbf05f66db7401f0733");
    poolInfo.put("address_token2","0xa71edc38d189767582c38a3145b5873052c3e47a");
    apy = "470.55";
    poolInfo.put("apy_year",revestAPY(apy));
    poolInfo.put("reinvest_apy_year",revestDAPY(apy));
    poolInfo.put("apy_day",new BigDecimal(apy).divide(new BigDecimal("365"),2,RoundingMode.HALF_UP).toPlainString());
    poolInfo.put("status",1);

    signature(poolInfo,SECRETK);
    log.info("ready to post: {}",toJSONString(poolInfo));
    push(poolInfo,PROJECT_POOL);

    //all Pool

    Map<String,String> dInterestMap  = baseConf.getDInterestMap();
    for(String marketName:dInterestMap.keySet()){
      poolInfo = new LinkedHashMap<>();
      poolInfo.put("access_key",ACCESSK);
      poolInfo.put("timestamp",System.currentTimeMillis());
      poolInfo.put("sign_version","1.0");
      poolInfo.put("chain","HECO");
      poolInfo.put("pool",coin(marketName));
      poolInfo.put("type",5);
      poolInfo.put("address_pool",dInterestMap.get(marketName));
      poolInfo.put("address_token1",coinAdr(marketName));
      apy = baseConf.getAPYMap().get(marketName);
      poolInfo.put("apy_year",apy);
      poolInfo.put("status",1);
      signature(poolInfo,SECRETK);
      log.info("ready to post: {}",toJSONString(poolInfo));
      push(poolInfo,PROJECT_POOL);
    }


  }


  /**
   * 加签：组织加签字符串 伪代码：String body = sign_version=1.0@access_key=xxxxxxxxxx@secret_key=xxxxxxxxxx@timestamp=123123123123@业务参数
   * 注意业务参数： 业务参数以参数名正序排列，使用@符连接，业务参数排除：access_key,timestamp,sign_version,signature
   * 例：body = “access_key=0b3LHEbj16HX3JDt@secret_key=0FNFfMwN1Pq6xCUBoC1rLVorD4QsCyRf@timestamp=1615859391285@aeaa=2323.121@bddd=fdaadfs@c=123.32@das=333“;
   * signature = base64(sha256(body));
   *
   *
   *
   * access_key=xxxxxxxxxx@secret_key=xxxxxxxxxx@timestamp=123123123123@业务参数
   * @param source
   * @return
   */


  private void signature(LinkedHashMap<String,Object> source,String secretKey){

    LinkedHashMap<String,Object> cloned = (LinkedHashMap<String,Object>)source.clone();
    List<String> pairs = new ArrayList<>();

    pairs.add("sign_version="+source.get("sign_version"));
    pairs.add("access_key="+source.get("access_key"));
    pairs.add("secret_key="+secretKey);
    pairs.add("timestamp="+source.get("timestamp"));

    cloned.remove("sign_version");
    cloned.remove("access_key");
    cloned.remove("secret_key");
    cloned.remove("timestamp");

    Set<String> ks = cloned.keySet();
    Object[] arr=ks.toArray();
    Arrays.sort(arr);

    for(Object k:arr){
      String key = (String) k;
      Object value = cloned.get(key);
      String pair = key+"="+value;
      pairs.add(pair);
    }
    String tosign  = joiner.join(pairs);
    log.info("toSign: {}",tosign);

    String base64 = sha256(tosign);
    log.info("base64: {}",base64);

    source.put("signature",base64);
  }


  private String push(LinkedHashMap<String,Object> source,String path){
    String result ="FAILED";
    try {
      result = HttpHelper.post(baseConf.getDefiBox()+path,new JSONObject(source).toJSONString());
      log.info("push res:{}",result);
      //sleep for 10s
      Thread.sleep(10000);
    } catch (Exception e) {
      log.error("push to Q Error",e);
    }
    return result;
  }

  public static String revestAPY(String apr){
    BigDecimal oneplus = new BigDecimal(apr).divide(new BigDecimal("100")).divide(new BigDecimal("52"),18,RoundingMode.HALF_UP).add(BigDecimal.ONE);
    BigDecimal res = oneplus.pow(52).subtract(BigDecimal.ONE).setScale(4,RoundingMode.HALF_UP).multiply(new BigDecimal("100"));
    return res.toPlainString();
  }


  public static String revestDAPY(String apr){
    BigDecimal oneplus = new BigDecimal(apr).divide(new BigDecimal("100")).divide(new BigDecimal("365"),18,RoundingMode.HALF_UP).add(BigDecimal.ONE);
    BigDecimal res = oneplus.pow(365).subtract(BigDecimal.ONE).setScale(4,RoundingMode.HALF_UP).multiply(new BigDecimal("100"));
    return res.toPlainString();
  }


  public static String sha256(String src) {
    try {
      byte[] sha256 = MessageDigest.getInstance("SHA-256").digest(src.getBytes());
      //  System.out.println(Arrays.toString(sha256));
      return Base64.encodeBase64String(sha256);
    } catch (Exception e) {
      return null;
    }
  }

  public static void main(String[] args) {
    log.info("{}",revestAPY("197.56"));
    log.info("{}",revestDAPY("197.57"));
  }

  private String coin(String msg){
    return msg.split("-")[1];
  }

  private String coinAdr(String msg){
    return baseConf.getAddressBySymbol(coin(msg));
  }
}
