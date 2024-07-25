package user.domain;

import lombok.Getter;
import user.constant.Role;

@Getter
public class User {
	private Long id;
	private String nickname;
	private Role role;
	private String hint;
	private String password;

	public User(Long id, String nickname, String hint, String password) {
		this.id = id;
		this.nickname = nickname;
		this.role = Role.NORMAL;
		this.hint = hint;
		this.password = password;
	}
}
