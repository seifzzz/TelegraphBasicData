package systemData.services.SystemDataServices;

import systemData.models.BasicData.RedirectReason.RedirectReason;
import systemData.repos.SystemDataRepos.RedirectReasonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RedirectReasonService {
    @Autowired
    RedirectReasonRepo redirectReasonRepo;

    public List<RedirectReason> getAll(){
        List<RedirectReason> incidentStatuses = redirectReasonRepo.findAll();
        if(incidentStatuses.isEmpty()) return null;
        return incidentStatuses;
    }

    public RedirectReason getOne(String code){
        Optional<RedirectReason> redirectReason = redirectReasonRepo.findById(code);
        return redirectReason.orElse(null);
    }

    public boolean DeleteRedirect(String code){
          RedirectReason redirectReason = getOne(code);
          if(redirectReason == null) return false;
          redirectReasonRepo.delete(redirectReason);
          return true;
    }

    public RedirectReason UpdateOne(RedirectReason redirectReason){
        Optional<RedirectReason> checked = redirectReasonRepo.findById(redirectReason.getREASON_CODE());
        if(checked.isPresent()) return  redirectReasonRepo.save(redirectReason);
        return null;

    }

    public RedirectReason saveOne(RedirectReason incidentStatus){
        RedirectReason one = getOne(incidentStatus.getREASON_CODE());
        if(one == null) return redirectReasonRepo.save(incidentStatus);
        return null;
    }

}
