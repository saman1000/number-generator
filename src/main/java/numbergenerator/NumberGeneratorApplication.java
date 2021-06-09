package numbergenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"mega", "history", "results", "controllers"})
public class NumberGeneratorApplication {

    private static Logger logger = LoggerFactory.getLogger(NumberGeneratorApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(NumberGeneratorApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner commandLineRunner(MegaChanceRecordGenerator megaNumberGeneratorService) {
//        return args -> {
//            System.out.println("How many?");
//            Scanner inputScanner = new Scanner(System.in);
//            int numberOfSets = Integer.parseUnsignedInt(inputScanner.nextLine());
//            logger.info(String.format("Generating %s sets", numberOfSets));
//            for(int counter = 0; counter < numberOfSets; counter++) {
//                System.out.println(String.format(
//                        "%s: %s",
//                        megaNumberGeneratorService.generateMainNumbers(),
//                        megaNumberGeneratorService.generateBallNumber()
//                ));
//            }
//        };
//    }
}
