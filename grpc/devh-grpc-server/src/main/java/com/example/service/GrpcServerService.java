package com.example.service;

import com.example.grpclib.HelloReply;
import com.example.grpclib.HelloRequest;
import com.example.grpclib.SimpleGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

/**
 *
 * @author hudongshan
 */
@GrpcService
public class GrpcServerService extends SimpleGrpc.SimpleImplBase{

    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
        HelloReply reply = HelloReply.newBuilder().setMessage("Hello ==> " + request.getName()).build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
