package org.codehaus.groovy.grails.plugins.recaptchaspringsecurity

class RecaptchaSpringSecurityMenuTagLib {

    def recaptchaLogin = { attrs ->
        out << render(template: "/recaptchaLogin", plugin: 'recaptcha-spring-security')
    }
}
