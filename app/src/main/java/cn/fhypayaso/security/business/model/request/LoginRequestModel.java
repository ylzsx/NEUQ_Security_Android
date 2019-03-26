package cn.fhypayaso.security.business.model.request;

public class LoginRequestModel {
    /**
     * id : 123456
     * password : 123456
     */

    private String id;
    private String password;

    public LoginRequestModel(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
