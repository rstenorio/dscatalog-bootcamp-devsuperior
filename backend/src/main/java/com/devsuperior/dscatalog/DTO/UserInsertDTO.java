package com.devsuperior.dscatalog.DTO;

public class UserInsertDTO extends UserDTO{

	private static final long serialVersionUID = 1L;
	
	private String password;

	public UserInsertDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserInsertDTO(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
