package games;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "lottery")
@Getter
@Setter
public class AllConfigs {

    private GameConfig powerball;

    private GameConfig mega;

}
