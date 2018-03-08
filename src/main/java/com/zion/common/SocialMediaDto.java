package com.zion.common;

public class SocialMediaDto {
    private String facebook;
    private String instagram;
    private String youtube;
    private String linkedin;

    public SocialMediaDto(String facebook, String instagram, String youtube, String linkedin) {
        this.facebook = facebook;
        this.instagram = instagram;
        this.youtube = youtube;
        this.linkedin = linkedin;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getYoutube() {
        return youtube;
    }

    public void setYoutube(String youtube) {
        this.youtube = youtube;
    }

    public String getLinkedin() {
        return linkedin;
    }

    public void setLinkedin(String linkedin) {
        this.linkedin = linkedin;
    }
}
