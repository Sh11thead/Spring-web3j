package com.company.project.configurer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

/**
 * Mybatis & Mapper & PageHelper 配置
 */
@Configuration
public class Web3Configurer {

    @Bean
    public Web3j web3J() {
        Credentials me =  Credentials.create("00");
        ContractGasProvider gasProvider = new DefaultGasProvider();

        Web3j web3j = Web3j.build(
            new HttpService("https://http-mainnet-node.huobichain.com"));
        return web3j;
    }


}

