package systemData.repos.SystemDataRepos;

import systemData.models.BasicData.IncidentStatus.IncidentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncidentStatusRepo extends JpaRepository<IncidentStatus, String> {
}
