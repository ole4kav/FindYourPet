package com.olgav.android.findyourpet;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by HOME on 30/04/2016.
 */
public class Ad implements Parcelable
{
    @SerializedName("id")
    String id;

    //@SerializedName("creationDate")
    //protected Date creationDate;

    @SerializedName("creationDate")
    String creationDate1;

    @SerializedName("descrition")
    String description;

    @SerializedName("imageURL")
    String imageURL;

    @SerializedName("area")
    String area;

    @SerializedName("sex")
    String sex;

    @SerializedName("yesno")
    String yesno;

    @SerializedName("adOwner")
    User adOwner;


    // constructors
    public Ad() {}

    protected Ad(Parcel in) {
        id = in.readString();
        creationDate1 = in.readString();
        description = in.readString();
        imageURL = in.readString();
        area = in.readString();
        sex = in.readString();
        yesno = in.readString();
        adOwner = in.readParcelable(User.class.getClassLoader());
    }

    public static final Creator<Ad> CREATOR = new Creator<Ad>()
    {
        @Override
        public Ad createFromParcel(Parcel in) {
            return new Ad(in);
        }
        @Override
        public Ad[] newArray(int size) {
            return new Ad[size];
        }
    };

    // properties
    public String getId() {return id;}
    public void setId(String id) {this.id = id;}


    //public Date getCreationDate() {return creationDate;}
    //public void setCreationDate(Date creationDate) {this.creationDate = creationDate;}

    public User getAdOwner() {return adOwner;}
    public void setAdOwner(User adOwner) {this.adOwner = adOwner;}

    public String getImageURL() {return imageURL;}
    public void setImageURL(String imageURL) {this.imageURL = imageURL;}

    public String getArea() {return area;}
    public void setArea(String area) {this.area = area;}

    public String getDescription() {return description;}
    public void setDescription(String description) {this.description = description;}

    public String getSex() {return sex;}
    public void setSex(String sex) {this.sex = sex;}

    public String getYesno() {return yesno;}
    public void setYesno(String yesno) {this.yesno = yesno;}

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public int describeContents() {return 0;}

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(creationDate1);
        dest.writeString(description);
        dest.writeString(imageURL);
        dest.writeString(area);
        dest.writeString(sex);
        dest.writeString(yesno);
    }
}
