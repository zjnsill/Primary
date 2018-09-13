package com.psi.shen.primary;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.ViewGroup;

public class SingleAlloyItem implements Parcelable{
    private String AlloyName,Component;
    private double Density,ThermalExpan,ThermalCon,SpecificHeat,
            Resistivity,ElasticModu,MeltingRange_Min,MeltingRange_Max,Hardness;
    public boolean[] Validation = new boolean[10];//boolean[10] accordingly have value or not

    public SingleAlloyItem(String alloyName,String component,double density,double thermalExpan,double thermalCon,double specificHeat,
                           double resistivity,double elasticModu,double meltingRange_Min,double meltingRange_Max,double hardness){
        this.AlloyName=alloyName;
        this.Component=component;
        this.Density=density;
        this.ThermalCon=thermalCon;
        this.ThermalExpan=thermalExpan;
        this.SpecificHeat=specificHeat;
        this.Resistivity=resistivity;
        this.ElasticModu=elasticModu;
        this.MeltingRange_Min=meltingRange_Min;
        this.MeltingRange_Max=meltingRange_Max;
        this.Hardness=hardness;
    }

    public SingleAlloyItem(String[] returnDatas){
        for(int i = 0; i<9; i++){Validation[i]=(returnDatas[i]==null? false:true);}
        Validation[9]=(returnDatas[10]==null? false:true);
        if(Validation[0])
            this.AlloyName = returnDatas[0];
        if(Validation[1])
            this.Component = returnDatas[1];
        if(Validation[2])
            this.Density = Double.parseDouble(returnDatas[2]);
        if(Validation[3])
            this.ThermalCon = Double.parseDouble(returnDatas[3]);
        if(Validation[4])
            this.ThermalExpan = Double.parseDouble(returnDatas[4]);
        if(Validation[5])
            this.SpecificHeat = Double.parseDouble(returnDatas[5]);
        if(Validation[6])
            this.Resistivity = Double.parseDouble(returnDatas[6]);
        if(Validation[7])
            this.ElasticModu = Double.parseDouble(returnDatas[7]);
        if(Validation[8]){
            this.MeltingRange_Min = Double.parseDouble(returnDatas[8]);
            this.MeltingRange_Max = Double.parseDouble(returnDatas[9]);}
        if(Validation[9])
            this.Hardness = Double.parseDouble(returnDatas[10]);
    }


    public SingleAlloyItem(String alloyName,String component,double density,double thermalExpan,double thermalCon,double specificHeat,
                           double resistivity,double elasticModu,double meltingRange_Min,double meltingRange_Max,double hardness,boolean[] validation){
        this.AlloyName=alloyName;
        this.Component=component;
        this.Density=density;
        this.ThermalCon=thermalCon;
        this.ThermalExpan=thermalExpan;
        this.SpecificHeat=specificHeat;
        this.Resistivity=resistivity;
        this.ElasticModu=elasticModu;
        this.MeltingRange_Min=meltingRange_Min;
        this.MeltingRange_Max=meltingRange_Max;
        this.Hardness=hardness;
        this.Validation=validation.clone();
    }

    public String getAlloyName() {
        return Validation[0]? AlloyName:"";
    }
    public String getComponent() {
        return Validation[1]? Component:"";
    }
    public double getDensity() {
        return Validation[2]? Density:0.0;
    }
    public double getThermalExpan() {
        return Validation[3]? ThermalExpan:0.0;
    }
    public double getThermalCon() {
        return Validation[4]? ThermalCon:0.0;
    }
    public double getSpecificHeat() {
        return Validation[5]? SpecificHeat:0.0;
    }
    public double getResistivity() {
        return Validation[6]? Resistivity:0.0;
    }
    public double getElasticModu() {
        return Validation[7]? ElasticModu:0.0;
    }
    public double getMeltingRange_Min() {
        return Validation[8]? MeltingRange_Min:0.0;
    }
    public double getMeltingRange_Max() {
        return Validation[8]? MeltingRange_Max:0.0;
    }
    public double getHardness() {
        return Validation[9]? Hardness:0.0;
    }

    @Override
    public int describeContents(){
        return 0;
    }
    @Override
    public void writeToParcel(Parcel out, int flags){
        out.writeString(AlloyName);
        out.writeString(Component);
        out.writeDouble(Density);
        out.writeDouble(ThermalExpan);
        out.writeDouble(ThermalCon);
        out.writeDouble(SpecificHeat);
        out.writeDouble(Resistivity);
        out.writeDouble(ElasticModu);
        out.writeDouble(MeltingRange_Min);
        out.writeDouble(MeltingRange_Max);
        out.writeDouble(Hardness);
        out.writeBooleanArray(Validation);
    }

    public static final  Parcelable.Creator<SingleAlloyItem> CREATOR = new Creator<SingleAlloyItem>() {
        @Override
        public SingleAlloyItem createFromParcel(Parcel source) {
            return new SingleAlloyItem(source);
        }

        @Override
        public SingleAlloyItem[] newArray(int size) {
            return new SingleAlloyItem[size];
        }
    };

    public SingleAlloyItem(Parcel in){
        this.AlloyName = in.readString();
        this.Component = in.readString();
        this.Density = in.readDouble();
        this.ThermalExpan = in.readDouble();
        this.ThermalCon = in.readDouble();
        this.SpecificHeat = in.readDouble();
        this.Resistivity = in.readDouble();
        this.ElasticModu = in.readDouble();
        this.MeltingRange_Min = in.readDouble();
        this.MeltingRange_Max = in.readDouble();
        this.Hardness = in.readDouble();
        in.readBooleanArray(this.Validation);
    }
}
