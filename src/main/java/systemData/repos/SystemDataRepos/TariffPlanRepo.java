package systemData.repos.SystemDataRepos;

import systemData.models.BasicData.TariffPlan.TariffPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TariffPlanRepo extends JpaRepository<TariffPlan, String>, JpaSpecificationExecutor<TariffPlan> {
}
