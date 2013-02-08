package org.codehaus.groovy.grails.plugins.recaptchaspringsecurity

class RecaptchaSpringSecurityMenuTagLib {

    def recaptchaLogin = {
        out << render(template: "/recaptchaLogin", plugin: 'recaptcha-spring-security')
    }
}
