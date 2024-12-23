package org.lts.lego_teh_set.config;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.hooks.EventListener;
import org.lts.lego_teh_set.Bot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;

/// Collects all {@link java.util.EventListener} in the whole application and creates a new bot with them.
///
/// Also takes a token from `.env` file named `TOKEN`.
@Configuration
public class BotConfig {

    private final Logger LOG = LoggerFactory.getLogger(BotConfig.class);

    /// Collects all {@link java.util.EventListener} in the whole application and creates a new bot with them.
    ///
    /// Also takes a token from `.env` file named `TOKEN`.
    @Bean
    public Bot bot(Collection<? extends EventListener> eventListeners) {
        LOG.info("All event listeners:");
        eventListeners.forEach(e -> LOG.info(e.toString()));

        return new Bot(Dotenv.configure().load().get("TOKEN"), eventListeners);
    }

}
