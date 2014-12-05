package model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author mady
 */
@Entity
@Table(name = "CREDENTIALS")
public class Credential implements Serializable {

    public Credential(String username, String password, String rolename) {
        this.username = username;
        this.password = password;
        this.roleName = rolename;
    }

    public Credential(){ 
    }
        
    @Id
    private String username;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "ROLE_NAME")
    private String roleName;

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
        this.password = password;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return "Credential{" + "username=" + username + ", password=" + password + ", roleName=" + roleName + '}';
    }

    
}
