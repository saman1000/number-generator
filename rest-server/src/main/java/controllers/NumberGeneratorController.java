package controllers;

import history.ChanceMethod;
import history.MegaChanceRecordGenerator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import restmodels.SetsOfNumbers;

@SuppressWarnings("unused")
@RestController
public record NumberGeneratorController(
        MegaChanceRecordGenerator megaNumberGeneratorService) {

    @GetMapping("/generate")
    public SetsOfNumbers[] generateNumbers(final @RequestParam int numberOfSets, final @RequestParam ChanceMethod chanceMethod) {
        SetsOfNumbers[] generatedNumberSets = new SetsOfNumbers[numberOfSets];
        for (int counter = numberOfSets; counter > 0; counter--) {
            generatedNumberSets[counter - 1] =
                    new SetsOfNumbers(
                            megaNumberGeneratorService.generateMainNumbers(chanceMethod),
                            megaNumberGeneratorService.generateBallNumber(chanceMethod, 1)[0]
                    );
        }

        return generatedNumberSets;
    }
}
