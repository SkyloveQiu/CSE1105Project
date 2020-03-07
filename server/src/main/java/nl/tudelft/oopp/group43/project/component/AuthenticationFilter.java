package nl.tudelft.oopp.group43.project.component;

import java.io.IOException;
import java.util.Optional;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import nl.tudelft.oopp.group43.project.models.User;
import nl.tudelft.oopp.group43.project.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;



public class AuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    @Autowired
    private AuthenticationManager authenticationManager;

    private static final String AUTHORIZATION = org.springframework.http.HttpHeaders.AUTHORIZATION  ;

    public AuthenticationFilter(final RequestMatcher requiresAuth) {
        super(requiresAuth);
    }

    @Autowired
    UserService userService;

    @Qualifier("userDetailsServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;
    /**
     * check the auth token is valid or not. if not throw exception.
     * @param request the request user made.
     * @param response the response of the server.
     * @return the result of the work.
     * @throws AuthenticationException auth not found.
     * @throws IOException can't read the database.
     * @throws ServletException internet problem.
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        Optional tokenParam = Optional.ofNullable(request.getHeader(AUTHORIZATION)); //Authorization: Bearer TOKEN
        String token= request.getHeader(AUTHORIZATION);
        token= StringUtils.removeStart(token, "Bearer").trim();
        System.out.println(token);
        User user = userService.findByToken(token);
        if (user == null) {
            throw new AuthenticationCredentialsNotFoundException("user can not be found");
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails,token, userDetails.getAuthorities());
        return authentication;
    }


    @Override
    protected void successfulAuthentication(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain, final Authentication authResult) throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(authResult);
        chain.doFilter(request, response);
    }
}
