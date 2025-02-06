package cantidateManagement.Application.Service;

import org.springframework.http.ResponseEntity;
import cantidateManagement.Domain.Dto.ResponseApi;
import cantidateManagement.Domain.Dto.ResponseApiList;
import cantidateManagement.Domain.Dto.ResponseNonData;
import cantidateManagement.Domain.Models.Candidate;
import cantidateManagement.Domain.Models.CandidateCalculated;
import cantidateManagement.Domain.Models.Metrics;

public interface ICandidateService {
    ResponseEntity<ResponseNonData> SaveClient(Candidate candidate);
    ResponseEntity<ResponseApi<Metrics>> GetMetrics();
    ResponseEntity<ResponseApiList<CandidateCalculated>> GetAllClients();
}
