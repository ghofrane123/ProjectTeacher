package wolfsoft.routes.Entities;

/**
 * Created by G on 07/12/2017.
 */

public class teacher {
    String id;
    String name;
    String lastname;
    String role;
    int telphone;
    float lattitude;
    float longitude;
    float Rating;
    String has_profile;

    public teacher() {
    }

    public teacher(String id, String name, String lastname, String role, int telphone, float lattitude, float longitude, float rating, String has_profile) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.role = role;
        this.telphone = telphone;
        this.lattitude = lattitude;
        this.longitude = longitude;
        Rating = rating;
        this.has_profile = has_profile;
    }

    public String getHas_profile() {
        return has_profile;
    }

    public void setHas_profile(String has_profile) {
        this.has_profile = has_profile;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getTelphone() {
        return telphone;
    }

    public void setTelphone(int telphone) {
        this.telphone = telphone;
    }

    public float getLattitude() {
        return lattitude;
    }

    public void setLattitude(float lattitude) {
        this.lattitude = lattitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getRating() {
        return Rating;
    }

    public void setRating(float rating) {
        Rating = rating;
    }
}
