package com.psi.shen.primary;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.ViewGroup;

public class SingleAlloyItem implements Parcelable{
    private String AlloyName,NamingStandard,Component;
    private double Density,ThermalExpan,ThermalCon,SpecificHeat,
            Resistivity,ElasticModu,PoissonsRatio,MeltingRange_Min,MeltingRange_Max,DampingIndex,
            Hardness_Min, Hardness_Max;
    private String Forging, Weldability, Machining, SurfaceTreatment, CorrisionResistance;
    private double FractureToughness;
    public boolean[] Validation = new boolean[21];//boolean[21] accordingly have value or not (Changed)

    public SingleAlloyItem(String alloyName,String namingStandard,String component,double density,double thermalExpan,double thermalCon,double specificHeat,
                           double resistivity,double elasticModu,double poissonsRatio,double meltingRange_Min,double meltingRange_Max,double dampingIndex,
                           double hardness_Min,double hardness_Max,String forging,String weldability,String machining,String surfaceTreatment,String corrisionResistance,
                           double fractureToughness){
        this.AlloyName=alloyName;
        this.NamingStandard=namingStandard; // New Added
        this.Component=component;
        this.Density=density;
        this.ThermalCon=thermalCon;
        this.ThermalExpan=thermalExpan;
        this.SpecificHeat=specificHeat;
        this.Resistivity=resistivity;
        this.ElasticModu=elasticModu;
        this.PoissonsRatio=poissonsRatio;   // New Added
        this.MeltingRange_Min=meltingRange_Min;
        this.MeltingRange_Max=meltingRange_Max;
        this.DampingIndex=dampingIndex; // New Added
        this.Hardness_Min=hardness_Min; // New Added
        this.Hardness_Max=hardness_Max; // Name Changed
        this.Forging=forging;   // New Added
        this.Weldability=weldability;   // New Added
        this.Machining=machining;   // New Added
        this.SurfaceTreatment=surfaceTreatment; // New Added
        this.CorrisionResistance=corrisionResistance;   // New Added
        this.FractureToughness=fractureToughness;   // New Added
    }

    public SingleAlloyItem(String[] returnDatas){
        for(int i = 0; i < 21; i++){Validation[i]=(returnDatas[i]!=null);}
        if(Validation[0])
            this.AlloyName = returnDatas[0];
        if(Validation[1])
            this.NamingStandard = returnDatas[1];
        if(Validation[2])
            this.Component = returnDatas[2];
        if(Validation[3])
            this.Density = Double.parseDouble(returnDatas[3]);
        if(Validation[4])
            this.ThermalCon = Double.parseDouble(returnDatas[4]);
        if(Validation[5])
            this.ThermalExpan = Double.parseDouble(returnDatas[5]);
        if(Validation[6])
            this.SpecificHeat = Double.parseDouble(returnDatas[6]);
        if(Validation[7])
            this.Resistivity = Double.parseDouble(returnDatas[7]);
        if(Validation[8])
            this.ElasticModu = Double.parseDouble(returnDatas[8]);
        if(Validation[9])
            this.PoissonsRatio = Double.parseDouble(returnDatas[9]);
        if(Validation[10])
            this.MeltingRange_Min = Double.parseDouble(returnDatas[10]);
        if(Validation[11])
            this.MeltingRange_Max = Double.parseDouble(returnDatas[11]);
        if(Validation[12])
            this.DampingIndex = Double.parseDouble(returnDatas[12]);
        if(Validation[13])
            this.Hardness_Min = Double.parseDouble(returnDatas[13]);
        if(Validation[14])
            this.Hardness_Max = Double.parseDouble(returnDatas[14]);
        if(Validation[15])
            this.Forging = returnDatas[15];
        if(Validation[16])
            this.Weldability = returnDatas[16];
        if(Validation[17])
            this.Machining = returnDatas[17];
        if(Validation[18])
            this.SurfaceTreatment = returnDatas[18];
        if(Validation[19])
            this.CorrisionResistance = returnDatas[19];
        if(Validation[20])
            this.FractureToughness = Double.parseDouble(returnDatas[20]);
    }


    public SingleAlloyItem(String alloyName,String namingStandard,String component,double density,double thermalExpan,double thermalCon,double specificHeat,
                           double resistivity,double elasticModu,double poissonsRatio,double meltingRange_Min,double meltingRange_Max,double dampingIndex,
                           double hardness_Min,double hardness_Max,String forging,String weldability,String machining,String surfaceTreatment,String corrisionResistance,
                           double fractureToughness,boolean[] validation){
        this.AlloyName=alloyName;
        this.NamingStandard=namingStandard; // New Added
        this.Component=component;
        this.Density=density;
        this.ThermalCon=thermalCon;
        this.ThermalExpan=thermalExpan;
        this.SpecificHeat=specificHeat;
        this.Resistivity=resistivity;
        this.ElasticModu=elasticModu;
        this.PoissonsRatio=poissonsRatio;   // New Added
        this.MeltingRange_Min=meltingRange_Min;
        this.MeltingRange_Max=meltingRange_Max;
        this.DampingIndex=dampingIndex; // New Added
        this.Hardness_Min=hardness_Min; // New Added
        this.Hardness_Max=hardness_Max; // Name Changed
        this.Forging=forging;   // New Added
        this.Weldability=weldability;   // New Added
        this.Machining=machining;   // New Added
        this.SurfaceTreatment=surfaceTreatment; // New Added
        this.CorrisionResistance=corrisionResistance;   // New Added
        this.FractureToughness=fractureToughness;   // New Added
        this.Validation=validation.clone();
    }

    public String getAlloyName() {
        return Validation[0]? AlloyName:"";
    }
    public String getNamingStandard() {
        return Validation[1] ? NamingStandard : "";
    }
    public String getComponent() {
        return Validation[2]? Component:"";
    }
    public double getDensity() {
        return Validation[3]? Density:0.0;
    }
    public double getThermalExpan() {
        return Validation[4]? ThermalExpan:0.0;
    }
    public double getThermalCon() {
        return Validation[5]? ThermalCon:0.0;
    }
    public double getSpecificHeat() {
        return Validation[6]? SpecificHeat:0.0;
    }
    public double getResistivity() {
        return Validation[7]? Resistivity:0.0;
    }
    public double getElasticModu() {
        return Validation[8]? ElasticModu:0.0;
    }
    public double getPoissonsRatio() {
        return Validation[9] ? PoissonsRatio : 0.0;
    }
    public double getMeltingRange_Min() {
        return Validation[10]? MeltingRange_Min:0.0;
    }
    public double getMeltingRange_Max() {
        return Validation[11]? MeltingRange_Max:0.0;
    }
    public double getDampingIndex() {
        return Validation[12] ? DampingIndex : 0.0;
    }
    public double getHardness_Min() {
        return Validation[13]? Hardness_Min:0.0;
    }
    public double getHardness_Max() {
        return Validation[14] ? Hardness_Max : 0.0;
    }
    public String getForging() {
        return Validation[15] ? Forging : "";
    }
    public String getWeldability() {
        return Validation[16] ? Weldability : "";
    }
    public String getMachining() {
        return Validation[17] ? Machining : "";
    }
    public String getSurfaceTreatment() {
        return Validation[18] ? SurfaceTreatment : "";
    }
    public String getCorrisionResistance() {
        return Validation[19] ? CorrisionResistance : "";
    }
    public double getFractureToughness() {
        return Validation[20] ? FractureToughness : 0.0;
    }

    @Override
    public int describeContents(){
        return 0;
    }
    @Override
    public void writeToParcel(Parcel out, int flags){
        out.writeString(AlloyName);
        out.writeString(NamingStandard);
        out.writeString(Component);
        out.writeDouble(Density);
        out.writeDouble(ThermalExpan);
        out.writeDouble(ThermalCon);
        out.writeDouble(SpecificHeat);
        out.writeDouble(Resistivity);
        out.writeDouble(ElasticModu);
        out.writeDouble(PoissonsRatio);
        out.writeDouble(MeltingRange_Min);
        out.writeDouble(MeltingRange_Max);
        out.writeDouble(DampingIndex);
        out.writeDouble(Hardness_Min);
        out.writeDouble(Hardness_Max);
        out.writeString(Forging);
        out.writeString(Weldability);
        out.writeString(Machining);
        out.writeString(SurfaceTreatment);
        out.writeString(CorrisionResistance);
        out.writeDouble(FractureToughness);
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
        this.NamingStandard = in.readString();
        this.Component = in.readString();
        this.Density = in.readDouble();
        this.ThermalExpan = in.readDouble();
        this.ThermalCon = in.readDouble();
        this.SpecificHeat = in.readDouble();
        this.Resistivity = in.readDouble();
        this.ElasticModu = in.readDouble();
        this.PoissonsRatio = in.readDouble();
        this.MeltingRange_Min = in.readDouble();
        this.MeltingRange_Max = in.readDouble();
        this.DampingIndex = in.readDouble();
        this.Hardness_Min = in.readDouble();
        this.Hardness_Max = in.readDouble();
        this.Forging = in.readString();
        this.Weldability = in.readString();
        this.Machining = in.readString();
        this.SurfaceTreatment = in.readString();
        this.CorrisionResistance = in.readString();
        this.FractureToughness = in.readDouble();
        in.readBooleanArray(this.Validation);
    }
}
