package com.cap.backendcapproject.security;

import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.security.sasl.AuthenticationException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class AuthorizationFilter extends BasicAuthenticationFilter {

    public AuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        try {
            response.addHeader("Access-Control-Allow-Origin", "*");
            // response.addHeader("Access-Control-Allow-Headers", "Origin, Accept, X-Requested-With, Content-Type, Access-Control-Request-Method, Access-Control-Request-Headers,authorization");
            // response.addHeader("Access-Control-Expose-Headers", "Access-Control-Allow-Origin, Access-Control-Allow-Credentials, authorization");
            response.addHeader("Access-Control-Allow-Headers", "*");
            response.addHeader("Access-Control-Expose-Headers", "*");
            response.addHeader("Access-Control-Allow-Methods","*");


            if(request.getMethod().equals("OPTIONS")){
                response.setStatus(HttpServletResponse.SC_OK);
            }
            else if(request.getRequestURI().equals("/login")) {
                System.out.println("auth login");
                chain.doFilter(request, response);
                return;
            }
            else {
                String header = request.getHeader(SecurityContants.HEADER_STRING);
                // System.out.println("header " + header);
                if(header == null || !header.startsWith(SecurityContants.TOKEN_PREFIX)) {
                    chain.doFilter(request, response);
                    return;
                }

                UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(request);
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                chain.doFilter(request, response);

            }

        }
        catch (AuthenticationException failed) {
            SecurityContextHolder.clearContext();

        }


    }



    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(SecurityContants.HEADER_STRING);

        // System.out.println("authz 1 " + token);

        if (token != null) {

            token = token.replace(SecurityContants.TOKEN_PREFIX, "");

            String user = Jwts.parser()
                    .setSigningKey( SecurityContants.TOKEN_SECRET )
                    .parseClaimsJws( token )
                    .getBody()
                    .getSubject();

            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
            }

            return null;
        }

        return null;
    }

}
