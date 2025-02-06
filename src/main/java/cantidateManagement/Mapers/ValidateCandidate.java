package cantidateManagement.Mapers;

import java.time.format.DateTimeFormatter;

import cantidateManagement.Entities.Client;
import cantidateManagement.Models.Candidate;

import java.time.LocalDateTime;

public  class ValidateCandidate {
    
    public static Client ValidateCandidate(Candidate candidate){
        LocalDateTime now = LocalDateTime.now();
        
        Client client = new Client();
        client.setName(candidate.name);
        client.setLastname(candidate.lastName);
        client.setAge(candidate.age);
        client.setBirthdate(candidate.birthdate);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = now.format(formatter);

        client.setCreationdate(formattedDate);

        return client;
    }
}
