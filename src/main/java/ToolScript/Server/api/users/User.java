package ToolScript.Server.api.users;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@EntityScan
@Table(value = "Users")
public class User {
    
    public User() {
    }
    
    public User(String username, String password, String mail, String permission) {
        super();
        this.username = username;
        this.password = password;
        this.mail = mail;
        this.permission = permission;
    }
    
    @Id
    private Integer id_user;
    private String username;
    private String password;
    private String mail;
    private String permission;

    
    @Column(value = "id_user")
    public Integer getIdUser() {
        return id_user;
    }
    
    @Column(value = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(value = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    @Column(value = "mail")
    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
    @Column(value = "permission")
    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

}