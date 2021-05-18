package ToolScript.Server.api.users;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.api.toolscript.models.Friend;
import com.api.toolscript.models.User;
import com.api.toolscript.repository.FriendRepository;
import com.api.toolscript.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= {com.api.toolscript.ToolScriptApp.class})
@ActiveProfiles("test")
public class FriendCRUDTest {

	 @Autowired
	 private UserRepository userRepository;
	 
	 @Autowired
	 private FriendRepository friendRepository;
	 
	 private Friend friend;
	 
	 private User user;
	 
	 private User user1;
	    
	    @Before
	    public void init() {
	        userRepository.deleteAll();
	        friendRepository.deleteAll();
	        user1 = new User();
	        for(int i = 0; i < 5; i++) {
	        	user = new User();
	            user.setUsername("testUser" + i);
	            friend = new Friend();
	            friend.setId_user(user1.getId_user());
	            friend.setId_useradd(user.getId_user());
	            userRepository.save(user);
	            friendRepository.save(friend);
	        }
	        Assert.assertNotNull(user.getId_user());
	    }
	    
	    @Test
	    public void testCreateFriend() {
	    	List<User> users = userRepository.findAll();
	    	friend = new Friend();
	    	friend.setId_user(users.get(0).getId_user());
	    	friend.setId_useradd(users.get(1).getId_user());
	    	friendRepository.save(friend);
	    	assertNotNull(friendRepository.findAll());
	    	assertNotNull(friend.getId_friend());
	    	assertNotNull(friend.getId_user());
	    	assertNotNull(friend.getId_useradd());
	    }
	    
	    @Test
	    public void testDeleteAllFriends() {
	    	friendRepository.deleteAll();
	    	assertEquals(0,friendRepository.count());
	    }
	    
	
}
