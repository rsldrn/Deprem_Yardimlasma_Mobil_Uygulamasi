package spes.myapplication.model;

public class modelIhtiyacItem {
    public String item;
    public String street;
    public String neighborhood;
    public String province;
    public String building_info;
    public String district;


    // Constructor
    public modelIhtiyacItem(String item, String neighborhood, String street, String building_info, String province, String district) {
        this.item = item;
        this.neighborhood = neighborhood;
        this.street = street;
        this.building_info = building_info;
        this.province = province;
        this.district = district;
    }

}



