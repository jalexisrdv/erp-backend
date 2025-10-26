package com.erp.authentication.controller;

import com.erp.authentication.dto.*;
import com.erp.authentication.dto.SignInResponseDTO;
import com.erp.authentication.service.PasswordResetService;
import com.erp.authentication.service.SignInService;
import com.erp.shared.domain.ResponseWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "auth")
public final class AuthenticationController {

    private final SignInService signInService;
    private final PasswordResetService passwordResetService;

    public AuthenticationController(SignInService signInService, PasswordResetService passwordResetService) {
        this.signInService = signInService;
        this.passwordResetService = passwordResetService;
    }

    @PostMapping("/sign-in")
    public ResponseEntity<ResponseWrapper<SignInResponseDTO>> signIn(@RequestBody SignInRequestDTO dto) {
        return ResponseWrapper.ok(signInService.signIn(dto.username(), dto.password()));
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