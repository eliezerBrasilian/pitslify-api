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

    @Value("${prod.access.token}")
    private String PROD_ACCESS_TOKEN;

    @Value("${test.access.token}")
    private final String TEST_ACCESS_TOKEN = "TEST-7951072446836198-072618-2f79178cf1345b06d3118bf10ba8e5a1-618365626";

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry){
        var allowedOrigins = originPatterns.split(",");

        corsRegistry
                .addMapping("/**")
                .allowedMethods("*")
                .allowedOrigins(allowedOrigins)
                .allowCredentials(true);
    }
}
