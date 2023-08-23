package systemData.repos.SystemDataRepos;

import systemData.models.BasicData.Template.TemplateType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemplateTypeRepo extends JpaRepository<TemplateType, String> {
}
