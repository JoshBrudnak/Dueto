package dueto.dueto.templates;



public class NotificationCell {
    private String name;
    private String description;
    private String timeStamp;
    private String imgURL;
    private String otherIMG;

    public NotificationCell(String name, String description, String timeStamp, String imgURL, String otherIMG) {
        this.description = description;
        this.name = name;
        this.timeStamp = timeStamp;
        this.imgURL = imgURL;
        this.otherIMG = otherIMG;
    }

    public String getImgURL() {
        return imgURL;
    }

    public void setotherIMG(String imgURL) {
        this.imgURL = imgURL;
    }

    public String getotherIMG() {
        return otherIMG;
    }

    public void setImgURL(String otherIMG) {
        this.otherIMG = otherIMG;
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

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

}