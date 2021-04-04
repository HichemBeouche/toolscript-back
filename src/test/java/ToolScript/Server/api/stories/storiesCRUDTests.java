package ToolScript.Server.api.stories;

import com.api.toolscript.models.Story;
import com.api.toolscript.models.User;
import com.api.toolscript.repository.StoryRepository;
import com.api.toolscript.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;

@RunWith(SpringRunner.class)
@DataJdbcTest
@AutoConfigureTestDatabase (replace = AutoConfigureTestDatabase.Replace.NONE)
//@Commit
public class storiesCRUDTests {
    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;
    private Story story;

    @Before
    public void init() {
        user = new User();
        user.setUsername("testUser"+new Random().nextInt());
        userRepository.save(user);
        Assert.assertNotNull(user.getId_user());
        story = Story.create("Un titre", null, user);
        storyRepository.save(story);
        Assert.assertNotNull(story.getId());
    }

    @Test
    public void testCreate() {
        //Le titre est null ou "" ou " "
        Exception exceptionTitreNull = Assert.assertThrows(IllegalArgumentException.class, () -> Story.create(null, null, null));
        Assert.assertTrue((exceptionTitreNull.getMessage()).contains("Le titre ne peut être vide"));
        Exception exceptionTitreVide = Assert.assertThrows(IllegalArgumentException.class, () -> Story.create("", null, null));
        Assert.assertTrue((exceptionTitreVide.getMessage()).contains("Le titre ne peut être vide"));
        Exception exceptionTitreEspace = Assert.assertThrows(IllegalArgumentException.class, () -> Story.create(" ", null, null));
        Assert.assertTrue((exceptionTitreEspace.getMessage()).contains("Le titre ne peut être vide"));

        //L'auteur est null
        Exception exceptionAuteurNull = Assert.assertThrows(IllegalArgumentException.class, () -> Story.create("Un titre", null, null));
        Assert.assertTrue((exceptionAuteurNull.getMessage()).contains("L'auteur ne peut être null"));

        //Création OK
        user = new User();
        user.setUsername("testUser"+new Random().nextInt());
        userRepository.save(user);
        Assert.assertNotNull(user.getId_user());
        Assert.assertEquals(0, storyRepository.findAllForUser(user.getId_user()).size());
        story = Story.create("aTitle", null, user);
        storyRepository.save(story);
        Assert.assertNotNull(story.getId());
        Assert.assertEquals("aTitle", story.getTitle());
        Assert.assertNull(story.getDesc());
        Assert.assertNull(story.getStory());
        Assert.assertEquals(1, storyRepository.findAllForUser(user.getId_user()).size());
        Assert.assertEquals(user.getId_user(), userRepository.findAllById(story.getUserIds()).iterator().next().getId_user());

        //Longueur de champs non valide pour la BDD
        //Le titre est trop long
        Exception exceptionTitreTropLong = Assert.assertThrows(IllegalArgumentException.class, () -> Story.create("ccccccccccccccccccccccccccccccccccccccccc", null, user));
        Assert.assertTrue((exceptionTitreTropLong.getMessage()).contains("Le titre ne peut dépasser 40 caractères"));

        //La description est trop longue
        Exception exceptionDescriptionTropLongue = Assert.assertThrows(IllegalArgumentException.class, () -> Story.create("Un titre", "ccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc", user));
        Assert.assertTrue((exceptionDescriptionTropLongue.getMessage()).contains("La description ne peut dépasser 200 caractères"));

        //L'histoire est trop longue
        Exception exceptionHistoireTropLongue = Assert.assertThrows(IllegalArgumentException.class, () -> story.setStory("ccccccccccccccccccccccccccccccccccccccccc"));
        Assert.assertTrue((exceptionHistoireTropLongue.getMessage()).contains("L'histoire ne peut être vide et ne peut dépasser 40 caractères"));
    }


    @Test
    public void testRelationships() {
        //Pour chaque histoire, retourner les utilisateurs y ayant accès
        for (Story s : storyRepository.findAll()) {
            Assert.assertNotNull(s);
            Assert.assertEquals(user.getId_user(), userRepository.findAllById(s.getUserIds()).iterator().next().getId_user());
        }

        //Pour chaque utilisateur, retourner les histoires auxquelles il a accès
        for (User u : userRepository.findAll()) {
            Assert.assertNotNull(u);
            Assert.assertNotNull(storyRepository.findAllForUser(user.getId_user()));
            Assert.assertEquals(story.getId(), storyRepository.findAllForUser(user.getId_user()).get(0).getId());
        }
    }


    @Test
    public void testUpdate() {
        //Longueur de champs non valide pour la BDD
        //Le nouveau titre est null
        Exception exceptionTitreNull = Assert.assertThrows(IllegalArgumentException.class, () -> story.setTitle(null));
        Assert.assertTrue((exceptionTitreNull.getMessage()).contains("Le titre ne peut être vide et ne peut dépasser 40 caractères"));
        //Le nouveau titre est vide
        Exception exceptionTitreVide = Assert.assertThrows(IllegalArgumentException.class, () -> story.setTitle(""));
        Assert.assertTrue((exceptionTitreVide.getMessage()).contains("Le titre ne peut être vide et ne peut dépasser 40 caractères"));
        //Le nouveau titre est un espace
        Exception exceptionTitreEspace = Assert.assertThrows(IllegalArgumentException.class, () -> story.setTitle(" "));
        Assert.assertTrue((exceptionTitreEspace.getMessage()).contains("Le titre ne peut être vide et ne peut dépasser 40 caractères"));
        //Le nouveau titre est trop long
        Exception exceptionTitreTropLong = Assert.assertThrows(IllegalArgumentException.class, () -> story.setTitle("ccccccccccccccccccccccccccccccccccccccccc"));
        Assert.assertTrue((exceptionTitreTropLong.getMessage()).contains("Le titre ne peut être vide et ne peut dépasser 40 caractères"));

        //La nouvelle description est null
        Exception exceptionDescriptionNull = Assert.assertThrows(IllegalArgumentException.class, () -> story.setDesc(null));
        Assert.assertTrue((exceptionDescriptionNull.getMessage()).contains("La description ne peut être vide et ne peut dépasser 200 caractères"));
        //La nouvelle description est vide
        Exception exceptionDescriptionVide = Assert.assertThrows(IllegalArgumentException.class, () -> story.setDesc(""));
        Assert.assertTrue((exceptionDescriptionVide.getMessage()).contains("La description ne peut être vide et ne peut dépasser 200 caractères"));
        //La nouvelle description est un espace
        Exception exceptionDescriptionEspace = Assert.assertThrows(IllegalArgumentException.class, () -> story.setDesc(" "));
        Assert.assertTrue((exceptionDescriptionEspace.getMessage()).contains("La description ne peut être vide et ne peut dépasser 200 caractères"));
        //La nouvelle description est trop longue
        Exception exceptionDescriptionTropLongue = Assert.assertThrows(IllegalArgumentException.class, () -> story.setDesc("ccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc"));
        Assert.assertTrue((exceptionDescriptionTropLongue.getMessage()).contains("La description ne peut être vide et ne peut dépasser 200 caractères"));

        //La nouvelle histoire est null
        Exception exceptionHistoireNull = Assert.assertThrows(IllegalArgumentException.class, () -> story.setStory(null));
        Assert.assertTrue((exceptionHistoireNull.getMessage()).contains("L'histoire ne peut être vide et ne peut dépasser 40 caractères"));
        //La nouvelle histoire est vide
        Exception exceptionHistoireVide = Assert.assertThrows(IllegalArgumentException.class, () -> story.setStory(""));
        Assert.assertTrue((exceptionHistoireVide.getMessage()).contains("L'histoire ne peut être vide et ne peut dépasser 40 caractères"));
        //La nouvelle histoire est un espace
        Exception exceptionHistoireEspace = Assert.assertThrows(IllegalArgumentException.class, () -> story.setStory(" "));
        Assert.assertTrue((exceptionHistoireEspace.getMessage()).contains("L'histoire ne peut être vide et ne peut dépasser 40 caractères"));
        //La nouvelle histoire est trop longue
        Exception exceptionHistoireTropLongue = Assert.assertThrows(IllegalArgumentException.class, () -> story.setStory("ccccccccccccccccccccccccccccccccccccccccc"));
        Assert.assertTrue((exceptionHistoireTropLongue.getMessage()).contains("L'histoire ne peut être vide et ne peut dépasser 40 caractères"));

        //Modification OK
        Assert.assertEquals("Un titre", story.getTitle());
        story.setTitle("Un nouveau titre");
        Assert.assertEquals("Un nouveau titre", story.getTitle());
        Assert.assertNull(story.getDesc());
        story.setDesc("Une nouvelle description");
        Assert.assertEquals("Une nouvelle description", story.getDesc());
        Assert.assertNull(story.getStory());
        story.setStory("Une nouvelle histoire");
        Assert.assertEquals("Une nouvelle histoire", story.getStory());
    }


    @Test
    public void testDelete() {
        Assert.assertEquals(1, storyRepository.findAllForUser(user.getId_user()).size());
        storyRepository.delete(story);
        Assert.assertEquals(0, storyRepository.findAllForUser(user.getId_user()).size());
    }

    @Test
    public void testAddUserPerm() {
        //Nombre d'utilisateurs ayant accès à l'histoire
        Assert.assertEquals(1, story.getUserIds().size());

        //L'utilisateur à ajouter est null
        Exception exceptionUser = Assert.assertThrows(IllegalArgumentException.class, () -> story.addUserPerm(null, "WWW"));
        Assert.assertTrue((exceptionUser.getMessage()).contains("L'utilisateur ne peut être null"));

        //Nouvel utilisateur
        User user2 = new User();
        user2.setUsername("testUser2");
        userRepository.save(user2);
        Assert.assertNotNull(user2.getId_user());

        //La permission à ajouter n'est pas valide
        Exception exceptionPermission = Assert.assertThrows(IllegalArgumentException.class, () -> story.addUserPerm(user2, "WWW"));
        Assert.assertTrue((exceptionPermission.getMessage()).contains("La permission renseignée n'est pas disponible"));

        //Ajout OK
        story.addUserPerm(user2, "W");
        Assert.assertEquals(2, story.getUserIds().size());
    }
}
