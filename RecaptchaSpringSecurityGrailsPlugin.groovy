import org.codehaus.groovy.grails.plugins.recaptchaspringsecurity.AuthenticationFailureListener
import org.codehaus.groovy.grails.plugins.recaptchaspringsecurity.AuthenticationSuccessListener
import org.codehaus.groovy.grails.plugins.recaptchaspringsecurity.CaptchaCaptureFilter
import grails.plugin.springsecurity.SecurityFilterPosition
import grails.plugin.springsecurity.SpringSecurityUtils

class RecaptchaSpringSecurityGrailsPlugin {

    def version = "0.2"
    def grailsVersion = "2.3.2 > *"
    def pluginExcludes = ["grails-app/conf/RecaptchaConfig.groovy"]
    def loadAfter = ['springSecurityCore']

    def title = "Recaptcha Spring Security Plugin"
    def author = "Roberto Pérez Alcolea"
    def authorEmail = "roberto@perezalcolea.info"
    def description = 'Prevents brute force attack with Spring security and Recaptcha.'

    def documentation = "http://grails.org/plugin/recaptcha-spring-security"

    def license = "APACHE"
//    def organization = [ name: "My Company", url: "http://www.my-company.com/" ]
//    def developers = [ [ name: "Joe Bloggs", email: "joe@bloggs.net" ]]
    def issueManagement = [system: "Github", url: "https://github.com/rpalcolea/recaptcha-spring-security/issues"]
    def scm = [url: "https://github.com/rpalcolea/recaptcha-spring-security"]

    def doWithSpring = {

        authenticationFailureListener(AuthenticationFailureListener) {
            loginAttemptCacheService = ref('loginAttemptCacheService')
        }

        authenticationSuccessEventListener(AuthenticationSuccessListener) {
            loginAttemptCacheService = ref('loginAttemptCacheService')
        }

        captchaCaptureFilter(CaptchaCaptureFilter) {
            failureUrl = SpringSecurityUtils.securityConfig.failureHandler.defaultFailureUrl
            recaptchaService = ref('recaptchaService')
        }

        SpringSecurityUtils.registerFilter 'captchaCaptureFilter', SecurityFilterPosition.SECURITY_CONTEXT_FILTER.order + 10
    }
}
