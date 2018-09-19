package com.psi.shen.primary;

import android.os.Bundle;

public class Request {
    /* Note:
       the datas in the Request class do not include Forging, Weldability, Machining,
       Surface_Treatment, Corrision_Resistance
     */
    private boolean[] Validation = new boolean[25]; // Changed
    private double[] doubleArray = new double[22];  // Changed
    private String name;
    private String namingStandard;
    private boolean[] componet;

    public Request(Bundle fromIntent){
        this.Validation = fromIntent.getBooleanArray("validation");
        this.componet = fromIntent.getBooleanArray("component");
        this.name = fromIntent.getString("name");
        this.namingStandard = fromIntent.getString("namingStandard");
        this.doubleArray = fromIntent.getDoubleArray("doubleArray");
    }


    public String getName() {
        return name;
    }

    public String getNamingStandard() { return namingStandard; }

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
