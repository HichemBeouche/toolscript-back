package ToolScript.Server.api.stories;

import lombok.AllArgsConstructor;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("Story_User")
public class UserId {
    @Column(value = "id_user")
    private Integer idUser;

    @Column(value = "permission")
    private String permission;

    //Constructor
    public UserId(Integer idUser, String permission) {
        this.idUser = idUser;
        this.permission = permission;
    }

    //Getters
    public Integer getIdUser() {
        return idUser;
    }

    public String getPermission() {
        return permission;
    }


    //Setters
    public void setIdUser(Integer idUser) { this.idUser = idUser; }

    public void setPermission(String permission) { this.permission = permission; }
}
