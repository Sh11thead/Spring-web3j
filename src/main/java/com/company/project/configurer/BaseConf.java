package com.company.project.configurer;

import java.util.LinkedHashMap;
import java.util.Map;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.web3j.crypto.Credentials;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

/**
 * web3j  generate  truffle --truffle-json=/Users/juliankang/Documents/fixed-rate-protocol-contracts/artifacts/contracts/rewards/Rewards.sol/Rewards.json -o /Users/juliankang/Documents/Spring-web3j/src/main/java -p com.company.project.gen
 */
@Component
@Data
public class BaseConf {
  public static Credentials me =  Credentials.create("00");
  public static ContractGasProvider gasProvider = new DefaultGasProvider();


  public static String GQL_BLOCK="{\"query\":\"       {         _meta {           block {             number           }         }       }     \"}";
  public static String GQL_DEPOSITS="{\"variables\":{},\"query\":\"{ dpool(id: \\\"DPOOLMARK\\\") {    id    moneyMarketIncomeIndex surplus    deposits(where: {nftID_gt: 0}, orderBy: nftID) {      nftID      amount      active     maturationTimestamp      interestEarned      initialMoneyMarketIncomeIndex      __typename    }    __typename  }}\"}";

  @Value("${graph.node}")
  private String graphNodeAddr;

  @Value("${qi.push}")
  private String qiPushAddr;

  @Value("${fix.pair.address}")
  private String fixPairAddress;

  @Value("${fix.address}")
  private String fixAddress;

  @Value("${fix.lpRewardsPool}")
  private String fixLpRewardsPool;


  @Value("${fix.rewardsPool}")
  private String fixrewardsPool;

  @Value("${fix.govWallet}")
  private String fixGovWallet;

  @Value("${fix.devWallet}")
  private String fixDevWallet;

  @Value("${fix.vesting}")
  private String fixVesting;

  @Value("${fix.DInterests}")
  private String dInterests;

  @Value("${fix.APY}")
  private String fixAPY;

  @Value("${defi.box}")
  private String defiBox;


  public Map<String,String> getDInterestMap(){
    Map<String,String> ret = new LinkedHashMap<>();
    for(String rpair:dInterests.split(",")){
      String [] pairpart = rpair.split(":");
      ret.put(pairpart[0],pairpart[1]);
    }
    return ret;
  }

  public Map<String,String> getAPYMap(){
    Map<String,String> ret = new LinkedHashMap<>();
    for(String rpair:fixAPY.split(",")){
      String [] pairpart = rpair.split(":");
      ret.put(pairpart[0],pairpart[1]);
    }
    return ret;
  }

  public String getAddressBySymbol(String symbol){
    /**
     * ETH  0x64ff637fb478863b7468bc97d30a5bf3a428a1fd
     * HBTC 0x66a79d23e58475d2738179ca52cd0b41d73f0bea
     * HDOT 0xa2c49cee16a5e5bdefde931107dc1fae9f7773e3
     * HFI 0xae3a768f9ab104c69a7cd6041fe16ffa235d1810
     */
    String ret = "";
    switch (symbol){
      case "ETH":
        ret = "0x64ff637fb478863b7468bc97d30a5bf3a428a1fd";break;
      case "MDX":
        ret = "0x25d2e80cb6b86881fd7e07dd263fb79f4abe033c";break;
      case "HBTC":
        ret = "0x66a79d23e58475d2738179ca52cd0b41d73f0bea";break;
      case "HDOT":
        ret = "0xa2c49cee16a5e5bdefde931107dc1fae9f7773e3";break;
      case "HFIL":
        ret = "0xae3a768f9ab104c69a7cd6041fe16ffa235d1810";break;
      case "HUSD":
        ret = "0x0298c2b32eae4da002a15f36fdf7615bea3da047";break;
      case "USDT":
        ret = "0xa71edc38d189767582c38a3145b5873052c3e47a";break;
    }
    return ret;
  }


}
