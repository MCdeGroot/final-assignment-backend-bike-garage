package com.example.bikegarage.dto.input;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserInputDto {
    @NotBlank(message = "Username is required!")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Username should only contain letters and numbers")
    public String username;
    @NotBlank (message = "Password is required!")
    @Pattern(regexp = "^(?=.*?[a-z])(?=.*?[A-Z])(?=.*?[0-9])(?=.*?[\\!\\#\\@\\$\\%\\&\\/\\(\\)\\=\\?\\*\\-\\+\\_\\.\\:\\;\\,\\{\\}\\^])[A-Za-z0-9!#@$%&/()=?*+-_.:;,{}]{8,20}", message = "Password needs to contain the following: " +
            "1. Minimum of 1 lowercase letter. 2. Minimum of 1 uppercase letter. 3. Minimum of 1 number 4. Minimum of 1 symbol. 5. It should be between 8 and 20 characters long.")
    public String password;
    @NotBlank (message = "Email is required!")
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE, message = "This email doesn't meet e-mail requirements (@ symbol and .com/nl etc)")
    public String email;
    public String firstName;
    public String lastName;
    public Character gender;
    @JsonFormat(pattern="yyyy-MM-dd")
    public LocalDate dateOfBirth;
    public String photoUrl;
    public Boolean enabled;
    public String apikey;
}
