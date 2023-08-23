package systemData.models.BasicData.Country;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "TGH_CITY", schema = "stgh")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class City {
    @Id
    @JsonProperty("cityCode")
    String CITY_CODE;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "COUNTRY_CODE")
    @JsonIgnore
    Country COUNTRY_CODE;
    @JsonProperty("cityName")
     String CITY_NAME;


}
