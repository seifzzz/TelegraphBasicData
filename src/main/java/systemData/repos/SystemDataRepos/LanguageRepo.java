package systemData.repos.SystemDataRepos;

import systemData.models.BasicData.Template.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageRepo extends JpaRepository<Language, String> {
}
