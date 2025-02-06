package cantidateManagement.Infraestructure.Repository;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import cantidateManagement.Domain.Entities.Client;
import cantidateManagement.Domain.Models.Metrics;
import cantidateManagement.Infraestructure.Interfaces.ICandidatesManagementRepository;
import cantidateManagement.Infraestructure.Interfaces.IClientRepository;

@Repository
public class ClientRepository implements IClientRepository{
    
     @Autowired
    private ICandidatesManagementRepository repository;

    public Client saveClient(Client client) {
        return repository.save(client);
    }

    public List<Client> getAllClients() {
        return repository.findAll();
    }

    public Optional<Client> getClientById(Long id) {
        return repository.findById(id);
    }

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
