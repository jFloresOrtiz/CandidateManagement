package cantidateManagement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import cantidateManagement.Api.Controller.candidateController;
import cantidateManagement.Application.Service.ICandidateService;
import cantidateManagement.Domain.Dto.ResponseApi;
import cantidateManagement.Domain.Dto.ResponseApiList;
import cantidateManagement.Domain.Dto.ResponseNonData;
import cantidateManagement.Domain.Entities.Client;
import cantidateManagement.Domain.Models.Candidate;
import cantidateManagement.Domain.Models.CandidateCalculated;
import cantidateManagement.Domain.Models.Metrics;
import cantidateManagement.Infraestructure.Interfaces.IClientRepository;

@SpringBootTest
@ExtendWith(SpringExtension.class) 
public class SelectionProcessApplicationErrorTests {
    @Autowired
    private candidateController clientController; 

    @MockBean
    private ICandidateService _service; 

    @MockBean
    private IClientRepository _clientRepository; 

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    
    @Test
    void test_SaveClient_DatabaseError() {
        Candidate requestClient = new Candidate();
        requestClient.name = "Johan";
        requestClient.lastName = "Flores";
        requestClient.age = 26;
        requestClient.birthdate = "1998-10-19";
    
        ResponseNonData errorResponse = new ResponseNonData();
        errorResponse.setStatusCode(500);
        errorResponse.setMessage("Database connection failed");
    
        when(_clientRepository.saveClient(any(Client.class)))
            .thenThrow(new RuntimeException("Database connection failed"));

        ResponseEntity<ResponseNonData> response = clientController.SaveClient(requestClient);
     
        assertNull(response);
    } 
        
    @Test
    void test_SaveClient_BadRequest2() {

        Candidate requestClient = new Candidate();
        requestClient.name = ""; 
    
        ResponseNonData errorResponse = new ResponseNonData();
        errorResponse.setStatusCode(400);
        errorResponse.setMessage("the name is required");
    
        when(_service.SaveClient(requestClient))
            .thenReturn(ResponseEntity.badRequest().body(errorResponse));
    
        ResponseEntity<ResponseNonData> response = clientController.SaveClient(requestClient);
    
        assertNotNull(response);
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("the name is required", response.getBody().getMessage());
    
        verify(_service, times(1)).SaveClient(requestClient);
    }
    
    @Test
    void test_GetAllMetrics_InternalServerError() {
        ResponseApi<Metrics> errorResponse = new ResponseApi<>();
        errorResponse.setData(null);
        errorResponse.setMessage("Unexpected server error");
        errorResponse.setStatusCode(500);

        when(_service.GetMetrics()).thenReturn(ResponseEntity.status(500).body(errorResponse));

        ResponseEntity<ResponseApi<Metrics>> response = clientController.GetAllMetrics();

        assertNotNull(response);
        assertEquals(500, response.getStatusCodeValue());
        assertEquals("Unexpected server error", response.getBody().getMessage());
        assertNull(response.getBody().getData());

        verify(_service, times(1)).GetMetrics();
    }

    @Test
    void test_GetAllClients_NotFound() {
        ResponseApiList<CandidateCalculated> errorResponse = new ResponseApiList<>();
        errorResponse.setData(null);
        errorResponse.setMessage("No clients found");
        errorResponse.setStatusCode(404);
    
        when(_service.GetAllClients()).thenReturn(ResponseEntity.status(404).body(errorResponse));
    
        ResponseEntity<ResponseApiList<CandidateCalculated>> response = clientController.GetAllClients();
    
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        assertEquals("No clients found", response.getBody().getMessage());
        assertNull(response.getBody().getData());
    
        verify(_service, times(1)).GetAllClients();
    }

    @Test
    void test_GetAllClients_ErrorInternal() {
        ResponseApiList<CandidateCalculated> errorResponse = new ResponseApiList<>();
        errorResponse.setData(null);
        errorResponse.setMessage("Unexpected server error");
        errorResponse.setStatusCode(500);

        when(_service.GetAllClients()).thenReturn(ResponseEntity.status(500).body(errorResponse));

        ResponseEntity<ResponseApiList<CandidateCalculated>> response = clientController.GetAllClients();

        assertNotNull(response);
        assertEquals(500, response.getStatusCodeValue());
        assertEquals("Unexpected server error", response.getBody().getMessage());
        assertNull(response.getBody().getData());

        verify(_service, times(1)).GetAllClients();
    }
    
}
