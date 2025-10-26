package nl.hsleiden.ammaris.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignupRequest {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 100)
    private String password;
}
