package com.github.mbaksheev.fallasleepbot.handlers;

import com.github.mbaksheev.fallasleepbot.SecretProvider;
import com.github.mbaksheev.fallasleepbot.users.BotUser;
import com.github.mbaksheev.fallasleepbot.users.BotUserImpl;
import com.github.mbaksheev.fallasleepbot.users.BotUserRepository;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;

/**
 * Message handler to verify secret phrase.
 * Expects  that handled message contains from user, otherwise NullPointerException wil be thrown.
 */
public class SecretMessageHandler implements MessageHandler {
    private final BotUserRepository userRepository;
    private final SecretProvider secretProvider;

    public SecretMessageHandler(final BotUserRepository userRepository, final SecretProvider secretProvider) {
        this.userRepository = userRepository;
        this.secretProvider = secretProvider;
    }

    @Override
    public SendMessage handle(final Message incomingMessage) {
        final BotUser botUser = userRepository.get(incomingMessage.getFrom().getId());
        if (botUser != null && botUser.getState() == BotUser.State.WAIT_SECRET) {
            if (secretProvider.verifySecret(incomingMessage.getText())) {
                userRepository.add(new BotUserImpl(botUser.getId(), BotUser.State.AUTHORIZED));
                return successMessage(incomingMessage.getChatId());
            } else {
                return failMessage(incomingMessage.getChatId());
            }
        }
        return unsupportedCommandMessage(incomingMessage.getChatId());
    }

    private SendMessage successMessage(final Long chatId) {
        return new SendMessage()
                .setChatId(chatId)
                .setText("You are successfully authorized.");
    }

    private SendMessage failMessage(final Long chatId) {
        return new SendMessage()
                .setChatId(chatId)
                .setText("You are not authorized. Please try again.");
    }

    private SendMessage unsupportedCommandMessage(final Long chatId) {
        return new SendMessage()
                .setChatId(chatId)
                .setText("This command is not supported. See /help.");
    }
}
