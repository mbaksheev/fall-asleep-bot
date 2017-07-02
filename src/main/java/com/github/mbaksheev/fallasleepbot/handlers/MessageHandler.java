package com.github.mbaksheev.fallasleepbot.handlers;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;

/**
 * Handler for message.
 */
public interface MessageHandler {

    /**
     * Handle specified message and returns response message.
     *
     * @param incomingMessage the message
     * @return the response message
     */
    SendMessage handle(Message incomingMessage);
}
