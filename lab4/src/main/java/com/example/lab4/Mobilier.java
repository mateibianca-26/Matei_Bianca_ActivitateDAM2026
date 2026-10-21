package com.example.lab4;

import java.io.Serializable;

public class Mobilier implements Serializable {
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



