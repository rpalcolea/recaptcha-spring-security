package org.codehaus.groovy.grails.plugins.recaptchaspringsecurity

class RecaptchaSpringSecurityMenuTagLib {

    def recaptchaLogin = { attrs ->
        def theme = attrs.theme ?: 'clean'
        out << render(template: "/recaptchaLogin", plugin: 'recaptcha-spring-security', model: [theme: theme])
    }
}
