package org.lts.lego_teh_set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/// Launches the bot
@Component
public class BotStarter implements CommandLineRunner {

    private final Bot bot;

    @Autowired
    public BotStarter(Bot bot) {
        this.bot = bot;
    }

    @Override
    public void run(String... args) throws Exception {
        bot.start();
    }

}
