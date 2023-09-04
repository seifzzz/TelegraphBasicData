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

import java.util.ArrayList;
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


    public List<TariffFeeReq> getAddValue(Integer item) throws NotFoundException {
        List<TariffPlan_ExtraFee> planExtraFeeList = tariffFeesRepo.FindByItemNo(item);
        if(planExtraFeeList.isEmpty()) throw new NotFoundException("id doesn't exist");
        List<TariffFeeReq> list = new ArrayList<>();
        for(TariffPlan_ExtraFee i :planExtraFeeList) list.add(new TariffFeeReq(i));
        return list;
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

    public List<TariffFeeReq> updateAddValue(List<TariffFeeReq> req, Integer item) throws NotFoundException {
        if(req.isEmpty()) throw new NotFoundException("id doesn't exist");
        List<TariffFeeReq> list = new ArrayList<>();

        for(TariffFeeReq i : req){
            if(tariffPlanRepo.findById(i.getPlanCode()).isPresent() && extraFeesRepo.findById(item).isPresent()) {
                i.setItemNo(item);
                tariffFeesRepo.UpdateValid(i.getPlanCode(), i.getItemNo(), i.getValid());

            }
            else {
                throw new NotFoundException("Invalid plan code or fee code");
            }
            }
           return getAddValue(item);
    }

    }


