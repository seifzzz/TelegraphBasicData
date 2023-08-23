package systemData.payload.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import systemData.models.BasicData.Country.Country;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VipReq {

    @JsonProperty("personNo")
    Long PERSON_NO;
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
