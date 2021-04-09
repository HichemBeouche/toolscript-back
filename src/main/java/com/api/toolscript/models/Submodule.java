package com.api.toolscript.models;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;

public class Submodule {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_submodule;
	
	@NotBlank
	private String name_submodule;
	
	@NotBlank
	private Long id_module;
	
	@Lob
	private String note;
	
	@NotBlank
	private Long id_story;
	
	public Submodule() {
		
	}
	
	public Submodule(String name_submodule, long id_module, String note, long id_story) {
		this.name_submodule = name_submodule;
		this.id_module = id_module;
		this.note = note;
		this.id_story = id_story;
	}

	public Long getId_submodule() {
		return id_submodule;
	}

	public void setId_submodule(Long id_submodule) {
		this.id_submodule = id_submodule;
	}

	public String getName_submodule() {
		return name_submodule;
	}

	public void setName_submodule(String name_submodule) {
		this.name_submodule = name_submodule;
	}

	public Long getId_module() {
		return id_module;
	}

	public void setId_module(Long id_module) {
		this.id_module = id_module;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Long getId_story() {
		return id_story;
	}

	public void setId_story(Long id_story) {
		this.id_story = id_story;
	}
	
	
	
}
