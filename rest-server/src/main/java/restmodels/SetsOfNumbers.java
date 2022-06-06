package restmodels;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.NonNull;

import java.util.List;

@AllArgsConstructor
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class SetsOfNumbers {

    @NonNull private List<Integer> mainNumberList;

    @NonNull private Integer ballNumber;
}
