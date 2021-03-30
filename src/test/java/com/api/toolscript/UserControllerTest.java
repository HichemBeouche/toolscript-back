package com.api.toolscript;



import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.api.toolscript.controllers.UserController;
import com.api.toolscript.models.User;
import com.api.toolscript.repository.UserRepository;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.Optional;

@AutoConfigureMockMvc
@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {
	
	@InjectMocks
	UserController userController;
	
	
	@Mock
	UserRepository userRepository;
	
	
	@Autowired
	private MockMvc mockMvc;
	
	@Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }
	
	@Test
	public void testgetAllUsers() throws Exception {
		
		User user1 = new User("pierre","pierre@gmail.com","123" );
		User user2 = new User("jean","jean@gmail.com","4568" );
		userRepository.save(user1);
		userRepository.save(user2);


		Mockito.when(userController.getAllUsers()).thenReturn(Arrays.asList(user1,user2));
		mockMvc.perform(MockMvcRequestBuilders.get("/users"))
					.andExpect(MockMvcResultMatchers.status().is(200))
					.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
					.andExpect(MockMvcResultMatchers.jsonPath("$.size", Matchers.is(2)))
					.andExpect(MockMvcResultMatchers.jsonPath("$[0].username", Matchers.is("pierre")))
					.andExpect(MockMvcResultMatchers.jsonPath("$[0].email", Matchers.is("pierre@gmail.com")))
					.andExpect(MockMvcResultMatchers.jsonPath("$[0].password", Matchers.is("123")))
					.andExpect(MockMvcResultMatchers.jsonPath("$[1].username", Matchers.is("jean")))
					.andExpect(MockMvcResultMatchers.jsonPath("$[1].email", Matchers.is("jean@gmail.com")))
					.andExpect(MockMvcResultMatchers.jsonPath("$[1].password", Matchers.is("4568")));
					


	}
	
	
	//public void testGet
	
}

