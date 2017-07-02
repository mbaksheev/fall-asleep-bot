package com.github.mbaksheev.fallasleepbot.users;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Simple user repository based on hash map.
 */
public class InMemoryBotUserRepository implements BotUserRepository {
    private final ConcurrentHashMap<Integer, BotUser> users = new ConcurrentHashMap<>();


    public BotUser get(final Integer userId) {
        return users.get(userId);
    }

    public void add(final BotUser user) {
        users.put(user.getId(), user);
    }

    public void remove(final Integer userId) {
        users.remove(userId);
    }
}
