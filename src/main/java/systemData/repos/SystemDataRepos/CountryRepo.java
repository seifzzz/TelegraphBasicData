package systemData.repos.SystemDataRepos;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import systemData.models.BasicData.Country.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import systemData.models.BasicData.PostOffice.PostOffice;

@Repository
public interface CountryRepo extends JpaRepository<Country, String>, JpaSpecificationExecutor<Country> {
}
