package com.jardvcode.erp.authentication.controller;

import com.jardvcode.erp.authentication.dto.*;
import com.jardvcode.erp.authentication.service.PasswordService;
import com.jardvcode.erp.authentication.service.AuthenticationService;
import com.jardvcode.erp.shared.domain.ResponseWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "auth")
public final class AuthenticationController {

    private final AuthenticationService signInService;
    private final PasswordService passwordResetService;

    public AuthenticationController(AuthenticationService signInService, PasswordService passwordResetService) {
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

    @PostMapping("/reset-password")
    public ResponseEntity<ResponseWrapper<ResetPasswordDTO>> resetPassword(@RequestBody ResetPasswordDTO dto) {
        return ResponseWrapper.ok(passwordResetService.reset(dto));
    }

    @PostMapping("/change-password")
    public ResponseEntity<ResponseWrapper<Void>> changePassword(@RequestBody ChangePasswordDTO dto) {
        passwordResetService.change(dto);
        return ResponseWrapper.ok(null, "Tu contraseña ha sido actualizada correctamente. Ya puedes acceder a tu cuenta.");
    }

}