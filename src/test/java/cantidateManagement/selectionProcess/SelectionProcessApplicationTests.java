package cantidateManagement.selectionProcess;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
import cantidateManagement.Domain.Models.Candidate;
import cantidateManagement.Domain.Models.CandidateCalculated;
import cantidateManagement.Domain.Models.Metrics;

@SpringBootTest
@ExtendWith(SpringExtension.class) 
public class SelectionProcessApplicationTests {

    @Autowired
    private candidateController clientController; 

    @MockBean
    private ICandidateService _service; 

    @Test
    void test_save_client() {
        Candidate requestClient = new Candidate();
        requestClient.name = "Johan";
        requestClient.lastName = "Flores";
        requestClient.age = 26;
        requestClient.birthdate = "1998-10-19";

        ResponseNonData mockResponse = new ResponseNonData();
        mockResponse.setStatusCode(200);
        mockResponse.setMessage("client created");

        when(_service.SaveClient(requestClient)).thenReturn(ResponseEntity.ok(mockResponse));

        ResponseEntity<ResponseNonData> response = clientController.SaveClient(requestClient);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("client created", response.getBody().getMessage());

        verify(_service, times(1)).SaveClient(requestClient);
    }

	@Test
    void test_GetAllMetrics() {

        ResponseApi<Metrics> mockResponse = new ResponseApi<>();
        mockResponse.setStatusCode(200);
        mockResponse.setMessage("metrics received correctly");

        when(_service.GetMetrics()).thenReturn(ResponseEntity.ok(mockResponse));

        ResponseEntity<ResponseApi<Metrics>> response = clientController.GetAllMetrics();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("metrics received correctly", response.getBody().getMessage());

        verify(_service, times(1)).GetMetrics();
    }

    @Test
    void test_GetAllClients() {
        ResponseApiList<CandidateCalculated> mockResponse = new ResponseApiList<>();
        mockResponse.setStatusCode(200);
        mockResponse.setMessage("client list received correctly");

        when(_service.GetAllClients()).thenReturn(ResponseEntity.ok(mockResponse));

        ResponseEntity<ResponseApiList<CandidateCalculated>> response = clientController.GetAllClients();

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("client list received correctly", response.getBody().getMessage());

        verify(_service, times(1)).GetAllClients();
    }
}
