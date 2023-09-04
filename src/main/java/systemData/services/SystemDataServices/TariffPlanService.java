package systemData.services.SystemDataServices;

import javassist.NotFoundException;
import systemData.Specification.TariffPlanSpec;
import systemData.models.BasicData.TariffPlan.CountryPlan;
import systemData.models.BasicData.TariffPlan.Joins.TariffJoinFee;
import systemData.models.BasicData.TariffPlan.TariffPlan;
import systemData.models.BasicData.TariffPlan.TariffPlan_ExtraFee;
import systemData.repos.SystemDataRepos.CountryPlanRepo;
import systemData.repos.SystemDataRepos.ExtraFeesRepo;
import systemData.repos.SystemDataRepos.TariffPlanRepo;
import systemData.repos.SystemDataRepos.TariffPlan_ExtraFeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TariffPlanService {


    @Autowired
    TariffPlanRepo tariffPlanRepo;
    @Autowired
    TariffPlan_ExtraFeeRepo tariffPlanExtraFeeRepo;

    @Autowired
    CountryPlanRepo countryPlanRepo;
    @Autowired
    ExtraFeesRepo extraFeesRepo;

    @Autowired
    ExtraFeesService extraFeesService;

     public List<TariffPlan> getAllTariff(){
         List<TariffPlan> tariffPlans = tariffPlanRepo.findAll();
         if(tariffPlans.isEmpty()) return null;
         return tariffPlans;
     }


     public void tariffPlanValidation(TariffPlan tariffPlan){
         if(tariffPlan.getTEMPLATE_ENABLE() >= 2 || tariffPlan.getTEMPLATE_ENABLE() < 0)
             throw new RuntimeException("Template Enable must be 0 or 1");
         if(tariffPlan.getDECORATION_ENABLE() >= 2 || tariffPlan.getDECORATION_ENABLE()< 0)
            throw new RuntimeException("Decoration Enable must be 0 or 1");
         if(tariffPlan.getDELIVERY_ENABLE() >= 2 || tariffPlan.getDELIVERY_ENABLE() < 0)
            throw new RuntimeException("Delivery Enable must be 0 or 1");
         if(tariffPlan.getURGENT_ENABLE() >= 2 || tariffPlan.getURGENT_ENABLE() < 0)
            throw new RuntimeException("Urgent Enable must be 0 or 1");
         if(tariffPlan.getSMS_ENABLE() >= 2 || tariffPlan.getSMS_ENABLE() < 0)
            throw new RuntimeException("SMS Enable must be 0 or 1");
         if(tariffPlan.getCHAR_WORD() < 0)
             throw new RuntimeException("Char Word must be positive");
            if(tariffPlan.getINQUIRY_PRICE() < 0)
                throw new RuntimeException("Inquiry Price must be positive");
            if(tariffPlan.getWORD_PRICE() < 0)
                throw new RuntimeException("Word Price must be positive");
            if(tariffPlan.getDECORATION_PRICE() < 0)
                throw new RuntimeException("Decoration Price must be positive");
            if(tariffPlan.getPO_PRICE() < 0)
                throw new RuntimeException("PO Price must be positive");
            if(tariffPlan.getURGENT_PRICE() < 0)
                throw new RuntimeException("Urgent Price must be positive");
            if(tariffPlan.getTEMPLATE_PRICE() < 0)
                throw new RuntimeException("Template Price must be positive");
            if(tariffPlan.getDELIVERY_PRICE() < 0)
                throw new RuntimeException("Delivery Price must be positive");
            if(tariffPlan.getADMIN() < 0 || tariffPlan.getADMIN() >= 2)
                throw new RuntimeException("Admin must be 0 or 1");
            if (tariffPlan.getWORDS_COUNT() < 0)
                throw new RuntimeException("Words Count must be positive");
     }

    public TariffPlan CreateTariffPlan(TariffPlan tariffPlan) throws NotFoundException {
         if(getPlan(tariffPlan.getPLAN_CODE()) != null) throw new NotFoundException("Id already exist");
         tariffPlanValidation(tariffPlan);
         tariffPlan.setSALES_TAX(0L);tariffPlan.setWORDS_COUNT(1);
         return tariffPlanRepo.save(tariffPlan);
     }

    public TariffPlan UpdateTariffPlan(String code,TariffPlan tariffPlan) {
        if(getPlan(code) == null) return null;
        tariffPlan.setPLAN_CODE(code);
        tariffPlan.setSALES_TAX(0L);

        return tariffPlanRepo.save(tariffPlan);
    }
    @Transactional
    public void DeletePlan(TariffPlan tariffPlan){tariffPlanRepo.delete(tariffPlan);}
    public TariffPlan getPlan(String code){
         Optional<TariffPlan> tariffPlan = tariffPlanRepo.findById(code);
        return tariffPlan.orElse(null);
    }
    public List<TariffPlan> getTariffPlanByAttribute(String attribute,String value){

        Specification<TariffPlan> specification = TariffPlanSpec.byFieldAndValue(attribute,value);

        List<TariffPlan> tariffPlans = tariffPlanRepo.findAll(specification);
        if(tariffPlans.isEmpty()) return null;
        return tariffPlans;
    }

    public List<CountryPlan> getCountryPlans(String code){
        Optional<List<CountryPlan>> countryPlans = countryPlanRepo.FindByCodePlan(code);
        return countryPlans.orElse(null);
    }


    public void DeleteCountryPlan(String code){countryPlanRepo.DeleteByPlanCode(code);}
    public void DeleteValuePlan(String code){
        tariffPlanExtraFeeRepo.DeleteByPlanCode(code);
    }

   public boolean DeletePlanAndValueAndCountryPlan(String code){
       TariffPlan tariffPlan = getPlan(code);
       if(tariffPlan == null) return false;

       List<CountryPlan> countryPlans = getCountryPlans(code);
       List<TariffPlan_ExtraFee> tariffPlanExtraFees =  getValuePlans(code);

       if(tariffPlanExtraFees != null ) DeleteValuePlan(code);
       if(countryPlans!= null) DeleteCountryPlan(code);

       DeletePlan(tariffPlan);
       return true;
     }

    public List<TariffPlan_ExtraFee> getValuePlans(String code){
        Optional<List<TariffPlan_ExtraFee>> tariffPlanExtraFees =  tariffPlanExtraFeeRepo.FindByCodePlan(code);
        return tariffPlanExtraFees.orElse(null);
    }


    public List<TariffJoinFee> getTariffJoinsFee(String code){
         if(getPlan(code) == null) throw new RuntimeException("Invalid Code");
        List<TariffJoinFee> tariffJoinFees = extraFeesRepo.TARIFF_JOIN_FEES(code);
        if(tariffJoinFees.isEmpty())  throw new RuntimeException("Empty List");
        return tariffJoinFees;
    }

    public boolean UpdateTariffValue(List<TariffJoinFee> tariffJoinFees,String code) throws NotFoundException {
          if(getPlan(code) == null)  throw new RuntimeException("Invalid Plan Code");

        for(TariffJoinFee tariffJoinFee : tariffJoinFees){
            if(extraFeesService.getById(tariffJoinFee.getITEM_NO()) == null) throw new NotFoundException("Invalid Fee Id");
        }

         for(TariffJoinFee tariffJoinFee : tariffJoinFees){
                  tariffPlanExtraFeeRepo.UpdateValid(code,
                          tariffJoinFee.getITEM_NO(),tariffJoinFee.getVALID());
         }
         return true;
    }
    public List<TariffPlan> searchWithAllConditions(TariffPlan tariffPlan) {
        Specification<TariffPlan> spec = TariffPlanSpec.withAllConditions(tariffPlan);
        return tariffPlanRepo.findAll(spec);
    }

    public List<TariffPlan> searchWithAnyConditions(TariffPlan tariffPlan) {

        Specification<TariffPlan> spec = TariffPlanSpec.withAnyConditions(tariffPlan);
        List<TariffPlan> tariffPlans =  tariffPlanRepo.findAll(spec);
        if(tariffPlans.isEmpty()) return null;
        return tariffPlans;
    }

}
