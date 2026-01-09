package com.fadlan.tht.controller;

import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fadlan.tht.security.AuthenticatedUser;

@RestController
@RequestMapping("/api/me")
public class MeController {
    
    @GetMapping
    public Map<String, Object> me(
        @AuthenticationPrincipal AuthenticatedUser user
    ) {
        return Map.of(
            "userId", user.getId(),
            "email", user.getEmail()
        );
    }
}
