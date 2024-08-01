package user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserUpdateRequestDto {
    private String nickname;
    private String hint;
    private String password;

    public UserUpdateRequestDto() {}

    @Builder
    public UserUpdateRequestDto(String nickname, String hint, String password) {
        this.nickname = nickname;
        this.hint = hint;
        this.password = password;
    }
}
