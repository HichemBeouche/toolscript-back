package ToolScript.Server.api.users;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id_user;

    private String username;

    private String password;

    private String mail;

    private String permission;

    public Integer getId_user() {
        return id_user;
    }

    public void setId_user(Integer id_user) {
        this.id_user = id_user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
    	BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    	this.password = bCryptPasswordEncoder.encode(password);
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission ) {
        this.permission = permission;
    }
}