package realWorld.tdd.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import realWorld.tdd.domain.User;
import realWorld.tdd.dto.UserSignUpRequest;
import realWorld.tdd.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User signUp(UserSignUpRequest request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = new User(request.getEmail(), request.getUserId(), encodedPassword);
        return userRepository.save(user);
    }
}
