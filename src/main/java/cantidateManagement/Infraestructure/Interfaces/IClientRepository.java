package cantidateManagement.Infraestructure.Interfaces;

import java.util.List;
import java.util.Optional;

import cantidateManagement.Domain.Entities.Client;
import cantidateManagement.Domain.Models.Metrics;

public interface IClientRepository {
    Client saveClient(Client client);
    List<Client> getAllClients();
    Optional<Client> getClientById(Long id);
    void deleteClient(Long id);
    Metrics getAgeMetrics(List<Client> clients);
}
