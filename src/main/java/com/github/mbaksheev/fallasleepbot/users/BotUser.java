package com.github.mbaksheev.fallasleepbot.users;

/**
 * Bot user.
 */
public interface BotUser {
    /**
     * Possible user states.
     */
    enum State {
        /**
         * User is authorized.
         */
        AUTHORIZED,

        /**
         * User is initiated authorization process, but secret code is not verified yet.
         */
        WAIT_SECRET
    }

    /**
     * @return user's id.
     */
    Integer getId();

    /**
     * @return user's state.
     */
    State getState();
}
