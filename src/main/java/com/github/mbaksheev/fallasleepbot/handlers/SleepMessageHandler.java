package com.github.mbaksheev.fallasleepbot.handlers;

import org.apache.commons.lang3.SystemUtils;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Message handler for sleep command.
 * Performs Operation System command to suspend computer.
 * Expects  that handled message contains from user, otherwise NullPointerException wil be thrown.
 */
public class SleepMessageHandler implements MessageHandler {
    private static final String SUSPEND_COMMAND_LINUX = "sudo systemctl suspend";
    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

    @Override
    public SendMessage handle(final Message incomingMessage) {

        if (SystemUtils.IS_OS_LINUX) {
            scheduleSuspendCommandAfterDelay(1000);
            return suspendMessage(incomingMessage.getChatId());
        } else {
            return osNotSupportedMessage(incomingMessage.getChatId());
        }
    }

    private SendMessage osNotSupportedMessage(final Long chatId) {
        return new SendMessage()
                .setChatId(chatId)
                .setText("Operation system is not supported");
    }


    private SendMessage suspendMessage(final Long chatId) {
        return new SendMessage()
                .setChatId(chatId)
                .setText("Computer will go sleep in a second");
    }

    /**
     * Shedules suspend command after specified delay.
     *
     * @param delayMs the delay in milliseconds
     */
    private void scheduleSuspendCommandAfterDelay(final long delayMs) {
        executorService.schedule(() -> {
            try {
                final Process p = Runtime.getRuntime().exec(SUSPEND_COMMAND_LINUX);
                p.waitFor();
                if (p.exitValue() != 0) {
                    try (final BufferedReader reader =
                                 new BufferedReader(new InputStreamReader(p.getErrorStream()))) {
                        StringBuilder output = new StringBuilder();
                        reader.lines().forEach(output::append);
                        System.err.println(output.toString());

                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, delayMs, TimeUnit.MILLISECONDS);
    }
}
