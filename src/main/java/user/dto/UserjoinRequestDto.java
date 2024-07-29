package user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserjoinRequestDto {
    private String nickname;
    private String hint;
    private String password;

    public UserjoinRequestDto() {}

    @Builder
    public UserjoinRequestDto(String nickname, String hint, String password) {
        this.nickname = nickname;
        this.hint = hint;
        this.password = password;
    }
}
