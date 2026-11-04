package com.example.lab4;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Mobilier implements Parcelable {
    private String denumire;
    private double pret;
    private String material;
    private boolean montajInclus;
    private boolean inStoc;

    public Mobilier(String denumire, double pret, String material, boolean montajInclus, boolean inStoc){
        this.denumire = denumire;
        this.pret = pret;
        this.material = material;
        this.montajInclus = montajInclus;
        this.inStoc = inStoc;
    }

    protected Mobilier(Parcel in) {
        denumire = in.readString();
        pret = in.readDouble();
        material = in.readString();
        montajInclus = in.readByte() != 0;
        inStoc = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(denumire);
        dest.writeDouble(pret);
        dest.writeString(material);
        dest.writeByte((byte) (montajInclus ? 1 : 0));
        dest.writeByte((byte) (inStoc ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Mobilier> CREATOR = new Creator<Mobilier>() {
        @Override
        public Mobilier createFromParcel(Parcel in) {
            return new Mobilier(in);
        }

        @Override
        public Mobilier[] newArray(int size) {
            return new Mobilier[size];
        }
    };

    public String getDenumire(){return denumire;}
    public double getPret(){return pret;}
    public String getMaterial(){return material;}
    public boolean isMontajInclus(){return montajInclus;}
    public boolean isInStoc(){return inStoc;}

    @Override
    public String toString(){
        return "Mobilier: "+ denumire + " | Pret: "+ pret + " RON\n"+ "Material: "+ material + " | Montaj: " + (montajInclus ? "Da" : "Nu") +
                " | In stoc: " + (inStoc ? "Da" : "Nu");
    }
}



