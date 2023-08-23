package systemData.models.BasicData.PostOffice;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import systemData.models.BasicData.Country.City;
import systemData.models.BasicData.Country.Country;

import javax.persistence.*;

@Entity
@Table(name = "TGH_POST_OFFICE", schema = "stgh")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostOffice {


    @Id
    @JsonProperty("officeCode")
    String OFFICE_CODE;
    @JsonProperty("officeName")
    String OFFICE_NAME;
    @JsonProperty("offTelNo")
    String OFF_TEL_NO;
    @JsonProperty("offAddress")
    String OFF_ADDRESS;
    @JsonProperty("international")
    int INTERNATIONAL;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "COUNTRY_CODE")
    @JsonProperty("countryCode")
    Country COUNTRY_CODE;
    @JsonProperty("destInd")
    String DEST_IND;
    @JsonProperty("inService")
    String IN_SERVICE;
    @JsonProperty("notes")
    String NOTES;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CITY_CODE")
    @JsonProperty("cityCode")
    City CITY_CODE;
    @JsonProperty("outgoing")
    int OUTGOING;
    @JsonProperty("incoming")
    int INCOMING;
    @JsonProperty("arrivalCount")
    int ARRIVAL_COUNT;

}
