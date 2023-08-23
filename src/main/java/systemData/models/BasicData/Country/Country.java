package systemData.models.BasicData.Country;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TGH_COUNTRY", schema = "stgh")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Country {

    @Id
    @JsonProperty("CountryCode")
    String COUNTRY_CODE;
    @JsonProperty("countryName")
    String  COUNTRY_NAME;
    @JsonProperty("destInd")
    String DEST_IND;
    @JsonProperty("active")
    Integer ACTIVE;
    @JsonProperty("capital")
    String CAPITAL;
    @JsonProperty("international")
    Integer INTERNATIONAL;
    @JsonProperty("englishName")
    String ENGLISH_NAME;
    @JsonProperty("destIndEng")
    String DEST_IND_ENG;

}
