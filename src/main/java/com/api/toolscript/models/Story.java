package com.api.toolscript.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

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


    //Constructor
    private Story() {}


	//Getters
    public Integer getId() {
        return idStory;
    }

    public Integer getIdStory() {
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
	
	public Set<Long> getUserIds() {
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

    /*
	public void setUsersIds(Set<UserId> users) {
        this.users = users;
    }*/
	
	
	//Methods
    public static Story create(String title, String desc, long id_user) {
            Story s = new Story();
            s.setTitle(title);
            s.setDesc(desc);
            s.addUserPerm(id_user, "W");
            return s;
    }

    public void addUserPerm(long id_user, String perm) {
        this.users.add(new UserId(id_user, perm));
    }

    @Override
    public String toString() {
        return "Story{" +
                "idStory=" + idStory +
                ", title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", story='" + story + '\'' +
                ", users=" + users +
                '}';
    }
}
