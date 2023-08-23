package systemData.models.BasicData.Country.Joins;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import systemData.models.BasicData.Country.City;
import systemData.models.BasicData.Country.Country;
import systemData.models.BasicData.Country.Joins.Ids.CountryXVipId;
import systemData.models.BasicData.PostOffice.PostOffice;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "TGH_COUNTRY_VIP", schema = "stgh")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CountryXVIP {
    @EmbeddedId
    CountryXVipId id;
    @JsonProperty("personTitle")
    String PERSON_TITLE;
    @JsonProperty("personName")
    String PERSON_NAME;
    @JsonProperty("officialAddress")
    String OFFICIAL_ADDRESS;
    @JsonProperty("addedCost")
    Long ADDED_COST;
    @JsonProperty("vipEntity")
    String VIP_ENTITY;
    @JsonProperty("cityCode")
    String CITY_CODE;
    @JsonProperty("officeCode")
    String OFFICE_CODE;
    @JsonProperty("fees")
    Long FEES;

}
