package realWorld.tdd.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import realWorld.tdd.dto.UserUpdateRequest;

@Getter
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String userName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String bio;

    @Column(nullable = false)
    private String image;

    public User(String email, String userName, String password) {
        this.email = email;
        this.userName = userName;
        this.password = password;
    }

    public void update(UserUpdateRequest request) {
        request.getEmailToUpdate().ifPresent(email -> this.email = request.getEmail());
        request.getUserNameToUpdate().ifPresent(username -> this.userName = request.getUserName());
        request.getPasswordToUpdate().ifPresent(password -> this.password = request.getPassword());
        request.getBioToUpdate().ifPresent(bio -> this.bio = request.getBio());
        request.getImageToUpdate().ifPresent(image -> this.image = request.getImage());
    }


}
