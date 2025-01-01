package org.lts.lego_teh_set.config.grpc;

import io.grpc.ManagedChannelBuilder;
import org.lts.ProfileHubGrpc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcProfileHubClientConfiguration {

    @Value("${profile_hub.host}")
    private String host;

    @Value("${profile_hub.port}")
    private int port;

    private final Logger LOG = LoggerFactory.getLogger(GrpcProfileHubClientConfiguration.class);

    @Bean
    public ProfileHubGrpc.ProfileHubBlockingStub profileHubBlockingStub() {
        LOG.info("Creating [ProfileHub] gRPC channel...");
        LOG.info("[ProfileHub] gRPC channel: host[{}], port [{}]", host, port);

        var channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()
                .build();
        var stub = ProfileHubGrpc.newBlockingStub(channel);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            LOG.info("Shutting down [ProfileHub] gRPC channel...");
            channel.shutdown();
            LOG.info("[ProfileHub] gRPC channel shutdown completed.");
        }));

        return stub;
    }

}
