package eslam.emad.bloodbank.data;

public class Constants {

    public static final String BASE_URL = "http://ipda3-tech.com/blood-bank/api/v1/";
    public static int PAGE_SIZE = 10;
    public static int FIRST_PAGE = 1;
    public static String API_TOKEN = SharedPreferencesManger.getINSTANCE(MyApplication.getInstance()).restoreStringValue("api_key");
    public static String BLOOD_TYPE_ID = "0";
    public static String GOVERNATE_ID = "0";
    public static int notification_id = 1;
}
