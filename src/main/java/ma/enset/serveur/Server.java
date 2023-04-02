package ma.enset.serveur;

import io.grpc.ServerBuilder;
import ma.enset.service.ChatAppService;

import java.io.IOException;

public class Server {
    public static void main(String[] args) throws IOException, InterruptedException {
        io.grpc.Server server= ServerBuilder.forPort(5555).addService(new ChatAppService()).build();
        server.start();
        server.awaitTermination();
    }
}
