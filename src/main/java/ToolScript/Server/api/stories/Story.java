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
        this.title = newTitle;
    }

    public void setDesc(String newDesc) {
        this.desc = newDesc;
    }
	
	public void setStory(String newStory) {
        this.story = newStory;
    }
	
	public void setUsersIds(Set<UserId> users) {
        this.users = users;
    }
	
	
	//Methods
    public static Story create(String title, String desc, User author) {
        Story s = new Story();
        s.setTitle(title);
        s.setDesc(desc);
        s.addUserPerm(author, "W");
        return s;
    }

    public void addUserPerm(User user, String perm) {
        this.users.add(new UserId(user.getIdUser(), perm));
    }
}