package com.github.mbaksheev.fallasleepbot.users;

/**
 * User repository.
 */
public interface BotUserRepository {

    /**
     * Gets bot user by user id.
     *
     * @param userId the user id.
     * @return the bot user
     */
    BotUser get(Integer userId);

    /**
     * Adds user into repository
     *
     * @param user user to add.
     */

    void add(BotUser user);

    /**
     * Removes user from repository.
     *
     * @param userId user id to remove user.
     */
    void remove(Integer userId);
}
