package com.cap.backendcapproject.security;

import com.cap.backendcapproject.dto.UserDto;
import com.cap.backendcapproject.entities.AppUser;
import com.cap.backendcapproject.response.UserResponse;
import com.cap.backendcapproject.services.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    private AccountService accountService;


    public AuthenticationFilter(AuthenticationManager authenticationManager,AccountService accountService) {
        this.authenticationManager = authenticationManager;
        //this.doctorService = doctorService;
        this.accountService = accountService;

    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {

            //System.out.println("path = " + request.getContextPath());
            //  AppUser appUser= new ObjectMapper().readValue(request.getInputStream(),AppUser.class);
            //UserLogingRequest creds = new ObjectMapper().readValue(request.getInputStream(), UserLogingRequest.class);
            AppUser creds = new ObjectMapper().readValue(request.getInputStream(), AppUser.class);


            //System.out.println(" creds.getEmail() =   " + creds.toString());

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getPassword(), new ArrayList<>())
            );
        }catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res,
                                            FilterChain chain, Authentication auth)
            throws IOException, ServletException {
        //System.out.println(" 1 auth  ");

        //get user from database
        String userName = ((User) auth.getPrincipal()).getUsername();
        //String user = ((User) auth.getPrincipal()).getAuthorities();
        //AppUser creds = new ObjectMapper().readValue(req.getInputStream(), AppUser.class);
        UserResponse userResp = new UserResponse();
        AppUser user = accountService.loadUserByEmail(userName);
        BeanUtils.copyProperties(user, userResp);
        //System.out.println("creds " + );

        //generate the token
        String token = Jwts.builder()
                .setSubject(userName)
                .claim("user", userResp)
                .setExpiration(new Date(System.currentTimeMillis() + SecurityContants.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SecurityContants.TOKEN_SECRET)
                .compact();


        //System.out.println("token =  " + token  );

        //DoctorService doctorService = (DoctorService)WSpringbootContext.getBean("doctorServiceImpl");
        //System.out.println("doctorService " + doctorService.toString());
        AppUser appUser = accountService.loadUserByEmail(userName);
        //Doctor doctor = doctorService.loadUserByEmail(userName);
        //System.out.println("doctordto getEmail " + appUser.getEmail());

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(appUser, userDto);

        res.addHeader(SecurityContants.HEADER_STRING, SecurityContants.TOKEN_PREFIX + token);
        //System.out.println("user_id " + doctorDto.getUserId());
        res.addHeader("username_user", userDto.getEmail());
        res.addHeader("Content-Type","application/json");
        res.setCharacterEncoding("UTF-8");
        //res.addHeader("Access-Control-Allow-Origin", "*");
//
    }

}
