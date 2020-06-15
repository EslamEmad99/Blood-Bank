
package eslam.emad.bloodbank.data.models.resetpassword;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResetPasswordModel {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private ResetPasswordData resetPasswordData;

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

    public ResetPasswordData getResetPasswordData() {
        return resetPasswordData;
    }

    public void setResetPasswordData(ResetPasswordData resetPasswordData) {
        this.resetPasswordData = resetPasswordData;
    }

}
