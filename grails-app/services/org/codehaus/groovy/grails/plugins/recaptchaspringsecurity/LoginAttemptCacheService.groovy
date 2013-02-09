package org.codehaus.groovy.grails.plugins.recaptchaspringsecurity

import java.util.concurrent.TimeUnit

import javax.annotation.PostConstruct

import org.springframework.web.context.request.RequestContextHolder

import com.google.common.cache.CacheBuilder
import com.google.common.cache.CacheLoader
import com.google.common.cache.LoadingCache

/**
 * @author Grygoriy Mykhalyunyo
 * @modified by Roberto Perez
 */
class LoginAttemptCacheService {

    def grailsApplication

    private LoadingCache<String, Integer> attempts

    private Integer allowedNumberOfAttempts

    @PostConstruct
    void init() {
        allowedNumberOfAttempts = grailsApplication.config.bruteforcedefender.allowedNumberOfAttempts
        Integer time = grailsApplication.config.bruteforcedefender.time

        attempts = CacheBuilder.newBuilder()
                .expireAfterWrite(time, TimeUnit.MINUTES)
                .build({0} as CacheLoader)
    }

    /**
     * Triggers on each unsuccessful login attempt and increases number of attempts
     */
    void failLogin(String login) {
        int numberOfAttempts = attempts.get(login)
        numberOfAttempts++

        if (numberOfAttempts >= allowedNumberOfAttempts) {
            activateRecaptcha()
            //attempts.invalidate(login)
        } else {
            attempts.put(login, numberOfAttempts)
        }
    }

    /**
     * Triggers on each successful login attempt and resets number of attempts
     */
    void loginSuccess(String login) {
        attempts.invalidate(login)
        deactivateRecaptcha()
    }

    private activateRecaptcha() {
        RequestContextHolder.currentRequestAttributes().session.recaptchaForLogin = true
    }

    private deactivateRecaptcha() {
        RequestContextHolder.currentRequestAttributes().session.recaptchaForLogin = false
    }
}
