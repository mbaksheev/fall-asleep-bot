package com.github.mbaksheev.fallasleepbot;

/**
 * Bot commands.
 */
public interface Commands {
    /**
     * Start - display greeting and list of commands.
     */
    String START = "/start";

    /**
     * Initiate login process, after this command user should input secret phrase.
     */
    String ON = "/on";

    /**
     * Perform logout for a user.
     */
    String STOP = "/stop";

    /**
     * Schedules OS suspend command.
     */
    String SLEEP = "/sleep";

    /**
     * Displays list of commands.
     */
    String HELP = "/help";
}
