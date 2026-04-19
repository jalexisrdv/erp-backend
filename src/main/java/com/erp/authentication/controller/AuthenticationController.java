package com.erp.authentication.controller;

import com.erp.authentication.dto.*;
import com.erp.authentication.dto.LoginResponseDTO;
import com.erp.authentication.service.PasswordResetService;
import com.erp.authentication.service.AuthenticationService;
import com.erp.shared.domain.ResponseWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "auth")
public final class AuthenticationController {

    private final AuthenticationService signInService;
    private final PasswordResetService passwordResetService;

    public AuthenticationController(AuthenticationService signInService, PasswordResetService passwordResetService) {
        this.signInService = signInService;
        this.passwordResetService = passwordResetService;
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseWrapper<LoginResponseDTO>> login(@RequestBody LoginRequestDTO dto) {
        return ResponseWrapper.ok(signInService.login(dto.username(), dto.password()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<ResponseWrapper<LoginResponseDTO>> refresh(@RequestBody RefreshRequestDTO dto) {
        return ResponseWrapper.ok(signInService.refreshToken(dto.refreshToken()));
    }

    @PostMapping("/reset-password/request-token")
    public ResponseEntity<ResponseWrapper<ResetPasswordTokenDTO>> generateResetPasswordToken(@RequestBody ResetPasswordTokenDTO dto) {
        return ResponseWrapper.ok(passwordResetService.generateToken(dto));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ResponseWrapper<Void>> resetPassword(@RequestBody ResetPasswordDTO dto) {
        passwordResetService.reset(dto);
        return ResponseWrapper.ok(null, "Tu contraseña ha sido actualizada correctamente. Ya puedes acceder a tu cuenta.");
    }

}