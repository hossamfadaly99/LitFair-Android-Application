package retrofit;

class CvMsg{
    private String file_url;
    private String original_name;

    public String getFile_url() {
        return file_url;
    }

    public String getOriginal_name() {
        return original_name;
    }
}

public class UploadCvModel {
    private String code;
    private String status;
    private CvMsg msg;

    public String getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }

    public String getFileUrl() {
        return msg.getFile_url();
    }

    public String getOriginalName() {
        return msg.getOriginal_name();
    }
}