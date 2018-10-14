package com.psi.shen.primary;

import android.os.Bundle;

public class SearchRequest {
        /* Note:
       the datas in the SearchRequest class do not include Forging, Weldability, Machining,
       Surface_Treatment, Corrision_Resistance

       Validation[0]->AlloyName,
       Validation[1]->NamingStandard,
       Validation[2]->Density_Min,Validation[3]->Density_Max,
       Validation[4]->ThermalExpan_Min,Validation[5]->ThermalExpan_Max,
       Validation[6]->ThermalCon_Min,Validation[7]->ThermalCon_Max
       Validation[8]->SpecificHeat_Min,Validation[9]->SpecificHeat_Max
       Validation[10]->Resistivity_Min,Validation[11]->Resistivity_Max
       Validation[12]->ElasticModu_Min,Validation[13]->ElasticModu_Max
       Validation[14]->PoissonsRatio_Min,Validation[15]->PoissonsRatio_Max
       Validation[16]->DampingIndex_Min,Validation[17]->DampingIndex_Max
       Validation[18]->FractureToughness_Min,Validation[19]->FractureToughness_Max
       Validation[20]->MeltingRange_Min,Validation[21]->MeltingRange_Max,
       Validation[22]->Hardness_Min,Validation[23]->Hardness_Max,
       Validation[24]->Al_Min, Validation[25]->Al_Max,
       validation[26]->Mn_Min, Validation[27]->Mn_Max,
       validation[28]->Zn_Min, Validation[29]->Zn_Max,
       validation[30]->Mg_Min, Validation[31]->Mg_Max,
       validation[32]->Nd_Min, Validation[33]->Nd_Max,
       validation[34]->Gd_Min, Validation[35]->Gd_Max,
       validation[36]->Zr_Min, Validation[37]->Zr_Max,
       validation[38]->Ag_Min, Validation[39]->Ag_Max,
       validation[40]->Cu_Min, Validation[41]->Cu_Max,
       validation[42]->Th_Min, Validation[43]->Th_Max,
       validation[44]->Y_Min, Validation[45]->Y_Max,
       validation[46]->RareElements_Min, Validation[47]->RareElements_Max
       Validation[48]->Al,
       validation[49]->Mn,
       validation[50]->Zn,
       validation[51]->Mg,
       validation[52]->Nd,
       validation[53]->Gd,
       validation[54]->Zr,
       validation[55]->Ag,
       validation[56]->Cu,
       validation[57]->Th,
       validation[58]->Y,
       validation[59]->RareElements,
     */

    private String name;
    private String namingStandard;
    private double[] doubleArray = new double[46];
    private boolean[] Validation = new boolean[60];

    public SearchRequest(Bundle fromIntent){
        this.Validation = fromIntent.getBooleanArray("validation");
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

    public double[] getDoubleArray() {
        return doubleArray;
    }
}
