package com.instogramm.instogramm.sucurity.jwt;

import com.google.gson.Gson;
import com.instogramm.instogramm.payload.response.InvalidLoginResponce;
import com.instogramm.instogramm.sucurity.SecurityConstants;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        //httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unathorized");

        InvalidLoginResponce invalidLoginResponce = new InvalidLoginResponce();
        String jsonLoginResponce = new Gson().toJson(invalidLoginResponce);
        httpServletResponse.setContentType(SecurityConstants.CONTENT_TYPE);
        httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        httpServletResponse.getWriter().println(jsonLoginResponce);
    }
}
