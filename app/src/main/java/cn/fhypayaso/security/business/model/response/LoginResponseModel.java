package cn.fhypayaso.security.business.model.response;

public class LoginResponseModel {
    /**
     * user_id : 5
     * tokenStr : bd1edbceea5742d278d352a0348b76c8
     */

    private int user_id;

    private String tokenStr;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getTokenStr() {
        return tokenStr;
    }

    public void setTokenStr(String tokenStr) {
        this.tokenStr = tokenStr;
    }
}
