
package eslam.emad.bloodbank.data.models.notification;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationModel {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private NotificationResponseData notificationResponseData;

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

    public NotificationResponseData getNotificationResponseData() {
        return notificationResponseData;
    }

    public void setNotificationResponseData(NotificationResponseData notificationResponseData) {
        this.notificationResponseData = notificationResponseData;
    }

}
