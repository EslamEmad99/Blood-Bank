
package eslam.emad.bloodbank.data.models.createDonation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CreateDonationModel {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private CreateDonationData createDonationData;

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

    public CreateDonationData getCreateDonationData() {
        return createDonationData;
    }

    public void setCreateDonationData(CreateDonationData createDonationData) {
        this.createDonationData = createDonationData;
    }

}
