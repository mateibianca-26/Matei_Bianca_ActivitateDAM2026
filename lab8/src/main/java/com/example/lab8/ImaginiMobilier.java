package com.example.lab8;

import android.graphics.Bitmap;

public class ImaginiMobilier {
    private String textAfisat;
    private Bitmap imagine;
    private String link;

    public ImaginiMobilier(String textAfisat, Bitmap imagine, String link){
        this.textAfisat = textAfisat;
        this.imagine = imagine;
        this.link = link;
    }

    public String getTextAfisat() {
        return textAfisat;
    }

    public Bitmap getImagine() {
        return imagine;
    }

    public String getLink() {
        return link;
    }
}
