package com.clinic.service;

import com.clinic.dto.request.AdminLoginRequest;
import com.clinic.dto.response.AdminLoginResponse;
import com.clinic.exception.UnauthorizedException;
import com.clinic.security.AdminPrincipal;
import com.clinic.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AdminLoginResponse login(AdminLoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            AdminPrincipal principal = (AdminPrincipal) authentication.getPrincipal();
            String token = jwtService.generateToken(principal);
            return AdminLoginResponse.builder()
                    .token(token)
                    .username(principal.getUsername())
                    .role(principal.getAuthorities().iterator().next().getAuthority())
                    .doctorId(principal.getDoctorId())
                    .build();
        } catch (BadCredentialsException ex) {
            throw new UnauthorizedException("Invalid username or password");
        }
    }

    public Long getAuthenticatedDoctorId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null
                || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken) {
            throw new UnauthorizedException("Authentication required");
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof AdminPrincipal adminPrincipal && adminPrincipal.getDoctorId() != null) {
            return adminPrincipal.getDoctorId();
        }
        throw new UnauthorizedException("Unable to resolve authenticated admin");
    }
}
