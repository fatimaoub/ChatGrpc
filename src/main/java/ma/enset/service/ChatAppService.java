package ma.enset.service;

import io.grpc.stub.StreamObserver;
import ma.enset.stubs.ChatServiceGrpc;
import ma.enset.stubs.GrpcModele;

import java.util.Scanner;

public class ChatAppService extends ChatServiceGrpc.ChatServiceImplBase  {
    Scanner scanner=new Scanner(System.in);
    @Override
    public StreamObserver<GrpcModele.TextingMessage> texting(StreamObserver<GrpcModele.TextingResponse> responseObserver) {
        return new StreamObserver<GrpcModele.TextingMessage>() {
            @Override
            public void onNext(GrpcModele.TextingMessage value) {
                System.out.println("====================");
                System.out.println("message from "+ value.getNom());
                System.out.println("Contained "+ value.getMessage());
                System.out.println("Write your response ");
                String rep=scanner.nextLine();
                System.out.println("====================");
                GrpcModele.TextingResponse response= GrpcModele.TextingResponse.newBuilder()
                        .setNom(value.getNom())
                        .setReponse(rep)
                        .build();
                responseObserver.onNext(response);
            }

            @Override
            public void onError(Throwable t) {

            }
            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }
}
