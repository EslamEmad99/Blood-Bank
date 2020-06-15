package eslam.emad.bloodbank.ui.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.emad.bloodbank.R;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import eslam.emad.bloodbank.data.Constants;
import eslam.emad.bloodbank.data.api.ApiClient;
import eslam.emad.bloodbank.data.models.bloodType.BloodTypeModel;
import eslam.emad.bloodbank.data.models.city.CityModel;
import eslam.emad.bloodbank.data.models.governate.GovernateModel;
import eslam.emad.bloodbank.data.models.register.RegisterModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileFragment extends Fragment {
    View view;
    @BindView(R.id.fragment_profile_name_edit_text)
    EditText nameEditText;
    @BindView(R.id.fragment_profile_email_edit_text)
    EditText emailEditText;
    @BindView(R.id.fragment_profile_birth_edit_text)
    TextView birthEditText;
    @BindView(R.id.fragment_profile_blood_type_spinner)
    Spinner bloodTypeSpinner;
    @BindView(R.id.fragment_register_last_donation_edit_text)
    TextView lastDonationEditText;
    @BindView(R.id.fragment_profile_governarate_spinner)
    Spinner governarateSpinner;
    @BindView(R.id.fragment_profile_city_spinner)
    Spinner citySpinner;
    @BindView(R.id.fragment_profile_phone_number_edit_text)
    EditText phoneNumberEditText;
    @BindView(R.id.fragment_profile_password_edit_text)
    EditText passwordEditText;
    @BindView(R.id.fragment_profile_password_again_edit_text)
    EditText passwordAgainEditText;
    @BindView(R.id.fragment_profile_enter_btn)
    Button enterBtn;
    private ArrayList<String> bloodTypesList;
    private ArrayList<String> governatesList;
    private ArrayList<String> citysList;
    private Dialog dialog;
    private CalendarView calendarView;
    private String name, email, birthDate, cityId, governateId, phone, donationLastDate, password, passwordConfirmation, bloodTypeId, apiToken;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        dialog = new Dialog(getContext());

        bloodTypesList = new ArrayList<>();
        governatesList = new ArrayList<>();
        citysList = new ArrayList<>();
        bloodTypesList.add("فصيلة الدم");
        governatesList.add("المحافظة");
        citysList.add("اختر المحافظة اولا");

        ApiClient.getINSTANCE().getBloodType().enqueue(new Callback<BloodTypeModel>() {
            @Override
            public void onResponse(Call<BloodTypeModel> call, Response<BloodTypeModel> response) {
                if (response.body().getStatus() == 1) {
                    for (int i = 0; i < response.body().getData().size(); i++) {
                        bloodTypesList.add(response.body().getData().get(i).getName());
                    }
                }
            }

            @Override
            public void onFailure(Call<BloodTypeModel> call, Throwable t) {

            }
        });


        ApiClient.getINSTANCE().getGovernate().enqueue(new Callback<GovernateModel>() {
            @Override
            public void onResponse(Call<GovernateModel> call, Response<GovernateModel> response) {
                if (response.body().getStatus() == 1) {
                    for (int i = 0; i < response.body().getData().size(); i++) {
                        governatesList.add(response.body().getData().get(i).getName());
                    }
                }
            }

            @Override
            public void onFailure(Call<GovernateModel> call, Throwable t) {

            }
        });

        final ArrayAdapter<String> bloodTypeAdapter = new ArrayAdapter<String>(getContext(), R.layout.custom_spinner_layout, bloodTypesList);
        bloodTypeAdapter.setDropDownViewResource(R.layout.custom_dropdown_list);
        bloodTypeSpinner.setAdapter(bloodTypeAdapter);

        final ArrayAdapter<String> governateAdapter = new ArrayAdapter<String>(getContext(), R.layout.custom_spinner_layout, governatesList);
        governateAdapter.setDropDownViewResource(R.layout.custom_dropdown_list);
        governarateSpinner.setAdapter(governateAdapter);

        final ArrayAdapter<String> cityAdapter = new ArrayAdapter<String>(getContext(), R.layout.custom_spinner_layout, citysList);
        cityAdapter.setDropDownViewResource(R.layout.custom_dropdown_list);
        citySpinner.setAdapter(cityAdapter);

        ApiClient.getINSTANCE().getProfileData(Constants.API_TOKEN).enqueue(new Callback<RegisterModel>() {
            @Override
            public void onResponse(Call<RegisterModel> call, Response<RegisterModel> response) {
                if (response.body() != null) {

                    name = response.body().getRegisterData().getClient().getName();
                    nameEditText.setText(name);

                    email = response.body().getRegisterData().getClient().getEmail();
                    emailEditText.setText(email);

                    birthDate = response.body().getRegisterData().getClient().getBirthDate();
                    String z = getResources().getString(R.string.birthday) + " " + birthDate;
                    birthEditText.setText(z);

                    bloodTypeId = response.body().getRegisterData().getClient().getBloodTypeId();
                    if (bloodTypesList.size() > 1) {
                        bloodTypeSpinner.setSelection(Integer.parseInt(bloodTypeId));
                    }

                    donationLastDate = response.body().getRegisterData().getClient().getDonationLastDate();
                    lastDonationEditText.setText(getResources().getString(R.string.last_donation_date) + " " + donationLastDate);

                    governateId = response.body().getRegisterData().getClient().getCity().getGovernorateId();
                    if (governatesList.size() > 1 && governateId != null) {
                        governarateSpinner.setSelection(Integer.parseInt(governateId));
                    }
                    if (governateId != null){
                        ApiClient.getINSTANCE().getCity(governateId).enqueue(new Callback<CityModel>() {
                            @Override
                            public void onResponse(Call<CityModel> call, Response<CityModel> response) {
                                if (response.body().getStatus() == 1) {
                                    citysList.clear();
                                    citysList.add("المدينة");
                                    for (int i = 0; i < response.body().getData().size(); i++) {
                                        citysList.add(response.body().getData().get(i).getName());
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<CityModel> call, Throwable t) {

                            }
                        });

                    }

                    phone = response.body().getRegisterData().getClient().getPhone();
                    phoneNumberEditText.setText(phone);
                }
            }

            @Override
            public void onFailure(Call<RegisterModel> call, Throwable t) {

            }
        });

        bloodTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if (id > 0) {
                    bloodTypeId = String.valueOf(id);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        governarateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if (id != 0) {
                    String x = String.valueOf(id);
                    ApiClient.getINSTANCE().getCity(x).enqueue(new Callback<CityModel>() {
                        @Override
                        public void onResponse(Call<CityModel> call, Response<CityModel> response) {
                            if (response.body().getStatus() == 1) {
                                citysList.clear();
                                citysList.add("المدينة");
                                for (int i = 0; i < response.body().getData().size(); i++) {
                                    citysList.add(response.body().getData().get(i).getName());
                                }
                                cityAdapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onFailure(Call<CityModel> call, Throwable t) {

                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                int mm = month + 1;
                String m = String.format("%02d", mm);
                String d = String.format("%02d", day);
                birthDate = year + "-" + m + "-" + d;
                String x = " التاريخ : " + birthDate;
                birthEditText.setText(x);
            }
        };

        return view;
    }

    @OnClick(R.id.fragment_profile_enter_btn)
    public void fragmentProfileEnterBtnClicked() {
        name = nameEditText.getText().toString().trim();
        email = emailEditText.getText().toString().trim();
        phone = phoneNumberEditText.getText().toString().trim();
        password = passwordEditText.getText().toString();
        passwordConfirmation = passwordAgainEditText.getText().toString();
        apiToken = Constants.API_TOKEN;

        if (!(name.trim().length() >= 1)) {
            Toast.makeText(getContext(), "Enter a valid name", Toast.LENGTH_SHORT).show();
        } else if (name.length() >= 50) {
            Toast.makeText(getContext(), "Enter a short name", Toast.LENGTH_SHORT).show();
        } else if (!(email.contains("@")) || !(email.contains(".com"))) {
            Toast.makeText(getContext(), "invalid email", Toast.LENGTH_SHORT).show();
        } else if (birthDate == null) {
            Toast.makeText(getContext(), "Enter your birthday", Toast.LENGTH_SHORT).show();
        } else if (bloodTypeId == null) {
            Toast.makeText(getContext(), "Enter you blood type", Toast.LENGTH_SHORT).show();
        } else if (donationLastDate == null) {
            Toast.makeText(getContext(), "Enter the last day you donated", Toast.LENGTH_SHORT).show();
        } else if (cityId == null) {
            Toast.makeText(getContext(), "Choose city", Toast.LENGTH_SHORT).show();
        } else if (phone.length() != 11 || !phone.startsWith("01")) {
            Toast.makeText(getContext(), "Wrong phone number", Toast.LENGTH_SHORT).show();
        } else if (!password.equals(passwordConfirmation)) {
            Toast.makeText(getContext(), "Not the Same password", Toast.LENGTH_SHORT).show();
        } else if (password.length() < 4) {
            Toast.makeText(getContext(), "Password must be bigger than 4 digits", Toast.LENGTH_SHORT).show();
        } else {
            ApiClient.getINSTANCE().editProfileData(name, email, birthDate, cityId, phone, donationLastDate, password, passwordConfirmation, bloodTypeId, apiToken)
                    .enqueue(new Callback<RegisterModel>() {
                        @Override
                        public void onResponse(Call<RegisterModel> call, Response<RegisterModel> response) {
                            Toast.makeText(getContext(), response.body().getMsg(), Toast.LENGTH_LONG).show();
                            if (response.body().getStatus() == 1) {
                                goToMoreFragment(response.body().getMsg());
                            }
                        }

                        @Override
                        public void onFailure(Call<RegisterModel> call, Throwable t) {
                            Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    @OnClick(R.id.fragment_profile_birth_edit_text)
    public void onFragmentProfileBirthEditTextClicked() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                getContext(),
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                mDateSetListener,
                year, month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    @OnClick(R.id.fragment_register_last_donation_edit_text)
    public void onFragmentRegisterLastDonationEditTextClicked() {
        dialog.setContentView(R.layout.calendar_custom_dialog);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        calendarView = dialog.findViewById(R.id.calendar_view);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                int mm = month + 1;
                String m = String.format("%02d", mm);
                String d = String.format("%02d", day);
                donationLastDate = year + "-" + m + "-" + d;
                String x = " أخر تاريخ تبرع : " + donationLastDate;
                lastDonationEditText.setText(x);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @OnClick(R.id.fragment_profile_back_btn)
    public void onViewClicked() {
        goToMoreFragment("Data haven't updated");
    }

    void goToMoreFragment(String txt) {
        Toast.makeText(getContext(), txt, Toast.LENGTH_SHORT).show();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_more_container,
                new MoreScreen()).addToBackStack(null).commit();
    }
}
