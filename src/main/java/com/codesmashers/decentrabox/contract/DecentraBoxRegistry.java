package com.codesmashers.decentrabox.contract;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Bool;
import org.web3j.abi.datatypes.DynamicArray;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.StaticStruct;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
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
 * <a href="https://github.com/LFDT-web3j/web3j/tree/main/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 1.8.0.
 */
@SuppressWarnings("rawtypes")
@Generated("org.web3j.codegen.SolidityFunctionWrapperGenerator")
public class DecentraBoxRegistry extends Contract {
    public static final String BINARY = "Bin file was not provided";

    public static final String FUNC_GRANTACCESS = "grantAccess";

    public static final String FUNC_RECORDACCESS = "recordAccess";

    public static final String FUNC_REGISTERFILE = "registerFile";

    public static final String FUNC_REVOKEACCESS = "revokeAccess";

    public static final String FUNC_TRANSFEROWNERSHIP = "transferOwnership";

    public static final String FUNC_CANACCESS = "canAccess";

    public static final String FUNC_GETACCESSCOUNT = "getAccessCount";

    public static final String FUNC_GETACCESSGRANTS = "getAccessGrants";

    public static final String FUNC_GETFILEOWNER = "getFileOwner";

    public static final String FUNC_ISFILEREGISTERED = "isFileRegistered";

    public static final Event ACCESSGRANTED_EVENT = new Event("AccessGranted", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}));
    ;

    public static final Event ACCESSREVOKED_EVENT = new Event("AccessRevoked", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Address>(true) {}));
    ;

    public static final Event FILEEVENT_EVENT = new Event("FileEvent", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>(true) {}, new TypeReference<Address>(true) {}, new TypeReference<Utf8String>() {}, new TypeReference<Uint256>() {}));
    ;

    @Deprecated
    protected DecentraBoxRegistry(String contractAddress, Web3j web3j, Credentials credentials,
            BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected DecentraBoxRegistry(String contractAddress, Web3j web3j, Credentials credentials,
            ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected DecentraBoxRegistry(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected DecentraBoxRegistry(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static List<AccessGrantedEventResponse> getAccessGrantedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(ACCESSGRANTED_EVENT, transactionReceipt);
        ArrayList<AccessGrantedEventResponse> responses = new ArrayList<AccessGrantedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            AccessGrantedEventResponse typedResponse = new AccessGrantedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.cid = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.owner = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.grantedTo = (String) eventValues.getIndexedValues().get(2).getValue();
            typedResponse.expiresAt = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static AccessGrantedEventResponse getAccessGrantedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(ACCESSGRANTED_EVENT, log);
        AccessGrantedEventResponse typedResponse = new AccessGrantedEventResponse();
        typedResponse.log = log;
        typedResponse.cid = (byte[]) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.owner = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.grantedTo = (String) eventValues.getIndexedValues().get(2).getValue();
        typedResponse.expiresAt = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
        return typedResponse;
    }

    public Flowable<AccessGrantedEventResponse> accessGrantedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getAccessGrantedEventFromLog(log));
    }

    public Flowable<AccessGrantedEventResponse> accessGrantedEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ACCESSGRANTED_EVENT));
        return accessGrantedEventFlowable(filter);
    }

    public static List<AccessRevokedEventResponse> getAccessRevokedEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(ACCESSREVOKED_EVENT, transactionReceipt);
        ArrayList<AccessRevokedEventResponse> responses = new ArrayList<AccessRevokedEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            AccessRevokedEventResponse typedResponse = new AccessRevokedEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.cid = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.owner = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.revokedFrom = (String) eventValues.getIndexedValues().get(2).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static AccessRevokedEventResponse getAccessRevokedEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(ACCESSREVOKED_EVENT, log);
        AccessRevokedEventResponse typedResponse = new AccessRevokedEventResponse();
        typedResponse.log = log;
        typedResponse.cid = (byte[]) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.owner = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.revokedFrom = (String) eventValues.getIndexedValues().get(2).getValue();
        return typedResponse;
    }

    public Flowable<AccessRevokedEventResponse> accessRevokedEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getAccessRevokedEventFromLog(log));
    }

    public Flowable<AccessRevokedEventResponse> accessRevokedEventFlowable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(ACCESSREVOKED_EVENT));
        return accessRevokedEventFlowable(filter);
    }

    public static List<FileEventEventResponse> getFileEventEvents(
            TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = staticExtractEventParametersWithLog(FILEEVENT_EVENT, transactionReceipt);
        ArrayList<FileEventEventResponse> responses = new ArrayList<FileEventEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            FileEventEventResponse typedResponse = new FileEventEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.cid = (byte[]) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.actor = (String) eventValues.getIndexedValues().get(1).getValue();
            typedResponse.action = (String) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.timestamp = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public static FileEventEventResponse getFileEventEventFromLog(Log log) {
        Contract.EventValuesWithLog eventValues = staticExtractEventParametersWithLog(FILEEVENT_EVENT, log);
        FileEventEventResponse typedResponse = new FileEventEventResponse();
        typedResponse.log = log;
        typedResponse.cid = (byte[]) eventValues.getIndexedValues().get(0).getValue();
        typedResponse.actor = (String) eventValues.getIndexedValues().get(1).getValue();
        typedResponse.action = (String) eventValues.getNonIndexedValues().get(0).getValue();
        typedResponse.timestamp = (BigInteger) eventValues.getNonIndexedValues().get(1).getValue();
        return typedResponse;
    }

    public Flowable<FileEventEventResponse> fileEventEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(log -> getFileEventEventFromLog(log));
    }

    public Flowable<FileEventEventResponse> fileEventEventFlowable(DefaultBlockParameter startBlock,
            DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(FILEEVENT_EVENT));
        return fileEventEventFlowable(filter);
    }

    public RemoteFunctionCall<TransactionReceipt> grantAccess(String cid, String to,
            BigInteger expiryDays) {
        final Function function = new Function(
                FUNC_GRANTACCESS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(cid), 
                new org.web3j.abi.datatypes.Address(160, to), 
                new org.web3j.abi.datatypes.generated.Uint256(expiryDays)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> recordAccess(String cid, String accessor) {
        final Function function = new Function(
                FUNC_RECORDACCESS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(cid), 
                new org.web3j.abi.datatypes.Address(160, accessor)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> registerFile(String cid, String owner) {
        final Function function = new Function(
                FUNC_REGISTERFILE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(cid), 
                new org.web3j.abi.datatypes.Address(160, owner)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> revokeAccess(String cid, String from) {
        final Function function = new Function(
                FUNC_REVOKEACCESS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(cid), 
                new org.web3j.abi.datatypes.Address(160, from)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<TransactionReceipt> transferOwnership(String cid, String newOwner) {
        final Function function = new Function(
                FUNC_TRANSFEROWNERSHIP, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(cid), 
                new org.web3j.abi.datatypes.Address(160, newOwner)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteFunctionCall<Boolean> canAccess(String cid, String who) {
        final Function function = new Function(FUNC_CANACCESS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(cid), 
                new org.web3j.abi.datatypes.Address(160, who)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    public RemoteFunctionCall<BigInteger> getAccessCount(String cid) {
        final Function function = new Function(FUNC_GETACCESSCOUNT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(cid)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteFunctionCall<List> getAccessGrants(String cid) {
        final Function function = new Function(FUNC_GETACCESSGRANTS, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(cid)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<DynamicArray<AccessGrant>>() {}));
        return new RemoteFunctionCall<List>(function,
                new Callable<List>() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public List call() throws Exception {
                        List<Type> result = (List<Type>) executeCallSingleValueReturn(function, List.class);
                        return convertToNative(result);
                    }
                });
    }

    public RemoteFunctionCall<String> getFileOwner(String cid) {
        final Function function = new Function(FUNC_GETFILEOWNER, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(cid)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteFunctionCall<Boolean> isFileRegistered(String cid) {
        final Function function = new Function(FUNC_ISFILEREGISTERED, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(cid)), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Bool>() {}));
        return executeRemoteCallSingleValueReturn(function, Boolean.class);
    }

    @Deprecated
    public static DecentraBoxRegistry load(String contractAddress, Web3j web3j,
            Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new DecentraBoxRegistry(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static DecentraBoxRegistry load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new DecentraBoxRegistry(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static DecentraBoxRegistry load(String contractAddress, Web3j web3j,
            Credentials credentials, ContractGasProvider contractGasProvider) {
        return new DecentraBoxRegistry(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static DecentraBoxRegistry load(String contractAddress, Web3j web3j,
            TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new DecentraBoxRegistry(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static class AccessGrant extends StaticStruct {
        public String grantedTo;

        public BigInteger grantedAt;

        public BigInteger expiresAt;

        public Boolean active;

        public AccessGrant(String grantedTo, BigInteger grantedAt, BigInteger expiresAt,
                Boolean active) {
            super(new org.web3j.abi.datatypes.Address(160, grantedTo), 
                    new org.web3j.abi.datatypes.generated.Uint256(grantedAt), 
                    new org.web3j.abi.datatypes.generated.Uint256(expiresAt), 
                    new org.web3j.abi.datatypes.Bool(active));
            this.grantedTo = grantedTo;
            this.grantedAt = grantedAt;
            this.expiresAt = expiresAt;
            this.active = active;
        }

        public AccessGrant(Address grantedTo, Uint256 grantedAt, Uint256 expiresAt, Bool active) {
            super(grantedTo, grantedAt, expiresAt, active);
            this.grantedTo = grantedTo.getValue();
            this.grantedAt = grantedAt.getValue();
            this.expiresAt = expiresAt.getValue();
            this.active = active.getValue();
        }
    }

    public static class AccessGrantedEventResponse extends BaseEventResponse {
        public byte[] cid;

        public String owner;

        public String grantedTo;

        public BigInteger expiresAt;
    }

    public static class AccessRevokedEventResponse extends BaseEventResponse {
        public byte[] cid;

        public String owner;

        public String revokedFrom;
    }

    public static class FileEventEventResponse extends BaseEventResponse {
        public byte[] cid;

        public String actor;

        public String action;

        public BigInteger timestamp;
    }
}
