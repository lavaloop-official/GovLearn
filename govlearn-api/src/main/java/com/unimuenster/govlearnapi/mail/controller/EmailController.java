package com.unimuenster.govlearnapi.mail.controller;

import com.unimuenster.govlearnapi.common.responsewrapper.Message;
import com.unimuenster.govlearnapi.common.responsewrapper.Response;
import com.unimuenster.govlearnapi.course.controller.wsto.CourseCreationWsTo;
import com.unimuenster.govlearnapi.course.entity.Course;
import com.unimuenster.govlearnapi.course.service.dto.CourseCreationDTO;
import com.unimuenster.govlearnapi.course.service.dto.CourseDTO;
import com.unimuenster.govlearnapi.mail.service.EmailService;
import com.unimuenster.govlearnapi.user.controller.wsto.RequestResetWsTo;
import com.unimuenster.govlearnapi.user.entity.UserEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Key;
import java.util.Date;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
@Slf4j
public class EmailController
{
    private final EmailService emailService;

    private static final String SECRET_KEY = "442A472D4B6150645367566B59703373367639792442264528482B4D62516554";

    @Operation(
        description = "Send a test email."
    )
    @PostMapping("/mail")
    public ResponseEntity<Response> createCourse(){
        emailService.sendEmail("fabian@uhlit.de", "Test", "Test");
        return ResponseEntity.ok(Response.of(true));
    }

    /*
    @Operation(
        description = "Send a password reset email."
    )
    @PostMapping("/reset-password")
    public ResponseEntity<Response> resetPassword(@RequestBody RequestResetWsTo requestResetWsTo){
        String token = Jwts
                .builder()
                .setSubject(requestResetWsTo.email())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*15))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();

        emailService.sendEmail(requestResetWsTo.email(), "Test", token);

        UserEntity userByEmail = getUserByEmail(requestResetWsTo.email());

        if ( notFound( userByEmail ) ){
            throw new UsernameNotFoundException("Username " + requestResetWsTo.email() + " not found in db");
        }

        return ResponseEntity.ok(Response.of(true));
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
     */
}
