package ToolScript.Server.api.stories;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import ToolScript.Server.api.users.User;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Table(value = "Story")
public class Story {
    //Attributes
    @Id
    private Integer id_story;
	
    private String title_story;
	
    private String story_story;

    private Set<UserId> users = new HashSet<>();
    
	
	//Constructor
    public Story(Integer id, String title, String story, User author) {
        super();
        this.id_story = id;
        this.title_story = title;
        this.story_story = story;
		this.users.add(new UserId(author.getIdUser(), "W"));
    }

	//Getters
    @Column(value = "id_story")
    public Integer getId() {
        return id_story;
    }
    
    @Column(value = "title_story")
    public String getTitle() {
        return title_story;
    }

	@Column(value = "story_story")
    public String getStory() {
        return story_story;
    }
	
	Set<Integer> getUserIds() {
        return this.users.stream()
                .map(UserId::getUser)
                .collect(Collectors.toSet());
    }


	//Setters
    public void setTitle(String newTitle) {
        this.title_story = newTitle;
    }
	
	public void setStory(String newStory) {
        this.story_story = newStory;
    }
	
	public void setUsersIds(Set<UserId> users) {
        this.users = users;
    }
	
	
	//Methods
    public static Story create(String title, User author) {
        return new Story(null, title, "", author);
    }

    public void addUserPerm(User user, String perm) {
        this.users.add(new UserId(user.getIdUser(), perm));
    }
}