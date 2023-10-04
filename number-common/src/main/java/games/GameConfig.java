package games;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Bean;

import java.util.Random;

@SuppressWarnings("unused")
@Getter
@Setter
public class GameConfig {

    private String filePath;

    private String resultsPattern;

    private int maxMainNumberValue=70;

    private int maxBallNumberValue=25;


    @Bean
    public Random megaRandomNumberGenerator() {
        return new Random();
    }

}
