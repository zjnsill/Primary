package com.psi.shen.primary;

import android.os.Bundle;

public class Request {
    private boolean[] Validation = new boolean[18];
    private double[] doubleArray = new double[16];
    private String name;
    private boolean[] componet;

    public Request(Bundle fromIntent){
        this.Validation = fromIntent.getBooleanArray("validation");
        this.componet = fromIntent.getBooleanArray("component");
        this.name = fromIntent.getString("name");
        this.doubleArray = fromIntent.getDoubleArray("doubleArray");
    }


    public String getName() {
        return name;
    }

    public boolean[] getValidation() {
        return Validation;
    }

    public boolean[] getComponet() {
        return componet;
    }

    public double[] getDoubleArray() {
        return doubleArray;
    }

}
