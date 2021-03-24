package com.company.project.gen;

import io.reactivex.Flowable;
import io.reactivex.functions.Function;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.RemoteFunctionCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.BaseEventResponse;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.4.1.
 */
@SuppressWarnings("rawtypes")
public class Rewards extends Contract {
    public static final String BINARY = "0x60806040526000600755600060085534801561001a57600080fd5b506040516115ac3803806115ac8339818101604052606081101561003d57600080fd5b5080516020820151604090920151600080546001600160a01b0319166001600160a01b0384161781559192916100716100e9565b600380546001600160a01b0319166001600160a01b038316908117909155604051919250906000907f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e0908290a350600580546001600160a01b0319166001600160a01b039390931692909217909155600655506100ed565b3390565b6114b0806100fc6000396000f3fe608060405234801561001057600080fd5b506004361061018d5760003560e01c806380faa57d116100de578063c8f33c9111610097578063e9fad8ee11610071578063e9fad8ee14610381578063ebe2b12b14610389578063f2fde38b14610391578063f7c618c1146103b75761018d565b8063c8f33c9114610369578063cd3daf9d14610371578063df136d65146103795761018d565b806380faa57d146103065780638b8763471461030e5780638da58897146103345780638da5cb5b1461033c5780638f32d59b14610344578063a694fc3a1461034c5761018d565b80633c6b16ab1161014b5780635ae80ce8116101255780635ae80ce8146102a257806370a08231146102d0578063715018a6146102f65780637b0a47ee146102fe5761018d565b80633c6b16ab146102595780633d18b9121461027657806351ed6a301461027e5761018d565b80628cc262146101925780630700037d146101ca57806318160ddd146101f05780631be05289146101f85780632e1a7d4d1461020057806339ab3c701461021f575b600080fd5b6101b8600480360360208110156101a857600080fd5b50356001600160a01b03166103bf565b60408051918252519081900360200190f35b6101b8600480360360208110156101e057600080fd5b50356001600160a01b0316610445565b6101b8610457565b6101b861045e565b61021d6004803603602081101561021657600080fd5b5035610465565b005b6102456004803603602081101561023557600080fd5b50356001600160a01b03166105a4565b604080519115158252519081900360200190f35b61021d6004803603602081101561026f57600080fd5b50356105b9565b61021d610858565b610286610975565b604080516001600160a01b039092168252519081900360200190f35b61021d600480360360408110156102b857600080fd5b506001600160a01b0381351690602001351515610984565b6101b8600480360360208110156102e657600080fd5b50356001600160a01b0316610a08565b61021d610a23565b6101b8610ac6565b6101b8610acc565b6101b86004803603602081101561032457600080fd5b50356001600160a01b0316610adf565b6101b8610af1565b610286610af7565b610245610b06565b61021d6004803603602081101561036257600080fd5b5035610b2c565b6101b8610c6b565b6101b8610c71565b6101b8610cc5565b61021d610ccb565b6101b8610ce6565b61021d600480360360208110156103a757600080fd5b50356001600160a01b0316610cec565b610286610d51565b6001600160a01b0381166000908152600c6020908152604080832054600b90925282205461043f919061043390670de0b6b3a7640000906104279061041290610406610c71565b9063ffffffff610d6016565b61041b88610a08565b9063ffffffff610da916565b9063ffffffff610e0216565b9063ffffffff610e4416565b92915050565b600c6020526000908152604090205481565b6001545b90565b62093a8081565b3361046e610c71565b600a55610479610acc565b6009556001600160a01b038116156104c057610494816103bf565b6001600160a01b0382166000908152600c6020908152604080832093909355600a54600b909152919020555b60065442101561050c576040805162461bcd60e51b815260206004820152601260248201527114995dd85c991cce881b9bdd081cdd185c9d60721b604482015290519081900360640190fd5b60008211610561576040805162461bcd60e51b815260206004820152601a60248201527f526577617264733a2063616e6e6f742077697468647261772030000000000000604482015290519081900360640190fd5b61056a82610e9e565b60408051838152905133917f7084f5476618d8e60b11ef0d7d3f06914655adb8793e28ff7f018d4c76d505d5919081900360200190a25050565b60046020526000908152604090205460ff1681565b600460006105c5610eff565b6001600160a01b0316815260208101919091526040016000205460ff1661061d5760405162461bcd60e51b81526004018080602001828103825260218152602001806114316021913960400191505060405180910390fd5b6000610627610c71565b600a55610632610acc565b6009556001600160a01b038116156106795761064d816103bf565b6001600160a01b0382166000908152600c6020908152604080832093909355600a54600b909152919020555b600082116106c5576040805162461bcd60e51b81526020600482015260146024820152730526577617264733a20726577617264203d3d20360641b604482015290519081900360640190fd5b7812725dd1d243aba0e75fe645cc4873f9e65afe688c928e1f21821061071c5760405162461bcd60e51b81526004018080602001828103825260268152602001806113ea6026913960400191505060405180910390fd5b6006544211156107ec57600754421061074a576107428262093a8063ffffffff610e0216565b600855610798565b600754600090610760904263ffffffff610d6016565b9050600061077960085483610da990919063ffffffff16565b905061079262093a80610427868463ffffffff610e4416565b60085550505b4260098190556107b19062093a8063ffffffff610e4416565b6007556040805183815290517fde88a922e0d3b88b24e9623efeb464919c6bf9f66857a65e2bfcf2ce87a9433d9181900360200190a1610854565b6107ff8262093a8063ffffffff610e0216565b600855600654600981905561081d9062093a8063ffffffff610e4416565b6007556040805183815290517fde88a922e0d3b88b24e9623efeb464919c6bf9f66857a65e2bfcf2ce87a9433d9181900360200190a15b5050565b33610861610c71565b600a5561086c610acc565b6009556001600160a01b038116156108b357610887816103bf565b6001600160a01b0382166000908152600c6020908152604080832093909355600a54600b909152919020555b6006544210156108ff576040805162461bcd60e51b815260206004820152601260248201527114995dd85c991cce881b9bdd081cdd185c9d60721b604482015290519081900360640190fd5b600061090a336103bf565b9050801561085457336000818152600c602052604081205560055461093b916001600160a01b039091169083610f03565b60408051828152905133917fe2403640ba68fed3a2f88b7557551d1993f84b99bb10ff833f0cf8db0c5e0486919081900360200190a25050565b6000546001600160a01b031681565b61098c610b06565b6109dd576040805162461bcd60e51b815260206004820181905260248201527f4f776e61626c653a2063616c6c6572206973206e6f7420746865206f776e6572604482015290519081900360640190fd5b6001600160a01b03919091166000908152600460205260409020805460ff1916911515919091179055565b6001600160a01b031660009081526002602052604090205490565b610a2b610b06565b610a7c576040805162461bcd60e51b815260206004820181905260248201527f4f776e61626c653a2063616c6c6572206973206e6f7420746865206f776e6572604482015290519081900360640190fd5b6003546040516000916001600160a01b0316907f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e0908390a3600380546001600160a01b0319169055565b60085481565b6000610ada42600754610f5a565b905090565b600b6020526000908152604090205481565b60065481565b6003546001600160a01b031690565b6003546000906001600160a01b0316610b1d610eff565b6001600160a01b031614905090565b33610b35610c71565b600a55610b40610acc565b6009556001600160a01b03811615610b8757610b5b816103bf565b6001600160a01b0382166000908152600c6020908152604080832093909355600a54600b909152919020555b600654421015610bd3576040805162461bcd60e51b815260206004820152601260248201527114995dd85c991cce881b9bdd081cdd185c9d60721b604482015290519081900360640190fd5b60008211610c28576040805162461bcd60e51b815260206004820152601760248201527f526577617264733a2063616e6e6f74207374616b652030000000000000000000604482015290519081900360640190fd5b610c3182610f70565b60408051838152905133917f9e71bc8eea02a63969f509818f2dafb9254532904319f9dbda79b67bd34a5f3d919081900360200190a25050565b60095481565b6000610c7b610457565b610c885750600a5461045b565b610ada610cb6610c96610457565b610427670de0b6b3a764000061041b60085461041b600954610406610acc565b600a549063ffffffff610e4416565b600a5481565b610cdc610cd733610a08565b610465565b610ce4610858565b565b60075481565b610cf4610b06565b610d45576040805162461bcd60e51b815260206004820181905260248201527f4f776e61626c653a2063616c6c6572206973206e6f7420746865206f776e6572604482015290519081900360640190fd5b610d4e81610fd2565b50565b6005546001600160a01b031681565b6000610da283836040518060400160405280601e81526020017f536166654d6174683a207375627472616374696f6e206f766572666c6f770000815250611073565b9392505050565b600082610db85750600061043f565b82820282848281610dc557fe5b0414610da25760405162461bcd60e51b81526004018080602001828103825260218152602001806114106021913960400191505060405180910390fd5b6000610da283836040518060400160405280601a81526020017f536166654d6174683a206469766973696f6e206279207a65726f00000000000081525061110a565b600082820183811015610da2576040805162461bcd60e51b815260206004820152601b60248201527f536166654d6174683a206164646974696f6e206f766572666c6f770000000000604482015290519081900360640190fd5b600154610eb1908263ffffffff610d6016565b60015533600090815260026020526040902054610ed4908263ffffffff610d6016565b336000818152600260205260408120929092559054610d4e916001600160a01b039091169083610f03565b3390565b604080516001600160a01b038416602482015260448082018490528251808303909101815260649091019091526020810180516001600160e01b031663a9059cbb60e01b179052610f5590849061116f565b505050565b6000818310610f695781610da2565b5090919050565b600154610f83908263ffffffff610e4416565b60015533600090815260026020526040902054610fa6908263ffffffff610e4416565b336000818152600260205260408120929092559054610d4e916001600160a01b0390911690308461132d565b6001600160a01b0381166110175760405162461bcd60e51b81526004018080602001828103825260268152602001806113c46026913960400191505060405180910390fd5b6003546040516001600160a01b038084169216907f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e090600090a3600380546001600160a01b0319166001600160a01b0392909216919091179055565b600081848411156111025760405162461bcd60e51b81526004018080602001828103825283818151815260200191508051906020019080838360005b838110156110c75781810151838201526020016110af565b50505050905090810190601f1680156110f45780820380516001836020036101000a031916815260200191505b509250505060405180910390fd5b505050900390565b600081836111595760405162461bcd60e51b81526020600482018181528351602484015283519092839260449091019190850190808383600083156110c75781810151838201526020016110af565b50600083858161116557fe5b0495945050505050565b611181826001600160a01b0316611387565b6111d2576040805162461bcd60e51b815260206004820152601f60248201527f5361666545524332303a2063616c6c20746f206e6f6e2d636f6e747261637400604482015290519081900360640190fd5b60006060836001600160a01b0316836040518082805190602001908083835b602083106112105780518252601f1990920191602091820191016111f1565b6001836020036101000a0380198251168184511680821785525050505050509050019150506000604051808303816000865af19150503d8060008114611272576040519150601f19603f3d011682016040523d82523d6000602084013e611277565b606091505b5091509150816112ce576040805162461bcd60e51b815260206004820181905260248201527f5361666545524332303a206c6f772d6c6576656c2063616c6c206661696c6564604482015290519081900360640190fd5b805115611327578080602001905160208110156112ea57600080fd5b50516113275760405162461bcd60e51b815260040180806020018281038252602a815260200180611452602a913960400191505060405180910390fd5b50505050565b604080516001600160a01b0385811660248301528416604482015260648082018490528251808303909101815260849091019091526020810180516001600160e01b03166323b872dd60e01b17905261132790859061116f565b6000813f7fc5d2460186f7233c927e7db2dcc703c0e500b653ca82273b7bfad8045d85a4708181148015906113bb57508115155b94935050505056fe4f776e61626c653a206e6577206f776e657220697320746865207a65726f2061646472657373526577617264733a207265776172647320746f6f206c617267652c20776f756c64206c6f636b536166654d6174683a206d756c7469706c69636174696f6e206f766572666c6f7743616c6c6572206973206e6f742072657761726420646973747269627574696f6e5361666545524332303a204552433230206f7065726174696f6e20646964206e6f742073756363656564a265627a7a72315820d72d47dc7552f814677ffb8fa20fc146a21d2f617fd2175a2078f525c0bccef764736f6c63430005110032";

    public static final String FUNC_DURATION = "DURATION";

    public static final String FUNC_BALANCEOF = "balanceOf";

    public static final String FUNC_EARNED = "earned";

    public static final String FUNC_EXIT = "exit";

    public static final String FUNC_GETREWARD = "getReward";

    public static final String FUNC_ISOWNER = "isOwner";

    public static final String FUNC_ISREWARDDISTRIBUTION = "isRewardDistribution";

    public static final String FUNC_LASTTIMEREWARDAPPLICABLE = "lastTimeRewardApplicable";

    public static final String FUNC_LASTUPDATETIME = "lastUpdateTime";

    public static final String FUNC_NOTIFYREWARDAMOUNT = "notifyRewardAmount";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_PERIODFINISH = "periodFinish";

    public static final String FUNC_RENOUNCEOWNERSHIP = "renounceOwnership";

    public static final String FUNC_REWARDPERTOKEN = "rewardPerToken";

    public static final String FUNC_REWARDPERTOKENSTORED = "rewardPerTokenStored";

    public static final String FUNC_REWARDRATE = "rewardRate";

    public static final String FUNC_REWARDTOKEN = "rewardToken";

    public static final String FUNC_REWARDS = "rewards";

    public static final String FUNC_SETREWARDDISTRIBUTION = "setRewardDistribution";

    public static final String FUNC_STAKE = "stake";

    public static final String FUNC_STAKETOKEN = "stakeToken";

    public static final String FUNC_STARTTIME = "starttime";

    public static final String FUNC_TOTALSUPPLY = "totalSupply";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final String FUNC_USERREWARDPERTOKENPAID = "userRewardPerTokenPaid";

    public static final String FUNC_WITHDRAW = "withdraw";

    public static final Event OWNERSHIPTRANSFERRED_EVENT = new Event("OwnershipTransferred", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event REWARDADDED_EVENT = new Event("RewardAdded", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
    ;

    public static final Event REWARDPAID_EVENT = new Event("RewardPaid", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event STAKED_EVENT = new Event("Staked", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event WITHDRAWN_EVENT = new Event("Withdrawn", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<String, String>();
    }

    @Deprecated
    protected Rewards(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Rewards(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Rewards(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Rewards(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<OwnershipTransferredEventResponse> getOwnershipTransferredEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, transactionReceipt);
        ArrayList<OwnershipTransferredEventResponse> responses = new ArrayList<OwnershipTransferredEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, OwnershipTransferredEventResponse>() {
            @Override
            public OwnershipTransferredEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(OWNERSHIPTRANSFERRED_EVENT, log);
                OwnershipTransferredEventResponse typedResponse = new OwnershipTransferredEventResponse();
                typedResponse.log = log;
                typedResponse.previousOwner = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.newOwner = (String) eventValues.getIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<OwnershipTransferredEventResponse> ownershipTransferredEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(OWNERSHIPTRANSFERRED_EVENT));
        return ownershipTransferredEventFlowable(filter);
    }

    public List<RewardAddedEventResponse> getRewardAddedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(REWARDADDED_EVENT, transactionReceipt);
        ArrayList<RewardAddedEventResponse> responses = new ArrayList<RewardAddedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            RewardAddedEventResponse typedResponse = new RewardAddedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.reward = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<RewardAddedEventResponse> rewardAddedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, RewardAddedEventResponse>() {
            @Override
            public RewardAddedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(REWARDADDED_EVENT, log);
                RewardAddedEventResponse typedResponse = new RewardAddedEventResponse();
                typedResponse.log = log;
                typedResponse.reward = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<RewardAddedEventResponse> rewardAddedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(REWARDADDED_EVENT));
        return rewardAddedEventFlowable(filter);
    }

    public List<RewardPaidEventResponse> getRewardPaidEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(REWARDPAID_EVENT, transactionReceipt);
        ArrayList<RewardPaidEventResponse> responses = new ArrayList<RewardPaidEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            RewardPaidEventResponse typedResponse = new RewardPaidEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.user = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.reward = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<RewardPaidEventResponse> rewardPaidEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, RewardPaidEventResponse>() {
            @Override
            public RewardPaidEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(REWARDPAID_EVENT, log);
                RewardPaidEventResponse typedResponse = new RewardPaidEventResponse();
                typedResponse.log = log;
                typedResponse.user = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.reward = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<RewardPaidEventResponse> rewardPaidEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(REWARDPAID_EVENT));
        return rewardPaidEventFlowable(filter);
    }

    public List<StakedEventResponse> getStakedEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(STAKED_EVENT, transactionReceipt);
        ArrayList<StakedEventResponse> responses = new ArrayList<StakedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            StakedEventResponse typedResponse = new StakedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.user = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<StakedEventResponse> stakedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, StakedEventResponse>() {
            @Override
            public StakedEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(STAKED_EVENT, log);
                StakedEventResponse typedResponse = new StakedEventResponse();
                typedResponse.log = log;
                typedResponse.user = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<StakedEventResponse> stakedEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(STAKED_EVENT));
        return stakedEventFlowable(filter);
    }

    public List<WithdrawnEventResponse> getWithdrawnEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(WITHDRAWN_EVENT, transactionReceipt);
        ArrayList<WithdrawnEventResponse> responses = new ArrayList<WithdrawnEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            WithdrawnEventResponse typedResponse = new WithdrawnEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.user = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<WithdrawnEventResponse> withdrawnEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, WithdrawnEventResponse>() {
            @Override
            public WithdrawnEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(WITHDRAWN_EVENT, log);
                WithdrawnEventResponse typedResponse = new WithdrawnEventResponse();
                typedResponse.log = log;
                typedResponse.user = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<WithdrawnEventResponse> withdrawnEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(WITHDRAWN_EVENT));
        return withdrawnEventFlowable(filter);
    }

    public RemoteFunctionCall<BigInteger> DURATION() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_DURATION, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> balanceOf(String account) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_BALANCEOF, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(account)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> earned(String account) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_EARNED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(account)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> exit() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_EXIT, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> getReward() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_GETREWARD, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> isOwner() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ISOWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<Boolean> isRewardDistribution(String param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_ISREWARDDISTRIBUTION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<BigInteger> lastTimeRewardApplicable() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_LASTTIMEREWARDAPPLICABLE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> lastUpdateTime() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_LASTUPDATETIME, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> notifyRewardAmount(BigInteger reward) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_NOTIFYREWARDAMOUNT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(reward)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> owner() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> periodFinish() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_PERIODFINISH, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> renounceOwnership() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_RENOUNCEOWNERSHIP, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> rewardPerToken() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_REWARDPERTOKEN, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> rewardPerTokenStored() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_REWARDPERTOKENSTORED, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> rewardRate() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_REWARDRATE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<String> rewardToken() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_REWARDTOKEN, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> rewards(String param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_REWARDS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> setRewardDistribution(String _rewardDistribution, Boolean _isRewardDistribution) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETREWARDDISTRIBUTION, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_rewardDistribution), 
                new org.web3j.abi.datatypes.Bool(_isRewardDistribution)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> stake(BigInteger amount) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_STAKE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> stakeToken() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_STAKETOKEN, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<BigInteger> starttime() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_STARTTIME, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<BigInteger> totalSupply() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_TOTALSUPPLY, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> transferOwnership(String newOwner) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_TRANSFEROWNERSHIP, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(newOwner)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<BigInteger> userRewardPerTokenPaid(String param0) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_USERREWARDPERTOKENPAID, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(param0)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<TransactionReceipt> withdraw(BigInteger amount) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_WITHDRAW, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static Rewards load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Rewards(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Rewards load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Rewards(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Rewards load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Rewards(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Rewards load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Rewards(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Rewards> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _stakeToken, String _rewardToken, BigInteger _starttime) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_stakeToken), 
                new org.web3j.abi.datatypes.Address(_rewardToken), 
                new org.web3j.abi.datatypes.generated.Uint256(_starttime)));
        return deployRemoteCall(Rewards.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<Rewards> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _stakeToken, String _rewardToken, BigInteger _starttime) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_stakeToken), 
                new org.web3j.abi.datatypes.Address(_rewardToken), 
                new org.web3j.abi.datatypes.generated.Uint256(_starttime)));
        return deployRemoteCall(Rewards.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Rewards> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _stakeToken, String _rewardToken, BigInteger _starttime) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_stakeToken), 
                new org.web3j.abi.datatypes.Address(_rewardToken), 
                new org.web3j.abi.datatypes.generated.Uint256(_starttime)));
        return deployRemoteCall(Rewards.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Rewards> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _stakeToken, String _rewardToken, BigInteger _starttime) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_stakeToken), 
                new org.web3j.abi.datatypes.Address(_rewardToken), 
                new org.web3j.abi.datatypes.generated.Uint256(_starttime)));
        return deployRemoteCall(Rewards.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static class OwnershipTransferredEventResponse extends BaseEventResponse {
        public String previousOwner;

        public String newOwner;
    }

    public static class RewardAddedEventResponse extends BaseEventResponse {
        public BigInteger reward;
    }

    public static class RewardPaidEventResponse extends BaseEventResponse {
        public String user;

        public BigInteger reward;
    }

    public static class StakedEventResponse extends BaseEventResponse {
        public String user;

        public BigInteger amount;
    }

    public static class WithdrawnEventResponse extends BaseEventResponse {
        public String user;

        public BigInteger amount;
    }
}
