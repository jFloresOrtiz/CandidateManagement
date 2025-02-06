package cantidateManagement.Domain.Models;

import lombok.Data;

@Data
public class Candidate {
    public String name;
    public String lastName;
    public int age;
    public String birthdate;
}
