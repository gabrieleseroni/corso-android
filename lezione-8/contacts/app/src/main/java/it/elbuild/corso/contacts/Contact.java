package it.elbuild.corso.contacts;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lucabellaroba on 29/02/16.
 */
public class Contact {
    private String name;
    private String uriProfileImage;
    private List<String> numbers;
    private List<String> emails;
    private Bitmap imageProfile;

    public Contact() {
        emails = new ArrayList<>();
        numbers = new ArrayList<>();
    }

    public Contact(Contact c){
        this.name = c.getName();
        this.uriProfileImage = c.getUriProfileImage();
        this.numbers = c.getNumbers();
        this.emails = c.getEmails();
        this.imageProfile = c.getImageProfile();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUriProfileImage() {
        return uriProfileImage;
    }

    public void setUriProfileImage(String uriProfileImage) {
        this.uriProfileImage = uriProfileImage;
    }

    public List<String> getNumbers() {
        return numbers;
    }

    public void setNumbers(List<String> nambers) {
        this.numbers = nambers;
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    public Bitmap getImageProfile() {
        return imageProfile;
    }

    public void setImageProfile(Bitmap imageProfile) {
        this.imageProfile = imageProfile;
    }
}