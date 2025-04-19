package realWorld.tdd.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import realWorld.tdd.domain.User;
import realWorld.tdd.dto.UserSignUpRequest;
import realWorld.tdd.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User signUp(UserSignUpRequest request) {
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = new User(request.getEmail(), request.getUserId(), encodedPassword);
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public User login(String email, String passWord) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent() && passwordEncoder.matches(passWord, user.get().getPassword())) {
            return user.get();
        }
        return null;
    }



}
