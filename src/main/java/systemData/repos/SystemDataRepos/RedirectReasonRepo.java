package systemData.repos.SystemDataRepos;

import systemData.models.BasicData.RedirectReason.RedirectReason;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedirectReasonRepo extends JpaRepository<RedirectReason, String> {
}
