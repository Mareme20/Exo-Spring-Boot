package com.marieme.wallet;

import com.marieme.wallet.dto.request.UserCreationDto;
import com.marieme.wallet.dto.response.UserResponse;
import com.marieme.wallet.exception.ConflictException;
import com.marieme.wallet.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test")
class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Test
    void shouldCreateUserSuccessfully() {
        UserCreationDto dto = new UserCreationDto("Marieme Diop", "marieme.diop@example.com");

        UserResponse response = userService.create(dto);

        assertThat(response.getId()).isNotNull();
        assertThat(response.getNom()).isEqualTo("Marieme Diop");
        assertThat(response.getEmail()).isEqualTo("marieme.diop@example.com");
    }

    @Test
    void shouldRejectDuplicateEmail() {
        UserCreationDto dto = new UserCreationDto("Fatou Sow", "fatou.sow@example.com");
        userService.create(dto);

        assertThatThrownBy(() -> userService.create(dto))
                .isInstanceOf(ConflictException.class);
    }
}
