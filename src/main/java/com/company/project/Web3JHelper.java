package com.company.project;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.company.project.configurer.HttpHelper;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class Web3JHelper {

  static String GQL_BLOCK="{\"query\":\"       {         _meta {           block {             number           }         }       }     \"}";
  static String GQL_DEPOSITS="{\"variables\":{},\"query\":\"{ dpool(id: \\\"DPOOLMARK\\\") {    id    moneyMarketIncomeIndex surplus    deposits(where: {nftID_gt: 0}, orderBy: nftID) {      nftID      amount      active     maturationTimestamp      interestEarned      initialMoneyMarketIncomeIndex      __typename    }    __typename  }}\"}";

  //web3j  generate  truffle --truffle-json=/Users/shulin/Documents/88mocks/fixed-rate-protocol-contracts/artifacts/contracts/DInterest.sol/DInterest.json -o /Users/shulin/Downloads/web3jseed/src/main/java -p com.company.project.gen

  public static void main(String[] args) throws Exception {
    String result = HttpHelper.post("https://graph.fixed.finance/subgraphs/name/fixed/fixed-protocol1",GQL_DEPOSITS.replaceAll("DPOOLMARK","0x52e2de5fccf3385748de4f76bf7d98d4ae647813"));
   // String result = HttpHelper.post("https://api.thegraph.com/subgraphs/name/bacon-labs/eighty-eight-mph-staging",GQL_DEPOSITS.replaceAll("DPOOLMARK","0x35966201a7724b952455b73a36c8846d8745218e"));

    JSONObject jsonObject = JSON.parseObject(result);
    String moneyMarketIncomeIndex = jsonObject.getJSONObject("data").getJSONObject("dpool").getString("moneyMarketIncomeIndex");
    JSONArray deposits = jsonObject.getJSONObject("data").getJSONObject("dpool").getJSONArray("deposits");

    System.out.println("totalSurplus:"+ jsonObject.getJSONObject("data").getJSONObject("dpool").getString("surplus"));

    BigDecimal res = new BigDecimal("0");
    for(int i=0;i<deposits.size();i++){
      JSONObject cube = deposits.getJSONObject(i);
      boolean active = cube.getBoolean("active");
      if(i<=2)
        continue;
      if(active){

        String amount = cube.getString("amount");
        String initialMoneyMarketIncomeIndex = cube.getString("initialMoneyMarketIncomeIndex");
        String interestEarned = cube.getString("interestEarned");
        BigDecimal s = surPlusCal(interestEarned,amount,moneyMarketIncomeIndex,initialMoneyMarketIncomeIndex);
        res = res.add(s);
        System.out.println(cube.getString("nftID")+" surplus is "+s.toPlainString()+" total is"+res.toPlainString());
      }else {
        System.out.println(cube.getString("nftID")+" is not active");

      }
    }
    System.out.println("totalSurplus added:"+ res);



    System.out.println(jsonObject);
/*    Credentials me =  Credentials.create("00");
    ContractGasProvider gasProvider = new DefaultGasProvider();

    Web3j web3j = Web3j.build(
    new HttpService("https://http-mainnet-node.huobichain.com"));
    EthBlockNumber result = new EthBlockNumber();
    result = web3j.ethBlockNumber()
        .sendAsync()
        .get();

    System.out.println(result.getBlockNumber());

    DInterest dInterest = DInterest.load("0x52e2dE5fcCF3385748dE4F76Bf7D98d4AE647813",web3j,me,gasProvider );

    System.out.println(dInterest.fixMinter().send());


    String mphAddr = "0x25d2e80cb6b86881fd7e07dd263fb79f4abe033c";
    String USDTAddr = "0xa71edc38d189767582c38a3145b5873052c3e47a";
    String pairAddr = "0x615e6285c5944540fd8bd921c9c8c56739fd1e13";

    ERC20 usdt = ERC20.load(USDTAddr,web3j,me,gasProvider);
    ERC20 mph = ERC20.load(mphAddr,web3j,me,gasProvider);

    BigInteger usdtBal = usdt.balanceOf(pairAddr).send();
    BigInteger mphBal = mph.balanceOf(pairAddr).send();

    System.out.println(div(usdtBal,mphBal));*/


  }

  public static BigDecimal surPlusCal(String earned,String amount,String currentIndex,String initialMoneyMarketIncomeIndex){
    BigDecimal relax = new BigDecimal(currentIndex).divide(new BigDecimal(initialMoneyMarketIncomeIndex),18,RoundingMode.HALF_UP);
    BigDecimal boomed = relax.multiply(new BigDecimal(amount)).subtract(new BigDecimal(amount));
    BigDecimal surplus = boomed.subtract(new BigDecimal(earned));
    return surplus;
  }





}
