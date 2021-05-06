package com.api.toolscript.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "Friend")
public class Friend {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_friend;
	
	@NotBlank
	private Long id_user;
	
	@NotBlank
	private Long id_useradd;
	
	public Friend() {
		
	}
	
	public Friend(Long id_user, Long id_useradd) {
		this.id_user = id_user;
		this.id_useradd = id_useradd;
	}

	public Long getId_friend() {
		return id_friend;
	}

	public void setId_friend(Long id_friend) {
		this.id_friend = id_friend;
	}

	public Long getId_user() {
		return id_user;
	}

	public void setId_user(Long id_user) {
		this.id_user = id_user;
	}

	public Long getId_useradd() {
		return id_useradd;
	}

	public void setId_useradd(Long id_useradd) {
		this.id_useradd = id_useradd;
	}
	
	
	
	
}
