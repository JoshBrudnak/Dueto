package dueto.dueto.templates;



public class ProfileCell {
    private String name;
    private String description;
    private String likes;
    private String comments;
    private String reposts;
    private String timeStamp;
    private String imgURL;
    private String thumbnail;
    private String videoURL;

    public ProfileCell(String name, String description, String likes, String comments, String reposts, String timeStamp, String imgURL, String thumbnail, String videoURL) {
        this.description = description;
        this.name = name;
        this.likes = likes;
        this.comments = comments;
        this.reposts = reposts;
        this.timeStamp = timeStamp;
        this.imgURL = imgURL;
        this.thumbnail = thumbnail;
        this.videoURL = videoURL;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setImgURL(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getReposts() {
        return reposts;
    }

    public void setReposts(String reposts) {
        this.reposts = reposts;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

}