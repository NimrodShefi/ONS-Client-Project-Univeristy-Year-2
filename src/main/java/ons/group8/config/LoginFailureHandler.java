package ons.group8.config;

import ons.group8.domain.User;
import ons.group8.services.LockOutService;
import ons.group8.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Service;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Autowired
    private LockOutService lockOutService;

    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        String email = request.getParameter("username");
        User user = userService.findByEmail(email);
        if (user != null) {
            if (user.isEnabled() && user.isAccountNonLocked()) {
                if (user.getFailedAttempt() < LockOutService.MAX_FAILED_ATTEMPTS - 1) {
                    lockOutService.increaseFailedAttempts(user);
                } else {
                    lockOutService.lock(user);
                    exception = new LockedException("Your account has been locked due to 3 failed attempts."
                            + " It will be unlocked after 5 min.");
                }
            } else if (!user.isAccountNonLocked()) {
                if (!lockOutService.unlockWhenTimeExpired(user)) {
                    exception = new LockedException("Your account has been locked due to 3 failed attempts.");
                }

            }


        }
        super.setDefaultFailureUrl("/login?error");
        super.onAuthenticationFailure(request, response, exception);

    }

}
