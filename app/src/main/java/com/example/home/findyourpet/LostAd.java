package com.example.home.findyourpet;

import java.util.Date;

/**
 * Created by HOME on 30/04/2016.
 */
public class LostAd extends Ad {

    protected Date lostDate;

    public Date getLostDate() {return lostDate;}
    public void setLostDate(Date lostDate) {this.lostDate = lostDate;}

    public LostAd() {
    }

}
