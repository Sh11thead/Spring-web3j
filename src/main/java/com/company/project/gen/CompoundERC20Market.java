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
import org.web3j.abi.datatypes.Utf8String;
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
public class CompoundERC20Market extends Contract {
    public static final String BINARY = "0x60806040523480156200001157600080fd5b506040516200140238038062001402833981810160405260808110156200003757600080fd5b50805160208201516040830151606090930151919290916000620000636001600160e01b03620001d216565b600080546001600160a01b0319166001600160a01b0383169081178255604051929350917f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e0908290a350620000cc846001600160a01b0316620001d660201b62000c1f1760201c565b8015620000f35750620000f3836001600160a01b0316620001d660201b62000c1f1760201c565b80156200011a57506200011a826001600160a01b0316620001d660201b62000c1f1760201c565b801562000141575062000141816001600160a01b0316620001d660201b62000c1f1760201c565b6200017e5760405162461bcd60e51b8152600401808060200182810382526037815260200180620013cb6037913960400191505060405180910390fd5b600180546001600160a01b039586166001600160a01b031991821617909155600280549486169482169490941790935560038054928516928416929092179091556004805491909316911617905562000213565b3390565b6000813f7fc5d2460186f7233c927e7db2dcc703c0e500b653ca82273b7bfad8045d85a4708181148015906200020b57508115155b949350505050565b6111a880620002236000396000f3fe608060405234801561001057600080fd5b50600436106100ea5760003560e01c80638f32d59b1161008c578063d4c3eea011610066578063d4c3eea0146101ad578063e9cbd822146101b5578063ec38a862146101bd578063f2fde38b146101e3576100ea565b80638f32d59b1461016c5780639ec5a89414610188578063b6b55f2514610190576100ea565b80635fe3b567116100c85780635fe3b5671461013057806369e527da14610154578063715018a61461015c5780638da5cb5b14610164576100ea565b806321c26120146100ef5780632e1a7d4d14610109578063372500ab14610126575b600080fd5b6100f7610209565b60408051918252519081900360200190f35b6100f76004803603602081101561011f57600080fd5b5035610280565b61012e6103de565b005b610138610558565b604080516001600160a01b039092168252519081900360200190f35b610138610567565b61012e610576565b610138610607565b610174610616565b604080519115158252519081900360200190f35b61013861063a565b61012e600480360360208110156101a657600080fd5b5035610649565b6100f76107db565b6101386108e8565b61012e600480360360208110156101d357600080fd5b50356001600160a01b03166108f7565b61012e600480360360208110156101f957600080fd5b50356001600160a01b03166109fb565b6001546040805163bd6d894d60e01b815290516000926001600160a01b03169163bd6d894d91600480830192602092919082900301818787803b15801561024f57600080fd5b505af1158015610263573d6000803e3d6000fd5b505050506040513d602081101561027957600080fd5b5051905090565b600061028a610616565b6102c9576040805162461bcd60e51b8152602060048201819052602482015260008051602061112a833981519152604482015290519081900360640190fd5b600082116103085760405162461bcd60e51b815260040180806020018281038252602c815260200180611091602c913960400191505060405180910390fd5b6001546040805163852a12e360e01b81526004810185905290516000926001600160a01b03169163852a12e391602480830192602092919082900301818787803b15801561035557600080fd5b505af1158015610369573d6000803e3d6000fd5b505050506040513d602081101561037f57600080fd5b5051146103bd5760405162461bcd60e51b815260040180806020018281038252602581526020018061106c6025913960400191505060405180910390fd5b6004546103da906001600160a01b0316338463ffffffff610a4b16565b5090565b600254604080516374d7814960e11b815230600482015290516001600160a01b039092169163e9af02929160248082019260009290919082900301818387803b15801561042a57600080fd5b505af115801561043e573d6000803e3d6000fd5b505050506000600260009054906101000a90046001600160a01b03166001600160a01b0316639d1b5a0a6040518163ffffffff1660e01b815260040160206040518083038186803b15801561049257600080fd5b505afa1580156104a6573d6000803e3d6000fd5b505050506040513d60208110156104bc57600080fd5b5051600354604080516370a0823160e01b81523060048201529051929350610555926001600160a01b03928316928516916370a08231916024808301926020929190829003018186803b15801561051257600080fd5b505afa158015610526573d6000803e3d6000fd5b505050506040513d602081101561053c57600080fd5b50516001600160a01b038416919063ffffffff610a4b16565b50565b6002546001600160a01b031681565b6001546001600160a01b031681565b61057e610616565b6105bd576040805162461bcd60e51b8152602060048201819052602482015260008051602061112a833981519152604482015290519081900360640190fd5b600080546040516001600160a01b03909116907f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e0908390a3600080546001600160a01b0319169055565b6000546001600160a01b031690565b600080546001600160a01b031661062b610aa2565b6001600160a01b031614905090565b6003546001600160a01b031681565b610651610616565b610690576040805162461bcd60e51b8152602060048201819052602482015260008051602061112a833981519152604482015290519081900360640190fd5b600081116106e5576040805162461bcd60e51b815260206004820181905260248201527f436f6d706f756e6445524332304d61726b65743a20616d6f756e742069732030604482015290519081900360640190fd5b600454610703906001600160a01b031633308463ffffffff610aa616565b600154600454610726916001600160a01b0391821691168363ffffffff610b0616565b6001546040805163140e25ad60e31b81526004810184905290516000926001600160a01b03169163a0712d6891602480830192602092919082900301818787803b15801561077357600080fd5b505af1158015610787573d6000803e3d6000fd5b505050506040513d602081101561079d57600080fd5b5051146105555760405162461bcd60e51b815260040180806020018281038252602b8152602001806110bd602b913960400191505060405180910390fd5b600154604080516370a0823160e01b8152306004820152905160009283926001600160a01b03909116916370a0823191602480820192602092909190829003018186803b15801561082b57600080fd5b505afa15801561083f573d6000803e3d6000fd5b505050506040513d602081101561085557600080fd5b50516001546040805163bd6d894d60e01b815290519293506000926001600160a01b039092169163bd6d894d9160048082019260209290919082900301818787803b1580156108a357600080fd5b505af11580156108b7573d6000803e3d6000fd5b505050506040513d60208110156108cd57600080fd5b505190506108e1828263ffffffff610bec16565b9250505090565b6004546001600160a01b031681565b6108ff610616565b61093e576040805162461bcd60e51b8152602060048201819052602482015260008051602061112a833981519152604482015290519081900360640190fd5b610950816001600160a01b0316610c1f565b61098b5760405162461bcd60e51b81526004018080602001828103825260218152602001806111096021913960400191505060405180910390fd5b600380546001600160a01b0383166001600160a01b0319909116811790915560408051667265776172647360c81b81528151908190036007018120928152905133917f64b03eb8356730cffd396927eec0e9b1e0599498960e022df3dae35791c17cf5919081900360200190a350565b610a03610616565b610a42576040805162461bcd60e51b8152602060048201819052602482015260008051602061112a833981519152604482015290519081900360640190fd5b61055581610c5b565b604080516001600160a01b038416602482015260448082018490528251808303909101815260649091019091526020810180516001600160e01b031663a9059cbb60e01b179052610a9d908490610cfb565b505050565b3390565b604080516001600160a01b0385811660248301528416604482015260648082018490528251808303909101815260849091019091526020810180516001600160e01b03166323b872dd60e01b179052610b00908590610cfb565b50505050565b60408051636eb1769f60e11b81523060048201526001600160a01b0384811660248301529151600092610b979285929188169163dd62ed3e91604480820192602092909190829003018186803b158015610b5f57600080fd5b505afa158015610b73573d6000803e3d6000fd5b505050506040513d6020811015610b8957600080fd5b50519063ffffffff610eb316565b604080516001600160a01b038616602482015260448082018490528251808303909101815260649091019091526020810180516001600160e01b031663095ea7b360e01b179052909150610b00908590610cfb565b6000610c16670de0b6b3a7640000610c0a858563ffffffff610f0d16565b9063ffffffff610f6616565b90505b92915050565b6000813f7fc5d2460186f7233c927e7db2dcc703c0e500b653ca82273b7bfad8045d85a470818114801590610c5357508115155b949350505050565b6001600160a01b038116610ca05760405162461bcd60e51b81526004018080602001828103825260268152602001806110466026913960400191505060405180910390fd5b600080546040516001600160a01b03808516939216917f8be0079c531659141344cd1fd0a4f28419497f9722a3daafe3b4186f6b6457e091a3600080546001600160a01b0319166001600160a01b0392909216919091179055565b610d0d826001600160a01b0316610c1f565b610d5e576040805162461bcd60e51b815260206004820152601f60248201527f5361666545524332303a2063616c6c20746f206e6f6e2d636f6e747261637400604482015290519081900360640190fd5b60006060836001600160a01b0316836040518082805190602001908083835b60208310610d9c5780518252601f199092019160209182019101610d7d565b6001836020036101000a0380198251168184511680821785525050505050509050019150506000604051808303816000865af19150503d8060008114610dfe576040519150601f19603f3d011682016040523d82523d6000602084013e610e03565b606091505b509150915081610e5a576040805162461bcd60e51b815260206004820181905260248201527f5361666545524332303a206c6f772d6c6576656c2063616c6c206661696c6564604482015290519081900360640190fd5b805115610b0057808060200190516020811015610e7657600080fd5b5051610b005760405162461bcd60e51b815260040180806020018281038252602a81526020018061114a602a913960400191505060405180910390fd5b600082820183811015610c16576040805162461bcd60e51b815260206004820152601b60248201527f536166654d6174683a206164646974696f6e206f766572666c6f770000000000604482015290519081900360640190fd5b600082610f1c57506000610c19565b82820282848281610f2957fe5b0414610c165760405162461bcd60e51b81526004018080602001828103825260218152602001806110e86021913960400191505060405180910390fd5b6000610c1683836040518060400160405280601a81526020017f536166654d6174683a206469766973696f6e206279207a65726f0000000000008152506000818361102f5760405162461bcd60e51b81526004018080602001828103825283818151815260200191508051906020019080838360005b83811015610ff4578181015183820152602001610fdc565b50505050905090810190601f1680156110215780820380516001836020036101000a031916815260200191505b509250505060405180910390fd5b50600083858161103b57fe5b049594505050505056fe4f776e61626c653a206e6577206f776e657220697320746865207a65726f2061646472657373436f6d706f756e6445524332304d61726b65743a204661696c656420746f2072656465656d436f6d706f756e6445524332304d61726b65743a20616d6f756e74496e556e6465726c79696e672069732030436f6d706f756e6445524332304d61726b65743a204661696c656420746f206d696e742063546f6b656e73536166654d6174683a206d756c7469706c69636174696f6e206f766572666c6f77436f6d706f756e6445524332304d61726b65743a206e6f7420636f6e74726163744f776e61626c653a2063616c6c6572206973206e6f7420746865206f776e65725361666545524332303a204552433230206f7065726174696f6e20646964206e6f742073756363656564a265627a7a723158209158b8df7b2a5aaba3a17b9233f1f5aeada4b3cb77fee6911739b2043a5cf55664736f6c63430005110032436f6d706f756e6445524332304d61726b65743a20416e20696e7075742061646472657373206973206e6f74206120636f6e7472616374";

    public static final String FUNC_CTOKEN = "cToken";

    public static final String FUNC_CLAIMREWARDS = "claimRewards";

    public static final String FUNC_COMPTROLLER = "comptroller";

    public static final String FUNC_DEPOSIT = "deposit";

    public static final String FUNC_INCOMEINDEX = "incomeIndex";

    public static final String FUNC_ISOWNER = "isOwner";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_RENOUNCEOWNERSHIP = "renounceOwnership";

    public static final String FUNC_REWARDS = "rewards";

    public static final String FUNC_SETREWARDS = "setRewards";

    public static final String FUNC_STABLECOIN = "stablecoin";

    public static final String FUNC_TOTALVALUE = "totalValue";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final String FUNC_WITHDRAW = "withdraw";

    public static final Event ESETPARAMADDRESS_EVENT = new Event("ESetParamAddress", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Utf8String>(true) {}, new TypeReference<Address>() {}));
    ;

    public static final Event OWNERSHIPTRANSFERRED_EVENT = new Event("OwnershipTransferred", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    protected static final HashMap<String, String> _addresses;

    static {
        _addresses = new HashMap<String, String>();
    }

    @Deprecated
    protected CompoundERC20Market(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected CompoundERC20Market(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected CompoundERC20Market(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected CompoundERC20Market(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public List<ESetParamAddressEventResponse> getESetParamAddressEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(ESETPARAMADDRESS_EVENT, transactionReceipt);
        ArrayList<ESetParamAddressEventResponse> responses = new ArrayList<ESetParamAddressEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ESetParamAddressEventResponse typedResponse = new ESetParamAddressEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.sender = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.paramName = (byte[]) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.newValue = (String) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<ESetParamAddressEventResponse> eSetParamAddressEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new Function<Log, ESetParamAddressEventResponse>() {
            @Override
            public ESetParamAddressEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(ESETPARAMADDRESS_EVENT, log);
                ESetParamAddressEventResponse typedResponse = new ESetParamAddressEventResponse();
                typedResponse.log = log;
                typedResponse.sender = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.paramName = (byte[]) eventValues.getIndexedValues().get(1).getValue();
                typedResponse.newValue = (String) eventValues.getNonIndexedValues().get(0).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<ESetParamAddressEventResponse> eSetParamAddressEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ESETPARAMADDRESS_EVENT));
        return eSetParamAddressEventFlowable(filter);
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

    public RemoteFunctionCall<String> cToken() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_CTOKEN, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> claimRewards() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_CLAIMREWARDS, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> comptroller() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_COMPTROLLER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> deposit(BigInteger amount) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_DEPOSIT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(amount)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> incomeIndex() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_INCOMEINDEX, 
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

    public RemoteFunctionCall<String> owner() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> renounceOwnership() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_RENOUNCEOWNERSHIP, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> rewards() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_REWARDS, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> setRewards(String newValue) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_SETREWARDS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(newValue)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<String> stablecoin() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(FUNC_STABLECOIN, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<TransactionReceipt> totalValue() {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_TOTALVALUE, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> transferOwnership(String newOwner) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_TRANSFEROWNERSHIP, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(newOwner)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> withdraw(BigInteger amountInUnderlying) {
        final org.web3j.abi.datatypes.Function function = new org.web3j.abi.datatypes.Function(
                FUNC_WITHDRAW, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(amountInUnderlying)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    @Deprecated
    public static CompoundERC20Market load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new CompoundERC20Market(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static CompoundERC20Market load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new CompoundERC20Market(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static CompoundERC20Market load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new CompoundERC20Market(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static CompoundERC20Market load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new CompoundERC20Market(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<CompoundERC20Market> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String _cToken, String _comptroller, String _rewards, String _stablecoin) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_cToken), 
                new org.web3j.abi.datatypes.Address(_comptroller), 
                new org.web3j.abi.datatypes.Address(_rewards), 
                new org.web3j.abi.datatypes.Address(_stablecoin)));
        return deployRemoteCall(CompoundERC20Market.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<CompoundERC20Market> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String _cToken, String _comptroller, String _rewards, String _stablecoin) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_cToken), 
                new org.web3j.abi.datatypes.Address(_comptroller), 
                new org.web3j.abi.datatypes.Address(_rewards), 
                new org.web3j.abi.datatypes.Address(_stablecoin)));
        return deployRemoteCall(CompoundERC20Market.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<CompoundERC20Market> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String _cToken, String _comptroller, String _rewards, String _stablecoin) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_cToken), 
                new org.web3j.abi.datatypes.Address(_comptroller), 
                new org.web3j.abi.datatypes.Address(_rewards), 
                new org.web3j.abi.datatypes.Address(_stablecoin)));
        return deployRemoteCall(CompoundERC20Market.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<CompoundERC20Market> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String _cToken, String _comptroller, String _rewards, String _stablecoin) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Address(_cToken), 
                new org.web3j.abi.datatypes.Address(_comptroller), 
                new org.web3j.abi.datatypes.Address(_rewards), 
                new org.web3j.abi.datatypes.Address(_stablecoin)));
        return deployRemoteCall(CompoundERC20Market.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    protected String getStaticDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static String getPreviouslyDeployedAddress(String networkId) {
        return _addresses.get(networkId);
    }

    public static class ESetParamAddressEventResponse extends BaseEventResponse {
        public String sender;

        public byte[] paramName;

        public String newValue;
    }

    public static class OwnershipTransferredEventResponse extends BaseEventResponse {
        public String previousOwner;

        public String newOwner;
    }
}
