package realWorld.tdd.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import realWorld.tdd.domain.User;
import realWorld.tdd.dto.UserSignUpRequest;
import realWorld.tdd.repository.UserRepository;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private UserService userService;

    @DisplayName("회원가입 성공")
    @Test
    void userJoin() {
        // given
        UserSignUpRequest request = new UserSignUpRequest("abc1234@gmail.com", "id", "password");

        when(userRepository.save(any(User.class))).thenReturn(new User());
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        // when
        userService.signUp(request);


        // then
        verify(userRepository).save(any(User.class));
        verify(passwordEncoder).encode("password");

    }
}
