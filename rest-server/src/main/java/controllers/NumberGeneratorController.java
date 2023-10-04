package controllers;

import history.ChanceMethod;
import history.IGameChanceRecordGenerator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import restmodels.SetsOfNumbers;

import java.util.List;

@SuppressWarnings("unused")
@RestController
public record NumberGeneratorController(
        IGameChanceRecordGenerator gamesNumberGeneratorService) {

    @GetMapping("/generate")
    public SetsOfNumbers[] generateNumbers(final @RequestParam int numberOfSets, final @RequestParam ChanceMethod chanceMethod) {
        SetsOfNumbers[] generatedNumberSets = new SetsOfNumbers[numberOfSets];
        List<Integer>[] generatedMainNumberSets = gamesNumberGeneratorService.generateMainNumbers("mega", chanceMethod, numberOfSets);
        Integer[] generatedBallNumbers = gamesNumberGeneratorService.generateBallNumbers("mega", chanceMethod, numberOfSets);

        for (int counter = 0; counter < numberOfSets; counter++) {
            generatedNumberSets[counter] =
                    new SetsOfNumbers(generatedMainNumberSets[counter], generatedBallNumbers[counter]);
        }

        return generatedNumberSets;
    }

    @GetMapping("/generate/{game}")
    public SetsOfNumbers[] generateNumbers(final @PathVariable String game, final @RequestParam int numberOfSets, final @RequestParam ChanceMethod chanceMethod) {
        SetsOfNumbers[] generatedNumberSets = new SetsOfNumbers[numberOfSets];
        List<Integer>[] generatedMainNumberSets = gamesNumberGeneratorService.generateMainNumbers(game, chanceMethod, numberOfSets);
        Integer[] generatedBallNumbers = gamesNumberGeneratorService.generateBallNumbers(game, chanceMethod, numberOfSets);

        for (int counter = 0; counter < numberOfSets; counter++) {
            generatedNumberSets[counter] =
                    new SetsOfNumbers(generatedMainNumberSets[counter], generatedBallNumbers[counter]);
        }

        return generatedNumberSets;
    }

}
