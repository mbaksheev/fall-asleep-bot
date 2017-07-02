package com.github.mbaksheev.fallasleepbot.handlers;

import com.github.mbaksheev.fallasleepbot.users.BotUser;
import com.github.mbaksheev.fallasleepbot.users.BotUserRepository;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;

/**
 * Message handler wrapper to check has user AUTHORIZED state.
 * Expects  that handled message contains from user, otherwise NullPointerException wil be thrown.
 */
public class CheckPermissionWrapper implements MessageHandler {
    private final MessageHandler handler;
    private final BotUserRepository userRepository;

    /**
     * Constructs the wrapper based on specified handler and user repository.
     *
     * @param handler        the handler to wrap
     * @param userRepository user repository
     */
    public CheckPermissionWrapper(final MessageHandler handler, final BotUserRepository userRepository) {
        this.handler = handler;
        this.userRepository = userRepository;
    }

    @Override
    public SendMessage handle(final Message incomingMessage) {
        final BotUser botUser = userRepository.get(incomingMessage.getFrom().getId());
        if (botUser != null && botUser.getState() == BotUser.State.AUTHORIZED) {
            return handler.handle(incomingMessage);
        } else {
            return unauthorizedMessage(incomingMessage.getChatId());
        }

    }

    private SendMessage unauthorizedMessage(final Long chatId) {
        return new SendMessage()
                .setChatId(chatId)
                .setText("You are not authorized to perform this command");
    }

}
