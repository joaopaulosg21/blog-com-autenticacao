package aprendendo.api.blog.entities.DTO;

import java.time.LocalDate;

import aprendendo.api.blog.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Builder
public class PostDTO {
    private String title;
    private String content;
    private LocalDate date;
    private User user;

    public UserDTO getUser() {
        return user.toDTO();
    }
}
