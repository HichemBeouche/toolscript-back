package com.api.toolscript.models;

import com.api.toolscript.repository.ModuleRepository;
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
public class ModuleCRUDTests {
    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private StoryRepository storyRepository;

    @Autowired
    private UserRepository userRepository;

    private User user;
    private Story story;
    private Module module;

    @Before
    public void init() {
        userRepository.deleteAll();
        storyRepository.deleteAll();
        moduleRepository.deleteAll();

        user = new User();
        user.setUsername("testUser"+new Random().nextInt());
        userRepository.save(user);
        Assert.assertNotNull(user.getId_user());
        story = Story.create("Un titre", null, user.getId_user());
        storyRepository.save(story);
        Assert.assertNotNull(story.getId());
        module = Module.create("Un nom", story.getId(), null);
        moduleRepository.save(module);
        Assert.assertNotNull(module.getId());
    }


    @Test
    public void testCreate() {
        //Le nom est null ou "" ou " "
        Exception exceptionNomNull = Assert.assertThrows(IllegalArgumentException.class, () -> Module.create(null, story.getId(), null));
        Assert.assertTrue((exceptionNomNull.getMessage()).contains("Error: Le nom ne peut être vide !"));
        Exception exceptionNomVide = Assert.assertThrows(IllegalArgumentException.class, () -> Module.create("", story.getId(), null));
        Assert.assertTrue((exceptionNomVide.getMessage()).contains("Error: Le nom ne peut être vide !"));
        Exception exceptionNomEspace = Assert.assertThrows(IllegalArgumentException.class, () -> Module.create(" ", story.getId(), null));
        Assert.assertTrue((exceptionNomEspace.getMessage()).contains("Error: Le nom ne peut être vide !"));

        //L'histoire est null
        Exception exceptionIdHistoireNull = Assert.assertThrows(IllegalArgumentException.class, () -> Module.create("Un nom", null, null));
        Assert.assertTrue((exceptionIdHistoireNull.getMessage()).contains("Error: L'identifiant de l'histoire ne peut être null !"));
        //L'id de l'histoire est incorrect
        Exception exceptionIdHistoireIncorrect = Assert.assertThrows(IllegalArgumentException.class, () -> Module.create("Un nom", 0, null));
        Assert.assertTrue((exceptionIdHistoireIncorrect.getMessage()).contains("Error: L'identifiant de l'histoire est incorrect !"));

        //L'id du module parent est incorrect
        Exception exceptionIdParentIncorrect = Assert.assertThrows(IllegalArgumentException.class, () -> Module.create(" ", story.getId(), 0));
        Assert.assertTrue((exceptionIdParentIncorrect.getMessage()).contains("Error: L'identifiant de du module parent est incorrect !"));

        //Longueur de champs non valide pour la BDD
        //Le nom est trop long
        Exception exceptionNomTropLong = Assert.assertThrows(IllegalArgumentException.class, () -> Module.create("ccccccccccccccccccccccccccccccccccccccccc", story.getId(), null));
        Assert.assertTrue((exceptionNomTropLong.getMessage()).contains("Error: Le nom ne peut dépasser 40 caractères !"));

        //Création OK
        user = new User();
        user.setUsername("testUser"+new Random().nextInt());
        userRepository.save(user);
        Assert.assertNotNull(user.getId_user());
        story = Story.create("un titre", null, user.getId_user());
        storyRepository.save(story);
        Assert.assertNotNull(module.getId());
        Assert.assertEquals(0, moduleRepository.findAllMainModulesForStory(story.getId()).size());
        Module module1 = Module.create("un nom", story.getId(), null);
        moduleRepository.save(module1);
        Assert.assertEquals("un nom", module1.getName());
        Assert.assertEquals(story.getId(), module1.getIdStory());
        Assert.assertNull(module1.getIdParent());
        Assert.assertNull(module1.getChildren());
        Assert.assertEquals(1, moduleRepository.findAllMainModulesForStory(story.getId()).size());
        Module module2 = Module.create("un autre nom", story.getId(), null);
        moduleRepository.save(module2);
        Assert.assertEquals("un autre nom", module2.getName());
        Assert.assertEquals(story.getId(), module2.getIdStory());
        Assert.assertNull(module2.getIdParent());
        Assert.assertNull(module2.getChildren());
        Assert.assertEquals(2, moduleRepository.findAllMainModulesForStory(story.getId()).size());
    }


    @Test
    public void testRelationships() {
        //Module principal de l'histoire
        Assert.assertNull(module.getIdParent());
        Assert.assertNull(module.getChildren());
        Assert.assertEquals(1, moduleRepository.findAllMainModulesForStory(story.getId()).size());
        module.setChildren(moduleRepository.findAllChildrenById(module.getId()));
        Assert.assertEquals(0, module.getChildren().size());

        //Module enfant du module principal de l'histoire
        Module module2 = Module.create("un autre nom", story.getId(), module.getId());
        moduleRepository.save(module2);
        Assert.assertEquals("un autre nom", module2.getName());
        Assert.assertEquals(story.getId(), module2.getIdStory());
        Assert.assertEquals(module.getId(), module2.getIdParent());
        Assert.assertEquals(1, moduleRepository.findAllMainModulesForStory(story.getId()).size());

        //Relation Parent/Enfant
        module.setChildren(moduleRepository.findAllChildrenById(module.getId()));
        Assert.assertEquals(1, module.getChildren().size());
    }


    @Test
    public void testUpdate() {
        //Longueur de champs non valide pour la BDD
        //Le nouveau nom est null
        Exception exceptionNomNull = Assert.assertThrows(IllegalArgumentException.class, () -> module.setName(null));
        Assert.assertTrue((exceptionNomNull.getMessage()).contains("Error: Le nom ne peut être vide !"));
        //Le nouveau nom est vide
        Exception exceptionNomVide = Assert.assertThrows(IllegalArgumentException.class, () -> module.setName(""));
        Assert.assertTrue((exceptionNomVide.getMessage()).contains("Error: Le nom ne peut être vide !"));
        //Le nouveau nom est un espace
        Exception exceptionNomEspace = Assert.assertThrows(IllegalArgumentException.class, () -> module.setName(" "));
        Assert.assertTrue((exceptionNomEspace.getMessage()).contains("Error: Le nom ne peut être vide !"));
        //Le nouveau nom est trop long
        Exception exceptionNomTropLong = Assert.assertThrows(IllegalArgumentException.class, () -> module.setName("ccccccccccccccccccccccccccccccccccccccccc"));
        Assert.assertTrue((exceptionNomTropLong.getMessage()).contains("Error: Le nom ne peut dépasser 40 caractères !"));

        //Modification OK
        Assert.assertEquals("Un nom", module.getName());
        module.setName("Un nouveau nom");
        Assert.assertEquals("Un nouveau nom", module.getName());
    }


    @Test
    public void testDelete() {
        Assert.assertEquals(1, moduleRepository.findAllMainModulesForStory(story.getId()).size());
        moduleRepository.delete(module);
        Assert.assertEquals(0, moduleRepository.findAllMainModulesForStory(story.getId()).size());
    }
}
