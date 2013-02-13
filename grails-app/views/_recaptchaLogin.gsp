<g:if test="${session.recaptchaForLogin}">
    <recaptcha:ifEnabled>
        <recaptcha:recaptcha theme="${theme}"/>
        <recaptcha:ifFailed>CAPTCHA Failed</recaptcha:ifFailed>
    </recaptcha:ifEnabled>
</g:if>