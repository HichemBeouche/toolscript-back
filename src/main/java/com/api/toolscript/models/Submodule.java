package com.api.toolscript.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;

import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Entity
@Table(value = "Submodule")
public class Submodule {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_submodule;
	
	@NotBlank
	private String name_submodule;
	
	@NotBlank
	@Column(value = "id_module")
	private Long idModule;
	
	@Lob
	private String note;
	
	@NotBlank
	private Long idStory;
	
	public Submodule() {
		
	}
	
	public Submodule(String name_submodule, long id_module, String note, long id_story) {
		this.name_submodule = name_submodule;
		this.idModule = id_module;
		this.note = note;
		this.idStory = id_story;
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

	public Long getIdModule() {
		return idModule;
	}

	public void setIdModule(Long idModule) {
		this.idModule = idModule;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Long getIdStory() {
		return idStory;
	}

	public void setIdStory(Long idStory) {
		this.idStory = idStory;
	}

	
	
	
	
}
