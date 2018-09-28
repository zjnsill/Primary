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

    }//keep this constructer to maintain the constructing method in other files

    public SingleAlloyItem(String alloyName,String namingStandard,String component,double density,double thermalExpan,double thermalCon,double specificHeat,
                           double resistivity,double elasticModu,double poissonsRatio,double meltingRange_Min,double meltingRange_Max,double dampingIndex,
                           double hardness_Min,double hardness_Max,String forging,String weldability,String machining,String surfaceTreatment,String corrisionResistance,
                           double fractureToughness, double al_Min, double al_Max, double mn_Min, double mn_Max, double zn_Min, double zn_Max, double mg_Min, double mg_Max,
                           double nd_Min, double nd_Max, double gd_Min, double gd_Max, double zr_Min, double zr_Max, double ag_Min, double ag_Max, double cu_Min, double cu_Max,
                           double th_Min, double th_Max, double y_Min, double y_Max, double rareElements_Min, double rareElements_Max,boolean[] validation){
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
        this.Al_Min = al_Min;
        this.Al_Max = al_Max;
        this.Mn_Min = mn_Min;
        this.Mn_Max = mn_Max;
        this.Zn_Min = zn_Min;
        this.Zn_Max = zn_Max;
        this.Mg_Min = mg_Min;
        this.Mg_Max = mg_Max;
        this.Nd_Min = nd_Min;
        this.Nd_Max = nd_Max;
        this.Gd_Min = gd_Min;
        this.Gd_Max = gd_Max;
        this.Zr_Min = zr_Min;
        this.Zr_Max = zr_Max;
        this.Ag_Min = ag_Min;
        this.Ag_Max = ag_Max;
        this.Cu_Min = cu_Min;
        this.Cu_Max = cu_Max;
        this.Th_Min = th_Min;
        this.Th_Max = th_Max;
        this.Y_Min = y_Min;
        this.Y_Max = y_Max;
        this.RareElements_Min = rareElements_Min;
        this.RareElements_Max = rareElements_Max;
        this.Validation = validation;
    }
//
    public SingleAlloyItem(Builder builder){
            this.AlloyName=builder.AlloyName;
            this.NamingStandard=builder.NamingStandard;
            this.Component=builder.Component;
            this.Density=builder.Density;
            this.ThermalCon=builder.ThermalCon;
            this.ThermalExpan=builder.ThermalExpan;
            this.SpecificHeat=builder.SpecificHeat;
            this.Resistivity=builder.Resistivity;
            this.ElasticModu=builder.ElasticModu;
            this.PoissonsRatio=builder.PoissonsRatio;
            this.MeltingRange_Min=builder.MeltingRange_Min;
            this.MeltingRange_Max=builder.MeltingRange_Max;
            this.DampingIndex=builder.DampingIndex;
            this.Hardness_Min=builder.Hardness_Min;
            this.Hardness_Max=builder.Hardness_Max;
            this.Forging=builder.Forging;
            this.Weldability=builder.Weldability;
            this.Machining=builder.Machining;
            this.SurfaceTreatment=builder.SurfaceTreatment;
            this.CorrisionResistance=builder.CorrisionResistance;
            this.FractureToughness=builder.FractureToughness;
            this.Al_Min = builder.Al_Min;
            this.Al_Max = builder.Al_Max;
            this.Mn_Min = builder.Mn_Min;
            this.Mn_Max = builder.Mn_Max;
            this.Zn_Min = builder.Zn_Min;
            this.Zn_Max = builder.Zn_Max;
            this.Mg_Min = builder.Mg_Min;
            this.Mg_Max = builder.Mg_Max;
            this.Nd_Min = builder.Nd_Min;
            this.Nd_Max = builder.Nd_Max;
            this.Gd_Min = builder.Gd_Min;
            this.Gd_Max = builder.Gd_Max;
            this.Zr_Min = builder.Zr_Min;
            this.Zr_Max = builder.Zr_Max;
            this.Ag_Min = builder.Ag_Min;
            this.Ag_Max = builder.Ag_Max;
            this.Cu_Min = builder.Cu_Min;
            this.Cu_Max = builder.Cu_Max;
            this.Th_Min = builder.Th_Min;
            this.Th_Max = builder.Th_Max;
            this.Y_Min = builder.Y_Min;
            this.Y_Max = builder.Y_Max;
            this.RareElements_Min = builder.RareElements_Min;
            this.RareElements_Max = builder.RareElements_Max;
            this.Validation = builder.Validation;
        }



    public static class Builder {
        private String AlloyName = "", NamingStandard = "", Component = "";
        private double Density, ThermalExpan, ThermalCon, SpecificHeat, Resistivity, ElasticModu, PoissonsRatio, MeltingRange_Min, MeltingRange_Max, DampingIndex,
                Hardness_Min, Hardness_Max;
        private String Forging = "", Weldability = "", Machining = "", SurfaceTreatment = "", CorrisionResistance = "";
        private double FractureToughness;
        private double Al_Min, Al_Max, Mn_Min, Mn_Max, Zn_Min, Zn_Max, Mg_Min, Mg_Max, Nd_Min, Nd_Max, Gd_Min, Gd_Max,
                Zr_Min, Zr_Max, Ag_Min, Ag_Max, Cu_Min, Cu_Max, Th_Min, Th_Max, Y_Min, Y_Max, RareElements_Min, RareElements_Max;
        private boolean[] Validation = new boolean[31];

        //use builder method to make constructor easier

        public Builder(String name, boolean[] validation) {
            this.AlloyName = name;
            this.Validation = validation.clone();
        }

        public Builder NamingStandard(String namingStandard) {
            this.NamingStandard = namingStandard;
            return this;
        }

        public Builder Component(String component) {
            this.Component = component;
            return this;
        }

        public Builder Density(double density) {
            this.Density = density;
            return this;
        }

        public Builder ThermalExpan(double thermalExpan) {
            this.ThermalExpan = thermalExpan;
            return this;
        }

        public Builder ThermalCon(double thermalCon) {
            this.ThermalCon = thermalCon;
            return this;
        }

        public Builder SpecificHeat(double specificHeat) {
            this.SpecificHeat = specificHeat;
            return this;
        }

        public Builder Resistivity(double resistivity) {
            this.Resistivity = resistivity;
            return this;
        }

        public Builder ElasticModu(double elasticModu) {
            this.ElasticModu = elasticModu;
            return this;
        }

        public Builder PoissonRatio(double poissonsRatio) {
            this.PoissonsRatio = poissonsRatio;
            return this;
        }

        public Builder MeltingRange(double meltingRange_Min, double meltingRange_Max) {
            this.MeltingRange_Min = meltingRange_Min;
            this.MeltingRange_Max = meltingRange_Max;
            return this;
        }

        public Builder DampingIndex(double dampingIndex) {
            this.DampingIndex = dampingIndex;
            return this;
        }

        public Builder Hardness(double hardness_Min, double hardness_Max) {
            this.Hardness_Min = hardness_Min;
            this.Hardness_Max = hardness_Max;
            return this;
        }

        public Builder Forging(String forging) {
            this.Forging = forging;
            return this;
        }

        public Builder Weldability(String weldability) {
            this.Weldability = weldability;
            return this;
        }

        public Builder Machining(String machining) {
            this.Machining = machining;
            return this;
        }

        public Builder SurfaceTreatment(String surfaceTreatment) {
            this.SurfaceTreatment = surfaceTreatment;
            return this;
        }

        public Builder CorrisionResistance(String corrisionResistance) {
            this.CorrisionResistance = corrisionResistance;
            return this;
        }

        public Builder FractureTou(double fractureToughness) {
            this.FractureToughness = fractureToughness;
            return this;
        }

        public Builder ElementPercentage(double Al_Min, double Al_Max, double Mn_Min, double Mn_Max, double Zn_Min, double Zn_Max,
                                         double Mg_Min, double Mg_Max, double Nd_Min, double Nd_Max, double Gd_Min, double Gd_Max,
                                         double Zr_Min, double Zr_Max, double Ag_Min, double Ag_Max, double Cu_Min, double Cu_Max,
                                         double Th_Min, double Th_Max, double Y_Min, double Y_Max, double RareElements_Min, double RareElements_Max) {
            this.Al_Min = Al_Min;
            this.Al_Max = Al_Max;
            this.Mn_Min = Mn_Min;
            this.Mn_Max = Mn_Max;
            this.Zn_Min = Zn_Min;
            this.Zn_Max = Zn_Max;
            this.Mg_Min = Mg_Min;
            this.Mg_Max = Mg_Max;
            this.Nd_Min = Nd_Min;
            this.Nd_Max = Nd_Max;
            this.Gd_Min = Gd_Min;
            this.Gd_Max = Gd_Max;
            this.Zr_Min = Zr_Min;
            this.Zr_Max = Zr_Max;
            this.Ag_Min = Ag_Min;
            this.Ag_Max = Ag_Max;
            this.Cu_Min = Cu_Min;
            this.Cu_Max = Cu_Max;
            this.Th_Min = Th_Min;
            this.Th_Max = Th_Max;
            this.Y_Min = Y_Min;
            this.Y_Max = Y_Max;
            this.RareElements_Min = RareElements_Min;
            this.RareElements_Max = RareElements_Max;
            return this;
        }

        public SingleAlloyItem build() {
            return new SingleAlloyItem(this);
        }
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
