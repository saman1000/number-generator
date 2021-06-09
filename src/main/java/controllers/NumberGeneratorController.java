package controllers;

import history.MegaChanceRecordGenerator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import restmodels.SetsOfNumbers;

@RestController
public class NumberGeneratorController {

    private MegaChanceRecordGenerator megaNumberGeneratorService;

    public NumberGeneratorController(MegaChanceRecordGenerator megaNumberGeneratorService) {
        this.megaNumberGeneratorService = megaNumberGeneratorService;
    }

    @GetMapping("/generate")
    public SetsOfNumbers[] generateNumbers(@RequestParam(required = true) int numberOfSets) {
        SetsOfNumbers[] generatedNumberSets = new SetsOfNumbers[numberOfSets];
        for (int counter=numberOfSets; counter > 0; counter--) {
            generatedNumberSets[counter-1] =
                    new SetsOfNumbers(
                            megaNumberGeneratorService.generateMainNumbers(),
                            megaNumberGeneratorService.generateBallNumber()
                    );
        }

        return generatedNumberSets;
    }
}
