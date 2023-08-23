package systemData.models.BasicData.RedirectReason;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TGH_REDIRECT_REASON", schema = "stgh")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RedirectReason {
    @JsonProperty("reasonCode")
    @Id
    String REASON_CODE;
    @JsonProperty("reasonName")
    String REASON_NAME;
}
