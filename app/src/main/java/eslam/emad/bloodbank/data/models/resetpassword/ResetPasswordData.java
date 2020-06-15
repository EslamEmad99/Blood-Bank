
package eslam.emad.bloodbank.data.models.resetpassword;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResetPasswordData {

    @SerializedName("pin_code_for_test")
    @Expose
    private Integer pinCodeForTest;
    @SerializedName("mail_fails")
    @Expose
    private List<Object> mailFails = null;
    @SerializedName("email")
    @Expose
    private String email;

    public Integer getPinCodeForTest() {
        return pinCodeForTest;
    }

    public void setPinCodeForTest(Integer pinCodeForTest) {
        this.pinCodeForTest = pinCodeForTest;
    }

    public List<Object> getMailFails() {
        return mailFails;
    }

    public void setMailFails(List<Object> mailFails) {
        this.mailFails = mailFails;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
