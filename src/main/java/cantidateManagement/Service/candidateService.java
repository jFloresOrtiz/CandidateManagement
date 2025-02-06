package cantidateManagement.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import cantidateManagement.Repository.ClientRepositoryService;
import cantidateManagement.Dto.ResponseApi;
import cantidateManagement.Dto.ResponseApiList;
import cantidateManagement.Dto.ResponseNonData;
import cantidateManagement.Entities.Client;
import cantidateManagement.Mapers.ValidateCandidate;
import cantidateManagement.Models.Candidate;
import cantidateManagement.Models.CandidateCalculated;
import cantidateManagement.Models.Metrics;

@Service
public class candidateService {
     @Autowired
    private ClientRepositoryService _clientRepository;

    public candidateService(ClientRepositoryService clientRepository){
        _clientRepository = clientRepository;
    }

    public ResponseEntity<ResponseApi<ResponseNonData>> SaveClient(Candidate candidate){
        ResponseApi<ResponseNonData> response = new ResponseApi<>();

        Client client = ValidateCandidate.ValidateCandidate(candidate);

        _clientRepository.saveClient(client);

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<ResponseApi<Metrics>> GetMetrics(){
        ResponseApi<Metrics> response = new ResponseApi<>();

        List<Client> clientsList = _clientRepository.getAllClients();
        Metrics metricas = _clientRepository.getAgeMetrics(clientsList);
        response.data = metricas;
        response.message = "";
        response.statusCode = 200;

        return ResponseEntity.ok(response);      
    }

    public ResponseEntity<ResponseApiList<CandidateCalculated>> GetAllClients(){
        ResponseApiList<CandidateCalculated> response = new ResponseApiList();
        List<Client> clientsList = _clientRepository.getAllClients();

        List<CandidateCalculated> calculatedClients = clientsList.stream()
        .map(client -> new CandidateCalculated(
            client.getName(),             // Nombre del cliente
            client.getLastname(),         // Apellido del cliente
            client.getAge(),              // Edad del cliente
            client.getBirthdate(),        // Fecha de nacimiento del cliente
            calculateLifeExpectancy(client.getAge()) // Calcular la esperanza de vida restante
        ))
        .collect(Collectors.toList());
        response.data = calculatedClients;
        response.message = "";
        response.statusCode = 200;

        return ResponseEntity.ok(response);
    }

    private int calculateLifeExpectancy(int age) {
        int lifeExpectancy = 80;  // Promedio de esperanza de vida
        return lifeExpectancy - age;  // Se calcula el número de años que le quedarían
    }
}
