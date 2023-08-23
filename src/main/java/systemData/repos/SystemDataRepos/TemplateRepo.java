package systemData.repos.SystemDataRepos;



import systemData.models.BasicData.Template.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TemplateRepo extends JpaRepository<Template, String> {
   @Query(value= "SELECT * from TGH_TEMPLATE WHERE TEMP_TYPE_CODE=:code",nativeQuery = true)
    List<Template> findByType(String code);

}
