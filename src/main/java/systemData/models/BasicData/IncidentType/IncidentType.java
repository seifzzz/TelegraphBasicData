package systemData.models.BasicData.IncidentType;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TGH_INCIDENT_TYPE", schema = "stgh")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IncidentType {
    @Id
    @JsonProperty("IncidentTypeCode")
    private String INCIDENT_TYPE_CODE;
    @JsonProperty("IncidentTypeName")
    private String INCIDENT_TYPE_NAME;
}
