package wolfsoft.routes.Entities;

import java.util.List;

import wolfsoft.routes.Main3Activity;

/**
 * Created by G on 21/11/2017.
 */

public class CourByUser {

    String matiere;
    String cid;
    String price;
    String email;
    String description;
    String is_logged;
    String created_at;
    String telephone;
    String image;
    String heure;
    String etudiant;
    String lastname;
    String role;
    String rating;
    String nmbresr;
    String user_id;

    public CourByUser() {


    }


    public CourByUser(String matiere, String cid, String price, String email, String description, String is_logged, String created_at, String telephone, String image, String heure, String etudiant, String lastname, String role, String rating, String nmbresr,String user_id) {
        this.matiere = matiere;
        this.cid = cid;
        this.price = price;
        this.email = email;
        this.description = description;
        this.is_logged = is_logged;
        this.created_at = created_at;
        this.telephone = telephone;
        this.image = image;
        this.heure = heure;
        this.etudiant = etudiant;
        this.lastname = lastname;
        this.role = role;
        this.rating = rating;
        this.nmbresr = nmbresr;
        this.user_id= user_id;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public String getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(String etudiant) {
        this.etudiant = etudiant;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMatiere() {
        return matiere;
    }

    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIs_logged() {
        return is_logged;
    }

    public void setIs_logged(String is_logged) {
        this.is_logged = is_logged;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }


    public String getNmbresr() {
        return nmbresr;
    }

    public void setNmbresr(String nmbresr) {
        this.nmbresr = nmbresr;
    }

    public String getUser_id() {
        return this.user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
