package like.constant;

import lombok.Getter;

@Getter
public enum Status {
	LIKE("1"), UNLIKE("0");

	private final String description;

	Status(String description) {
		this.description = description;
	}

	public static Status fromDescription(String description) {
		for (Status status : Status.values()) {
            if (status.getDescription().equals(description)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid status description: " + description);
	}
}
