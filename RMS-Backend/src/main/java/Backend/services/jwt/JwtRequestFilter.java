package Backend.services.jwt;

import Backend.services.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import org.springframework.stereotype.Component;

import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;



    @Override
    protected void doFilterInternal(

            HttpServletRequest request,

            HttpServletResponse response,

            FilterChain filterChain

    ) throws ServletException, IOException {



        // ================= GET REQUEST PATH =================

        String path = request.getServletPath();



        // ================= SKIP PUBLIC ENDPOINTS =================

        if (

                path.equals("/authenticate")

                        || path.equals("/customer/sign-up")

                        || path.equals("/owner/sign-up")

        ) {

            filterChain.doFilter(request, response);

            return;
        }



        // ================= GET AUTH HEADER =================

        String authHeader = request.getHeader("Authorization");

        String token = null;

        String username = null;



        // ================= EXTRACT TOKEN =================

        if (

                authHeader != null

                        && authHeader.startsWith("Bearer ")

        ) {

            token = authHeader.substring(7);

            username = jwtUtil.extractUsername(token);
        }



        // ================= VALIDATE TOKEN =================

        if (

                username != null

                        && SecurityContextHolder

                        .getContext()

                        .getAuthentication() == null

        ) {

            UserDetails userDetails =

                    userDetailsService

                            .loadUserByUsername(username);



            // ================= CHECK TOKEN =================

            if (

                    jwtUtil.validateToken(token, userDetails)

            ) {

                UsernamePasswordAuthenticationToken authToken =

                        new UsernamePasswordAuthenticationToken(

                                userDetails,

                                null,

                                userDetails.getAuthorities()
                        );



                authToken.setDetails(

                        new WebAuthenticationDetailsSource()

                                .buildDetails(request)
                );



                SecurityContextHolder

                        .getContext()

                        .setAuthentication(authToken);
            }
        }



        // ================= CONTINUE FILTER =================

        filterChain.doFilter(request, response);
    }
}