package pitslify.api.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Setter
@Getter
@Configuration
public class AppConfig implements WebMvcConfigurer {

    @Value("${cors.originPatterns}")
    private String originPatterns;

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        // Remove espaços adicionais que possam causar problemas
        var allowedOrigins = originPatterns.replace("\"", "").split(",");

        corsRegistry
                .addMapping("/**")
                .allowedMethods("*")
                .allowedOrigins(allowedOrigins)
                .allowCredentials(true)
                .allowedHeaders("*")
                .exposedHeaders("Authorization", "Link", "X-Total-Count")
                .maxAge(3600);
    }
}
