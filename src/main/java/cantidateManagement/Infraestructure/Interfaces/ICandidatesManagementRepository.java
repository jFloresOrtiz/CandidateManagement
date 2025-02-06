package cantidateManagement.Infraestructure.Interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cantidateManagement.Domain.Entities.Client;

@Repository
public interface ICandidatesManagementRepository  extends JpaRepository<Client, Long>{

}
