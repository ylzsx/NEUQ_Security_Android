package cn.fhypayaso.security.business.model.request;

public class CarRequestModel {
    /**
     * car_number : aq10000009783
     */

    private String car_number;

    public CarRequestModel(String car_number) {
        this.car_number = car_number;
    }

    public String getCar_number() {
        return car_number;
    }

    public void setCar_number(String car_number) {
        this.car_number = car_number;
    }
}
