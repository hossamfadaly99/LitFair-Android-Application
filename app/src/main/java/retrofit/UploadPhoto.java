package retrofit;

class MSG{
    private String photo_url;

    public String getPhoto_url() {
        return photo_url;
    }
}
public class UploadPhoto {
private MSG msg;
private String code;

    public String getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }

    private String status;

    public String getMsg() {
        return msg.getPhoto_url();
    }
}
