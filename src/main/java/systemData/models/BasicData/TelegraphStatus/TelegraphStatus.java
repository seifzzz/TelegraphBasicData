package systemData.models.BasicData.TelegraphStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TGH_MSG_STATUS", schema = "stgh")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TelegraphStatus {
    @Id @JsonProperty("statusCode")
    private String STATUS_CODE;
    @JsonProperty("statusName")
    private String STATUS_NAME;
    @JsonIgnore
    private Integer STATUS_ORDER;
    @JsonIgnore
    private String DESCRIPTION;
    @JsonIgnore
    private Integer SHOW_ARCHIVE;
}
