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

    /*@ManyToMany
	@JoinTable(name="Stoy_User",
				joinColumns = { @JoinColum(name="id_story") },
				inverseJoinColumns = { @JoinColum(name="id_user") })
	@MapKeyColumn(name = "permission")
	private Map<User, String> users;*/
	@ElementCollection
	@CollectionTable(name = "Stoy_User", joinColumns = @JoinColumn(name = "id_story"))
	@MapKeyJoinColumn(name = "id_user")
	@Column(name = "permission")
	private Map<User, String> usersPerm;
    
	
	//Constructor
    public Story(String title, String story, User author) {
        super();
        this.title_story = title;
        this.story_story = story;
		this.usersPerm.put(author, 'W');
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
	
	public Map<User, String> getUsersPermMap() {
        return usersPerm;
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