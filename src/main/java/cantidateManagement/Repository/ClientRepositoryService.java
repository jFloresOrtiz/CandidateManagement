package cantidateManagement.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cantidateManagement.Entities.Client;
import cantidateManagement.Models.Candidate;
import cantidateManagement.Models.Metrics;

@Service
public class ClientRepositoryService {
    
     @Autowired
    private CandidatesManagementRepository repository;

    // Método para guardar un nuevo cliente
    public Client saveClient(Client client) {
        return repository.save(client);
    }

    // Método para obtener todos los clientes
    public List<Client> getAllClients() {
        return repository.findAll();
    }

    // Método para obtener un cliente por su id
    public Optional<Client> getClientById(Long id) {
        return repository.findById(id);
    }

    // Método para eliminar un cliente
    public void deleteClient(Long id) {
        repository.deleteById(id);
    }

     public Metrics getAgeMetrics(List<Client> clients) {
        double averageAge = clients.stream().mapToInt(Client::getAge).average().orElse(0);
        double stdDeviation = Math.sqrt(clients.stream().mapToDouble(c -> Math.pow(c.getAge() - averageAge, 2)).average().orElse(0));
        Metrics response = new Metrics();
        response.averageAge = averageAge;
        response.standardDeviation = stdDeviation;
        return response;
    }
}
