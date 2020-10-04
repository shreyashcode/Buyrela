package com.example.firestore.Modal;

public class Useraddress
{
    private String Name;
    private String PlotNumber;
    private String ResidentName;
    private String area;
    private String LandMark;
    private String PinCode;

    public Useraddress()
    {

    }

    public Useraddress(String Name, String PlotNumber, String ResidentName, String area, String LandMark, String PinCode) {
        this.Name = Name;
        this.PlotNumber = PlotNumber;
        this.ResidentName = ResidentName;
        this.area = area;
        this.LandMark = LandMark;
        this.PinCode = PinCode;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getPlotNumber() {
        return PlotNumber;
    }

    public void setPlotNumber(String PlotNumber) {
        this.PlotNumber = PlotNumber;
    }

    public String getResidentName() {
        return ResidentName;
    }

    public void setResidentName(String ResidentName) {
        this.ResidentName = ResidentName;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getLandMark() {
        return LandMark;
    }

    public void setLandMark(String LandMark) {
        this.LandMark = LandMark;
    }

    public String getPinCode() {
        return PinCode;
    }

    public void setPinCode(String PinCode) {
        this.PinCode = PinCode;
    }
}
