package com.github.mbaksheev.fallasleepbot;

import com.github.mbaksheev.fallasleepbot.users.BotUserRepository;
import com.github.mbaksheev.fallasleepbot.users.InMemoryBotUserRepository;
import org.apache.commons.lang3.StringUtils;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

/**
 * Main class to register and start bot.
 */
public class Main {
    public static void main(String[] args) {

        try {
            final BotSettings botSettings = parseBotSettings(args);
            final SecretProvider secretProvider = parseSecret(args);
            final BotUserRepository userRepository = new InMemoryBotUserRepository();

            ApiContextInitializer.init();
            final TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
            telegramBotsApi.registerBot(new FallAsleepBot(botSettings, userRepository, secretProvider));
            System.out.println(botSettings.getBotName() + " is started.");

        } catch (IllegalArgumentException iae) {
            System.err.println(iae.getMessage());
            printUsage();
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }

    }

    /**
     * Parses bot settings from command line args.
     * Expects that the first arg is bot name, the second arg is bot token.
     *
     * @param args the args
     * @return bot settings
     */
    private static BotSettings parseBotSettings(String[] args) {
        if (args.length < 1 || StringUtils.isBlank(args[0])) {
            throw new IllegalArgumentException("Bot Name is not specified.");
        }
        if (args.length < 2 || StringUtils.isBlank(args[1])) {
            throw new IllegalArgumentException("Bot Token is not specified.");
        }
        return new BotSettings() {
            @Override
            public String getBotName() {
                return args[0];
            }

            @Override
            public String getBotToken() {
                return args[1];
            }
        };
    }

    /**
     * Parses secret phrase form command line args and creates secret provider with this phrase.
     * Expects the third arg is secret phrase
     *
     * @param args the args
     * @return the secret provider
     */
    private static SecretProvider parseSecret(String[] args) {
        if (args.length < 3 || StringUtils.isBlank(args[2])) {
            throw new IllegalArgumentException("Secret is not specified.");
        }
        return args[2]::equals;
    }

    /**
     * Prints usage.
     */
    private static void printUsage() {
        System.out.println("Required arguments:\n" +
                "1 - bot name\n" +
                "2 - bot token\n" +
                "3 - secret phrase\n" +
                "for example\n" +
                "java -jar fall-asleep-bot.jar suspenderBot 123445566:HVdsahshavq_sadjasbHUS secret");
    }
}
