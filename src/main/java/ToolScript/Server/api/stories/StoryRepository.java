package ToolScript.Server.api.stories;
import org.springframework.data.repository.CrudRepository;

public interface StoryRepository extends CrudRepository<Story, Integer> {
	
	@Query("Select s from Story s where s.id_story in (select id_story from Story_User where id_user= :#{#idUser})")
	List<Story> findAllForUser(@Param("idUser") Integer idUser);
}