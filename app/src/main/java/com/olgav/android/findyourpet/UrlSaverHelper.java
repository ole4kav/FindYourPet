package com.olgav.android.findyourpet;

/**
 * Created by olga on 12/18/16.
 */

public class UrlSaverHelper {

    private static volatile UrlSaverHelper mUrlSaverHelper;
    private String url; // The working url for us to use

    public static UrlSaverHelper getInstance() {
        if (mUrlSaverHelper == null) {
            mUrlSaverHelper = new UrlSaverHelper();
        }
        return mUrlSaverHelper;
    }

    public void saveWorkingUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
