grails.project.work.dir = 'target'
grails.project.target.level = 1.6

grails.project.dependency.resolution = {

    inherits 'global'
    log 'warn'

    repositories {
        grailsCentral()
        mavenLocal()
        mavenCentral()
    }

    dependencies {
        compile "com.google.guava:guava:14.0-rc1"
    }

    plugins {
        build ':release:2.2.0', ':rest-client-builder:1.0.3', {
            export = false
        }

        compile ":recaptcha:0.5.3"
        compile ":spring-security-core:1.2.7.3"
    }
}
