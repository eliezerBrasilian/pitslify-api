package pitslify.api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Pitslify API", version = "1", description = "API desenvolvida para o sistema da Pitslify"))

public class MainApplication {
	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
    }

}
