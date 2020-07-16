package eslam.emad.bloodbank.ui.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import eslam.emad.bloodbank.data.models.bloodType.BloodTypeModel;
import eslam.emad.bloodbank.data.models.city.CityModel;
import eslam.emad.bloodbank.data.models.createDonation.CreateDonationModel;
import eslam.emad.bloodbank.data.models.governate.GovernateModel;
import eslam.emad.bloodbank.data.models.login.LoginModel;
import eslam.emad.bloodbank.data.models.newPassword.NewPasswordModel;
import eslam.emad.bloodbank.data.models.notificationSettings.NotificationSettingsModel;
import eslam.emad.bloodbank.data.models.register.RegisterModel;
import eslam.emad.bloodbank.data.models.resetpassword.ResetPasswordModel;
import eslam.emad.bloodbank.data.models.setGetFavoritePosts.SetGetFavoritePostsModel;
import eslam.emad.bloodbank.repository.Repository;

public class ApplicationViewModel extends ViewModel {

    private MutableLiveData<LoginModel> loginModel;
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

    private MutableLiveData<Boolean> isDonationDataEmpty;
    private MutableLiveData<Boolean> isFavoritePostsEmpty;
    private MutableLiveData<Boolean> isNotificationEmpty;
    private MutableLiveData<Boolean> isPostsEmpty;
    private MutableLiveData<Boolean> isSearchPostsEmpty;

    public ApplicationViewModel() {
        loginModel = new MutableLiveData<>();
        loginModel = Repository.getINSTANCE().getOnloginModel();

        onResetPassword = new MutableLiveData<>();
        onResetPassword = Repository.getINSTANCE().getOnResetPassword();

        onNewPassword = new MutableLiveData<>();
        onNewPassword = Repository.getINSTANCE().getOnNewPassword();

        bloodType = new MutableLiveData<>();
        bloodType = Repository.getINSTANCE().getBloodType();

        governate = new MutableLiveData<>();
        governate = Repository.getINSTANCE().getGovernate();

        city = new MutableLiveData<>();
        city = Repository.getINSTANCE().getCity();

        onRegister = new MutableLiveData<>();
        onRegister = Repository.getINSTANCE().getOnRegister();

        setGetFavoritePost = new MutableLiveData<>();
        setGetFavoritePost = Repository.getINSTANCE().getSetGetFavoritePost();

        profileData = new MutableLiveData<>();
        profileData = Repository.getINSTANCE().getProfileData();

        editProfileData = new MutableLiveData<>();
        editProfileData = Repository.getINSTANCE().getEditProfileData();

        notificationSettings = new MutableLiveData<>();
        notificationSettings = Repository.getINSTANCE().getNotificationSettings();

        createDonationRequest = new MutableLiveData<>();
        createDonationRequest = Repository.getINSTANCE().getCreateDonationRequest();

        isDonationDataEmpty = new MutableLiveData<>();
        isFavoritePostsEmpty = new MutableLiveData<>();
        isNotificationEmpty = new MutableLiveData<>();
        isPostsEmpty = new MutableLiveData<>();
        isSearchPostsEmpty = new MutableLiveData<>();
    }

    public MutableLiveData<LoginModel> getOnLogin() {
        return loginModel;
    }

    public void setOnLogin(String phone, String password) {
        Repository.getINSTANCE().setOnLogin(phone, password);
    }

    public MutableLiveData<ResetPasswordModel> getOnResetPassword() {
        return onResetPassword;
    }

    public void setOnResetPassword(String phone) {
        Repository.getINSTANCE().setOnResetPassword(phone);
    }

    public MutableLiveData<NewPasswordModel> getOnNewPassword() {
        return onNewPassword;
    }

    public void setOnNewPassword(String password, String passwordConfirmation, Integer pinCode, String phone) {
        Repository.getINSTANCE().setOnNewPassword(password, passwordConfirmation, pinCode, phone);
    }

    public MutableLiveData<BloodTypeModel> getBloodType() {
        return bloodType;
    }

    public void setBloodType() {
        Repository.getINSTANCE().setBloodType();
    }

    public MutableLiveData<GovernateModel> getGovernate() {
        return governate;
    }

    public void setGovernate() {
        Repository.getINSTANCE().setGovernate();
    }

    public MutableLiveData<CityModel> getCity() {
        return city;
    }

    public void setCity(String governorateId) {
        Repository.getINSTANCE().setCity(governorateId);
    }

    public MutableLiveData<RegisterModel> getOnRegister() {
        return onRegister;
    }

    public void setOnRegister(String name, String email, String birth_date, String city_id, String phone, String donation_last_date, String password, String password_confirmation, String blood_type_id) {
        Repository.getINSTANCE().setOnRegister(name, email, birth_date, city_id, phone, donation_last_date, password, password_confirmation, blood_type_id);
    }

    public MutableLiveData<SetGetFavoritePostsModel> getSetGetFavoritePost() {
        return setGetFavoritePost;
    }

    public void setSetGetFavoritePost(int post_id, String api_token) {
        Repository.getINSTANCE().setSetGetFavoritePost(post_id, api_token);
    }

    public MutableLiveData<RegisterModel> getProfileData() {
        return profileData;
    }

    public void setProfileData(String api_token) {
        Repository.getINSTANCE().setProfileData(api_token);
    }

    public MutableLiveData<RegisterModel> getEditProfileData() {
        return editProfileData;
    }

    public void setEditProfileData(String name, String email, String birth_date, String city_id, String phone, String donation_last_date, String password, String password_confirmation, String blood_type_id, String api_token) {
        Repository.getINSTANCE().setEditProfileData(name, email, birth_date, city_id, phone, donation_last_date,password, password_confirmation, blood_type_id,api_token);
    }

    public MutableLiveData<NotificationSettingsModel> getNotificationSettings() {
        return notificationSettings;
    }

    public void setNotificationSettings(String api_token, ArrayList<String> governorates, ArrayList<String> blood_types) {
        Repository.getINSTANCE().setNotificationSettings(api_token, governorates, blood_types);
    }

    public MutableLiveData<CreateDonationModel> getCreateDonationRequest() {
        return createDonationRequest;
    }

    public void setCreateDonationRequest(String api_token, String patient_name, String patient_age, String blood_type_id, String bags_num, String hospital_name, String hospital_address, String city_id, String phone, String notes, String latitude, String longitude) {
        Repository.getINSTANCE().setCreateDonationRequest(api_token, patient_name, patient_age, blood_type_id, bags_num, hospital_name, hospital_address, city_id, phone, notes, latitude, longitude);
    }

    public void setIsDonationDataEmpty(String api_token, int page, String keyword, String category_id){
        isDonationDataEmpty = Repository.getINSTANCE().isDonationDataEmpty(api_token, page, keyword, category_id);
    }

    public MutableLiveData<Boolean> getIsDonationDataEmpty(){
        return isDonationDataEmpty;
    }

    public void setIsFavoritePostsEmpty(String api_token, int page){
        isFavoritePostsEmpty = Repository.getINSTANCE().isFavoritePostsEmpty(api_token, page);
    }

    public MutableLiveData<Boolean> getIsFavoritePostsEmpty(){
        return isFavoritePostsEmpty;
    }

    public void setIsNotificationEmpty(String api_token, int page){
        isNotificationEmpty = Repository.getINSTANCE().isNotificationEmpty(api_token, page);
    }

    public MutableLiveData<Boolean> getIsNotificationEmpty(){
        return isNotificationEmpty;
    }

    public void setIsPostsEmpty(String api_token, int page){
        isPostsEmpty = Repository.getINSTANCE().isPostsEmpty(api_token, page);
    }

    public MutableLiveData<Boolean> getIsPostsEmpty(){
        return isPostsEmpty;
    }

    public void setIsSearchPostsEmpty(String api_token, int page, String keyword, String category_id){
        isSearchPostsEmpty = Repository.getINSTANCE().isSearchPostsEmpty(api_token, page, keyword, category_id);
    }

    public MutableLiveData<Boolean> getIsSearchPostsEmpty(){
        return isSearchPostsEmpty;
    }
}