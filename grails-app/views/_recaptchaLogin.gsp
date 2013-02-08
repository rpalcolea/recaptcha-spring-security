<g:if test="${session.recaptchaForLogin}">
    <recaptcha:ifEnabled>
        <recaptcha:recaptcha theme="clean"/>
        <recaptcha:ifFailed>CAPTCHA Failed</recaptcha:ifFailed>
    </recaptcha:ifEnabled>
</g:if>