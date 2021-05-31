package mega;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
@ConfigurationProperties(prefix = "mega")
@Getter
@Setter
public class Config {

    private String filePath;

    private String resultsPattern;

    @Bean(name = "historyScanner")
    public Path getHistoryScanner() throws IOException {
        return Paths.get(filePath);
    }

}
