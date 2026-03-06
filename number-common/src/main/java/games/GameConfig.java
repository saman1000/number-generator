package games;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GameConfig {

    private String filePath;

    private String resultsPattern;

    private int maxMainNumberValue=70;

    private int maxBallNumberValue=25;

    private int mainNumberSetSize;

}
