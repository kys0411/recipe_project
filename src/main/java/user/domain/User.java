package user.domain;

import lombok.Builder;
import lombok.Getter;
import user.constant.Role;

@Builder
@Getter
public class User {
	private Long id;
	private String nickname;
	private Role role;
	private String hint;
	private String password;

	public User(Long id, String nickname, Role role, String hint, String password) {
		this.id = id;
		this.nickname = nickname;
		this.role = role;
		this.hint = hint;
		this.password = password;
	}
}
