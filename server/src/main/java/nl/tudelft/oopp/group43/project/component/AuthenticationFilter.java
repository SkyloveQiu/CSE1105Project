package nl.tudelft.oopp.group43.project.component;

import java.io.IOException;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;



public class AuthenticationFilter extends AbstractAuthenticationProcessingFilter {


    public AuthenticationFilter(final RequestMatcher requiresAuth) {
        super(requiresAuth);
    }

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
        Map<String,String[]> map = request.getParameterMap();
        String token = map.get("token")[0];

        System.out.println(token);
        Authentication requestAuthentication = new UsernamePasswordAuthenticationToken(token, token);
        return getAuthenticationManager().authenticate(requestAuthentication);
    }


    @Override
    protected void successfulAuthentication(final HttpServletRequest request, final HttpServletResponse response, final FilterChain chain, final Authentication authResult) throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(authResult);
        chain.doFilter(request, response);
    }
}
