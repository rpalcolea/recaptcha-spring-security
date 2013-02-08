recaptcha-spring-security
=========================

Grails: Using recaptcha with spring security core 


Installation
========================

1. clone the repository: git clone https://github.com/rpalcolea/recaptcha-spring-security.git

2. Enter to the repository via command line.

3. package the plugin: grails package-plugin

Configuration
========================

# Config.groovy

Add the following lines to your Grails Config.groovy file:

	bruteforcedefender {
    	time = 5
    	allowedNumberOfAttempts = 3
	}

time = minutes mantaining failed attempts
allowedNumberOfAttempts = number of failed attempts before showing the recaptcha widget.

