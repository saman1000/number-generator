package controllers;

import history.ChanceMethod;
import history.MegaChanceRecordGenerator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import restmodels.SetsOfNumbers;

import java.util.List;

@SuppressWarnings("unused")
@RestController
public record NumberGeneratorController(
        MegaChanceRecordGenerator megaNumberGeneratorService) {

    @GetMapping("/generate")
    public SetsOfNumbers[] generateNumbers(final @RequestParam int numberOfSets, final @RequestParam ChanceMethod chanceMethod) {
        SetsOfNumbers[] generatedNumberSets = new SetsOfNumbers[numberOfSets];
        List<Integer>[] generatedMainNumberSets = megaNumberGeneratorService.generateMainNumbers(chanceMethod, numberOfSets);
        Integer[] generatedBallNumbers = megaNumberGeneratorService.generateBallNumber(chanceMethod, numberOfSets);

        for (int counter = 0; counter < numberOfSets; counter++) {
            generatedNumberSets[counter] =
                    new SetsOfNumbers(generatedMainNumberSets[counter], generatedBallNumbers[counter]);
        }

        return generatedNumberSets;
    }
}
