package org.lts.profile_hub;

import io.grpc.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/// Starts {@link Server} (configuration: {@link org.lts.profile_hub.configuration.grpc.GrpcServerConfiguration}) and blocks main tread.
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
