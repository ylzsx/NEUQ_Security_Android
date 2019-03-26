package cn.fhypayaso.security.business.model.response;

public class CarResponseModel {
    /**
     * id : 11
     * name : pig
     * department : CS
     * car_number : aq10000009783
     * phone : 13113313779
     */

    private int id;
    private String name;
    private String department;
    private String car_number;
    private String phone;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getCar_number() {
        return car_number;
    }

    public void setCar_number(String car_number) {
        this.car_number = car_number;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
