package com.psi.shen.primary;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.ViewGroup;

public class SingleAlloyItem implements Parcelable{
    private String AlloyName,NamingStandard,Component;
    private Double Density,ThermalExpan,ThermalCon,SpecificHeat,Resistivity,ElasticModu,PoissonsRatio,MeltingRange_Min,MeltingRange_Max,DampingIndex,
            Hardness_Min, Hardness_Max;
    private String Forging, Weldability, Machining, SurfaceTreatment, CorrisionResistance;
    private Double FractureToughness;
    private Double Al_Min, Al_Max, Mn_Min, Mn_Max, Zn_Min, Zn_Max, Mg_Min, Mg_Max, Nd_Min, Nd_Max, Gd_Min, Gd_Max,
        Zr_Min, Zr_Max, Ag_Min, Ag_Max, Cu_Min, Cu_Max, Th_Min, Th_Max, Y_Min, Y_Max, RareElements_Min, RareElements_Max;

    //use builder method to make constructor easier

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
        }

    public static class Builder {
        private String AlloyName, NamingStandard = "", Component = "";
        private Double Density, ThermalExpan, ThermalCon, SpecificHeat, Resistivity, ElasticModu, PoissonsRatio, MeltingRange_Min, MeltingRange_Max, DampingIndex,
                Hardness_Min, Hardness_Max;
        private String Forging = "", Weldability = "", Machining = "", SurfaceTreatment = "", CorrisionResistance = "";
        private Double FractureToughness;
        private Double Al_Min, Al_Max, Mn_Min, Mn_Max, Zn_Min, Zn_Max, Mg_Min, Mg_Max, Nd_Min, Nd_Max, Gd_Min, Gd_Max,
                Zr_Min, Zr_Max, Ag_Min, Ag_Max, Cu_Min, Cu_Max, Th_Min, Th_Max, Y_Min, Y_Max, RareElements_Min, RareElements_Max;

        public Builder(String name) {
            this.AlloyName = name;
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
            this.DampingIndex = density;
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


    //need modification

    public SingleAlloyItem(String[] returnDatas){
        this.AlloyName = (returnDatas[0].equals("null")) ? returnDatas[0]:null;
        this.NamingStandard = (returnDatas[1].equals("null")) ? returnDatas[1]:null;
        this.Component = (returnDatas[2].equals("null")) ? returnDatas[2]:null;
        this.Density = (returnDatas[3].equals("null")) ? Double.parseDouble(returnDatas[3]):null;
        this.ThermalCon = (returnDatas[4].equals("null")) ? Double.parseDouble(returnDatas[4]):null;
        this.ThermalExpan = (returnDatas[5].equals("null")) ? Double.parseDouble(returnDatas[5]):null;
        this.SpecificHeat = (returnDatas[6].equals("null")) ? Double.parseDouble(returnDatas[6]):null;
        this.Resistivity = (returnDatas[7].equals("null")) ? Double.parseDouble(returnDatas[7]):null;
        this.ElasticModu = (returnDatas[8].equals("null")) ? Double.parseDouble(returnDatas[8]):null;
        this.PoissonsRatio = (returnDatas[9].equals("null")) ? Double.parseDouble(returnDatas[9]):null;
        this.MeltingRange_Min = (returnDatas[10].equals("null")) ? Double.parseDouble(returnDatas[10]):null;
        this.MeltingRange_Max = (returnDatas[11].equals("null")) ? Double.parseDouble(returnDatas[11]):null;
        this.DampingIndex = (returnDatas[12].equals("null")) ? Double.parseDouble(returnDatas[12]):null;
        this.Hardness_Min = (returnDatas[13].equals("null")) ? Double.parseDouble(returnDatas[13]):null;
        this.Hardness_Max = (returnDatas[14].equals("null")) ? Double.parseDouble(returnDatas[14]):null;
        this.Forging = (returnDatas[15].equals("null")) ? returnDatas[15]:null;
        this.Weldability = (returnDatas[16].equals("null")) ? returnDatas[16]:null;
        this.Machining = (returnDatas[17].equals("null")) ? returnDatas[17]:null;
        this.SurfaceTreatment = (returnDatas[18].equals("null")) ? returnDatas[18]:null;
        this.CorrisionResistance = (returnDatas[19].equals("null")) ? returnDatas[19]:null;
        this.FractureToughness = (returnDatas[20].equals("null")) ? Double.parseDouble(returnDatas[20]):null;
        this.Al_Min = (returnDatas[21].equals("null")) ? Double.parseDouble(returnDatas[21]) : null;
        this.Al_Max = (returnDatas[22].equals("null")) ? Double.parseDouble(returnDatas[22]) : null;
        this.Mn_Min = (returnDatas[23].equals("null")) ? Double.parseDouble(returnDatas[23]) : null;
        this.Mn_Max = (returnDatas[24].equals("null")) ? Double.parseDouble(returnDatas[24]) : null;
        this.Zn_Min = (returnDatas[25].equals("null")) ? Double.parseDouble(returnDatas[25]) : null;
        this.Zn_Max = (returnDatas[26].equals("null")) ? Double.parseDouble(returnDatas[26]) : null;
        this.Mg_Min = (returnDatas[27].equals("null")) ? Double.parseDouble(returnDatas[27]) : null;
        this.Mg_Max = (returnDatas[28].equals("null")) ? Double.parseDouble(returnDatas[28]) : null;
        this.Nd_Min = (returnDatas[29].equals("null")) ? Double.parseDouble(returnDatas[29]) : null;
        this.Nd_Max = (returnDatas[30].equals("null")) ? Double.parseDouble(returnDatas[30]) : null;
        this.Gd_Min = (returnDatas[31].equals("null")) ? Double.parseDouble(returnDatas[31]) : null;
        this.Gd_Max = (returnDatas[32].equals("null")) ? Double.parseDouble(returnDatas[32]) : null;
        this.Zr_Min = (returnDatas[33].equals("null")) ? Double.parseDouble(returnDatas[33]) : null;
        this.Zr_Max = (returnDatas[34].equals("null")) ? Double.parseDouble(returnDatas[34]) : null;
        this.Ag_Min = (returnDatas[35].equals("null")) ? Double.parseDouble(returnDatas[35]) : null;
        this.Ag_Max = (returnDatas[36].equals("null")) ? Double.parseDouble(returnDatas[36]) : null;
        this.Cu_Min = (returnDatas[37].equals("null")) ? Double.parseDouble(returnDatas[37]) : null;
        this.Cu_Max = (returnDatas[38].equals("null")) ? Double.parseDouble(returnDatas[38]) : null;
        this.Th_Min = (returnDatas[39].equals("null")) ? Double.parseDouble(returnDatas[39]) : null;
        this.Th_Max = (returnDatas[40].equals("null")) ? Double.parseDouble(returnDatas[40]) : null;
        this.Y_Min = (returnDatas[41].equals("null")) ? Double.parseDouble(returnDatas[41]) : null;
        this.Y_Max = (returnDatas[42].equals("null")) ? Double.parseDouble(returnDatas[42]) : null;
        this.RareElements_Min = (returnDatas[43].equals("null")) ? Double.parseDouble(returnDatas[43]) : null;
        this.RareElements_Max = (returnDatas[44].equals("null")) ? Double.parseDouble(returnDatas[44]) : null;
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
    public Double getDensity() {
        return Density;
    }
    public Double getThermalExpan() {
        return ThermalExpan;
    }
    public Double getThermalCon() {
        return ThermalCon;
    }
    public Double getSpecificHeat() {
        return SpecificHeat;
    }
    public Double getResistivity() {
        return Resistivity;
    }
    public Double getElasticModu() {
        return ElasticModu;
    }
    public Double getPoissonsRatio() {
        return PoissonsRatio;
    }
    public String getMeltingRangeString(){
        String out = "";
        out += MeltingRange_Min==null? "null ~ ":MeltingRange_Min+"~ ";
        out += MeltingRange_Max==null? "null":MeltingRange_Max;
        return out.equals("null ~ null")? "No data":out;
    }
    public Double getDampingIndex() {
        return DampingIndex;
    }
    public Double getHardness_Min() {
        return Hardness_Min;
    }
    public Double getHardness_Max() {
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
    public Double getFractureToughness() {
        return FractureToughness;
    }
    public Double getAl_Min() {
        return Al_Min;
    }
    public Double getAl_Max() {
        return Al_Max;
    }
    public Double getMn_Min() {
        return Mn_Min;
    }
    public Double getMn_Max() {
        return Mn_Max;
    }
    public Double getZn_Min() {
        return Zn_Min;
    }
    public Double getZn_Max() {
        return Zn_Max;
    }
    public Double getMg_Min() {
        return Mg_Min;
    }
    public Double getMg_Max() {
        return Mg_Max;
    }
    public Double getNd_Min() {
        return Nd_Min;
    }
    public Double getNd_Max() {
        return Nd_Max;
    }
    public Double getGd_Min() {
        return Gd_Min;
    }
    public Double getGd_Max() {
        return Gd_Max;
    }
    public Double getZr_Min() {
        return Zr_Min;
    }
    public Double getZr_Max() {
        return Zr_Max;
    }
    public Double getAg_Min() {
        return Ag_Min;
    }
    public Double getAg_Max() {
        return Ag_Max;
    }
    public Double getCu_Min() {
        return Cu_Min;
    }
    public Double getCu_Max() {
        return Cu_Max;
    }
    public Double getTh_Min() {
        return Th_Min;
    }
    public Double getTh_Max() {
        return Th_Max;
    }
    public Double getY_Min() {
        return Y_Min;
    }
    public Double getY_Max() {
        return Y_Max;
    }
    public Double getRareElements_Min() {
        return RareElements_Min;
    }
    public Double getRareElements_Max() {
        return RareElements_Max;
    }


    //Parcelable method

    @Override
    public int describeContents(){
        return 0;
    }
    @Override
    public void writeToParcel(Parcel out, int flags){
        boolean[] packagedOtherNull = packDoubleNull(new Double[]{Density,ThermalExpan,ThermalCon,SpecificHeat,Resistivity,ElasticModu,PoissonsRatio,MeltingRange_Min,MeltingRange_Max,
                DampingIndex,Hardness_Min,Hardness_Max,FractureToughness});
        out.writeBooleanArray(packagedOtherNull);
        out.writeString(AlloyName);
        out.writeString(NamingStandard);
        out.writeString(Component);
        out.writeDouble(Density==null? 0.0:Density);
        out.writeDouble(ThermalExpan==null? 0.0:ThermalExpan);
        out.writeDouble(ThermalCon==null? 0.0:ThermalCon);
        out.writeDouble(SpecificHeat==null? 0.0:SpecificHeat);
        out.writeDouble(Resistivity==null? 0.0:Resistivity);
        out.writeDouble(ElasticModu==null? 0.0:ElasticModu);
        out.writeDouble(PoissonsRatio==null? 0.0:PoissonsRatio);
        out.writeDouble(MeltingRange_Min==null? 0.0:MeltingRange_Min);
        out.writeDouble(MeltingRange_Max==null? 0.0:MeltingRange_Max);
        out.writeDouble(DampingIndex==null? 0.0:DampingIndex);
        out.writeDouble(Hardness_Min==null? 0.0:Hardness_Min);
        out.writeDouble(Hardness_Max==null? 0.0:Hardness_Max);
        out.writeString(Forging);
        out.writeString(Weldability);
        out.writeString(Machining);
        out.writeString(SurfaceTreatment);
        out.writeString(CorrisionResistance);
        out.writeDouble(FractureToughness==null? 0.0:FractureToughness);
        boolean[] packageElementNull = packDoubleNull(new Double[]{Al_Min,Al_Max,Mn_Min,Mn_Max,Zn_Min,Zn_Max,Mg_Min,Mg_Max,Nd_Min,Nd_Max,Gd_Min,Gd_Max,Zr_Min,Zr_Max,Ag_Min,Ag_Max,
                Cu_Min,Cu_Max,Th_Min,Th_Max,Y_Min,Y_Max,RareElements_Min,RareElements_Max});
        out.writeBooleanArray(packageElementNull);
        out.writeDouble(Al_Min==null? 0.0:Al_Min);
        out.writeDouble(Al_Max==null? 0.0:Al_Max);
        out.writeDouble(Mn_Min==null? 0.0:Mn_Min);
        out.writeDouble(Mn_Max==null? 0.0:Mn_Max);
        out.writeDouble(Zn_Min==null? 0.0:Zn_Min);
        out.writeDouble(Zn_Max==null? 0.0:Zn_Max);
        out.writeDouble(Mg_Min==null? 0.0:Mg_Min);
        out.writeDouble(Mg_Max==null? 0.0:Mg_Max);
        out.writeDouble(Nd_Min==null? 0.0:Nd_Min);
        out.writeDouble(Nd_Max==null? 0.0:Nd_Max);
        out.writeDouble(Gd_Min==null? 0.0:Gd_Min);
        out.writeDouble(Gd_Max==null? 0.0:Gd_Max);
        out.writeDouble(Zr_Min==null? 0.0:Zr_Max);
        out.writeDouble(Zr_Max==null? 0.0:Zr_Max);
        out.writeDouble(Ag_Min==null? 0.0:Ag_Min);
        out.writeDouble(Ag_Max==null? 0.0:Ag_Max);
        out.writeDouble(Cu_Min==null? 0.0:Cu_Min);
        out.writeDouble(Cu_Max==null? 0.0:Cu_Max);
        out.writeDouble(Th_Min==null? 0.0:Th_Min);
        out.writeDouble(Th_Max==null? 0.0:Th_Max);
        out.writeDouble(Y_Min==null? 0.0:Y_Min);
        out.writeDouble(Y_Max==null? 0.0:Y_Max);
        out.writeDouble(RareElements_Min==null? 0.0:RareElements_Min);
        out.writeDouble(RareElements_Max==null? 0.0:RareElements_Max);
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
        boolean[] otherPackNull = new boolean[13];
        in.readBooleanArray(otherPackNull);
        this.AlloyName = in.readString();
        this.NamingStandard = in.readString();
        this.Component = in.readString();
        this.Density = otherPackNull[0]? null:in.readDouble();
        this.ThermalExpan = otherPackNull[1]? null:in.readDouble();
        this.ThermalCon = otherPackNull[2]? null:in.readDouble();
        this.SpecificHeat = otherPackNull[3]? null:in.readDouble();
        this.Resistivity = otherPackNull[4]? null:in.readDouble();
        this.ElasticModu = otherPackNull[5]? null:in.readDouble();
        this.PoissonsRatio = otherPackNull[6]? null:in.readDouble();
        this.MeltingRange_Min = otherPackNull[7]? null:in.readDouble();
        this.MeltingRange_Max = otherPackNull[8]? null:in.readDouble();
        this.DampingIndex = otherPackNull[9]? null:in.readDouble();
        this.Hardness_Min = otherPackNull[10]? null:in.readDouble();
        this.Hardness_Max = otherPackNull[11]? null:in.readDouble();
        this.Forging = in.readString();
        this.Weldability = in.readString();
        this.Machining = in.readString();
        this.SurfaceTreatment = in.readString();
        this.CorrisionResistance = in.readString();
        this.FractureToughness = otherPackNull[12]? null:in.readDouble();
        boolean[] elementNull = new boolean[24];
        in.readBooleanArray(elementNull);
        this.Al_Min = elementNull[0]? null:in.readDouble();
        this.Al_Max = elementNull[1]? null:in.readDouble();
        this.Mn_Min = elementNull[2]? null:in.readDouble();
        this.Mn_Max = elementNull[3]? null:in.readDouble();
        this.Zn_Min = elementNull[4]? null:in.readDouble();
        this.Zn_Max = elementNull[5]? null:in.readDouble();
        this.Mg_Min = elementNull[6]? null:in.readDouble();
        this.Mg_Max = elementNull[7]? null:in.readDouble();
        this.Nd_Min = elementNull[8]? null:in.readDouble();
        this.Nd_Max = elementNull[9]? null:in.readDouble();
        this.Gd_Min = elementNull[10]? null:in.readDouble();
        this.Gd_Max = elementNull[11]? null:in.readDouble();
        this.Zr_Min = elementNull[12]? null:in.readDouble();
        this.Zr_Max = elementNull[13]? null:in.readDouble();
        this.Ag_Min = elementNull[14]? null:in.readDouble();
        this.Ag_Max = elementNull[15]? null:in.readDouble();
        this.Cu_Min = elementNull[16]? null:in.readDouble();
        this.Cu_Max = elementNull[17]? null:in.readDouble();
        this.Th_Min = elementNull[18]? null:in.readDouble();
        this.Th_Max = elementNull[19]? null:in.readDouble();
        this.Y_Min = elementNull[20]? null:in.readDouble();
        this.Y_Max = elementNull[21]? null:in.readDouble();
        this.RareElements_Min = elementNull[22]? null:in.readDouble();
        this.RareElements_Max = elementNull[23]? null:in.readDouble();
    }
    private boolean[] packDoubleNull(Double[] data){
        int size = data.length;
        boolean[] out = new boolean[size];
        for(int i=0;i<size;i++){
            out[i]= !(data[i]==null);
        }
        return out;
    }
}

