
package eslam.emad.bloodbank.data.models.donation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DonationModel {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private DonationResponseData donationResponseData;

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

    public DonationResponseData getDonationResponseData() {
        return donationResponseData;
    }

    public void setDonationResponseData(DonationResponseData donationResponseData) {
        this.donationResponseData = donationResponseData;
    }

}
