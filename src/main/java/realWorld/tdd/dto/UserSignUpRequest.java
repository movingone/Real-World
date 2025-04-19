package realWorld.tdd.dto;

import lombok.Getter;

@Getter
public class UserSignUpRequest {

    private String email;
    private String userId;
    private String password;

    public UserSignUpRequest(String email, String userId, String password) {
        this.email = email;
        this.userId = userId;
        this.password = password;
    }
}
