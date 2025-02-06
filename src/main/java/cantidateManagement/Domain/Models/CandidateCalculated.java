package cantidateManagement.Domain.Models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CandidateCalculated {
    
    public String name;
    public String lastName;
    public int age;
    public String birthdate;
    public int lifeExpectancyRemaining;
}
