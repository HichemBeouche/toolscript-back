package ToolScript.Server.api.stories;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping(path="/")
public class ControllerStory {
	  @Autowired
	  private StoryRepository storyRepository;
	
	  /* ****************** STORIES ****************** */
	  @GetMapping(path="stories/{id_story}")
	  public @ResponseBody Optional<Story> getStoryById(@PathVariable Integer id_story) {
	    return storyRepository.findById(id_story);
	  }
	  
	  @GetMapping(path="{id_user}/stories")
	  public @ResponseBody Iterable<Story> getAllStories(@PathVariable Integer id_user) {
	    return storyRepository.findAllForUser(id_user);
	  }
}
