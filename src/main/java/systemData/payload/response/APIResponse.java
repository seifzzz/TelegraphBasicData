package systemData.payload.response;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class APIResponse {

	protected LocalDateTime timestamp;
	protected int statusCode;
	protected HttpStatus status;
	protected String reason;
	protected String clientMessage;
	protected String developerMessage;
	protected Object body;
	protected Map<?, ?> data;
	
}