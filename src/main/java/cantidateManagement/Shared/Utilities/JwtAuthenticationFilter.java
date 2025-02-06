package cantidateManagement.Shared.Utilities;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter  {

    @Value("${jwt.secret}")
    private String secretKey;
     
    private static final List<String> SWAGGER_WHITELIST = List.of(
        "/v3/api-docs",
        "/swagger-ui",
        "/swagger-ui.html"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
            
       
        if (SWAGGER_WHITELIST.stream().anyMatch(request.getRequestURI()::startsWith)) {
            chain.doFilter(request, response);
            return;
        }
       
        String jwt = getJwtFromRequest(request);

        if (StringUtils.hasText(jwt)) {
            try {
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(secretKey.getBytes())
                        .build()
                        .parseClaimsJws(jwt)
                        .getBody();

                String username = claims.getSubject(); 
                if (username != null) {
                     UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            username, null, new ArrayList<>()); 
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (SignatureException ex) {
                enviarRespuestaError(response, HttpServletResponse.SC_UNAUTHORIZED, "Token inválido");
                return;
            }
            catch (ExpiredJwtException ex) {
                enviarRespuestaError(response, HttpServletResponse.SC_UNAUTHORIZED, "Token expirado");
                return;
            }
            
        } else {
            enviarRespuestaError(response, HttpServletResponse.SC_UNAUTHORIZED, "Token no proporcionado");
            return;
        }

        chain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private void enviarRespuestaError(HttpServletResponse response, int estado, String mensaje) throws IOException {
        response.setStatus(estado);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write("{\"message\": \"" + mensaje + "\"}");
        response.getWriter().flush(); 
    }
}