package com.psi.shen.primary;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.ViewGroup;

public class SingleAlloyItem implements Parcelable{
    private String AlloyName,NamingStandard,Component;
    private double Density,ThermalExpan,ThermalCon,SpecificHeat,Resistivity,ElasticModu,PoissonsRatio,MeltingRange_Min,MeltingRange_Max,DampingIndex,
            Hardness_Min, Hardness_Max;
    private String Forging, Weldability, Machining, SurfaceTreatment, CorrisionResistance;
    private double FractureToughness;
    public boolean[] Validation = new boolean[19];
    //take 0.0 and "" as the default value, then we can save time on these much judgements

    public SingleAlloyItem(String alloyName,String namingStandard,String component,double density,double thermalExpan,double thermalCon,double specificHeat,
                           double resistivity,double elasticModu,double poissonsRatio,double meltingRange_Min,double meltingRange_Max,double dampingIndex,
                           double hardness_Min,double hardness_Max,String forging,String weldability,String machining,String surfaceTreatment,String corrisionResistance,
                           double fractureToughness,boolean[] validation){
        this.AlloyName=alloyName;
        this.NamingStandard=namingStandard;
        this.Component=component;
        this.Density=density;
        this.ThermalCon=thermalCon;
        this.ThermalExpan=thermalExpan;
        this.SpecificHeat=specificHeat;
        this.Resistivity=resistivity;
        this.ElasticModu=elasticModu;
        this.PoissonsRatio=poissonsRatio;
        this.MeltingRange_Min=meltingRange_Min;
        this.MeltingRange_Max=meltingRange_Max;
        this.DampingIndex=dampingIndex;
        this.Hardness_Min=hardness_Min;
        this.Hardness_Max=hardness_Max;
        this.Forging=forging;
        this.Weldability=weldability;
        this.Machining=machining;
        this.SurfaceTreatment=surfaceTreatment;
        this.CorrisionResistance=corrisionResistance;
        this.FractureToughness=fractureToughness;
        this.Validation = validation;
    }

    public SingleAlloyItem(String[] returnDatas){
        for(int i = 0; i < 21; i++){Validation[i]=(returnDatas[i]!=null);}
        this.AlloyName = Validation[0]? returnDatas[0]:"";
        this.NamingStandard = Validation[1]? returnDatas[1]:"";
        this.Component = Validation[2]? returnDatas[2]:"";
        this.Density = Validation[3]? Double.parseDouble(returnDatas[3]):0.0;
        this.ThermalCon = Validation[4]? Double.parseDouble(returnDatas[4]):0.0;
        this.ThermalExpan = Validation[5]? Double.parseDouble(returnDatas[5]):0.0;
        this.SpecificHeat = Validation[6]? Double.parseDouble(returnDatas[6]):0.0;
        this.Resistivity = Validation[7]? Double.parseDouble(returnDatas[7]):0.0;
        this.ElasticModu = Validation[8]? Double.parseDouble(returnDatas[8]):0.0;
        this.PoissonsRatio = Validation[9]? Double.parseDouble(returnDatas[9]):0.0;
        this.MeltingRange_Min = Validation[10]? Double.parseDouble(returnDatas[10]):0.0;
        this.MeltingRange_Max = Validation[10]? Double.parseDouble(returnDatas[11]):0.0;
        this.DampingIndex = Validation[11]? Double.parseDouble(returnDatas[12]):0.0;
        this.Hardness_Min = Validation[12]? Double.parseDouble(returnDatas[13]):0.0;
        this.Hardness_Max = Validation[12]? Double.parseDouble(returnDatas[14]):0.0;
        this.Forging = Validation[13]? returnDatas[15]:"";
        this.Weldability = Validation[14]? returnDatas[16]:"";
        this.Machining = Validation[15]? returnDatas[17]:"";
        this.SurfaceTreatment = Validation[16]? returnDatas[18]:"";
        this.CorrisionResistance = Validation[17]? returnDatas[19]:"";
        this.FractureToughness = Validation[18]? Double.parseDouble(returnDatas[20]):0.0;
    }


    public String getAlloyName() {
        return AlloyName;
    }
    public String getNamingStandard() {
        return NamingStandard;
    }
    public String getComponent() {
        return Component;
    }
    public double getDensity() {
        return Density;
    }
    public double getThermalExpan() {
        return ThermalExpan;
    }
    public double getThermalCon() {
        return ThermalCon;
    }
    public double getSpecificHeat() {
        return SpecificHeat;
    }
    public double getResistivity() {
        return Resistivity;
    }
    public double getElasticModu() {
        return ElasticModu;
    }
    public double getPoissonsRatio() {
        return PoissonsRatio;
    }
    public double getMeltingRange_Min(){
        return MeltingRange_Min;
    }
    public double getMeltingRange_Max() {
        return Validation[10]? MeltingRange_Max:0.0;
    }
    public String getMeltingRangeString(){
        return Validation[10]? MeltingRange_Min+"~"+MeltingRange_Max:"";
    }
    public double getDampingIndex() {
        return DampingIndex;
    }
    public double getHardness_Min() {
        return Hardness_Min;
    }
    public double getHardness_Max() {
        return Hardness_Max;
    }
    public String getForging() {
        return Forging;
    }
    public String getWeldability() {
        return Weldability;
    }
    public String getMachining() {
        return Machining;
    }
    public String getSurfaceTreatment() {
        return SurfaceTreatment;
    }
    public String getCorrisionResistance() {
        return CorrisionResistance;
    }
    public double getFractureToughness() {
        return FractureToughness;
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
