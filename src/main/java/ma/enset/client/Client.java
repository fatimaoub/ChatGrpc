package ma.enset.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import javafx.application.Application;
import javafx.stage.Stage;
import ma.enset.stubs.ChatServiceGrpc;
import ma.enset.stubs.GrpcModele;

import java.io.IOException;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Client extends Application {
    public static void main(String[] args) throws IOException {
        Scanner scanner=new Scanner(System.in);
        System.out.println("write your Name");
        String name=scanner.nextLine();
        System.out.println("You cannavigate Now");
        ManagedChannel managedChannel= ManagedChannelBuilder.forAddress("localhost",5555)
                .usePlaintext()
                .build();
        ChatServiceGrpc.ChatServiceStub asyncStub= ChatServiceGrpc.newStub(managedChannel);
        StreamObserver<GrpcModele.TextingMessage> performStream = asyncStub.texting(new StreamObserver<GrpcModele.TextingResponse>() {
            @Override
            public void onNext(GrpcModele.TextingResponse value) {
                System.out.println("recieved message "+ value.getReponse());

            }

            @Override
            public void onError(Throwable t) {
                System.out.println(t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("END");
            }
        });
        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                String rep=scanner.nextLine();
                GrpcModele.TextingMessage currencyRequest= GrpcModele.TextingMessage.newBuilder()
                        .setNom(name)
                        .setMessage(rep)
                        .build();
                performStream.onNext(currencyRequest);
            }
        },1000,1000);

        System.in.read();
    }

    @Override
    public void start(Stage stage) throws Exception {

    }
}
