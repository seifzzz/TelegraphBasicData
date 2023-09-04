package systemData.services.SystemDataServices;

import javassist.NotFoundException;
import systemData.models.BasicData.Incident.Incident;
import systemData.models.BasicData.IncidentType.IncidentType;
import systemData.repos.SystemDataRepos.IncidentRepo;
import systemData.repos.SystemDataRepos.IncidentTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IncidentTypeService {
    @Autowired
    IncidentTypeRepo incidentTypeRepo;
    @Autowired
    IncidentRepo incidentRepo;
    public List<IncidentType> getAllTypes(){
        List<IncidentType> incidentTypes = incidentTypeRepo.findAll();
        if(incidentTypes.isEmpty()) return null;
        return incidentTypes;

    }
    public IncidentType getOneByCode(String code){
        Optional<IncidentType> incidentType = incidentTypeRepo.findById(code);
        return incidentType.orElse(null);

    }

    public IncidentType CreateOne(IncidentType incidentType){
        Optional<IncidentType> checked = incidentTypeRepo.findById(incidentType.getINCIDENT_TYPE_CODE());
        if(checked.isPresent()) return null;
        return  incidentTypeRepo.save(incidentType);

    }

    public IncidentType  UpdateOne(IncidentType incidentType){
        Optional<IncidentType> checked = incidentTypeRepo.findById(incidentType.getINCIDENT_TYPE_CODE());
        if(checked.isPresent()) return  incidentTypeRepo.save(incidentType);
        return null;

    }

    public boolean DeleteOne(String code) throws NotFoundException {
        Optional<IncidentType> checked =  incidentTypeRepo.findById(code);
        if(!checked.isPresent()) return false;
        List<Incident> incidents = incidentRepo.findByIncidentTypeCode(checked.get());
        if(!incidents.isEmpty()) throw new NotFoundException("Can't delete this type, there are incidents with this type");
        incidentTypeRepo.deleteById(code);
        return true;

    }
}
