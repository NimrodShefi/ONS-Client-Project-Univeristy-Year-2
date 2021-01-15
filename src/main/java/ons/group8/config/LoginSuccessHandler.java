package ons.group8.config;

import ons.group8.domain.User;
import ons.group8.services.LockOutService;
import ons.group8.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private LockOutService lockOutService;
    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        MyUserDetails userDetails =  (MyUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();
        if (user.getFailedAttempt() > 0) {
            lockOutService.resetFailedAttempts(user.getEmail());
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
