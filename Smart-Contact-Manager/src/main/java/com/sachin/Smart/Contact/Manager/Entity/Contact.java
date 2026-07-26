package com.sachin.Smart.Contact.Manager.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "CONTACT")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cid;

    private String name;
    @Column(name = "nick_name")
    private String secondName;

    private String work;

    private String email;

    private String phone;

    private String img;

    @Column(length = 300)
    private String contactAbout;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Contact() {
    }

    // Getter Setter

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getContactAbout() {
        return contactAbout;
    }

    public void setContactAbout(String contactAbout) {
        this.contactAbout = contactAbout;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
 
    }

    @Override
    public String toString() {
        return "Contact [cid=" + cid + ", name=" + name + ", secondName=" + secondName + ", work=" + work + ", email="
                + email + ", phone=" + phone + ", img=" + img + ", contactAbout=" + contactAbout + ", user=" + user
                + ", getCid()=" + getCid() + ", getName()=" + getName() + ", getSecondName()=" + getSecondName()
                + ", getWork()=" + getWork() + ", getEmail()=" + getEmail() + ", getClass()=" + getClass()
                + ", getPhone()=" + getPhone() + ", getImg()=" + getImg() + ", getContactAbout()=" + getContactAbout()
                + ", getUser()=" + getUser() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
    }

    
}