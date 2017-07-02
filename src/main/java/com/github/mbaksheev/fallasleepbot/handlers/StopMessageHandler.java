package com.github.mbaksheev.fallasleepbot.handlers;

import com.github.mbaksheev.fallasleepbot.users.BotUserRepository;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;

/**
 * Message handler for '/stop' command.
 * Logout user.
 * Expects  that handled message contains from user, otherwise NullPointerException wil be thrown.
 */
public class StopMessageHandler implements MessageHandler {
    private final BotUserRepository userRepository;

    public StopMessageHandler(final BotUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public SendMessage handle(final Message incomingMessage) {
        userRepository.remove(incomingMessage.getFrom().getId());
        return stopMessage(incomingMessage.getChatId());
    }

    private SendMessage stopMessage(final Long chatId) {
        return new SendMessage()
                .setChatId(chatId)
                .setText("Buy!");
    }
}
