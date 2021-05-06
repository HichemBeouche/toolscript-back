package com.api.toolscript.repository;

import com.api.toolscript.models.Story;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface StoryRepository extends CrudRepository<Story, Integer> {
	
	@Query("Select s.* from Story s where s.id_story in (select id_story from Story_User where id_user = :idUser and permission='A')")
	List<Story> findAllForUser(@Param("idUser") Long idUser);

	@Query("Select s.* from Story s where s.id_story in (select id_story from Story_User where id_user = :idUser and permission!='A')")
	List<Story> findAllSharedToUser(@Param("idUser") Long id_user);
}
