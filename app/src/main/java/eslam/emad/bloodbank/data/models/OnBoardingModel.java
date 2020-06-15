package eslam.emad.bloodbank.data.models;

public class OnBoardingModel {

    private String Description;
    private int ScreenImg;

    public OnBoardingModel(String description, int screenImg) {
        Description = description;
        ScreenImg = screenImg;
    }

    public String getDescription() {
        return Description;
    }

    public int getScreenImg() {
        return ScreenImg;
    }
}
