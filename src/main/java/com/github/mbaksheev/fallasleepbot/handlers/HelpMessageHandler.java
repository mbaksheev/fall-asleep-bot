package com.github.mbaksheev.fallasleepbot.handlers;

import com.github.mbaksheev.fallasleepbot.Commands;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;

public class HelpMessageHandler implements MessageHandler {
    protected final String help = String.format(
            "Send %s command to login\n" +
                    "Send %s command to logout\n" +
                    "Send %s command to suspend computer\n" +
                    "Send %s command to display this help\n",
            Commands.ON, Commands.STOP, Commands.SLEEP, Commands.HELP);

    @Override
    public SendMessage handle(final Message incomingMessage) {
        return new SendMessage()
                .setChatId(incomingMessage.getChatId())
                .setText(help);
    }
}
