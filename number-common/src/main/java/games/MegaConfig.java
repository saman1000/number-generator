package games;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Random;

@SuppressWarnings("unused")
@Configuration
@ConfigurationProperties(prefix = "mega")
@Getter
@Setter
public class MegaConfig {

    private String filePath;

    private String resultsPattern;

    private int maxMainNumberValue=70;

    private int maxBallNumberValue=25;


    @Bean
    public Random megaRandomNumberGenerator() {
        return new Random();
    }

}
