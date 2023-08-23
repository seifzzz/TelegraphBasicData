package systemData.Controller.systemDataControllers;

import javassist.NotFoundException;
import systemData.models.BasicData.IncidentType.IncidentType;
import systemData.payload.response.Response;
import systemData.services.SystemDataServices.IncidentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("IncidentType")
public class IncidentTypeController {
    @Autowired
    IncidentTypeService incidentTypeService;

    @GetMapping("all")
    public ResponseEntity<?> AllIncidentType() throws NotFoundException {
        List<IncidentType>  incidentTypes = incidentTypeService.getAllTypes();
        if(incidentTypes == null || incidentTypes.isEmpty()) throw new NotFoundException("No Rows");
        return  ResponseEntity.ok(new Response(200,"All Incident Types",incidentTypes));
    }

    @DeleteMapping("{code}")
    public ResponseEntity<?> DeleteIncidentType(@PathVariable String code) throws NotFoundException {
        if(incidentTypeService.DeleteOne(code)) return ResponseEntity.ok(new Response(200,"Incident Status is deleted","Successful request"));
        throw new NotFoundException("Id doesn't exist");
    }

    @PostMapping
    public ResponseEntity<?> NewIncident(@RequestBody IncidentType incidentType) throws NotFoundException {
        IncidentType newOne =  incidentTypeService.CreateOne(incidentType);
        if(newOne == null) throw new NotFoundException("Id already exist");
        return ResponseEntity.ok(new Response(200,"Incident Type is created",newOne));
    }

    @PatchMapping
    public ResponseEntity<?> UpdateIncident(@RequestBody IncidentType incidentType) throws NotFoundException {
        IncidentType updated = incidentTypeService.UpdateOne(incidentType);
        if(updated == null) throw new NotFoundException("Id doesn't exist");
        return ResponseEntity.ok(new Response(200,"Incident Type is updated",updated));
    }
    @GetMapping("{code}")
    public ResponseEntity<?> IncidentOne(@PathVariable String code) throws NotFoundException {
        IncidentType incidentType = incidentTypeService.getOneByCode(code);
        if(incidentType == null) throw new NotFoundException("Id doesn't exist");
        return  ResponseEntity.ok(new Response(200,"Successful Request",incidentType));
    }
}
