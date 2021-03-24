package com.company.project.configurer;

import java.util.LinkedHashMap;
import java.util.Map;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.web3j.crypto.Credentials;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

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

}
