package ToolScript.Server.api.users;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Random;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.api.toolscript.models.Story;
import com.api.toolscript.models.User;
import com.api.toolscript.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= {com.api.toolscript.ToolScriptApp.class})
@ActiveProfiles("test")
public class usersReadUpdateTests {
    @Autowired
    private UserRepository userRepository;
    private User user;

    @Before
    public void init() {
        userRepository.deleteAll();
        for(int i = 0; i < 5; i++) {
        	user = new User();
            user.setUsername("testUser" + i);
            userRepository.save(user);
        }
        Assert.assertNotNull(user.getId_user());
    }
    
    @Test
    public void testGetAllUsers() {
    	assertNotNull(userRepository.findAll());
    	assertEquals(5, userRepository.count());
    }
    
    @Test
    public void testGetUserById() {
    	
    }
    
    @Test
    public void testChangeUsername() {
    	user = new User();
    	user.setUsername("test");
    	userRepository.save(user);
    	User res = userRepository.findByUsername("test").get();
    	res.setUsername("test2");
    	userRepository.save(res);
    	assertEquals("test2", userRepository.findByUsername("test2").get().getUsername());
    }
    
    
}

