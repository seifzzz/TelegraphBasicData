package systemData.models.BasicData.IncidentStatus;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TGH_INCIDENT_STATUS", schema = "stgh")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IncidentStatus {
    @Id @JsonProperty("StatusCode")
    private String STATUS_CODE;
    @JsonProperty("statusName")
    private String STATUS_NAME;
}
