package org.lts.profile_hub;

import io.grpc.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class GrpcServerStarter implements CommandLineRunner {

    private final Server server;

    @Autowired
    public GrpcServerStarter(Server server) {
        this.server = server;
    }

    @Override
    public void run(String... args) throws Exception {
        server.start();
        server.awaitTermination();
    }

}
