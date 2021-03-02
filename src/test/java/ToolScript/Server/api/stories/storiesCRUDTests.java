package ToolScript.Server.api.stories;

import ToolScript.Server.api.users.User;
import ToolScript.Server.api.users.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJdbcTest
@AutoConfigureTestDatabase (replace = AutoConfigureTestDatabase.Replace.NONE)
@Commit
public class storiesCRUDTests {
    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testRelationships() {
        User user = new User();
        user.setUsername("testUser");
        userRepository.save(user);
        Assert.assertNotNull(user.getIdUser());

        Story story = Story.create("aTitle", user);
        storyRepository.save(story);

        Assert.assertNotNull(story.getId());
        Assert.assertEquals("aTitle", story.getTitle());

        //Pour chaque histoire, retourner les utilisateurs y ayant accès
        for (Story s : storyRepository.findAll()) {
            Assert.assertNotNull(s);
            Assert.assertEquals(user.getIdUser(), userRepository.findAllById(s.getUserIds()).iterator().next().getIdUser());
        }

        //Pour chaque utilisateur, retourner les histoires auxquelles il a accès
        for (User u : userRepository.findAll()) {
            Assert.assertNotNull(u);
            Assert.assertNotNull(storyRepository.findAllForUser(user.getIdUser()));
            Assert.assertEquals(story.getId(), storyRepository.findAllForUser(user.getIdUser()).get(0).getId());
        }

        storyRepository.delete(story);
        userRepository.delete(user);
    }
}
