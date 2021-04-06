package com.api.toolscript.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.api.toolscript.models.Story;
import com.api.toolscript.payload.response.MessageResponse;
import com.api.toolscript.repository.StoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping(path="/")
public class StoryController {
	  @Autowired
	  private StoryRepository storyRepository;
	
	  //To display all the user's stories
	  @GetMapping(path="stories/{id_user}")
	  public @ResponseBody Iterable<Story> getAllStories(@PathVariable Long id_user) {
		  return storyRepository.findAllForUser(id_user);
	  }

	  //To display a story
	  @GetMapping(path="story/{id_story}")
	  public @ResponseBody Optional<Story> getStoryById(@PathVariable Integer id_story) {
	    return storyRepository.findById(id_story);
	  }

	  //To create a story
	  @PostMapping("story/{id_user}/create")
	  public ResponseEntity<?> createStory(@PathVariable Long id_user, @RequestBody Story story) {
	  	if(story.getTitle() == null || story.getTitle().isBlank()) {
			return ResponseEntity.badRequest().body(
					new MessageResponse("Error: Le titre ne peut pas être vide!"));
	  	} else if (story.getTitle().length() > 40){
		return ResponseEntity.badRequest().body(
				new MessageResponse("Error: Le titre ne peut pas dépasser 40 caractères!"));
		} else if (story.getDesc() != null && story.getDesc().length() > 200){
			return ResponseEntity.badRequest().body(
					new MessageResponse("Error: La description ne peut pas dépasser 200 caractères!"));
	  	} else if (id_user == null) {
			return ResponseEntity.badRequest().body(
					new MessageResponse("L'auteur ne peut pas être null"));
		}
	  	Story newStory = Story.create(story.getTitle(), story.getDesc(), id_user);
	  	Story s = storyRepository.save(newStory);
	  	return new ResponseEntity<Story>(s, HttpStatus.OK);

	  }

	  //To edit a story
	  @PutMapping(path="story/{id_story}/edit")
	  public ResponseEntity<?> editStory (@PathVariable Integer id_story, @RequestBody Story newStory) {
	  	if (id_story.equals(newStory.getId()) && storyRepository.findById(id_story).isPresent()) {
	  		Story updatedStory = storyRepository.findById(id_story).get();
			updatedStory.setTitle(newStory.getTitle());
			updatedStory.setDesc(newStory.getDesc());
			updatedStory.setStory(newStory.getStory());
			storyRepository.save(updatedStory);
			return ResponseEntity.ok(/*new MessageResponse("Story successfully edited !")*/null);
		}
	  	else {
	  		return ResponseEntity
				.badRequest()
				.body(/*new MessageResponse("Error: ID does not match!")*/null);
	  	}
	  }

	  //To delete a story
	  @DeleteMapping("story/{id_story}/delete")
	  public Map<String, Boolean> deleteStory(@PathVariable Integer id_story) {
		  Map<String, Boolean> response = new HashMap<>();
	  	  if (storyRepository.findById(id_story).isPresent()) {
			  storyRepository.delete(storyRepository.findById(id_story).get());
			  response.put("deleted", Boolean.TRUE);
		  } else { response.put("deleted", Boolean.FALSE); }
		  return response;
	  }
}
