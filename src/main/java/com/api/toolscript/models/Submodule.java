//package com.api.toolscript.models;
//
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.validation.constraints.NotBlank;
//
//public class Submodule {
//	
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long id_submodule;
//	
//	@NotBlank
//	private String name_submodule;
//	
//	@NotBlank
//	private Long id_module;
//	
//	public Submodule() {
//		
//	}
//	
//	public Submodule(String name_submodule, long id_module) {
//		this.name_submodule = name_submodule;
//		this.id_module = id_module;
//	}
//
//	public Long getId_submodule() {
//		return id_submodule;
//	}
//
//	public void setId_submodule(Long id_submodule) {
//		this.id_submodule = id_submodule;
//	}
//
//	public String getName_submodule() {
//		return name_submodule;
//	}
//
//	public void setName_submodule(String name_submodule) {
//		this.name_submodule = name_submodule;
//	}
//
//	public Long getId_module() {
//		return id_module;
//	}
//
//	public void setId_module(Long id_module) {
//		this.id_module = id_module;
//	}
//	
//	
//	
//}
