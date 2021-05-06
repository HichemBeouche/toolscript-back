package com.api.toolscript.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.toolscript.models.Friend;
import com.api.toolscript.payload.response.MessageResponse;
import com.api.toolscript.repository.FriendRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/friend")
public class FriendController {

	@Autowired
	private FriendRepository friendRepository;
	
	@GetMapping(path="/{id_friend}")
	public @ResponseBody Optional<Friend> getFriendById(@PathVariable Long id_friend){
		return friendRepository.findById(id_friend);
	}
	
	@PostMapping(path="/createFriend")
	public ResponseEntity<?> createFriend(@RequestBody Friend friend){
		if(friend.getId_user() == null) {
			return ResponseEntity.badRequest().body(
					new MessageResponse("Error: l'id_user is required !"));
		}
		else if(friend.getId_useradd() == null) {
			return ResponseEntity.badRequest().body(
					new MessageResponse("Error: l'id_useradd is required !"));
		}
		else {
			friendRepository.save(friend);
			return ResponseEntity.ok(new MessageResponse("Friend successfully created !"));
		}
	}
	
	@DeleteMapping(path="/{id_friend}/deleteFriend")
	public ResponseEntity<?> deleteFriend(@PathVariable Long id_friend){
		if(id_friend == null) {
			return ResponseEntity.badRequest().body(
					new MessageResponse("Error: l'id_friend is required !"));
		}else {
			friendRepository.deleteById(id_friend);
			return ResponseEntity.ok(new MessageResponse("Friend successfully deleted !"));

		}
	}
	
	@DeleteMapping(path="/deleteAllFriend")
	public ResponseEntity<?> deleteAllFriend() {
		friendRepository.deleteAll();
		return ResponseEntity.ok(new MessageResponse("all Friend successfully deleted !"));
	}
	
	@PutMapping(path="/changeFriend")
	public ResponseEntity<?> changeFriend(@RequestBody Friend friend){
		Friend res = friendRepository.findById(friend.getId_friend()).get();
		res.setId_user(friend.getId_user());
		res.setId_useradd(friend.getId_useradd());
		friendRepository.save(res);
		return ResponseEntity.ok(new MessageResponse("Friend successfully changed !"));
	}
}
