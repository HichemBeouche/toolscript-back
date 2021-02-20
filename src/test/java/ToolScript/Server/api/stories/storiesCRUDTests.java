package ToolScript.Server.api.stories;

import ToolScript.Server.api.users.User;
import ToolScript.Server.api.users.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJdbcTest
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

        Story story = Story.create("aTitle", user);
        storyRepository.save(story);

        //Pour chaque histoire, retourner les utilisateurs y ayant accès
        for (Story s : storyRepository.findAll()) {
            System.out.println(userRepository.findAllById(s.getUserIds()));
        }

        //Pour chaque utilisateur, retourner les histoires auquelles il a accès
        for (User u : userRepository.findAll()) {
            System.out.println(storyRepository.findAllForUser(u.getIdUser()));
        }
    }
}
