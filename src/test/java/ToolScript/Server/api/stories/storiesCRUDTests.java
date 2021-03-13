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
//@Commit
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

        Story story = Story.create("aTitle", null, user);
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

    @Test
    public void testCreate() {
        //Le titre est null ou "" ou " "
        Exception exceptionTitreNull = Assert.assertThrows(IllegalArgumentException.class, () -> {
            Story.create(null, null, null);
        });
        Assert.assertTrue((exceptionTitreNull.getMessage()).contains("Le titre ne peut être vide"));
        Exception exceptionTitreVide = Assert.assertThrows(IllegalArgumentException.class, () -> {
            Story.create("", null, null);
        });
        Assert.assertTrue((exceptionTitreVide.getMessage()).contains("Le titre ne peut être vide"));
        Exception exceptionTitreEspace = Assert.assertThrows(IllegalArgumentException.class, () -> {
            Story.create(" ", null, null);
        });
        Assert.assertTrue((exceptionTitreEspace.getMessage()).contains("Le titre ne peut être vide"));

        //L'auteur est null
        Exception exceptionAuteurNull = Assert.assertThrows(IllegalArgumentException.class, () -> {
            Story.create("Un titre", null, null);
        });
        Assert.assertTrue((exceptionAuteurNull.getMessage()).contains("L'auteur ne peut être null"));

        //Création OK
        User user = new User();
        user.setUsername("testUser");
        userRepository.save(user);
        Assert.assertNotNull(user.getIdUser());
        Assert.assertEquals(0, storyRepository.findAllForUser(user.getIdUser()).size());
        Story story = Story.create("aTitle", null, user);
        storyRepository.save(story);
        Assert.assertNotNull(story.getId());
        Assert.assertEquals("aTitle", story.getTitle());
        Assert.assertEquals(1, storyRepository.findAllForUser(user.getIdUser()).size());
        Assert.assertEquals(user.getIdUser(), userRepository.findAllById(story.getUserIds()).iterator().next().getIdUser());

        //Longueur de champs non valide pour la BDD
        Exception exceptionTitreTropLong = Assert.assertThrows(IllegalArgumentException.class, () -> {
            Story.create("ccccccccccccccccccccccccccccccccccccccccc", null, user);
        });
        Assert.assertTrue((exceptionTitreTropLong.getMessage()).contains("Le titre ne peut dépasser 40 caractères"));

        Exception exceptionDescriptionTropLongue = Assert.assertThrows(IllegalArgumentException.class, () -> {
            Story.create("Un titre", "ccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc", user);
        });
        Assert.assertTrue((exceptionDescriptionTropLongue.getMessage()).contains("La description ne peut dépasser 200 caractères"));

        Exception exceptionHistoireTropLongue = Assert.assertThrows(IllegalArgumentException.class, () -> {
            story.setStory("ccccccccccccccccccccccccccccccccccccccccc");
        });
        Assert.assertTrue((exceptionHistoireTropLongue.getMessage()).contains("L'histoire ne peut être vide et ne peut dépasser 40 caractères"));
    }

    @Test
    public void testDelete() {
        User user = new User();
        user.setUsername("testUser");
        userRepository.save(user);
        Assert.assertNotNull(user.getIdUser());
        Story story = Story.create("aTitle", null, user);
        storyRepository.save(story);
        Assert.assertNotNull(story.getId());
        Assert.assertEquals(1, storyRepository.findAllForUser(user.getIdUser()).size());
        storyRepository.delete(story);
        Assert.assertEquals(0, storyRepository.findAllForUser(user.getIdUser()).size());
    }

    @Test
    public void testAddUserPerm() {
        User user1 = new User();
        user1.setUsername("testUser1");
        userRepository.save(user1);
        Assert.assertNotNull(user1.getIdUser());
        User user2 = new User();
        user2.setUsername("testUser2");
        userRepository.save(user2);
        Assert.assertNotNull(user2.getIdUser());
        Story story = Story.create("aTitle", null, user1);
        storyRepository.save(story);
        Assert.assertNotNull(story.getId());
        int length = 0;
        while (story.getUserIds().iterator().hasNext()) {
            story.getUserIds().iterator().next();
            length++;
        }
        Assert.assertEquals(1, length);
        Exception exceptionUser = Assert.assertThrows(IllegalArgumentException.class, () -> {
            story.addUserPerm(null, "WWW");
        });
        Assert.assertTrue((exceptionUser.getMessage()).contains("L'utilisateur ne peut être null"));
        Exception exceptionPermission = Assert.assertThrows(IllegalArgumentException.class, () -> {
            story.addUserPerm(user2, "WWW");
        });
        Assert.assertTrue((exceptionPermission.getMessage()).contains("La permission renseignée n'est pas disponible"));
        story.addUserPerm(user2, "W");
        length = 0;
        while (story.getUserIds().iterator().hasNext()) {
            story.getUserIds().iterator().next();
            length++;
        }
        Assert.assertEquals(2, length);
    }
}
