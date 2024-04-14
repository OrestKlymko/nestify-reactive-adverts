package nesttify.backend.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.CorsConfigurationSource
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource


@Configuration
@EnableWebFluxSecurity
class SecurityConfig {

    @Bean
    fun securityWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http
            .csrf { it.disable() }  // Disabling CSRF for simplicity, consider your security requirements
            .cors { it.configurationSource(corsConfigurationSource()) }
            .authorizeExchange { it.anyExchange().permitAll() }  // Use authorizeExchange for reactive
            .build()
    }

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration().apply {
            allowedOrigins = listOf("http://localhost:8080", "http://localhost:3000", "https://frontend-nestify.vercel.app")
            allowedMethods = listOf("GET", "POST")
        }
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

}