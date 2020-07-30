package eslam.emad.bloodbank.data.api;

import eslam.emad.bloodbank.data.MyApplication;
import eslam.emad.bloodbank.data.models.bloodType.BloodTypeModel;
import eslam.emad.bloodbank.data.models.city.CityModel;
import eslam.emad.bloodbank.data.models.createDonation.CreateDonationModel;
import eslam.emad.bloodbank.data.models.donation.DonationModel;
import eslam.emad.bloodbank.data.models.favoritePosts.FavoritePostsModel;
import eslam.emad.bloodbank.data.models.setGetFavoritePosts.SetGetFavoritePostsModel;
import eslam.emad.bloodbank.data.models.governate.GovernateModel;
import eslam.emad.bloodbank.data.models.login.LoginModel;
import eslam.emad.bloodbank.data.models.newPassword.NewPasswordModel;
import eslam.emad.bloodbank.data.models.notification.NotificationModel;
import eslam.emad.bloodbank.data.models.notificationSettings.NotificationSettingsModel;
import eslam.emad.bloodbank.data.models.posts.PostModel;
import eslam.emad.bloodbank.data.models.register.RegisterModel;
import eslam.emad.bloodbank.data.models.resetpassword.ResetPasswordModel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static eslam.emad.bloodbank.data.Constants.BASE_URL;

public class ApiClient {

    private ApiInterface apiInterface;
    private static ApiClient INSTANCE;
    private static final long cacheSize = 5 * 1024 * 1024; // 5 MB
    public static final String HEADER_CACHE_CONTROL = "Cache-Control";
    public static final String HEADER_PRAGMA = "Pragma";

    private ApiClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiInterface = retrofit.create(ApiInterface.class);
    }

    public static ApiClient getINSTANCE() {
        if (null == INSTANCE) {
            INSTANCE = new ApiClient();
        }
        return INSTANCE;
    }

    private static OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder()
                .cache(cache())
                .addInterceptor(httpLoggingInterceptor()) // used if network off OR on
                .addNetworkInterceptor(networkInterceptor()) // only used when network is on
                .addInterceptor(offlineInterceptor())
                .build();
    }

    private static Cache cache() {
        return new Cache(new File(MyApplication.getInstance().getCacheDir(), "someIdentifier"), cacheSize);
    }

    private static Interceptor offlineInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                // prevent caching when network is on. For that we use the "networkInterceptor"
                if (!MyApplication.hasNetwork()) {
                    CacheControl cacheControl = new CacheControl.Builder()
                            .maxStale(7, TimeUnit.DAYS)
                            .build();

                    request = request.newBuilder()
                            .removeHeader(HEADER_PRAGMA)
                            .removeHeader(HEADER_CACHE_CONTROL)
                            .cacheControl(cacheControl)
                            .build();
                }

                return chain.proceed(request);
            }
        };
    }

    private static Interceptor networkInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response response = chain.proceed(chain.request());

                CacheControl cacheControl = new CacheControl.Builder()
                        .maxAge(5, TimeUnit.SECONDS)
                        .build();

                return response.newBuilder()
                        .removeHeader(HEADER_PRAGMA)
                        .removeHeader(HEADER_CACHE_CONTROL)
                        .header(HEADER_CACHE_CONTROL, cacheControl.toString())
                        .build();
            }
        };
    }

    private static HttpLoggingInterceptor httpLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor =
                new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                    }
                });
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return httpLoggingInterceptor;
    }

    public Call<LoginModel> onLogin(String phone, String password) {
        return apiInterface.onLogin(phone, password);
    }

    public Call<ResetPasswordModel> onResetPassword(String phone) {
        return apiInterface.onResetPassword(phone);
    }

    public Call<NewPasswordModel> onNewPassword(String password,
                                                String passwordConfirmation,
                                                Integer pinCode,
                                                String phone) {
        return apiInterface.onNewPassword(password, passwordConfirmation, pinCode, phone);
    }

    public Call<BloodTypeModel> getBloodType() {
        return apiInterface.getBloodType();
    }

    public Call<GovernateModel> getGovernate() {
        return apiInterface.getGovernate();
    }

    public Call<CityModel> getCity(String governorateId) {
        return apiInterface.getCity(governorateId);
    }

    public Call<RegisterModel> onRegister(String name,
                                          String email,
                                          String birth_date,
                                          String city_id,
                                          String phone,
                                          String donation_last_date,
                                          String password,
                                          String password_confirmation,
                                          String blood_type_id) {
        return apiInterface.onRegister(
                name,
                email,
                birth_date,
                city_id,
                phone,
                donation_last_date,
                password,
                password_confirmation,
                blood_type_id);
    }

    public Call<PostModel> getAllPosts(String api_token, int page) {
        return apiInterface.getAllPosts(api_token, page);
    }

    public Call<DonationModel> getDonationByFilter(String api_token, String blood_type_id, String governorate_id, int page) {
        return apiInterface.getDonationsByFilter(api_token, blood_type_id, governorate_id, page);
    }

    public Call<SetGetFavoritePostsModel> setGetFavoritePost(int post_id, String api_token) {
        return apiInterface.setFavoritePost(post_id, api_token);
    }

    public Call<RegisterModel> getProfileData(String api_token) {
        return apiInterface.getProfileData(api_token);
    }

    public Call<RegisterModel> editProfileData(String name,
                                               String email,
                                               String birth_date,
                                               String city_id,
                                               String phone,
                                               String donation_last_date,
                                               String password,
                                               String password_confirmation,
                                               String blood_type_id,
                                               String api_token) {
        return apiInterface.editProfileData(
                name,
                email,
                birth_date,
                city_id,
                phone,
                donation_last_date,
                password,
                password_confirmation,
                blood_type_id,
                api_token);
    }

    public Call<NotificationSettingsModel> setNotificationSettings(String api_token, ArrayList<String> governorates, ArrayList<String> blood_types) {
        return apiInterface.setNotificationSettings(api_token, governorates, blood_types);
    }

    public Call<NotificationModel> getNotifications(String api_token, int page) {
        return apiInterface.getNotifications(api_token, page);
    }

    public Call<CreateDonationModel> createDonationRequest(String api_token,
                                                     String patient_name,
                                                     String patient_age,
                                                     String blood_type_id,
                                                     String bags_num,
                                                     String hospital_name,
                                                     String hospital_address,
                                                     String city_id,
                                                     String phone,
                                                     String notes,
                                                     String latitude,
                                                     String longitude) {
        return apiInterface.createDonationRequest(
                api_token,
                patient_name,
                patient_age,
                blood_type_id,
                bags_num,
                hospital_name,
                hospital_address,
                city_id,
                phone,
                notes,
                latitude,
                longitude);
    }

    public Call<FavoritePostsModel> getAllFavoritePosts(String api_token, int page) {
        return apiInterface.getAllFavoritePosts(api_token, page);
    }

    public Call<PostModel> getAllSearchPosts(String api_token, int page, String keyword, String category_id) {
        return apiInterface.getAllSearchPosts(api_token, page, keyword, category_id);
    }
}
