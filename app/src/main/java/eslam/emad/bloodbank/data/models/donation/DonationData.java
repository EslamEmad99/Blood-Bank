
package eslam.emad.bloodbank.data.models.donation;

import eslam.emad.bloodbank.data.models.bloodType.BloodTypeData;
import eslam.emad.bloodbank.data.models.city.CityData;
import eslam.emad.bloodbank.data.models.login.Client;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DonationData {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("client_id")
    @Expose
    private String clientId;
    @SerializedName("patient_name")
    @Expose
    private String patientName;
    @SerializedName("patient_age")
    @Expose
    private String patientAge;
    @SerializedName("blood_type_id")
    @Expose
    private String bloodTypeId;
    @SerializedName("bags_num")
    @Expose
    private String bagsNum;
    @SerializedName("hospital_name")
    @Expose
    private String hospitalName;
    @SerializedName("hospital_address")
    @Expose
    private String hospitalAddress;
    @SerializedName("city_id")
    @Expose
    private String cityId;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("notes")
    @Expose
    private String notes;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("city")
    @Expose
    private CityData city;
    @SerializedName("client")
    @Expose
    private Client client;
    @SerializedName("blood_type")
    @Expose
    private BloodTypeData bloodType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(String patientAge) {
        this.patientAge = patientAge;
    }

    public String getBloodTypeId() {
        return bloodTypeId;
    }

    public void setBloodTypeId(String bloodTypeId) {
        this.bloodTypeId = bloodTypeId;
    }

    public String getBagsNum() {
        return bagsNum;
    }

    public void setBagsNum(String bagsNum) {
        this.bagsNum = bagsNum;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getHospitalAddress() {
        return hospitalAddress;
    }

    public void setHospitalAddress(String hospitalAddress) {
        this.hospitalAddress = hospitalAddress;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public CityData getCity() {
        return city;
    }

    public void setCity(CityData city) {
        this.city = city;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public BloodTypeData getBloodType() {
        return bloodType;
    }

    public void setBloodType(BloodTypeData bloodType) {
        this.bloodType = bloodType;
    }

}
