package com.github.mbaksheev.fallasleepbot;

/**
 * Secret phrase provider.
 */
public interface SecretProvider {
    /**
     * Verifies secret phrase.
     *
     * @param secret phrase to verify
     * @return true if the phrase is secret phrase, false otherwise
     */
    Boolean verifySecret(String secret);
}
