package com.company.project.task;

import static com.company.project.Web3JHelper.surPlusCal;
import static com.company.project.configurer.BaseConf.GQL_BLOCK;
import static com.company.project.configurer.BaseConf.GQL_DEPOSITS;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.company.project.configurer.BaseConf;
import com.company.project.configurer.HttpHelper;
import com.company.project.configurer.Sha256;
import com.company.project.gen.CompoundERC20Market;
import com.company.project.gen.DInterest;
import com.company.project.gen.ICERC20;
import com.company.project.gen.UniswapPair;
import com.google.common.base.Joiner;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
public class DefiBoxTask {

  @Autowired
  Web3j web3j;
  @Autowired
  BaseConf baseConf;
  Joiner joiner = Joiner.on('@');
  public static final String PROJECT_DATA="/dgg/open/report/project/data";
  public static final String PROJECT_COIN="/dgg/open/report/project/coin";
  public static final String PROJECT_POOL="/dgg/open/report/project/pool";

  public static final String SECRETK="secretk";
  public static final String ACCESSK="1234";


  private String toJSONString(Object o){
    return JSONObject.toJSONString(o);
  }


  @Scheduled(fixedDelayString="${defibox.delay}")//30min
  public void monitor() {
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
    tvlInfo.put("tvl","21190596");
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
    LinkedHashMap<String,Object> coinInfo = new LinkedHashMap<>();
    coinInfo.put("access_key",ACCESSK);
    coinInfo.put("timestamp",System.currentTimeMillis());
    coinInfo.put("sign_version","1.0");
    coinInfo.put("chain","HECO");
    coinInfo.put("name"," fixed.finance");
    coinInfo.put("address","0xde9495de889996404b14ddbf05f66db7401f0733");
    coinInfo.put("issue_amount","219339.2897");
    coinInfo.put("market_amount","194862.0908");

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
    poolInfo.put("apy_year","279.6848");
    poolInfo.put("apy_day",new BigDecimal(poolInfo.get("apy_year").toString()).divide(new BigDecimal("365"),2,RoundingMode.HALF_UP).toPlainString());
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

    poolInfo.put("apy_year","543.5526");
    poolInfo.put("apy_day",new BigDecimal(poolInfo.get("apy_year").toString()).divide(new BigDecimal("365"),2,RoundingMode.HALF_UP).toPlainString());
    poolInfo.put("status",1);

    signature(poolInfo,SECRETK);
    log.info("ready to post: {}",toJSONString(poolInfo));
    push(poolInfo,PROJECT_POOL);

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
    String sha256 = Sha256.getSHA256(tosign);
    log.info("sha256: {}",sha256);
    String base64 = Base64.getEncoder().encodeToString(sha256.getBytes());
    log.info("base64: {}",base64);

    source.put("signature",base64);
  }


  private String push(LinkedHashMap<String,Object> source,String path){
    String result ="FAILED";
    try {
      result = HttpHelper.post(baseConf.getDefiBox()+path,new JSONObject(source).toJSONString());
      log.info("push res:{}",result);
      //sleep for 2s
      Thread.sleep(2000);
    } catch (Exception e) {
      log.error("push to Q Error",e);
    }
    return result;
  }


}
