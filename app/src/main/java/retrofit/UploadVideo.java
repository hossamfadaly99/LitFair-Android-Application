package retrofit;

class VideoMsg{
    private String video_url;
    private String original_name;

    public String getVideo_url() {
        return video_url;
    }

    public String getOriginal_name() {
        return original_name;
    }
}

public class UploadVideo {
    private String code;
    private String status;
    private VideoMsg msg;

    public String getCode() {
        return code;
    }

    public String getStatus() {
        return status;
    }

    public String getVideoUrl() {
        return msg.getVideo_url();
    }

    public String getOriginalName() {
        return msg.getOriginal_name();
    }
}