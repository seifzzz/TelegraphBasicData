package systemData.models.BasicData.Country.Joins;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import systemData.models.BasicData.Country.Country;
import systemData.models.BasicData.Country.Joins.Ids.CountryXLanguageId;
import systemData.models.BasicData.Country.Joins.Ids.CountryXVipId;
import systemData.models.BasicData.Template.Language;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "TGH_COUNTRY_LANGUAGE", schema = "stgh")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CountryXLanguage {
     @EmbeddedId
     CountryXLanguageId countryXLanguageId;
}
