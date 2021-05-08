package com.api.toolscript.repository;

import com.api.toolscript.models.Module;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ModuleRepository extends CrudRepository<Module, Integer> {
	@Query("Select m.* from Module m where m.id_parent = :idModule)")
	List<Module> findAllChildrenById(@Param("idModule") Integer idModule);

	@Query("Select m.* from Module m where m.id_story = :idStory and id_parent is null")
	List<Module> findAllMainModulesForStory(@Param("idStory") Integer idStory);
	
	List<Module>findAllByIdStory(Long id_story);
}
