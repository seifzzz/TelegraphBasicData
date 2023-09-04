package systemData.Controller.systemDataControllers;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import systemData.models.BasicData.Country.Country;
import systemData.models.BasicData.TariffPlan.ExtraFee;
import systemData.models.BasicData.TariffPlan.TariffPlan_ExtraFee;
import systemData.payload.request.TariffFeeReq;
import systemData.payload.response.Response;
import systemData.services.SystemDataServices.ExtraFeesService;

import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("ExtraFees")
public class ExtraFeeController {

    @Autowired
    ExtraFeesService extraFeesService;

    @GetMapping("all/fees")
    public ResponseEntity<?> AllFees() throws NotFoundException {
        List<ExtraFee> extraFees = extraFeesService.allFees();
        return  ResponseEntity.ok(new Response(200,"All fees",extraFees));
    }
    @GetMapping("all/fees/{item}/plans")
    public ResponseEntity<?> getValuesByItemsNo(@PathVariable Integer item) throws NotFoundException {
        List<TariffFeeReq> extraFees = extraFeesService.getAddValue(item);
        return  ResponseEntity.ok(new Response(200,"Successful request",extraFees));
    }
    @PostMapping("create")
    public ResponseEntity<?> AddFee(@RequestBody ExtraFee fee) throws NotFoundException {
        ExtraFee saved = extraFeesService.createFee(fee);
        return  ResponseEntity.ok(new Response(200,"Fee is created",saved));
    }
    @PatchMapping("update")
    public ResponseEntity<?> updateFee(@RequestBody ExtraFee fee) throws NotFoundException {
        ExtraFee saved = extraFeesService.updateFee(fee);
        return  ResponseEntity.ok(new Response(200,"Fee is updated",saved));
    }

    @PatchMapping("update/{item}/plans")
    public ResponseEntity<?> updateFeeValues(@PathVariable Integer item, @RequestBody List<TariffFeeReq> req) throws NotFoundException {
        List<TariffFeeReq> updated = extraFeesService.updateAddValue(req ,item);
        return  ResponseEntity.ok(new Response(200,"Items is updated",updated));
    }

    @DeleteMapping("all/fees/{item}")
    public ResponseEntity<?> DeleteFeeAndValue(@PathVariable Integer item) throws NotFoundException {
        boolean deleted = extraFeesService.deleteFee(item);
        return  ResponseEntity.ok(new Response(200,"items is deleted","successful Request"));
    }

}
