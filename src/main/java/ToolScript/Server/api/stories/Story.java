package ToolScript.Server.api.stories;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import ToolScript.Server.api.users.User;

@Entity
@Table(value = "Story")
public class Story {
    //Attributes
    @Id
    private Integer id_story;
	
    private String title_story;
	
    private String story_story;

    private Map<UserId, String> usersPerm = new Map<UserId, String>();
    
	
	//Constructor
    public Story(Integer id, String title, String story, User author) {
        super();
        this.id_story = id;
        this.title_story = title;
        this.story_story = story;
		this.usersPerm.put(author, 'W');
    }

    Story create(String title, User author) {
        return new Story(null, title, "", author);
    }

    void addUserPerm(User user, String perm) {
        this.usersPerm.put(new UserId(user.getId()), perm)
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
	
	Map<UserId, String> getUsersPermMap() {
        return this.usersPerm.stream()
                .map(UserId::getUser)
                .collect(Collectors.toMap());
    }


	//Setters
    public void setTitle(String newTitle) {
        this.title_story = newTitle;
    }
	
	public void setStory(String newStory) {
        this.story_story = newStory;
    }
	
	public void setUsersPermMap(Map<User, String> usersPerm) {
        this.usersPerm = usersPerm;
    }
	
	
	//Methods
	public void addPermission(User user, String perm) {
		this.usersPerm.put(user, perm);
	}
}