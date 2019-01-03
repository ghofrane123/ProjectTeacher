package wolfsoft.routes.Entities;

/**
 * Created by G on 05/01/2018.
 */

public class reclamation {
    String email;
    int rating;
    String description;

    public reclamation() {
    }

    public reclamation(String email, int rating, String description) {
        this.email = email;
        this.rating = rating;
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
