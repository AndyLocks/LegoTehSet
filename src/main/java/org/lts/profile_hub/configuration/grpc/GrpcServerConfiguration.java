package org.lts.profile_hub.configuration.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.lts.profile_hub.controller.ProfileGrpcController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class GrpcServerConfiguration {

    private final Logger LOG = LoggerFactory.getLogger(GrpcServerConfiguration.class);

    @Value("${grpc.server.port}")
    private int port;

    @Bean
    public Server grpcServer(ProfileGrpcController profileGrpcController) throws IOException {
        var server = ServerBuilder.forPort(port)
                .addService(profileGrpcController)
                .build();

        LOG.info("gRPC server was started on port [{}]", port);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            LOG.info("Shutting down gRPC server...");
            server.shutdown();
            LOG.info("gRPC server shutdown completed.");
        }));

        return server;
    }

}
