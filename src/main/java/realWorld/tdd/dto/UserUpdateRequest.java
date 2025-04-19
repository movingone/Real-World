package realWorld.tdd.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRequest {

    private String email;
    private String userName;
    private String password;
    private String bio;
    private String image;



    public Optional<String> getEmailToUpdate() {
        return Optional.ofNullable(email);
    }

    public Optional<String> getUserNameToUpdate() {
        return Optional.ofNullable(userName);
    }

    public Optional<String> getPasswordToUpdate() {
        return Optional.ofNullable(password);
    }

    public Optional<String> getBioToUpdate() {
        return Optional.ofNullable(bio);
    }

    public Optional<String> getImageToUpdate() {
        return Optional.ofNullable(image);
    }

    public static UserUpdateRequest of(String username, String bio) {
        return UserUpdateRequest.builder()
                .userName(username)
                .bio(bio)
                .build();
    }
}
