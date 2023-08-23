package systemData.Controller.systemDataControllers;


import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import systemData.models.BasicData.TariffPlan.Joins.TariffJoinFee;
import systemData.models.BasicData.TariffPlan.TariffPlan;
import systemData.payload.response.Response;
import systemData.services.SystemDataServices.TariffPlanService;

import javax.validation.Valid;
import java.util.List;
@RestController
@CrossOrigin("*")
@RequestMapping("TariffPlan")
@Validated
public class TariffPlanController {

     @Autowired
     TariffPlanService tariffPlanService;

    @GetMapping("all")
    public ResponseEntity<?> AllPlans() throws NotFoundException {
        List<TariffPlan> tariffPlans = tariffPlanService.getAllTariff();
        if(tariffPlans == null) throw new NotFoundException("No Rows");
        return  ResponseEntity.ok(new Response(200,"Successful Request",tariffPlans));
    }

    @GetMapping("{code}")
    public ResponseEntity<?> oneTariffById(@PathVariable String code) throws NotFoundException {
        TariffPlan tariffPlan = tariffPlanService.getPlan(code);
        if(tariffPlan != null) return  ResponseEntity.ok(new Response(200,"Successful Request",tariffPlan));
        throw new NotFoundException("Id doesn't exist");
    }

    @DeleteMapping("{code}")
    public ResponseEntity<?> DeleteTariff(@PathVariable String code) throws NotFoundException {
        if(tariffPlanService.DeletePlanAndValueAndCountryPlan(code)) return  ResponseEntity.
                ok( new Response(200,"Plan is deleted","Successful Request"));
        throw new NotFoundException("Id doesn't exist");
    }
    @PostMapping
    public ResponseEntity<?> CreateTariffCode(@Valid @RequestBody TariffPlan tariffPlan) throws NotFoundException {
        TariffPlan created = tariffPlanService.CreateTariffPlan(tariffPlan);
        return  ResponseEntity.ok(new Response(200,"Plan is created",created));
    }
    @GetMapping("TariffJoinsFee/{code}")
    public ResponseEntity<?> TariffJoinsFee(@PathVariable String code) throws NotFoundException {
        List<TariffJoinFee> tariffJoinFees = tariffPlanService.getTariffJoinsFee(code);
        if(tariffJoinFees == null)  throw new NotFoundException("Id doesn't exist");
        return ResponseEntity.ok(new Response(200,"Request Successful",tariffJoinFees));
    }

    @PatchMapping("{code}")
    public ResponseEntity<?> UpdateTariff(@PathVariable String code,@RequestBody TariffPlan tariffPlan) throws NotFoundException {
        TariffPlan updated = tariffPlanService.UpdateTariffPlan(code,tariffPlan);
        if(updated == null)   throw new NotFoundException("Id doesn't exist");
        return ResponseEntity.ok((new Response(200,"Plan is updated",updated)));
    }
    @PatchMapping("{code}/values")
    public ResponseEntity<?> UpdateTariffValues(@PathVariable String code,@RequestBody List<TariffJoinFee> tariffJoinFees) throws NotFoundException {
        boolean valid = tariffPlanService.UpdateTariffValue(tariffJoinFees,code);
        if(valid)  return ResponseEntity.ok(
                (new Response(200,"Extra Fees is updated",tariffPlanService.getTariffJoinsFee(code))));
        throw new NotFoundException("Id doesn't exist");
    }

    @PostMapping("AdvancedSearchByAllConditions")
    public ResponseEntity<?> AdvancedSearchByAllConditions(@RequestBody TariffPlan tariffPlan)  {
        List<TariffPlan> tariffPlans = tariffPlanService.searchWithAllConditions(tariffPlan);
        if(tariffPlans.isEmpty())  return ResponseEntity.status(HttpStatus.NOT_FOUND).body( new Response(404,"Not Found","Not Found data"));;
        return  ResponseEntity.ok(new Response(200,"Request Successful",tariffPlans));
    }



}

