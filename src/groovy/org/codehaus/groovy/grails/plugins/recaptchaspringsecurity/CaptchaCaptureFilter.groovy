package org.codehaus.groovy.grails.plugins.recaptchaspringsecurity

import org.apache.log4j.Logger
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.filter.OncePerRequestFilter

import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * Filter for capturing Captcha fields and validate them using recaptchaService.
 */
public class CaptchaCaptureFilter extends OncePerRequestFilter {

    private String recaptcha_response;
    private String recaptcha_challenge;
    private String remoteAddr;
    private String failureUrl;

    private final Logger log = Logger.getLogger(getClass())

    transient recaptchaService

    private SimpleUrlAuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler();

    @Override
    public void doFilterInternal(HttpServletRequest req, HttpServletResponse res,
                                 FilterChain chain) throws IOException, ServletException {

        def session = RequestContextHolder.currentRequestAttributes().getSession()

        try {

            if (session.recaptchaForLogin) {
                // Assign values only when user has submitted a Captcha value.
                if (req.getParameter("recaptcha_response_field") != null) {
                    recaptcha_response = req.getParameter("recaptcha_response_field");
                    recaptcha_challenge = req.getParameter("recaptcha_challenge_field");
                    remoteAddr = req.getRemoteAddr();


                    Map params = ["recaptcha_response_field": recaptcha_response, "recaptcha_challenge_field": recaptcha_challenge]
                    // Check if valid
                    if (!recaptchaService.verifyAnswer(session, remoteAddr, params)) {
                        // Redirect user to login page
                        failureHandler.setDefaultFailureUrl(failureUrl);
                        failureHandler.onAuthenticationFailure(req, res, new BadCredentialsException("Captcha invalid!"));
                        return
                    }
                    recaptchaService.cleanUp(session)
                }

                // Proceed with the remaining filters
            }

            chain.doFilter(req, res);

        } catch (all) {
            log.error(all)
        }

    }

    public void setFailureUrl(String failureUrl) {
        this.failureUrl = failureUrl;
    }

    protected String getFailureUrl() {
        return failureUrl;
    }


}