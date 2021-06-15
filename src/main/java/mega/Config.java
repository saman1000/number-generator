package mega;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@Configuration
@ConfigurationProperties(prefix = "mega")
@Getter
@Setter
public class Config {

    private String filePath;

    private String resultsPattern;

    private int maxMainNumberValue=70;

    private int maxBallNumberValue=25;


    @Bean
    public Random megaRandomNumberGenerator() {
        return new Random();
    }

}
