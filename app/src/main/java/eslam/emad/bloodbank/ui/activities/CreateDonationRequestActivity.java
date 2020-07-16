package eslam.emad.bloodbank.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.emad.bloodbank.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import eslam.emad.bloodbank.data.Constants;
import eslam.emad.bloodbank.data.api.ApiClient;
import eslam.emad.bloodbank.data.models.bloodType.BloodTypeData;
import eslam.emad.bloodbank.data.models.bloodType.BloodTypeModel;
import eslam.emad.bloodbank.data.models.city.CityData;
import eslam.emad.bloodbank.data.models.city.CityModel;
import eslam.emad.bloodbank.data.models.createDonation.CreateDonationModel;
import eslam.emad.bloodbank.data.models.governate.GovernateData;
import eslam.emad.bloodbank.data.models.governate.GovernateModel;
import eslam.emad.bloodbank.ui.viewModels.ApplicationViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateDonationRequestActivity extends AppCompatActivity {

    @BindView(R.id.donation_activity_name_edit_text)
    EditText donationActivityNameEditText;
    @BindView(R.id.donation_activity_birth_edit_text)
    EditText donationActivityBirthEditText;
    @BindView(R.id.donation_activity_blood_type_spinner)
    Spinner donationActivityBloodTypeSpinner;
    @BindView(R.id.donation_activity_bags_edit_text)
    EditText donationActivityBagsEditText;
    @BindView(R.id.donation_activity_hospital_name_edit_text)
    EditText donationActivityHospitalNameEditText;
    @BindView(R.id.donation_activity_address_text_view)
    TextView donationActivityAddressTextView;
    @BindView(R.id.donation_activity_governarate_spinner)
    Spinner donationActivityGovernarateSpinner;
    @BindView(R.id.donation_activity_city_spinner)
    Spinner donationActivityCitySpinner;
    @BindView(R.id.donation_activity_phone_number_edit_text)
    EditText donationActivityPhoneNumberEditText;
    @BindView(R.id.donation_activity_notes_edit_text)
    EditText donationActivityNotesEditText;
    @BindView(R.id.donation_activity_enter_btn)
    Button donationActivityEnterBtn;
    public static final int CREATE_DONATION_REQUEST_ACTIVITY_REQUEST_CODE = 149;
    private ArrayList<BloodTypeData> bloodTypesList;
    private ArrayList<GovernateData> governatesList;
    private ArrayList<CityData> citysList;
    private String apiToken, name, age, bloodTypeId, bagsNum, hospitalName, hospitalAddress, cityId, phone, notes, latitude, longitude;
    ApplicationViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_donation_request);
        ButterKnife.bind(this);
        setTitle("Create donation request");

        viewModel = new ViewModelProvider(this).get(ApplicationViewModel.class);

        bloodTypesList = new ArrayList<>();
        governatesList = new ArrayList<>();
        citysList = new ArrayList<>();
        bloodTypesList.add(new BloodTypeData(0, "فصيلة الدم"));
        governatesList.add(new GovernateData(0, "المحافظة"));
        citysList.add(new CityData(0, "اختر المحافظة اولا"));

        viewModel.setBloodType();
        viewModel.getBloodType().observe(this, new Observer<BloodTypeModel>() {
            @Override
            public void onChanged(BloodTypeModel bloodTypeModel) {
                if (bloodTypeModel.getStatus() == 1) {
                    bloodTypesList.addAll(bloodTypeModel.getData());
                }
            }
        });

        viewModel.setGovernate();
        viewModel.getGovernate().observe(this, new Observer<GovernateModel>() {
            @Override
            public void onChanged(GovernateModel governateModel) {
                if (governateModel.getStatus() == 1) {
                    governatesList.addAll(governateModel.getData());
                    Toast.makeText(CreateDonationRequestActivity.this, "Toast", Toast.LENGTH_SHORT).show();
                }
            }
        });

        final ArrayAdapter<BloodTypeData> bloodTypeAdapter = new ArrayAdapter<>(this, R.layout.custom_spinner_layout, bloodTypesList);
        bloodTypeAdapter.setDropDownViewResource(R.layout.custom_dropdown_list);
        donationActivityBloodTypeSpinner.setAdapter(bloodTypeAdapter);

        final ArrayAdapter<GovernateData> governateAdapter = new ArrayAdapter<>(this, R.layout.custom_spinner_layout, governatesList);
        governateAdapter.setDropDownViewResource(R.layout.custom_dropdown_list);
        donationActivityGovernarateSpinner.setAdapter(governateAdapter);

        final ArrayAdapter<CityData> cityAdapter = new ArrayAdapter<>(this, R.layout.custom_spinner_layout, citysList);
        cityAdapter.setDropDownViewResource(R.layout.custom_dropdown_list);
        donationActivityCitySpinner.setAdapter(cityAdapter);


        donationActivityBloodTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if (id != 0) {
                    bloodTypeId = String.valueOf(id);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        donationActivityGovernarateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if (id != 0) {
                    String x = String.valueOf(id);
                    viewModel.setCity(x);
                    viewModel.getCity().observe(CreateDonationRequestActivity.this, new Observer<CityModel>() {
                        @Override
                        public void onChanged(CityModel cityModel) {
                            if (cityModel.getStatus() == 1) {
                                citysList.clear();
                                citysList.add(new CityData(0, "المدينة"));
                                citysList.addAll(cityModel.getData());
                                cityAdapter.notifyDataSetChanged();
                            }
                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        donationActivityCitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if (id != 0) {
                    cityId = String.valueOf(id);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @OnClick(R.id.donation_activity_address_text_view)
    public void onDonationActivityAddressTextViewClicked() {
        Intent intent = new Intent(CreateDonationRequestActivity.this, MapActivity.class);
        startActivityForResult(intent, CREATE_DONATION_REQUEST_ACTIVITY_REQUEST_CODE);
    }

    @OnClick(R.id.donation_activity_enter_btn)
    public void onDonationActivityEnterBtnClicked() {
        apiToken = Constants.API_TOKEN;
        name = donationActivityNameEditText.getText().toString().trim();
        age = donationActivityBirthEditText.getText().toString().trim();
        bagsNum = donationActivityBagsEditText.getText().toString().trim();
        hospitalName = donationActivityHospitalNameEditText.getText().toString().trim();
        hospitalAddress = donationActivityAddressTextView.getText().toString().trim();
        phone = donationActivityPhoneNumberEditText.getText().toString().trim();
        notes = donationActivityNotesEditText.getText().toString().trim();

        if (!(name.trim().length() >= 1)) {
            Toast.makeText(this, "Enter a valid name", Toast.LENGTH_SHORT).show();
        } else if (name.length() >= 50) {
            Toast.makeText(this, "Enter a short name", Toast.LENGTH_SHORT).show();
        } else if (age.equals("")) {
            Toast.makeText(this, "Enter your age", Toast.LENGTH_SHORT).show();
        } else if (Integer.parseInt(age) < 0 || Integer.parseInt(age) > 100 || age.startsWith("0")) {
            Toast.makeText(this, "Enter a valid age", Toast.LENGTH_SHORT).show();
        } else if (bloodTypeId == null) {
            Toast.makeText(this, "Enter you blood type", Toast.LENGTH_SHORT).show();
        } else if (bagsNum.equals("")) {
            Toast.makeText(this, "Enter the required blood bags", Toast.LENGTH_SHORT).show();
        } else if (Integer.parseInt(bagsNum) < 0 || bagsNum.startsWith("0")) {
            Toast.makeText(this, "Enter a valid required blood bags", Toast.LENGTH_SHORT).show();
        } else if (hospitalName.equals("")) {
            Toast.makeText(this, "Enter the hospital name", Toast.LENGTH_SHORT).show();
        } else if (hospitalAddress.equals("")) {
            Toast.makeText(this, "Enter the hospital address", Toast.LENGTH_SHORT).show();
        } else if (cityId == null) {
            Toast.makeText(this, "Choose city", Toast.LENGTH_SHORT).show();
        } else if (phone.length() != 11 || !phone.startsWith("01")) {
            Toast.makeText(this, "Wrong phone number", Toast.LENGTH_SHORT).show();
        } else {
            if (notes.equals("")) {
                notes = "No Notes";
            }

            viewModel.setCreateDonationRequest(apiToken, name, age, bloodTypeId, bagsNum, hospitalName, hospitalAddress, cityId, phone, notes, latitude, longitude);
            viewModel.getCreateDonationRequest().observe(this, new Observer<CreateDonationModel>() {
                @Override
                public void onChanged(CreateDonationModel createDonationModel) {
                    Toast.makeText(CreateDonationRequestActivity.this, createDonationModel.getMsg(), Toast.LENGTH_SHORT).show();
                    if (createDonationModel.getStatus() == 1) {
                        goToHomeActivity();
                    }
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CREATE_DONATION_REQUEST_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            hospitalAddress = data.getStringExtra("address");
            donationActivityAddressTextView.setText(hospitalAddress);
            latitude = data.getStringExtra("latitude");
            longitude = data.getStringExtra("longitude");
        }
    }

    private void goToHomeActivity() {
        Intent intent = new Intent(CreateDonationRequestActivity.this, HomeActivity.class);
        startActivity(intent);
    }
}