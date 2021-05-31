package numbergenerator;

import history.MegaChanceRecordGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Scanner;

@SpringBootApplication(scanBasePackages = {"mega", "history", "results"})
public class NumberGeneratorApplication {

    private static Logger logger = LoggerFactory.getLogger(NumberGeneratorApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(NumberGeneratorApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(MegaChanceRecordGenerator megaNumberGeneratorService) {
        return args -> {
            System.out.println("How many?");
            Scanner inputScanner = new Scanner(System.in);
            int numberOfSets = Integer.parseUnsignedInt(inputScanner.nextLine());
            logger.info(String.format("Generating %s sets", numberOfSets));
            for(int counter = 0; counter < numberOfSets; counter++) {
                System.out.println(String.format(
                        "%s: %s",
                        megaNumberGeneratorService.generateMainNumbers(),
                        megaNumberGeneratorService.generateBallNumber()
                ));
            }
        };
    }
}
