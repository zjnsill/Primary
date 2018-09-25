package com.psi.shen.primary;

import android.os.Bundle;
import android.widget.EditText;

public class assistingTools {
    public boolean[] StringToBooleanArray(String booleanStringArray){
        int length = booleanStringArray.length();
        boolean[] out = new boolean[length];
        for(int i=0;i<length;i++){
            if(booleanStringArray.charAt(i)=='1'){out[i]=true;}
            if(booleanStringArray.charAt(i)=='0'){out[i]=false;}
        }
        return out;
    }

    public String booleanArrayToString(boolean[] booleanArray){
        int length = booleanArray.length;
        String out = "";
        for(int i=0;i<length;i++){
            if(booleanArray[i]){out+='1';}
            else{out+='0';}
        }
        return out;
    }

    boolean hasInput(EditText editText){
        return !editText.getText().toString().isEmpty();
    }

    double EditTextTodouble(EditText editText){
        if(hasInput(editText)){
            return Double.parseDouble(editText.getText().toString());
        }
        else return 0.0;
    }

    String EditTextToString(EditText editText){
        if(hasInput(editText)){
            return editText.getText().toString();
        }
        else {return "";}
    }

    boolean isAllFalse(boolean[] array){
        boolean out = true;
        int length = array.length;
        for(int i=0;i<length;i++){
            if(array[i]){out=false;}
        }
        return out;
    }

    String getStringComponent(boolean[] Component){
        String out = "";
        if(Component[0]){out+="Al ";}
        if(Component[1]){out+="Zn ";}
        if(Component[2]){out+="Mn ";}
        if(Component[3]){out+="Zr ";}
        if(Component[4]){out+="Yr ";}
        if(Component[5]){out+="Others ";}
        return out;
    }
    //in newer version of datastructure, data transfer can be done using parcelable instead of bundle;
/*
    Bundle pacakgingbundle(SingleAlloyItem Item){
        Bundle bundle = new Bundle();
        bundle.putString("name",Item.Validation[0]? Item.getAlloyName():"");
        bundle.putString("component",Item.Validation[1]? Item.getComponent():"");
        bundle.putDouble("density",Item.Validation[2]? Item.getDensity():0.0);
        bundle.putDouble("thermalExpan",Item.Validation[3]? Item.getThermalExpan():0.0);
        bundle.putDouble("thermalCon",Item.Validation[4]? Item.getThermalCon():0.0);
        bundle.putDouble("specificHeat",Item.Validation[5]? Item.getSpecificHeat():0.0);
        bundle.putDouble("resistivity",Item.Validation[6]? Item.getResistivity():0.0);
        bundle.putDouble("elasticModu",Item.Validation[7]? Item.getElasticModu():0.0);
        bundle.putDouble("meltingPoint_min",Item.Validation[8]? Item.getMeltingRange_Min():0.0);
        bundle.putDouble("meltingPoint_max",Item.Validation[8]? Item.getMeltingRange_Max():0.0);
        bundle.putDouble("hardness",Item.Validation[9]? Item.getHardness():0.0);
        bundle.putBooleanArray("validation",Item.Validation);
        return bundle;
    }

    SingleAlloyItem unzippingBundle(Bundle singleAlloyItemBundle){
        String name = singleAlloyItemBundle.getString("name");
        String component = singleAlloyItemBundle.getString("component");
        double density = singleAlloyItemBundle.getDouble("density");
        double thermalExpan = singleAlloyItemBundle.getDouble("thermalExpan");
        double thermalCon = singleAlloyItemBundle.getDouble("thermalCon");
        double specificHeat = singleAlloyItemBundle.getDouble("specificHeat");
        double resistivity = singleAlloyItemBundle.getDouble("resistivity");
        double elasticModu = singleAlloyItemBundle.getDouble("elasticModu");
        double meltingPoint_min = singleAlloyItemBundle.getDouble("meltingPoint_min");
        double meltingPoint_max = singleAlloyItemBundle.getDouble("meltingPoint_max");
        double hardness = singleAlloyItemBundle.getDouble("hardness");
        boolean[] validation = singleAlloyItemBundle.getBooleanArray("validation");
        SingleAlloyItem singleAlloyItem = new SingleAlloyItem(name,component,density,thermalExpan,thermalCon,specificHeat
        ,resistivity,elasticModu,meltingPoint_min,meltingPoint_max,hardness,validation);
        return singleAlloyItem;
    }

*/

}
