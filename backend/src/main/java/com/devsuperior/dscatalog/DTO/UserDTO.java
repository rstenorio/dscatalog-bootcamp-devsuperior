package com.devsuperior.dscatalog.DTO;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.devsuperior.dscatalog.entities.User;

public class UserDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long id;
	
	@Size(min=5, max=20, message = "Favor inserir entre 5 e 20 caracteres")
	@NotBlank(message = "Campo Obrigatorio")
	private String firstName;
	private String lastName;

	@Email(message = "Email invalido")
	private String email;

	Set<RoleDTO> roles = new HashSet<>();
	
	public UserDTO() {
	}

	public UserDTO(Long id, String firstName, String lastName, String email) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}
	
	public UserDTO(User entity) {
		id = entity.getId();
		firstName = entity.getFirstName();
		lastName = entity.getLastName();
		email = entity.getEmail();

		entity.getRoles().forEach(roles -> this.roles.add(new RoleDTO(roles)));
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<RoleDTO> getRoles() {
		return roles;
	}
	
	
	
}
