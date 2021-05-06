package com.api.toolscript.models;

import com.api.toolscript.repository.StoryRepository;
import com.api.toolscript.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= {com.api.toolscript.ToolScriptApp.class})
@ActiveProfiles("test")
public class StoryCRUDTests {

	@Test
	public void contextLoads() {
	}

    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;
    private Story story;

    @Before
    public void init() {

        userRepository.deleteAll();
        storyRepository.deleteAll();
        user = new User();
        user.setUsername("testUser"+new Random().nextInt());
        userRepository.save(user);
        Assert.assertNotNull(user.getId_user());
        story = Story.create("Un titre", null, user.getId_user());
        storyRepository.save(story);
        Assert.assertNotNull(story.getId());
    }

    @Test
    public void testCreate() {
        //Le titre est null ou "" ou " "
        Exception exceptionTitreNull = Assert.assertThrows(IllegalArgumentException.class, () -> Story.create(null, null, -1));
        Assert.assertTrue((exceptionTitreNull.getMessage()).contains("Error: Le titre ne peut être vide !"));
        Exception exceptionTitreVide = Assert.assertThrows(IllegalArgumentException.class, () -> Story.create("", null, -1));
        Assert.assertTrue((exceptionTitreVide.getMessage()).contains("Error: Le titre ne peut être vide !"));
        Exception exceptionTitreEspace = Assert.assertThrows(IllegalArgumentException.class, () -> Story.create(" ", null, -1));
        Assert.assertTrue((exceptionTitreEspace.getMessage()).contains("Error: Le titre ne peut être vide !"));

        //L'auteur est null
        Exception exceptionAuteurNull = Assert.assertThrows(IllegalArgumentException.class, () -> Story.create("Un titre", null, -1));
        Assert.assertTrue((exceptionAuteurNull.getMessage()).contains("Error: L'identifiant de l'auteur est incorrect !"));

        //Longueur de champs non valide pour la BDD
        //Le titre est trop long
        Exception exceptionTitreTropLong = Assert.assertThrows(IllegalArgumentException.class, () -> Story.create("ccccccccccccccccccccccccccccccccccccccccc", null, user.getId_user()));
        Assert.assertTrue((exceptionTitreTropLong.getMessage()).contains("Error: Le titre ne peut dépasser 40 caractères !"));

        //La description est trop longue
        Exception exceptionDescriptionTropLongue = Assert.assertThrows(IllegalArgumentException.class, () -> Story.create("Un titre", "ccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc", user.getId_user()));
        Assert.assertTrue((exceptionDescriptionTropLongue.getMessage()).contains("Error: La description ne peut dépasser 200 caractères !"));

        //L'histoire est trop longue
        //Exception exceptionHistoireTropLongue = Assert.assertThrows(IllegalArgumentException.class, () -> story.setStory("ccccccccccccccccccccccccccccccccccccccccc"));
        //Assert.assertTrue((exceptionHistoireTropLongue.getMessage()).contains("Error: L'histoire ne peut dépasser 4 294 967 295 caractères !"));


        //Création OK
        user = new User();
        user.setUsername("testUser"+new Random().nextInt());
        userRepository.save(user);
        Assert.assertNotNull(user.getId_user());
        Assert.assertEquals(0, storyRepository.findAllForUser(user.getId_user()).size());
        story = Story.create("aTitle", null, user.getId_user());
        storyRepository.save(story);
        Assert.assertNotNull(story.getId());
        Assert.assertEquals("aTitle", story.getTitle());
        Assert.assertNull(story.getDesc());
        Assert.assertNull(story.getStory());
        Assert.assertEquals(1, storyRepository.findAllForUser(user.getId_user()).size());
        Assert.assertEquals(user.getId_user(), userRepository.findAllById(story.getUserIds()).iterator().next().getId_user());

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
        Assert.assertTrue((exceptionTitreNull.getMessage()).contains("Error: Le titre ne peut être vide !"));
        //Le nouveau titre est vide
        Exception exceptionTitreVide = Assert.assertThrows(IllegalArgumentException.class, () -> story.setTitle(""));
        Assert.assertTrue((exceptionTitreVide.getMessage()).contains("Error: Le titre ne peut être vide !"));
        //Le nouveau titre est un espace
        Exception exceptionTitreEspace = Assert.assertThrows(IllegalArgumentException.class, () -> story.setTitle(" "));
        Assert.assertTrue((exceptionTitreEspace.getMessage()).contains("Error: Le titre ne peut être vide !"));
        //Le nouveau titre est trop long
        Exception exceptionTitreTropLong = Assert.assertThrows(IllegalArgumentException.class, () -> story.setTitle("ccccccccccccccccccccccccccccccccccccccccc"));
        Assert.assertTrue((exceptionTitreTropLong.getMessage()).contains("Error: Le titre ne peut dépasser 40 caractères !"));

        //La nouvelle description est trop longue
        Exception exceptionDescriptionTropLongue = Assert.assertThrows(IllegalArgumentException.class, () -> story.setDesc("ccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccccc"));
        Assert.assertTrue((exceptionDescriptionTropLongue.getMessage()).contains("Error: La description ne peut dépasser 200 caractères !"));

        //La nouvelle histoire est trop longue
        //Exception exceptionHistoireTropLongue = Assert.assertThrows(IllegalArgumentException.class, () -> story.setStory("ccccccccccccccccccccccccccccccccccccccccc"));
        //Assert.assertTrue((exceptionHistoireTropLongue.getMessage()).contains("Error: L'histoire ne peut dépasser 4 294 967 295 caractères !"));

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
        Exception exceptionUser = Assert.assertThrows(IllegalArgumentException.class, () -> story.addUserPerm(-1, "WWW"));
        Assert.assertTrue((exceptionUser.getMessage()).contains("Error: L'identifiant de l'utilisateur est incorrect !"));

        //Nouvel utilisateur
        User user2 = new User();
        user2.setUsername("testUser2");
        userRepository.save(user2);
        Assert.assertNotNull(user2.getId_user());

        //La permission à ajouter n'est pas valide
        Exception exceptionPermission = Assert.assertThrows(IllegalArgumentException.class, () -> story.addUserPerm(user2.getId_user(), "WWW"));
        Assert.assertTrue((exceptionPermission.getMessage()).contains("Error: La permission renseignée n'est pas disponible !"));

        //Ajout OK
        story.addUserPerm(user2.getId_user(), "W");
        Assert.assertEquals(2, story.getUserIds().size());
    }
}
