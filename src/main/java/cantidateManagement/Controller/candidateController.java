package cantidateManagement.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cantidateManagement.Dto.ResponseApi;
import cantidateManagement.Dto.ResponseApiList;
import cantidateManagement.Dto.ResponseNonData;
import cantidateManagement.Models.Candidate;
import cantidateManagement.Models.CandidateCalculated;
import cantidateManagement.Models.Metrics;
import cantidateManagement.Service.candidateService;

@RestController
@RequestMapping("api/management")
public class candidateController {

    private final candidateService _service;
    
    @Autowired
    public candidateController(candidateService service){
        _service = service;
    }

    @PostMapping("/saveclient")
    public ResponseEntity<ResponseApi<ResponseNonData>> SaveClient(@RequestBody Candidate requestClient){      
        return _service.SaveClient(requestClient);
    }

    @GetMapping("/getmetrics")
    public ResponseEntity<ResponseApi<Metrics>> GetAllMetrics(){
       return _service.GetMetrics();
    }

    @GetMapping("/getallclients")
    public ResponseEntity<ResponseApiList<CandidateCalculated>> GetAllClients(){
       return _service.GetAllClients();
    }
}
