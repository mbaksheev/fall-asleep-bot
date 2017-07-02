package com.github.mbaksheev.fallasleepbot.handlers;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.User;

/**
 * Message handler wrapper to check has incoming massage specified from user.
 * If from user is not specified, it returns Command not supported response message.
 */
public class CheckUserWrapper implements MessageHandler {
    private final MessageHandler wrappedHandler;

    /**
     * Constructs wrapper for specified handler.
     *
     * @param wrappedHandler the handler
     */
    public CheckUserWrapper(final MessageHandler wrappedHandler) {
        this.wrappedHandler = wrappedHandler;
    }

    @Override
    public SendMessage handle(final Message incomingMessage) {
        final User fromUser = incomingMessage.getFrom();
        if (fromUser != null) {
            return wrappedHandler.handle(incomingMessage);
        } else {
            return commandIsNotSupportedMessage(incomingMessage.getChatId());
        }
    }

    private SendMessage commandIsNotSupportedMessage(final Long chatId) {
        return new SendMessage()
                .setChatId(chatId)
                .setText("This command is not supported for chats");
    }
}
