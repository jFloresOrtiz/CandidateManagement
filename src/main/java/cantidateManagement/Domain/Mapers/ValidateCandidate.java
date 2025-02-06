package cantidateManagement.Domain.Mapers;

import java.time.format.DateTimeFormatter;
import cantidateManagement.Domain.Dto.ValidateObject;
import cantidateManagement.Domain.Entities.Client;
import cantidateManagement.Domain.Models.Candidate;

import java.time.LocalDateTime;

public  class ValidateCandidate {
    
    public static ValidateObject ValidateCandidate(Candidate candidate){
        ValidateObject response = new ValidateObject();

        if(candidate.name.isEmpty()){
            response.parameter = "the name is required";          
        }
        else if(candidate.lastName.isEmpty()){
            response.parameter = "the lastname is required"; 
        }
        else if(candidate.age == 0){
            response.parameter = "the age is required"; 

        }else if(candidate.birthdate.isEmpty()){
             response.parameter = "the birthdate is required"; 
        }
        else{
            LocalDateTime now = LocalDateTime.now();     
            Client client = new Client();
            client.setName(candidate.name);
            client.setLastname(candidate.lastName);
            client.setAge(candidate.age);
            client.setBirthdate(candidate.birthdate);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDate = now.format(formatter);
            client.setCreationdate(formattedDate);
            response.client = client;
            response.parameter = "";
        }
       
        return response;
        
    }
}
