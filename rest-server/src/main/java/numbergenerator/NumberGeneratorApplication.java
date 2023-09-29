package numbergenerator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"games", "history", "results", "controllers"})
public class NumberGeneratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(NumberGeneratorApplication.class, args);
    }

}
