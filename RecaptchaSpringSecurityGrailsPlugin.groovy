class RecaptchaSpringSecurityGrailsPlugin {

    def version = "0.1"
    def grailsVersion = "1.3.7 > *"
    def pluginExcludes = ["grails-app/conf/RecaptchaConfig.groovy"]

    def title = "Recaptcha Spring Security Plugin"
    def author = "Roberto Pérez Alcolea"
    def authorEmail = "roberto@perezalcolea.info"
    def description = 'Prevents brute force attack with Spring security and Recaptcha.'

    def documentation = "http://grails.org/plugin/recaptcha-spring-security"

    // Extra (optional) plugin metadata

    // License: one of 'APACHE', 'GPL2', 'GPL3'
//    def license = "APACHE"

    // Details of company behind the plugin (if there is one)
//    def organization = [ name: "My Company", url: "http://www.my-company.com/" ]

    // Any additional developers beyond the author specified above.
//    def developers = [ [ name: "Joe Bloggs", email: "joe@bloggs.net" ]]

    // Location of the plugin's issue tracker.
//    def issueManagement = [ system: "JIRA", url: "http://jira.grails.org/browse/GPMYPLUGIN" ]

    // Online location of the plugin's browseable source code.
//    def scm = [ url: "http://svn.grails-plugins.codehaus.org/browse/grails-plugins/" ]

    def doWithSpring = {
        // TODO Implement runtime spring config (optional)
    }
}
