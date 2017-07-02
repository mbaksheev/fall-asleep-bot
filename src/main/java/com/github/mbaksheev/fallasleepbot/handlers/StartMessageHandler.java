package com.github.mbaksheev.fallasleepbot.handlers;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;

/**
 * Message handler for '/start' command.
 * Displays greeting and list of commands.
 */
public class StartMessageHandler extends HelpMessageHandler implements MessageHandler {
    @Override
    public SendMessage handle(final Message incomingMessage) {
        return startMessage(incomingMessage.getChatId());
    }

    private SendMessage startMessage(final Long chatId) {
        return new SendMessage()
                .setChatId(chatId)
                .setText("Hello!\n" + help);
    }
}
