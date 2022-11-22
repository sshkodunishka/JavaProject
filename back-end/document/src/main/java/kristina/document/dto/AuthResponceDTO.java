package kristina.document.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthResponceDTO {
  private String username;
  private String token;
  private String roles;

  public AuthResponceDTO(String username, String token, String roles) {
    this.username = username;
    this.token = token;
    this.roles = roles;
  }

  public AuthResponceDTO() {
  }
}
