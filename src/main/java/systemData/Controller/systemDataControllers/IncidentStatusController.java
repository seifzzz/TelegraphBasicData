package systemData.Controller.systemDataControllers;

import javassist.NotFoundException;
import systemData.models.BasicData.IncidentStatus.IncidentStatus;
import systemData.payload.response.Response;
import systemData.services.SystemDataServices.IncidentStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("IncidentStatus")
public class IncidentStatusController {

    @Autowired
    IncidentStatusService incidentStatusService;

    @GetMapping("all")
    public ResponseEntity<?> AllIncidentStatus() throws NotFoundException {
        List<IncidentStatus> incidentStatuses = incidentStatusService.getAllStatus();
        if(incidentStatuses == null || incidentStatuses.isEmpty()) throw new NotFoundException("No Rows");
        return  ResponseEntity.ok(new Response(200,"All Incident Status",incidentStatuses));
    }

    @DeleteMapping("{code}")
    public ResponseEntity<?> DeleteIncident(@PathVariable String code) throws NotFoundException {
        if(incidentStatusService.DeleteOne(code)) return  ResponseEntity.ok(new Response(200,"Incident Status is deleted","Successful Request"));
        throw new NotFoundException("Id doesn't exist");
    }

    @PostMapping
    public ResponseEntity<?> NewIncident(@RequestBody IncidentStatus  incidentStatus) throws NotFoundException {
        IncidentStatus newOne = incidentStatusService.CreateOne(incidentStatus);
        if(newOne == null) throw new NotFoundException("Id already exist");
        return ResponseEntity.ok(new Response(200,"Incident Status is created",newOne));
    }

    @PatchMapping
    public ResponseEntity<?> UpdateIncident(@RequestBody IncidentStatus incidentStatus) throws NotFoundException {
        IncidentStatus updated = incidentStatusService.UpdateOne(incidentStatus);
        if(updated == null) throw new NotFoundException("Id doesn't exist");
        return ResponseEntity.ok((new Response(200,"Incident Status is updated",updated)));
    }
    @GetMapping("{code}")
    public ResponseEntity<?> IncidentOne(@PathVariable String code) throws NotFoundException {
        IncidentStatus incidentStatus = incidentStatusService.getOneByCode(code);
        if(incidentStatus == null) throw new NotFoundException("Id doesn't exist");
        return  ResponseEntity.ok(new Response(200,"Successful Request",incidentStatus));
    }
}
