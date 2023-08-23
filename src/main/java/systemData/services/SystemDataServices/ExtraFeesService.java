package systemData.services.SystemDataServices;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import systemData.models.BasicData.TariffPlan.ExtraFee;
import systemData.models.BasicData.TariffPlan.TariffPlan_ExtraFee;
import systemData.payload.request.TariffFeeReq;
import systemData.repos.SystemDataRepos.ExtraFeesRepo;
import systemData.repos.SystemDataRepos.TariffPlanRepo;
import systemData.repos.SystemDataRepos.TariffPlan_ExtraFeeRepo;

import java.util.List;
import java.util.Optional;

@Service
public class ExtraFeesService {

    @Autowired
    ExtraFeesRepo extraFeesRepo;
    @Autowired
    TariffPlan_ExtraFeeRepo tariffFeesRepo;

     @Autowired
     TariffPlanRepo tariffPlanRepo;

    public List<ExtraFee> allFees() throws NotFoundException {
        List<ExtraFee> fees = extraFeesRepo.findAll();
        if(fees.isEmpty()) throw new NotFoundException("No rows");
        return fees;
    }

    public ExtraFee getById(Integer id) throws NotFoundException {
        Optional<ExtraFee> extraFee = extraFeesRepo.findById(id);
        if(!extraFee.isPresent()) throw new NotFoundException("this fee with this id doesn't exist ");
        return extraFee.get();

    }


    public List<TariffPlan_ExtraFee> getAddValue(Integer item) throws NotFoundException {
        List<TariffPlan_ExtraFee> planExtraFeeList = tariffFeesRepo.FindByItemNo(item);
        if(planExtraFeeList.isEmpty()) throw new NotFoundException("id doesn't exist");
        return planExtraFeeList;
    }

    public boolean deleteFee(Integer item) throws NotFoundException {
        Optional<ExtraFee> extraFee = extraFeesRepo.findById(item);
        if(!extraFee.isPresent()) throw new NotFoundException("id doesn't exist");
        if(getAddValue(item) != null) tariffFeesRepo.DeleteByItemNo(item);

        extraFeesRepo.deleteById(item);
        return true;

    }
    public ExtraFee createFee(ExtraFee fee) throws NotFoundException {
        Optional<ExtraFee> check = extraFeesRepo.findById(fee.getITEM_NO());
        if(check.isPresent()) throw new NotFoundException("id already exist");
        return extraFeesRepo.save(fee);
    }
    public ExtraFee updateFee(ExtraFee fee) throws NotFoundException {
        Optional<ExtraFee> check = extraFeesRepo.findById(fee.getITEM_NO());
        if(!check.isPresent()) throw new NotFoundException("id doesn't exist");
        return extraFeesRepo.save(fee);
    }

    public List<TariffPlan_ExtraFee> updateAddValue(List<TariffFeeReq> req) throws NotFoundException {
        if(req.isEmpty()) throw new NotFoundException("id doesn't exist");


        for(TariffFeeReq i : req){
            if(tariffPlanRepo.findById(i.getPlanCode()).isPresent() && extraFeesRepo.findById(i.getItemNo()).isPresent()) {
                tariffFeesRepo.UpdateValid(i.getPlanCode(), i.getItemNo(), i.getValid());
            }
            else {
                throw new NotFoundException("Invalid plan code or fee code");
            }
            }
           return tariffFeesRepo.FindByItemNo(req.get(0).getItemNo());
    }

    }


