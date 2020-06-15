
package eslam.emad.bloodbank.data.models.setGetFavoritePosts;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SetGetFavoritePostsModel {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private SetGetFavoritePostsData setGetFavoritePostsData;

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

    public SetGetFavoritePostsData getSetGetFavoritePostsData() {
        return setGetFavoritePostsData;
    }

    public void setSetGetFavoritePostsData(SetGetFavoritePostsData setGetFavoritePostsData) {
        this.setGetFavoritePostsData = setGetFavoritePostsData;
    }

}
