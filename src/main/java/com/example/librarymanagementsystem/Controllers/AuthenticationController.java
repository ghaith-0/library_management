package com.example.librarymanagementsystem.Controllers;


import com.example.librarymanagementsystem.Configs.APIRequestConfigs.SignInRequest;
import com.example.librarymanagementsystem.Configs.APIResponseConfigs.APIResponse;
import com.example.librarymanagementsystem.Configs.APIResponseConfigs.JwtAuthenticationResponse;
import com.example.librarymanagementsystem.Services.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/auth")
    public ResponseEntity<APIResponse<JwtAuthenticationResponse>> signIn(@RequestBody SignInRequest request) {
        JwtAuthenticationResponse token = authenticationService.signIn(request);
        APIResponse<JwtAuthenticationResponse> response = new APIResponse<>(HttpStatus.OK.value(), true, token, HttpStatus.OK.getReasonPhrase());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}