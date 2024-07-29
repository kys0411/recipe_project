package user.constant;

import lombok.Getter;

@Getter
public enum Role {
	ADMIN("0"), NORMAL("1");

	private final String roleType;

	Role(String roleType) {
		this.roleType = roleType;
	}
}
