package systemData.Controller.systemDataControllers;

import javassist.NotFoundException;
import systemData.models.BasicData.RedirectReason.RedirectReason;
import systemData.payload.response.Response;
import systemData.services.SystemDataServices.RedirectReasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("redirectReason")
public class RedirectReasonController {

    @Autowired
    RedirectReasonService redirectReasonService;

    @GetMapping("all")
    public ResponseEntity<?> AllRedirectReason() throws NotFoundException {
        List<RedirectReason> incidentStatuses = redirectReasonService.getAll();
        if(incidentStatuses == null || incidentStatuses.isEmpty()) throw new NotFoundException("No Rows");
        return  ResponseEntity.ok(new Response(200,"All Incident Types",incidentStatuses));
    }

    @DeleteMapping("{code}")
    public ResponseEntity<?> DeleteRedirect(@PathVariable String code) throws NotFoundException {
        if(redirectReasonService.DeleteRedirect(code)) return  ResponseEntity.ok(new Response(200,"Redirect reason is deleted","Successful Request"));
        throw new NotFoundException("Id doesn't exist");
    }

    @PostMapping
    public ResponseEntity<?> NewRedirect(@RequestBody RedirectReason redirectReason) throws NotFoundException {
        RedirectReason newOne = redirectReasonService.saveOne(redirectReason);
        if(newOne == null) throw new NotFoundException("Id already exist");
        return ResponseEntity.ok(new Response(200,"Redirect Reason is created",newOne));
    }

    @PatchMapping
    public ResponseEntity<?> UpdateRedirect(@RequestBody RedirectReason redirectReason) throws NotFoundException {
        RedirectReason updated = redirectReasonService.UpdateOne(redirectReason);
        if(updated == null) throw new NotFoundException("Id doesn't exist");
        return ResponseEntity.ok(new Response(200,"Redirect Reason is updated",updated));
    }

    @GetMapping("{code}")
    public ResponseEntity<?> RedirectOne(@PathVariable String code) throws NotFoundException {
        RedirectReason redirectReason = redirectReasonService.getOne(code);
        if(redirectReason == null) throw new NotFoundException("Id doesn't exist");
        return  ResponseEntity.ok(new Response(200,"Successful Request",redirectReason));
    }

}
