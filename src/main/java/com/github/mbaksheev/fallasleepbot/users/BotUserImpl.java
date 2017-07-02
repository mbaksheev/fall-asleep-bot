package com.github.mbaksheev.fallasleepbot.users;

/**
 * Simple implementation for bot user.
 */
public class BotUserImpl implements BotUser {
    private final Integer id;
    private final State state;

    public BotUserImpl(final Integer id, final State state) {
        this.id = id;
        this.state = state;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public State getState() {
        return state;
    }
}
