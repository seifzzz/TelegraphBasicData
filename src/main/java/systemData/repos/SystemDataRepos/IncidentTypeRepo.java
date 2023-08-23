package systemData.repos.SystemDataRepos;

import systemData.models.BasicData.IncidentType.IncidentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncidentTypeRepo extends JpaRepository<IncidentType, String> {
}
