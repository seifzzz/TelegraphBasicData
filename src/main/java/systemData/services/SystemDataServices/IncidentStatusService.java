package systemData.services.SystemDataServices;

import systemData.models.BasicData.IncidentStatus.IncidentStatus;
import systemData.repos.SystemDataRepos.IncidentStatusRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IncidentStatusService {


    @Autowired
    IncidentStatusRepo incidentStatusRepo;


    public List<IncidentStatus> getAllStatus(){
        List<IncidentStatus> incidentStatuses = incidentStatusRepo.findAll();
        if(incidentStatuses.isEmpty()) return null;
        return incidentStatuses;

    }
    public IncidentStatus getOneByCode(String code){
        Optional<IncidentStatus> incidentStatus = incidentStatusRepo.findById(code);
        return incidentStatus.orElse(null);

    }

    public IncidentStatus CreateOne(IncidentStatus incidentStatus){
        Optional<IncidentStatus> checked = incidentStatusRepo.findById(incidentStatus.getSTATUS_CODE());
        if(checked.isPresent()) return null;
        return  incidentStatusRepo.save(incidentStatus);

    }

    public IncidentStatus UpdateOne(IncidentStatus incidentStatus){
        Optional<IncidentStatus> checked = incidentStatusRepo.findById(incidentStatus.getSTATUS_CODE());
        if(checked.isPresent()) return  incidentStatusRepo.save(incidentStatus);
        return null;

    }

    public boolean DeleteOne(String code){
        Optional<IncidentStatus> checked =  incidentStatusRepo.findById(code);
        if(!checked.isPresent()) return false;
        incidentStatusRepo.deleteById(code);
        return true;

    }

}
