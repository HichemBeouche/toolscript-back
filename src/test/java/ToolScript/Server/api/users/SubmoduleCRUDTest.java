package ToolScript.Server.api.users;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.api.toolscript.models.Story;
import com.api.toolscript.models.Module;
import com.api.toolscript.models.Submodule;
import com.api.toolscript.models.User;
import com.api.toolscript.repository.ModuleRepository;
import com.api.toolscript.repository.StoryRepository;
import com.api.toolscript.repository.SubmoduleRepository;
import com.api.toolscript.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= {com.api.toolscript.ToolScriptApp.class})
@ActiveProfiles("test")
public class SubmoduleCRUDTest {
	
	@Autowired
	private SubmoduleRepository submoduleRepository;
	
	@Autowired
	private ModuleRepository moduleRepository;
	
	@Autowired
	private StoryRepository storyRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	private Module module;
	
	private Story story;
	
	private Submodule submodule;
	
	private User user;
	
	@Before
	public void init() {
		user = new User();
		user.setUsername("testUserForSubmodule");
		userRepository.save(user);
		submoduleRepository.deleteAll();
		moduleRepository.deleteAll();
		story = Story.create("test", "desc", user.getId_user() );
		storyRepository.save(story);
		module = Module.create("test", story.getId(), 0);
		moduleRepository.save(module);
		
		for(int i = 0; i < 5; i++) {
			
			submodule = new Submodule();
			submodule.setName_submodule("testSub" + i);
			submodule.setIdModule(Long.valueOf(module.getId()));
			submodule.setIdStory(Long.valueOf(story.getId()));
			submoduleRepository.save(submodule);
			
		}
		Assert.assertNotNull(submodule.getId_submodule());
	}
	
	@Test
	public void testGetSubmodulesStory() {
		List<Submodule> sub = submoduleRepository.findAll();
		Long id = sub.get(0).getIdStory();
		List<Submodule> res = submoduleRepository.findAllByIdStory(id);
		assertNotNull(submoduleRepository.findAll());
		assertEquals(5, res.size());
	}
	
	@Test
	public void testCreateSubmodule() {
		submoduleRepository.deleteAll();
		submodule = new Submodule();
		submodule.setIdModule(Long.valueOf(module.getId()));
		submodule.setName_submodule("test1");
		submodule.setIdStory(Long.valueOf(story.getId()));
		submoduleRepository.save(submodule);
		assertNotNull(submoduleRepository.findAll());
		assertNotNull(submodule.getId_submodule());
	}
	
	@Test
	public void testChangeName() {
		submodule = new Submodule();
		submodule.setIdModule(Long.valueOf(module.getId()));
		submodule.setIdStory(Long.valueOf(story.getId()));
		submodule.setName_submodule("test");
		submoduleRepository.save(submodule);
		List<Submodule> res = submoduleRepository.findAllByIdModule(module.getId());
		Submodule test = res.get(0);
		test.setName_submodule("changeName");
		submoduleRepository.save(test);
		res = submoduleRepository.findAllByIdModule(module.getId());
		test = res.get(0);
		assertEquals("changeName", test.getName_submodule());
		
	}
	
	@Test
	public void testChangeNote() {
		submodule = new Submodule();
		submodule.setIdModule(Long.valueOf(module.getId()));
		submodule.setIdStory(Long.valueOf(story.getId()));
		submodule.setNote("test");
		submoduleRepository.save(submodule);
		List<Submodule> res = submoduleRepository.findAllByIdModule(module.getId());
		Submodule test = res.get(0);
		test.setNote("changeNote");
		submoduleRepository.save(test);
		res = submoduleRepository.findAllByIdModule(module.getId());
		test = res.get(0);
		assertEquals("changeNote", test.getNote());
	}

}
