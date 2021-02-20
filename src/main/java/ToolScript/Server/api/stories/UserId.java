package ToolScript.Server.api.stories;

import org.springframework.data.relational.core.mapping.Table;

@Table("story_user")
public class UserId {
    private Integer user;
    private String perm;

    public UserId(Integer user, String perm) {
        this.user = user;
        this.perm = perm;
    }

    //Getters
    public Integer getUser() {
        return user;
    }

    public String getPerm() {
        return perm;
    }
}
