package org.lts.lego_teh_set.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.lts.lego_teh_set.rebrickableAPI.RebrickableAPIRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/// Takes a token from `.env` file named `REBRICKABLE_API_KEY`.
@Configuration
public class RebrickableAPIRepositoryConfig {

    /// Takes a token from `.env` file named `REBRICKABLE_API_KEY`.
    @Bean
    public RebrickableAPIRepository rebrickableAPIRepository() {
        return new RebrickableAPIRepository(Dotenv.configure().load().get("REBRICKABLE_API_KEY"));
    }

}
