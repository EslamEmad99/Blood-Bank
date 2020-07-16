package eslam.emad.bloodbank.repository;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

import eslam.emad.bloodbank.data.api.ApiClient;
import eslam.emad.bloodbank.data.models.bloodType.BloodTypeModel;
import eslam.emad.bloodbank.data.models.city.CityModel;
import eslam.emad.bloodbank.data.models.createDonation.CreateDonationModel;
import eslam.emad.bloodbank.data.models.favoritePosts.FavoritePostsModel;
import eslam.emad.bloodbank.data.models.governate.GovernateModel;
import eslam.emad.bloodbank.data.models.login.LoginModel;
import eslam.emad.bloodbank.data.models.newPassword.NewPasswordModel;
import eslam.emad.bloodbank.data.models.notification.NotificationModel;
import eslam.emad.bloodbank.data.models.notificationSettings.NotificationSettingsModel;
import eslam.emad.bloodbank.data.models.posts.PostModel;
import eslam.emad.bloodbank.data.models.register.RegisterModel;
import eslam.emad.bloodbank.data.models.resetpassword.ResetPasswordModel;
import eslam.emad.bloodbank.data.models.setGetFavoritePosts.SetGetFavoritePostsModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {

    private static Repository INSTANCE;
    //Called when log out
    public MutableLiveData<LoginModel> onloginModel;
    private MutableLiveData<ResetPasswordModel> onResetPassword;
    private MutableLiveData<NewPasswordModel> onNewPassword;
    private MutableLiveData<BloodTypeModel> bloodType;
    private MutableLiveData<GovernateModel> governate;
    private MutableLiveData<CityModel> city;
    private MutableLiveData<RegisterModel> onRegister;
    private MutableLiveData<SetGetFavoritePostsModel> setGetFavoritePost;
    private MutableLiveData<RegisterModel> profileData;
    private MutableLiveData<RegisterModel> editProfileData;
    private MutableLiveData<NotificationSettingsModel> notificationSettings;
    private MutableLiveData<CreateDonationModel> createDonationRequest;

    private MutableLiveData<Boolean> isDonationEmpty;
    private MutableLiveData<Boolean> isFavoritePostsEmpty;
    private MutableLiveData<Boolean> isNotificationEmpty;
    private MutableLiveData<Boolean> isPostsEmpty;
    private MutableLiveData<Boolean> isSearchPostsEmpty;

    private Repository() {
        onloginModel = new MutableLiveData<>();
        onResetPassword = new MutableLiveData<>();
        onNewPassword = new MutableLiveData<>();
        bloodType = new MutableLiveData<>();
        governate = new MutableLiveData<>();
        city = new MutableLiveData<>();
        onRegister = new MutableLiveData<>();
        setGetFavoritePost = new MutableLiveData<>();
        profileData = new MutableLiveData<>();
        editProfileData = new MutableLiveData<>();
        notificationSettings = new MutableLiveData<>();
        createDonationRequest = new MutableLiveData<>();

        isDonationEmpty = new MutableLiveData<>();
        isFavoritePostsEmpty = new MutableLiveData<>();
        isNotificationEmpty = new MutableLiveData<>();
        isPostsEmpty = new MutableLiveData<>();
        isSearchPostsEmpty = new MutableLiveData<>();
    }

    public static Repository getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new Repository();
        }
        return INSTANCE;
    }

    public MutableLiveData<LoginModel> getOnloginModel() {
        return onloginModel;
    }

    public void setOnLogin(String phone, String password) {
        ApiClient.getINSTANCE()
                .onLogin(phone, password)
                .enqueue(new Callback<LoginModel>() {
                    @Override
                    public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                        onloginModel.setValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<LoginModel> call, Throwable t) {
                        LoginModel obj = new LoginModel();
                        obj.setMsg("Error connection");
                        obj.setStatus(-1);
                        onloginModel.setValue(obj);
                    }
                });
    }

    public MutableLiveData<ResetPasswordModel> getOnResetPassword() {
        return onResetPassword;
    }

    public void setOnResetPassword(String phone) {
        ApiClient.getINSTANCE().onResetPassword(phone)
                .enqueue(new Callback<ResetPasswordModel>() {
                    @Override
                    public void onResponse(Call<ResetPasswordModel> call, Response<ResetPasswordModel> response) {
                        onResetPassword.setValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<ResetPasswordModel> call, Throwable t) {
                        ResetPasswordModel obj = new ResetPasswordModel();
                        obj.setMsg("Error connection");
                        obj.setStatus(-1);
                        onResetPassword.setValue(obj);
                    }
                });
    }

    public MutableLiveData<NewPasswordModel> getOnNewPassword() {
        return onNewPassword;
    }

    public void setOnNewPassword(String password, String passwordConfirmation, Integer pinCode, String phone) {
        ApiClient.getINSTANCE().onNewPassword(password, passwordConfirmation, pinCode, phone)
                .enqueue(new Callback<NewPasswordModel>() {
                    @Override
                    public void onResponse(Call<NewPasswordModel> call, Response<NewPasswordModel> response) {
                        onNewPassword.setValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<NewPasswordModel> call, Throwable t) {
                        NewPasswordModel obj = new NewPasswordModel();
                        obj.setMsg("Error connection");
                        obj.setStatus(-1);
                        onNewPassword.setValue(obj);
                    }
                });
    }

    public MutableLiveData<BloodTypeModel> getBloodType() {
        return bloodType;
    }

    public void setBloodType() {
        ApiClient.getINSTANCE().getBloodType()
                .enqueue(new Callback<BloodTypeModel>() {
                    @Override
                    public void onResponse(Call<BloodTypeModel> call, Response<BloodTypeModel> response) {
                        bloodType.setValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<BloodTypeModel> call, Throwable t) {
                        BloodTypeModel obj = new BloodTypeModel();
                        obj.setMsg("Error connection");
                        obj.setStatus(-1);
                        bloodType.setValue(obj);
                    }
                });
    }

    public MutableLiveData<GovernateModel> getGovernate() {
        return governate;
    }

    public void setGovernate() {
        ApiClient.getINSTANCE().getGovernate()
                .enqueue(new Callback<GovernateModel>() {
                    @Override
                    public void onResponse(Call<GovernateModel> call, Response<GovernateModel> response) {
                        governate.setValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<GovernateModel> call, Throwable t) {
                        GovernateModel obj = new GovernateModel();
                        obj.setMsg("Error connection");
                        obj.setStatus(-1);
                        governate.setValue(obj);
                    }
                });
    }

    public MutableLiveData<CityModel> getCity() {
        return city;
    }

    public void setCity(String governorateId) {
        ApiClient.getINSTANCE().getCity(governorateId)
                .enqueue(new Callback<CityModel>() {
                    @Override
                    public void onResponse(Call<CityModel> call, Response<CityModel> response) {
                        city.setValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<CityModel> call, Throwable t) {
                        CityModel obj = new CityModel();
                        obj.setMsg("Error connection");
                        obj.setStatus(-1);
                        city.setValue(obj);
                    }
                });
    }

    public MutableLiveData<RegisterModel> getOnRegister() {
        return onRegister;
    }

    public void setOnRegister(String name, String email, String birth_date, String city_id, String phone, String donation_last_date, String password, String password_confirmation, String blood_type_id) {
        ApiClient.getINSTANCE().onRegister(name, email, birth_date, city_id, phone, donation_last_date, password, password_confirmation, blood_type_id)
                .enqueue(new Callback<RegisterModel>() {
                    @Override
                    public void onResponse(Call<RegisterModel> call, Response<RegisterModel> response) {
                        onRegister.setValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<RegisterModel> call, Throwable t) {
                        RegisterModel obj = new RegisterModel();
                        obj.setMsg("Error connection");
                        obj.setStatus(-1);
                        onRegister.setValue(obj);
                    }
                });
    }


    public MutableLiveData<SetGetFavoritePostsModel> getSetGetFavoritePost() {
        return setGetFavoritePost;
    }

    public void setSetGetFavoritePost(int post_id, String api_token) {
        ApiClient.getINSTANCE().setGetFavoritePost(post_id, api_token)
                .enqueue(new Callback<SetGetFavoritePostsModel>() {
                    @Override
                    public void onResponse(Call<SetGetFavoritePostsModel> call, Response<SetGetFavoritePostsModel> response) {
                        setGetFavoritePost.setValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<SetGetFavoritePostsModel> call, Throwable t) {
                        SetGetFavoritePostsModel obj = new SetGetFavoritePostsModel();
                        obj.setMsg("Error connection");
                        obj.setStatus(-1);
                        setGetFavoritePost.setValue(obj);
                    }
                });
    }

    public MutableLiveData<RegisterModel> getProfileData() {
        return profileData;
    }

    public void setProfileData(String api_token) {
        ApiClient.getINSTANCE().getProfileData(api_token)
                .enqueue(new Callback<RegisterModel>() {
                    @Override
                    public void onResponse(Call<RegisterModel> call, Response<RegisterModel> response) {
                        profileData.setValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<RegisterModel> call, Throwable t) {
                        RegisterModel obj = new RegisterModel();
                        obj.setMsg("Error connection");
                        obj.setStatus(-1);
                        profileData.setValue(obj);
                    }
                });
    }

    public MutableLiveData<RegisterModel> getEditProfileData() {
        return editProfileData;
    }

    public void setEditProfileData(String name, String email, String birth_date, String city_id, String phone, String donation_last_date, String password, String password_confirmation, String blood_type_id, String api_token) {

        ApiClient.getINSTANCE().editProfileData(name, email, birth_date, city_id, phone, donation_last_date, password, password_confirmation, blood_type_id, api_token)
                .enqueue(new Callback<RegisterModel>() {
                    @Override
                    public void onResponse(Call<RegisterModel> call, Response<RegisterModel> response) {
                        editProfileData.setValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<RegisterModel> call, Throwable t) {
                        RegisterModel obj = new RegisterModel();
                        obj.setMsg("Error connection");
                        obj.setStatus(-1);
                        editProfileData.setValue(obj);
                    }
                });

    }

    public MutableLiveData<NotificationSettingsModel> getNotificationSettings() {
        return notificationSettings;
    }

    public void setNotificationSettings(String api_token, ArrayList<String> governorates, ArrayList<String> blood_types) {
        ApiClient.getINSTANCE().setNotificationSettings(api_token, governorates, blood_types)
                .enqueue(new Callback<NotificationSettingsModel>() {
                    @Override
                    public void onResponse(Call<NotificationSettingsModel> call, Response<NotificationSettingsModel> response) {
                        notificationSettings.setValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<NotificationSettingsModel> call, Throwable t) {
                        NotificationSettingsModel obj = new NotificationSettingsModel();
                        obj.setMsg("Error connection");
                        obj.setStatus(-1);
                        notificationSettings.setValue(obj);
                    }
                });
    }

    public MutableLiveData<CreateDonationModel> getCreateDonationRequest() {
        return createDonationRequest;
    }

    public void setCreateDonationRequest(String api_token, String patient_name, String patient_age, String blood_type_id, String bags_num, String hospital_name, String hospital_address, String city_id, String phone, String notes, String latitude, String longitude) {

        ApiClient.getINSTANCE().createDonationRequest(api_token, patient_name, patient_age, blood_type_id, bags_num, hospital_name, hospital_address, city_id, phone, notes, latitude, longitude)
                .enqueue(new Callback<CreateDonationModel>() {
                    @Override
                    public void onResponse(Call<CreateDonationModel> call, Response<CreateDonationModel> response) {
                        createDonationRequest.setValue(response.body());
                    }

                    @Override
                    public void onFailure(Call<CreateDonationModel> call, Throwable t) {
                        CreateDonationModel obj = new CreateDonationModel();
                        obj.setMsg("Error connection");
                        obj.setStatus(-1);
                        createDonationRequest.setValue(obj);
                    }
                });

    }

    public MutableLiveData<Boolean> isDonationDataEmpty(String api_token, int page, String keyword, String category_id) {
        ApiClient.getINSTANCE().getAllSearchPosts(api_token, page, keyword, category_id)
                .enqueue(new Callback<PostModel>() {
                    @Override
                    public void onResponse(Call<PostModel> call, Response<PostModel> response) {
                        if (response.body().getPostsResponseData().getPostData().size() == 0) {
                            isDonationEmpty.setValue(true);
                        } else {
                            isDonationEmpty.setValue(false);
                        }
                    }

                    @Override
                    public void onFailure(Call<PostModel> call, Throwable t) {
                        isDonationEmpty.setValue(true);
                    }
                });
        return isDonationEmpty;
    }

    public MutableLiveData<Boolean> isFavoritePostsEmpty(String api_token, int page) {
        ApiClient.getINSTANCE().getAllFavoritePosts(api_token, page)
                .enqueue(new Callback<FavoritePostsModel>() {
                    @Override
                    public void onResponse(Call<FavoritePostsModel> call, Response<FavoritePostsModel> response) {
                        if (response.body().getFavoritePostsResponseData().getData().size() == 0) {
                            isFavoritePostsEmpty.setValue(true);
                        } else {
                            isFavoritePostsEmpty.setValue(false);
                        }
                    }

                    @Override
                    public void onFailure(Call<FavoritePostsModel> call, Throwable t) {
                        isFavoritePostsEmpty.setValue(true);
                    }
                });
        return isFavoritePostsEmpty;
    }

    public MutableLiveData<Boolean> isNotificationEmpty(String api_token, int page) {
        ApiClient.getINSTANCE().getNotifications(api_token, page)
                .enqueue(new Callback<NotificationModel>() {
                    @Override
                    public void onResponse(Call<NotificationModel> call, Response<NotificationModel> response) {
                        if (response.body().getNotificationResponseData().getData().size() == 0) {
                            isNotificationEmpty.setValue(true);
                        } else {
                            isNotificationEmpty.setValue(false);
                        }
                    }

                    @Override
                    public void onFailure(Call<NotificationModel> call, Throwable t) {
                        isNotificationEmpty.setValue(true);
                    }
                });
        return isNotificationEmpty;
    }

    public MutableLiveData<Boolean> isPostsEmpty(String api_token, int page) {
        ApiClient.getINSTANCE().getAllPosts(api_token, page)
                .enqueue(new Callback<PostModel>() {
                    @Override
                    public void onResponse(Call<PostModel> call, Response<PostModel> response) {
                        if (response.body().getPostsResponseData().getPostData().size() == 0) {
                            isPostsEmpty.setValue(true);
                        } else {
                            isPostsEmpty.setValue(false);
                        }
                    }

                    @Override
                    public void onFailure(Call<PostModel> call, Throwable t) {
                        isPostsEmpty.setValue(true);
                    }
                });
        return isPostsEmpty;
    }

    public MutableLiveData<Boolean> isSearchPostsEmpty(String api_token, int page, String keyword, String category_id) {
        ApiClient.getINSTANCE().getAllSearchPosts(api_token, page, keyword, category_id)
                .enqueue(new Callback<PostModel>() {
                    @Override
                    public void onResponse(Call<PostModel> call, Response<PostModel> response) {
                        if (response.body().getPostsResponseData().getPostData().size() == 0) {
                            isSearchPostsEmpty.setValue(true);
                        } else {
                            isSearchPostsEmpty.setValue(false);
                        }
                    }

                    @Override
                    public void onFailure(Call<PostModel> call, Throwable t) {
                        isSearchPostsEmpty.setValue(true);
                    }
                });
        return isSearchPostsEmpty;
    }
}