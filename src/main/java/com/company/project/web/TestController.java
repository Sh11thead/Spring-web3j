package com.company.project.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.company.project.configurer.HttpHelper;
import com.company.project.core.Result;
import com.company.project.core.ResultGenerator;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.EthBlockNumber;

/**
* Created by CodeGenerator on 2021/02/28.
*/
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    Web3j web3j;
    @Value("${graph.node}")
    private String graphNodeAddr;
    String GQL_BLOCK="{\"query\":\"\\n      {\\n        _meta {\\n          block {\\n            number\\n          }\\n        }\\n      }\\n    \"}";


    @GetMapping("/list")
    public Result list() throws ExecutionException, InterruptedException, IOException {
        EthBlockNumber ethBlockNumber= web3j.ethBlockNumber()
            .sendAsync()
            .get();

        String result = HttpHelper.post(graphNodeAddr,GQL_BLOCK);
        JSONObject jsonObject = JSON.parseObject(result);


        return ResultGenerator.genSuccessResult(jsonObject);
    }
}
