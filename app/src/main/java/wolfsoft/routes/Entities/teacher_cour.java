package wolfsoft.routes.Entities;

/**
 * Created by G on 19/11/2017.
 */

public class teacher_cour {
    String  id;
    String matiere;
    String description;
    String price;
    String created_at;
    String updated_at;
    String user_id;
    String heure;
    int etudiant;

    public teacher_cour(String id, String matiere, String description, String price, String created_at, String updated_at, String user_id, String heure, int etudiant) {
        this.id = id;
        this.matiere = matiere;
        this.description = description;
        this.price = price;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.user_id = user_id;
        this.heure = heure;
        this.etudiant = etudiant;
    }

    public teacher_cour() {
    }


    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public int getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(int etudiant) {
        this.etudiant = etudiant;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMatiere() {
        return matiere;
    }

    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
