
package eslam.emad.bloodbank.data.models.posts;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostModel {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private PostsResponseData postsResponseData;

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

    public PostsResponseData getPostsResponseData() {
        return postsResponseData;
    }

    public void setPostsResponseData(PostsResponseData postsResponseData) {
        this.postsResponseData = postsResponseData;
    }

}
