package com.manoj.ChatAppBackend.security.config;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.manoj.ChatAppBackend.entity.User;
import com.manoj.ChatAppBackend.entity.dao.IUserDao;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

	@SuppressWarnings("unchecked")
	@Override
    public Authentication attemptAuthentication(HttpServletRequest req,
                                                HttpServletResponse res)
                                         throws AuthenticationException {
        try {
            Map<String, String> creds = new ObjectMapper()
                    .readValue(req.getInputStream(), Map.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            creds.get("username"),
                            "12345",
                            new ArrayList<>())
            );
        } catch (Exception e) {
        //	System.out.println("Hello");
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        UserDetails user=(UserDetails)auth.getPrincipal();
    	TokenUtility tokenUtility=new TokenUtility();
    	String token=tokenUtility.generateToken(user.getUsername(),null);
        res.addHeader("Authorization", "Bearer " + token);
        res.setContentType(MediaType.APPLICATION_JSON_VALUE);
        PrintWriter out=res.getWriter();
        Map<String, Object> successMap = new HashMap<>();
        successMap.put("token", res.getHeader("Authorization"));
        ObjectMapper mapper = new ObjectMapper();
        String json =  mapper.writeValueAsString(successMap);
        out.write(json);
        out.flush();
        out.close();
    }
}