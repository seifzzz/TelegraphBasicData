package systemData.Controller.systemDataControllers;

import javassist.NotFoundException;
import systemData.models.BasicData.TelegraphStatus.TelegraphStatus;
import systemData.payload.response.Response;
import systemData.services.SystemDataServices.TelegraphStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("TelegraphStatus")
public class TelegraphStatusController {

    @Autowired
    TelegraphStatusService telegraphStatusService;

    @GetMapping("all")
    public ResponseEntity<?> AllTelegraphStatus() throws NotFoundException {
        List<TelegraphStatus> telegraphStatuses = telegraphStatusService.getAllStatus();
        if(telegraphStatuses.isEmpty()) throw new NotFoundException("No Rows");
        return  ResponseEntity.ok(new Response(200,"All Telegraph Status",telegraphStatuses));
    }


    @GetMapping("{code}")
    public ResponseEntity<?> getStatusByCode(@PathVariable String code) throws NotFoundException {
        TelegraphStatus telegraphStatus = telegraphStatusService.getOneByCode(code);
        if(telegraphStatus == null) throw new NotFoundException("Id doesn't exist");
        return ResponseEntity.ok(new Response(200,"Successful Request",telegraphStatus));
    }

    @PostMapping
    public ResponseEntity<?> NewTelegraphStatus(@RequestBody TelegraphStatus telegraphStatus) throws NotFoundException {
        TelegraphStatus newOne = telegraphStatusService.CreateOne(telegraphStatus);
        if(newOne == null) throw new NotFoundException("Id already exist");
        return ResponseEntity.ok(new Response(200,"Telegraph Status is created",newOne));
    }

    @DeleteMapping("{code}")
    public ResponseEntity<?> DeleteTelegraphStatus(@PathVariable String code) throws NotFoundException {
        if(telegraphStatusService.DeleteOne(code)) return  ResponseEntity.ok(new Response(200,"Telegraph Status is deleted","Successful Request"));
        throw new NotFoundException("Id doesn't exist");
    }
    @PatchMapping("{code}")
    public ResponseEntity<?> updateTelegraphStatus (@RequestBody TelegraphStatus telegraphStatus) throws NotFoundException {
        TelegraphStatus updated = telegraphStatusService.UpdateOne(telegraphStatus);
        if(updated == null) throw new NotFoundException("Id doesn't exist");
        return ResponseEntity.ok(new Response(200,"Telegraph Status is updated",updated));

    }
}
