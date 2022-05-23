package retrofit;

public class LoginPost {

    private String email;
    private String password;
    //json return depending on the status
    private String msg;
    private String tokenObject;

    public String getTokenObject() {
        return tokenObject;
    }
    public void setTokenObject(String tokenObject) {
        this.tokenObject = tokenObject;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public String getMsg() {
        return msg;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public LoginPost(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
