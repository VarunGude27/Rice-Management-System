package Backend.controllers;

import Backend.dtos.AuthenticationRequest;
import Backend.dtos.AuthenticationResponse;
import Backend.dtos.SignupRequestDTO;
import Backend.dtos.UserDto;
import Backend.entites.Client;
import Backend.repository.UserRepository;
import Backend.services.Authentication.AuthService;
import Backend.services.jwt.UserDetailsServiceImpl;
import Backend.services.util.JwtUtil;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    public static final String TOKEN_PREFIX = "Bearer ";

    public static final String HEADER_STRING = "Authorization";



    // ================= CUSTOMER SIGNUP =================

    @PostMapping("/customer/sign-up")
    public ResponseEntity<?> signupUser(
            @RequestBody SignupRequestDTO signupRequestDTO) {

        if (authService.presentEmail(signupRequestDTO.getEmail())) {

            return new ResponseEntity<>(
                    "User Already Exist With This Email",
                    HttpStatus.NOT_ACCEPTABLE
            );
        }

        UserDto createdUser =
                authService.signupUser(signupRequestDTO);

        return new ResponseEntity<>(
                createdUser,
                HttpStatus.OK
        );
    }



    // ================= OWNER SIGNUP =================

    @PostMapping("/owner/sign-up")
    public ResponseEntity<?> signupAdmin(
            @RequestBody SignupRequestDTO signupRequestDTO) {

        if (authService.presentEmail(signupRequestDTO.getEmail())) {

            return new ResponseEntity<>(
                    "Admin Already Exist With This Email",
                    HttpStatus.NOT_ACCEPTABLE
            );
        }

        UserDto createdAdmin =
                authService.signupAdmin(signupRequestDTO);

        return new ResponseEntity<>(
                createdAdmin,
                HttpStatus.OK
        );
    }



    // ================= LOGIN =================

    @PostMapping("/authenticate")
    public void createAuthenticationToken(
            @RequestBody AuthenticationRequest authenticationRequest,
            HttpServletResponse response) throws IOException {

        try {

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getEmail(),
                            authenticationRequest.getPassword()
                    )
            );

        } catch (BadCredentialsException e) {

            throw new BadCredentialsException(
                    "Incorrect Email Or Password",
                    e
            );
        }

        final UserDetails userDetails =
                userDetailsService.loadUserByUsername(
                        authenticationRequest.getEmail()
                );

        final String jwt =
                jwtUtil.generateToken(userDetails.getUsername());

        Client client =
                userRepository.findFirstByEmail(
                        authenticationRequest.getEmail()
                );

        ObjectMapper mapper = new ObjectMapper();

        String jsonResponse = mapper.writeValueAsString(
                Map.of(
                        "userId", client.getId(),
                        "role", client.getRole()
                )
        );

        response.setContentType("application/json");

        response.getWriter().write(jsonResponse);

        response.addHeader(
                "Access-Control-Expose-Headers",
                "Authorization"
        );

        response.addHeader(
                "Access-Control-Allow-Headers",
                "Authorization, X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept, X-Custom-header"
        );

        response.addHeader(
                HEADER_STRING,
                TOKEN_PREFIX + jwt
        );
    }
}