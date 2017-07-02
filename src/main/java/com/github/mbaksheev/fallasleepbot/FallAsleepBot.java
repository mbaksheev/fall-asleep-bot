package com.github.mbaksheev.fallasleepbot;

import com.github.mbaksheev.fallasleepbot.handlers.*;
import com.github.mbaksheev.fallasleepbot.users.BotUserRepository;
import com.google.common.collect.ImmutableMap;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

/**
 * Bot which suspends computer.
 */
public class FallAsleepBot extends TelegramLongPollingBot {
    private final BotSettings botSettings;
    private final BotUserRepository users;
    private final SecretProvider secretProvider;
    private final ImmutableMap<String, MessageHandler> messageHandlers;

    public FallAsleepBot(final BotSettings botSettings, final BotUserRepository userRepository, final SecretProvider secretProvider) {
        this.botSettings = botSettings;
        this.users = userRepository;
        this.secretProvider = secretProvider;

        messageHandlers = ImmutableMap.of(
                Commands.SLEEP, new CheckUserWrapper(new CheckPermissionWrapper(new SleepMessageHandler(), users)),
                Commands.ON, new CheckUserWrapper(new LoginMessageHandler(users)),
                Commands.START, new StartMessageHandler(),
                Commands.STOP, new CheckUserWrapper(new StopMessageHandler(users)),
                Commands.HELP, new HelpMessageHandler()
        );
    }


    public void onUpdateReceived(final Update update) {
        final Message receivedMsg = update.getMessage();
        if (receivedMsg != null) {

            final MessageHandler handler = messageHandlers.getOrDefault(receivedMsg.getText(), new SecretMessageHandler(users, secretProvider));
            try {
                sendMessage(handler.handle(receivedMsg));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }


    public String getBotUsername() {
        return botSettings.getBotName();
    }

    @Override
    public String getBotToken() {
        return botSettings.getBotToken();
    }
}

