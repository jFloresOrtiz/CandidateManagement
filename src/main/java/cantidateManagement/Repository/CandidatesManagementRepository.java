package cantidateManagement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cantidateManagement.Entities.Client;

@Repository
public interface CandidatesManagementRepository  extends JpaRepository<Client, Long>{

}
