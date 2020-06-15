
package eslam.emad.bloodbank.data.models.favoritePosts;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FavoritePostsModel {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private FavoritePostsResponseData favoritePostsResponseData;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public FavoritePostsResponseData getFavoritePostsResponseData() {
        return favoritePostsResponseData;
    }

    public void setFavoritePostsResponseData(FavoritePostsResponseData favoritePostsResponseData) {
        this.favoritePostsResponseData = favoritePostsResponseData;
    }

}
