package ToolScript.Server.api.stories;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;
import ToolScript.Server.api.users.User;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Table(value = "Story")
public class Story {
    //Attributes
    @Id
    private Integer idStory;

    @Column(value = "title_story")
    private String title;

    @Column(value = "desc_story")
    private String desc;

    @Column(value = "story_story")
    private String story;

    @MappedCollection(idColumn = "id_story")
    private Set<UserId> users = new HashSet<>();


	//Getters
    public Integer getId() {
        return idStory;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getStory() {
        return story;
    }
	
	Set<Integer> getUserIds() {
        return this.users.stream()
                .map(UserId::getIdUser)
                .collect(Collectors.toSet());
    }


	//Setters
    public void setTitle(String newTitle) {
        if (newTitle == null || newTitle.isBlank() || newTitle.length() > 40) {throw new IllegalArgumentException("Le titre ne peut être vide et ne peut dépasser 40 caractères");}
        else { this.title = newTitle; }
    }

    public void setDesc(String newDesc) {
        if (newDesc == null || newDesc.isBlank() || newDesc.length() > 200) {throw new IllegalArgumentException("La description ne peut être vide et ne peut dépasser 200 caractères");}
        else { this.desc = newDesc; }
    }
	
	public void setStory(String newStory) {
        if (newStory == null || newStory.isBlank() || newStory.length() > 40) {throw new IllegalArgumentException("L'histoire ne peut être vide et ne peut dépasser 40 caractères");}
        else { this.story = newStory; }
    }

    /*
	public void setUsersIds(Set<UserId> users) {
        this.users = users;
    }*/
	
	
	//Methods
    public static Story create(String title, String desc, User author) {
        if (title == null || title.isBlank()) { throw new IllegalArgumentException("Le titre ne peut être vide");}
        else if (author == null) { throw new IllegalArgumentException("L'auteur ne peut être null");}
        else if (title.length() > 40) {throw new IllegalArgumentException("Le titre ne peut dépasser 40 caractères");}
        else if (desc != null && desc.length() > 200) {throw new IllegalArgumentException("La description ne peut dépasser 200 caractères");}
        else {
            Story s = new Story();
            s.setTitle(title);
            if (desc != null && !desc.isBlank()) { s.setDesc(desc); }
            s.addUserPerm(author, "W");
            return s;
        }
    }

    public void addUserPerm(User user, String perm) {
        if (user == null) { throw new IllegalArgumentException("L'utilisateur ne peut être null"); }
        //Condition à modifier une fois les différentes permissions discutées
        else if (perm == null || perm.length() > 2) { throw new IllegalArgumentException("La permission renseignée n'est pas disponible"); }
        else { this.users.add(new UserId(user.getIdUser(), perm)); }
    }
}
