package systemData.repos.SystemDataRepos;

import systemData.models.BasicData.TelegraphStatus.TelegraphStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelegraphStatusRepo extends JpaRepository<TelegraphStatus, String> {
}
