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

    @GetMapping("/generate/{game}")
    public SetsOfNumbers[] generateNumbers(final @PathVariable("game") String game,
                                           final @RequestParam("numberOfSets") int numberOfSets,
                                           final @RequestParam("chanceMethod") ChanceMethod chanceMethod) {
        SetsOfNumbers[] generatedNumberSets = new SetsOfNumbers[numberOfSets];
        List<List<Integer>> generatedMainNumberSets = gamesNumberGeneratorService.generateMainNumbers(game, chanceMethod, numberOfSets);
        Integer[] generatedBallNumbers = gamesNumberGeneratorService.generateBallNumbers(game, chanceMethod, numberOfSets);

        for (int counter = 0; counter < numberOfSets; counter++) {
            generatedNumberSets[counter] =
                    new SetsOfNumbers(generatedMainNumberSets.get(counter), generatedBallNumbers[counter]);
        }

        return generatedNumberSets;
    }

}
