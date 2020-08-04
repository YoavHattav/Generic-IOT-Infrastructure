package il.co.ilrd.iot_servlets;

public enum Status {
	
	OK                   (200, "ok"),
	EMAIL_NOT_FOUND      (401, "email not found"),
	EMAIL_ALREADY_IN_USE (402, "email already in use"),
	WRONG_PASSWORD       (403, "wrong password"),
	INVALID_TOKEN        (404, "invalid token"), 
	INVALID_PARAMS       (405, "could not update with params"),
	FAILED               (406, "failed"),
	NO_CHANGES_MADE      (407, "NO_CHANGES_MADE");
	
	private String description;
	private Integer code;
	
	private Status(Integer code, String description) {
		this.description = description;
		this.code = code;
	}
	
	public String getDescription() {
		return description;
	}
	
	public Integer getCode() {
		return code;
	}
}
