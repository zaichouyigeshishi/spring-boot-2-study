package com.example.service;

import com.example.grpclib.HelloReply;
import com.example.grpclib.HelloRequest;
import com.example.grpclib.SimpleGrpc;
import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

/**
 * @author hudongshan
 */
@Service
public class GrpcClientService {

    @GrpcClient("my-grpc-server")
    private SimpleGrpc.SimpleBlockingStub simpleStub;

    public String sendMessage(final String name) {
        try {

            final HelloReply response = this.simpleStub.sayHello(HelloRequest.newBuilder().setName(name).build());

            return response.getMessage();
        } catch (final StatusRuntimeException e) {
            return "FAILED with " + e.getStatus().getCode().name();
        }
    }
}
