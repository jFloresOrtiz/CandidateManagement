package cantidateManagement.Api.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cantidateManagement.Application.Service.ICandidateService;
import cantidateManagement.Domain.Dto.ResponseApi;
import cantidateManagement.Domain.Dto.ResponseApiList;
import cantidateManagement.Domain.Dto.ResponseNonData;
import cantidateManagement.Domain.Models.Candidate;
import cantidateManagement.Domain.Models.CandidateCalculated;
import cantidateManagement.Domain.Models.Metrics;
import cantidateManagement.Shared.Utilities.ILoggerService;

@RestController
@RequestMapping("api/management")
public class candidateController {

    @Autowired
    private final ICandidateService _service;

    @Autowired
    private final ILoggerService _logger;
    
    public candidateController(ICandidateService service, ILoggerService logger){
        _service = service;
        _logger = logger;
    }

    @PostMapping("/saveclient")
    public ResponseEntity<ResponseNonData> SaveClient(@RequestBody Candidate requestClient){  
        _logger.logInformation("begin endpoint /saveclient");    
        ResponseEntity<ResponseNonData> response = _service.SaveClient(requestClient);
        _logger.logInformation("end endpoint /saveclient");
        _logger.logInformation("/**/");
        return response;
    }

    @GetMapping("/getmetrics")
    public ResponseEntity<ResponseApi<Metrics>> GetAllMetrics(){
        _logger.logInformation("begin endpoint /getmetrics");  
        ResponseEntity<ResponseApi<Metrics>> response = _service.GetMetrics();
       _logger.logInformation("end endpoint /getmetrics");
       _logger.logInformation("/**/");
        return response;
    }

    @GetMapping("/getallclients")
    public ResponseEntity<ResponseApiList<CandidateCalculated>> GetAllClients(){
        _logger.logInformation("begin endpoint /getallclients");  
        ResponseEntity<ResponseApiList<CandidateCalculated>> response = _service.GetAllClients();
       _logger.logInformation("end endpoint /getallclients");
       _logger.logInformation("/**/");
       return response;
    }
}
