package jp.syned.galleryapp;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataResponse {
    @SerializedName("status")
    private String Status;
    @SerializedName("images")
    List<Images> Images;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public List<jp.syned.galleryapp.Images> getImages() {
        return Images;
    }
}

class Images{
    @SerializedName("id")
    private int Imageid;
    @SerializedName("image_path")
    private String ImagePath;

    public int getImageid() {
        return Imageid;
    }

    public String getImagePath() {
        return ImagePath;
    }
}