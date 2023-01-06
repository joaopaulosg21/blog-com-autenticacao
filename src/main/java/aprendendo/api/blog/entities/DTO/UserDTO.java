package aprendendo.api.blog.entities.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Accessors(chain = true)
public class UserDTO {
    private Long id;
    private String name;
    private String email;
}
