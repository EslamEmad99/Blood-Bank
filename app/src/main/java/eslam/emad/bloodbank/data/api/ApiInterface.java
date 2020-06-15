package eslam.emad.bloodbank.data.api;

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

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @POST("login")
    @FormUrlEncoded
    Call<LoginModel> onLogin(@Field("phone") String phone,
                             @Field("password") String password);

    @POST("reset-password")
    @FormUrlEncoded
    Call<ResetPasswordModel> onResetPassword(@Field("phone") String phone);

    @POST("new-password")
    @FormUrlEncoded
    Call<NewPasswordModel> onNewPassword(@Field("password") String password,
                                         @Field("password_confirmation") String passwordConfirmation,
                                         @Field("pin_code") Integer pinCode,
                                         @Field("phone") String phone);

    @GET("blood-types")
    Call<BloodTypeModel> getBloodType();

    @GET("governorates")
    Call<GovernateModel> getGovernate();

    @GET("cities")
    Call<CityModel> getCity(@Query("governorate_id") String governorateId);

    @POST("signup")
    @FormUrlEncoded
    Call<RegisterModel> onRegister(@Field("name") String name,
                                   @Field("email") String email,
                                   @Field("birth_date") String birth_date,
                                   @Field("city_id") String city_id,
                                   @Field("phone") String phone,
                                   @Field("donation_last_date") String donation_last_date,
                                   @Field("password") String password,
                                   @Field("password_confirmation") String password_confirmation,
                                   @Field("blood_type_id") String blood_type_id);

    @GET("posts")
    Call<PostModel> getAllPosts(@Query("api_token") String api_token,
                                @Query("page") int page);

    @GET("donation-requests")
    Call<DonationModel> getDonationsByFilter(@Query("api_token") String api_token,
                                             @Query("blood_type_id") String blood_type_id,
                                             @Query("governorate_id") String governorateId,
                                             @Query("page") int page);

    @POST("post-toggle-favourite")
    @FormUrlEncoded
    Call<SetGetFavoritePostsModel> setFavoritePost(@Field("post_id") int post_id,
                                                   @Field("api_token") String api_token);

    @POST("profile")
    @FormUrlEncoded
    Call<RegisterModel> getProfileData(@Field("api_token") String api_token);

    @POST("profile")
    @FormUrlEncoded
    Call<RegisterModel> editProfileData(@Field("name") String name,
                                        @Field("email") String email,
                                        @Field("birth_date") String birth_date,
                                        @Field("city_id") String city_id,
                                        @Field("phone") String phone,
                                        @Field("donation_last_date") String donation_last_date,
                                        @Field("password") String password,
                                        @Field("password_confirmation") String password_confirmation,
                                        @Field("blood_type_id") String blood_type_id,
                                        @Field("api_token") String api_token);

    @FormUrlEncoded
    @POST("notifications-settings")
    Call<NotificationSettingsModel> setNotificationSettings(
            @Field("api_token") String api_token,
            @Field("governorates[]") ArrayList<String> governorates,
            @Field("blood_types[]") ArrayList<String> blood_types);

    @GET("notifications")
    Call<NotificationModel> getNotifications(@Query("api_token") String api_token,
                                             @Query("page") int page);

    @POST("donation-request/create")
    @FormUrlEncoded
    Call<CreateDonationModel> createDonationRequest(@Field("api_token") String api_token,
                                                    @Field("patient_name") String patient_name,
                                                    @Field("patient_age") String patient_age,
                                                    @Field("blood_type_id") String blood_type_id,
                                                    @Field("bags_num") String bags_num,
                                                    @Field("hospital_name") String hospital_name,
                                                    @Field("hospital_address") String hospital_address,
                                                    @Field("city_id") String city_id,
                                                    @Field("phone") String phone,
                                                    @Field("notes") String notes,
                                                    @Field("latitude") String latitude,
                                                    @Field("longitude") String longitude);

    @GET("my-favourites")
    Call<FavoritePostsModel> getAllFavoritePosts(@Query("api_token") String api_token,
                                                 @Query("page") int page);

    @GET("posts")
    Call<PostModel> getAllSearchPosts(@Query("api_token") String api_token,
                                      @Query("page") int page,
                                      @Query("keyword") String keyword,
                                      @Query("category_id") String category_id);
}
