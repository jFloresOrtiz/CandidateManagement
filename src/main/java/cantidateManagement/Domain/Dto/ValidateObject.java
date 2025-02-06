package cantidateManagement.Domain.Dto;

import cantidateManagement.Domain.Entities.Client;
import lombok.Data;

@Data
public class ValidateObject {
    public Client client;
    public String parameter;
}
