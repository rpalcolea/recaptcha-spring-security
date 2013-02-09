package org.codehaus.groovy.grails.plugins.recaptchaspringsecurity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsWebRequest;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.megatome.grails.RecaptchaService;

/**
 * Filter for capturing Captcha fields and validate them using recaptchaService.
 */
public class CaptchaCaptureFilter extends OncePerRequestFilter {

    private final Logger log = Logger.getLogger(getClass());

    private String failureUrl;
    private RecaptchaService recaptchaService;

    private SimpleUrlAuthenticationFailureHandler failureHandler = new SimpleUrlAuthenticationFailureHandler();

    @Override
    public void doFilterInternal(HttpServletRequest req, HttpServletResponse res,
                                 FilterChain chain) throws IOException, ServletException {

        HttpSession session = ((GrailsWebRequest)RequestContextHolder.currentRequestAttributes()).getSession();

        try {

            if (session.getAttribute("recaptchaForLogin") != null) {
                // Assign values only when user has submitted a Captcha value.
                if (req.getParameter("recaptcha_response_field") != null) {

                    String recaptcha_response = req.getParameter("recaptcha_response_field");
                    String recaptcha_challenge = req.getParameter("recaptcha_challenge_field");
                    String remoteAddr = req.getRemoteAddr();

                    Map<String, String> params = new HashMap<String, String>();
                    params.put("recaptcha_response_field", recaptcha_response);
                    params.put("recaptcha_challenge_field", recaptcha_challenge);
                    // Check if valid
                    boolean verified =  (Boolean) recaptchaService.verifyAnswer(session, remoteAddr, params);
                    if (!verified) {
                        // Redirect user to login page
                        failureHandler.setDefaultFailureUrl(failureUrl);
                        failureHandler.onAuthenticationFailure(req, res, new BadCredentialsException("Captcha invalid!"));
                        return;
                    }
                    recaptchaService.cleanUp(session);
                }

                // Proceed with the remaining filters
            }

            chain.doFilter(req, res);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public void setFailureUrl(String failureUrl) {
        this.failureUrl = failureUrl;
    }

    protected String getFailureUrl() {
        return failureUrl;
    }

    public void setRecaptchaService(RecaptchaService recaptchaService) {
        this.recaptchaService = recaptchaService;
    }
}
