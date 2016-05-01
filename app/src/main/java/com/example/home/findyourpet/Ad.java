package com.example.home.findyourpet;

import java.util.Date;

/**
 * Created by HOME on 30/04/2016.
 */
public class Ad {

    protected int id;
    protected Date creationDate;
    protected User adOwner;

    protected String imageURL;
    protected String area;
    protected String description;

    public Ad() {
    }

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public Date getCreationDate() {return creationDate;}
    public void setCreationDate(Date creationDate) {this.creationDate = creationDate;}

    public User getAdOwner() {return adOwner;}
    public void setAdOwner(User adOwner) {this.adOwner = adOwner;}

    public String getImageURL() {return imageURL;}
    public void setImageURL(String imageURL) {this.imageURL = imageURL;}

    public String getArea() {return area;}
    public void setArea(String area) {this.area = area;}

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}
}
