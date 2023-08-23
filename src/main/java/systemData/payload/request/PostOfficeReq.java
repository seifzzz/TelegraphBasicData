package systemData.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostOfficeReq {


    @JsonProperty("officeCode")
    String OFFICE_CODE;
    @JsonProperty("officeName")
    String OFFICE_NAME;
    @JsonProperty("offTelNo")
    String OFF_TEL_NO;
    @JsonProperty("offAddress")
    String OFF_ADDRESS;
    @JsonProperty("international")
    Integer INTERNATIONAL;
    @JsonProperty("CountryCode")
    String COUNTRY_CODE;
    @JsonProperty("cityCode")
    String CITY_CODE;
    @JsonProperty("outgoing")
    Integer OUTGOING;
    @JsonProperty("incoming")
    Integer INCOMING;
    @JsonProperty("arrivalCount")
    Integer ARRIVAL_COUNT;
    @JsonProperty("destInd")
    String DEST_IND;
    @JsonProperty("inService")
    String IN_SERVICE;
    @JsonProperty("notes")
    String NOTES;

}
