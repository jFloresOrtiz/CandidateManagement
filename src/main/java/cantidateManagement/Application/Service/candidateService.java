package cantidateManagement.Application.Service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import cantidateManagement.Domain.Dto.ResponseApi;
import cantidateManagement.Domain.Dto.ResponseApiList;
import cantidateManagement.Domain.Dto.ResponseNonData;
import cantidateManagement.Domain.Dto.ValidateObject;
import cantidateManagement.Domain.Entities.Client;
import cantidateManagement.Domain.Mapers.ValidateCandidate;
import cantidateManagement.Domain.Models.Candidate;
import cantidateManagement.Domain.Models.CandidateCalculated;
import cantidateManagement.Domain.Models.Metrics;
import cantidateManagement.Infraestructure.Interfaces.IClientRepository;
import cantidateManagement.Shared.Utilities.ILoggerService;

@Service
public class candidateService implements ICandidateService {
    
    @Autowired
    private IClientRepository _clientRepository;

    @Autowired
    private final ILoggerService _logger;

    public candidateService(IClientRepository clientRepository, ILoggerService logger){
        _clientRepository = clientRepository;
        _logger = logger;
    }

    public ResponseEntity<ResponseNonData> SaveClient(Candidate candidate){
        ResponseNonData response = new ResponseNonData();
        try{         

            ObjectMapper objectMapper = new ObjectMapper();        
            _logger.logInformation("request cliente: " + objectMapper.writeValueAsString(candidate));

            ValidateObject responseValidate = ValidateCandidate.ValidateCandidate(candidate);
            if(responseValidate.parameter.isEmpty()){
                _clientRepository.saveClient(responseValidate.client);
                response.setMessage("Client created");
                response.setStatusCode(201);

                _logger.logInformation("client created");

                 return ResponseEntity.status(HttpStatus.CREATED).body(response);
            }
            else{
                response.setMessage(responseValidate.parameter);
                response.setStatusCode(400);
                _logger.logError(responseValidate.parameter);
                return ResponseEntity.badRequest().body(response);
            }          
        }catch(Exception e){
            response.setMessage(e.getMessage());
            response.setStatusCode(500);
            _logger.logError(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }

    public ResponseEntity<ResponseApi<Metrics>> GetMetrics(){
        ResponseApi<Metrics> response = new ResponseApi<>();
        try{
            List<Client> clientsList = _clientRepository.getAllClients();
            Metrics metricas = _clientRepository.getAgeMetrics(clientsList);
            response.data = metricas;
            response.message = "metrics received correctly";
            response.statusCode = 200;
            _logger.logInformation("metrics received correctly");
            return ResponseEntity.ok(response); 

        }catch(Exception e){
            response.data = null;
            response.message = e.getMessage();
            response.statusCode = 500;
            _logger.logError(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
                  
    }

    public ResponseEntity<ResponseApiList<CandidateCalculated>> GetAllClients(){
        ResponseApiList<CandidateCalculated> response = new ResponseApiList();
        try{
            List<Client> clientsList = _clientRepository.getAllClients();
            if(clientsList.size() == 0){
                response.data = null;
                response.message = "No clients found";
                response.statusCode = 404;
                _logger.logError("No clients found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            List<CandidateCalculated> calculatedClients = clientsList.stream()
            .map(client -> new CandidateCalculated(
                client.getName(),             
                client.getLastname(),         
                client.getAge(),             
                client.getBirthdate(),        
                calculateLifeExpectancy(client.getAge()) 
            ))
            .collect(Collectors.toList());
            response.data = calculatedClients;
            response.message = "client list received correctly";
            response.statusCode = 200; 
            _logger.logInformation("client list received correctly");
            return ResponseEntity.ok(response);

        }catch(Exception e){
            response.data = null;
            response.message = e.getMessage();
            response.statusCode = 500;
            _logger.logError(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
       
    }

    private int calculateLifeExpectancy(int age) {
        int lifeExpectancy = 80;  
        return lifeExpectancy - age;  
    }
}
