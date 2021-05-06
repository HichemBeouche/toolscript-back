package com.api.toolscript.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.toolscript.models.Submodule;

@Repository
public interface SubmoduleRepository extends JpaRepository<Submodule, Long> {
	Optional<Submodule> findById(Long id_submodule);
	List<Submodule> findAllByIdStory(Long id_story);
	
}
