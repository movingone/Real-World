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

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
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

    @DisplayName("로그인 성공")
    @Test
    void login_success() {
        // given
        String email = "abc1234@test.com";
        String passWord = "abcd1234";
        String encodePassword = "12345678";
        User user = new User(email, "testUser", encodePassword);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(passWord, encodePassword)).thenReturn(true);

        // when
        User loginUser = userService.login(email, passWord);

        // then
        assertThat(loginUser).isEqualTo(user);
     }

    @DisplayName("로그인 실패 이메일 없음")
    @Test
    void login_fail_notEmail() {
        // given
        String email = "abc1234@test.com";
        String passWord = "abcd1234";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // when
        User loginUser = userService.login(email, passWord);

        // then
        assertThat(loginUser).isNull();
    }

    @DisplayName("로그인 실패 비밀번호 불일치")
    @Test
    void login_fail_notPassword() {
        // given
        String email = "abc1234@test.com";
        String passWord = "abcd1234";
        String encodePassword = "12345678";
        User user = new User(email, "testUser", encodePassword);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(passWord, encodePassword)).thenReturn(false);

        // when
        User loginUser = userService.login(email, passWord);

        // then
        assertThat(loginUser).isNull();
    }

    @DisplayName("사용자 정보 조회 성공")
    @Test
    void getUser() {
        // given
        Long userId = 1L;
        User user = new User("test@google.com", "trump", "abc1234");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // when
        User findUser = userService.findUser(userId);

        // then
        assertThat(findUser).isEqualTo(user);
     }

     @DisplayName("사용자 조회 실패")
     @Test
     void fail_getUser() {
         // given
         Long userId = 1L;

         when(userRepository.findById(userId)).thenReturn(Optional.empty());

         // then // when
         assertThatThrownBy(() -> userService.findUser(userId))
                 .isInstanceOf(NoSuchElementException.class);
      }


      @DisplayName("사용자 정보 수정 성공")
      @Test
      void test() {
          // given
          String email = "abc1234@test.com";
          String passWord = "abcd1234";
          String encodePassword = "12345678";
          User user = new User(email, "testUser", encodePassword);
          String newName = "trump";
          String newBio = "new bio";

          UserUpdateRequest request = new UserUpdateRequest();
          request.userName(newName);
          request.setBio(newBio);

          when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
          when(userRepository.save(user)).thenReturn(user);

          // when
          User updateUser = userService.updateUser(email, request);

          // then
          assertThat(updateUser.getUserName()).isEqualTo(newName);
          assertThat(updateUser.getBio()).isEqualTo(newBio);
       }

    @DisplayName("사용자 정보 수정 실패 이메일 없음")
    @Test
    void test() {
        // given
        String email = "notfound@test.com";
        UserUpdateRequest request = new UserUpdateRequest();

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // when // then
        assertThatThrownBy(() -> userService.updateUser(email, request))
                .isInstanceOf(NoSuchElementException.class);
    }
}
