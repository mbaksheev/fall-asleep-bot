package com.github.mbaksheev.fallasleepbot.handlers;

import com.github.mbaksheev.fallasleepbot.users.BotUser;
import com.github.mbaksheev.fallasleepbot.users.BotUserImpl;
import com.github.mbaksheev.fallasleepbot.users.BotUserRepository;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;

import static com.github.mbaksheev.fallasleepbot.users.BotUser.State;

/**
 * Message handler for login command.
 * Adds user to repository with status WAIT SECRET.
 * Expects  that handled message contains from user, otherwise NullPointerException wil be thrown.
 */
public class LoginMessageHandler implements MessageHandler {
    private final BotUserRepository userRepository;

    public LoginMessageHandler(final BotUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public SendMessage handle(final Message incomingMessage) {
        final Integer userId = incomingMessage.getFrom().getId();
        final BotUser botUser = userRepository.get(userId);
        if (botUser != null && State.AUTHORIZED == botUser.getState()) {
            return alreadyAuthorizedMessage(incomingMessage);
        } else {
            userRepository.add(new BotUserImpl(userId, State.WAIT_SECRET));
            return enterSecretMessage(incomingMessage);
        }

    }

    private SendMessage enterSecretMessage(final Message incomingMessage) {
        return new SendMessage()
                .setChatId(incomingMessage.getChatId())
                .setText("Please enter secret code");
    }

    private SendMessage alreadyAuthorizedMessage(final Message incomingMessage) {
        return new SendMessage()
                .setChatId(incomingMessage.getChatId())
                .setText("You are already authorized");
    }
}
