package user.constant;

import lombok.Getter;

@Getter
public enum Role {
	ADMIN("0"), NORMAL("1");

	private final String type;

	Role(String type) {
		this.type = type;
	}

	public static Role fromType(String type) {
		for (Role role : Role.values()) {
			if (role.getType().equals(type)) {
				return role;
			}
		}
		throw new IllegalArgumentException("No enum constant with type: " + type);
	}
}
