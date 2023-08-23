package systemData.services.SystemDataServices;

import systemData.models.BasicData.TelegraphStatus.TelegraphStatus;
import systemData.repos.SystemDataRepos.TelegraphStatusRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TelegraphStatusService {

    @Autowired
    TelegraphStatusRepo telegraphStatusRepo;

    public List<TelegraphStatus> getAllStatus(){
        List<TelegraphStatus> telegraphStatuses = telegraphStatusRepo.findAll();
        if(telegraphStatuses.isEmpty()) return null;
        return telegraphStatuses;

    }
    public TelegraphStatus getOneByCode(String code){
        Optional<TelegraphStatus> telegraphStatus = telegraphStatusRepo.findById(code);
        return telegraphStatus.orElse(null);

    }

    public TelegraphStatus CreateOne(TelegraphStatus telegraphStatus){
        Optional<TelegraphStatus> checked = telegraphStatusRepo.findById(telegraphStatus.getSTATUS_CODE());
        if(checked.isPresent()) return null;
        return telegraphStatusRepo.save(telegraphStatus);

    }

    public TelegraphStatus UpdateOne(TelegraphStatus telegraphStatus){
        Optional<TelegraphStatus> checked = telegraphStatusRepo.findById(telegraphStatus.getSTATUS_CODE());
        if(checked.isPresent()) return telegraphStatusRepo.save(telegraphStatus);
        return null;

    }

    public boolean DeleteOne(String code){
        Optional<TelegraphStatus> checked = telegraphStatusRepo.findById(code);
        if(!checked.isPresent()) return false;
        telegraphStatusRepo.deleteById(code);
        return true;

    }


}
