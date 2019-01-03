package wolfsoft.routes.Entities;

/**
 * Created by G on 10/12/2017.
 */

public class comments {

    String id;
    String user_comment;
    String cour_id;
    String user_id;
    String name;
    String lastname;


    public comments() {
    }

    public comments(String id, String user_comment, String cour_id, String user_id, String name, String lastname) {
        this.id = id;
        this.user_comment = user_comment;
        this.cour_id = cour_id;
        this.user_id = user_id;
        this.name = name;
        this.lastname = lastname;
    }

    public comments(String user_comment) {
        this.user_comment = user_comment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_comment() {
        return user_comment;
    }

    public void setUser_comment(String user_comment) {
        this.user_comment = user_comment;
    }

    public String getCour_id() {
        return cour_id;
    }

    public void setCour_id(String cour_id) {
        this.cour_id = cour_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
