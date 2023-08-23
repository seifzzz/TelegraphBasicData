package systemData.models.BasicData.PostOffice;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "TGH_POST_OFFICE_ALT", schema = "stgh")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlternateOffice {

    @Id
    @JsonProperty("altOfficeCode")
    String ALT_OFFICE_CODE;
    @JsonProperty("officeOrder")
    Integer OFFICE_ORDER;
    @JsonProperty("active")
    Integer ACTIVE;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "officeCode")
    PostOffice OFFICE_CODE;



}
