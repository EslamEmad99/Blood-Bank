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
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.emad.bloodbank.R;

import eslam.emad.bloodbank.data.api.ApiClient;
import eslam.emad.bloodbank.data.models.bloodType.BloodTypeData;
import eslam.emad.bloodbank.data.models.bloodType.BloodTypeModel;
import eslam.emad.bloodbank.data.models.city.CityData;
import eslam.emad.bloodbank.data.models.city.CityModel;
import eslam.emad.bloodbank.data.models.governate.GovernateData;
import eslam.emad.bloodbank.data.models.governate.GovernateModel;
import eslam.emad.bloodbank.data.models.register.RegisterModel;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import eslam.emad.bloodbank.ui.viewModels.ApplicationViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterFragment extends Fragment {
    View view;
    @BindView(R.id.fragment_register_name_edit_text)
    EditText fragmentRegisterNameEditText;
    @BindView(R.id.fragment_register_email_edit_text)
    EditText fragmentRegisterEmailEditText;
    @BindView(R.id.fragment_register_birth_edit_text)
    TextView fragmentRegisterBirthEditText;
    @BindView(R.id.fragment_register_blood_type_spinner)
    Spinner fragmentRegisterBloodTypeSpinner;
    @BindView(R.id.fragment_register_last_donation_edit_text)
    TextView fragmentRegisterLastDonationEditText;
    @BindView(R.id.fragment_register_goernarate_spinner)
    Spinner fragmentRegisterGoernarateSpinner;
    @BindView(R.id.fragment_register_city_spinner)
    Spinner fragmentRegisterCitySpinner;
    @BindView(R.id.fragment_register_phone_number_edit_text)
    EditText fragmentRegisterPhoneNumberEditText;
    @BindView(R.id.fragment_register_password_edit_text)
    EditText fragmentRegisterPasswordEditText;
    @BindView(R.id.fragment_register_password_again_edit_text)
    EditText fragmentRegisterPasswordAgainEditText;
    private ArrayList<BloodTypeData> bloodTypesList;
    private ArrayList<GovernateData> governatesList;
    private ArrayList<CityData> citysList;
    private Dialog dialog;
    private CalendarView calendarView;
    private String name, email, birthDate, cityId, phone, donationLastDate, password, passwordConfirmation, bloodTypeId;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private ApplicationViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_register, container, false);
        ButterKnife.bind(this, view);
        viewModel = new ViewModelProvider(this).get(ApplicationViewModel.class);
        viewModel.getOnRegister().observe(getViewLifecycleOwner(), new Observer<RegisterModel>() {
            @Override
            public void onChanged(RegisterModel registerModel) {
                if (registerModel != null) {
                    Toast.makeText(getContext(), registerModel.getMsg(), Toast.LENGTH_LONG).show();
                    if (registerModel.getStatus() == 1) {
                        goToLoginFragment();
                    }
                }
            }
        });
        dialog = new Dialog(getContext());

        bloodTypesList = new ArrayList<>();
        governatesList = new ArrayList<>();
        citysList = new ArrayList<>();
        bloodTypesList.add(new BloodTypeData(0, "فصيلة الدم"));
        governatesList.add(new GovernateData(0, "المحافظة"));
        citysList.add(new CityData(0, "اختر المحافظة اولا"));

        viewModel.setBloodType();
        viewModel.getBloodType().observe(getViewLifecycleOwner(), new Observer<BloodTypeModel>() {
            @Override
            public void onChanged(BloodTypeModel bloodTypeModel) {
                if (bloodTypeModel.getStatus() == 1) {
                    bloodTypesList.addAll(bloodTypeModel.getData());
                }
            }
        });
        viewModel.setGovernate();
        viewModel.getGovernate().observe(getViewLifecycleOwner(), new Observer<GovernateModel>() {
            @Override
            public void onChanged(GovernateModel governateModel) {
                if (governateModel.getStatus() == 1) {
                    governatesList.addAll(governateModel.getData());
                }
            }
        });

        final ArrayAdapter<BloodTypeData> bloodTypeAdapter = new ArrayAdapter<>(getContext(), R.layout.custom_spinner_layout, bloodTypesList);
        bloodTypeAdapter.setDropDownViewResource(R.layout.custom_dropdown_list);
        fragmentRegisterBloodTypeSpinner.setAdapter(bloodTypeAdapter);

        final ArrayAdapter<GovernateData> governateAdapter = new ArrayAdapter<>(getContext(), R.layout.custom_spinner_layout, governatesList);
        governateAdapter.setDropDownViewResource(R.layout.custom_dropdown_list);
        fragmentRegisterGoernarateSpinner.setAdapter(governateAdapter);

        final ArrayAdapter<CityData> cityAdapter = new ArrayAdapter<>(getContext(), R.layout.custom_spinner_layout, citysList);
        cityAdapter.setDropDownViewResource(R.layout.custom_dropdown_list);
        fragmentRegisterCitySpinner.setAdapter(cityAdapter);


        fragmentRegisterBloodTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if (id != 0) {
                    bloodTypeId = String.valueOf(((BloodTypeData) adapterView.getSelectedItem()).getId());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        fragmentRegisterGoernarateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if (id != 0) {
                    String x = String.valueOf(((GovernateData) adapterView.getSelectedItem()).getId());

                    viewModel.setCity(x);
                    viewModel.getCity().observe(getViewLifecycleOwner(), new Observer<CityModel>() {
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

        fragmentRegisterCitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if (id != 0) {
                    cityId = String.valueOf(((CityData) adapterView.getSelectedItem()).getId());
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
                String m;
                String d;
                if (mm <= 9) {
                    m = "0" + mm;
                } else {
                    m = String.valueOf(mm);
                }
                if (day <= 9) {
                    d = "0" + day;
                } else {
                    d = String.valueOf(day);
                }
                birthDate = year + "-" + m + "-" + d;
                String x = " التاريخ : " + birthDate;
                fragmentRegisterBirthEditText.setText(x);
            }
        };
        return view;
    }

    @OnClick(R.id.fragment_register_birth_edit_text)
    public void onFragmentRegisterBirthEditTextClicked() {

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
                String m;
                String d;
                if (mm <= 9) {
                    m = "0" + mm;
                } else {
                    m = String.valueOf(mm);
                }
                if (day <= 9) {
                    d = "0" + day;
                } else {
                    d = String.valueOf(day);
                }
                donationLastDate = year + "-" + m + "-" + d;
                String x = " أخر تاريخ تبرع : " + donationLastDate;
                fragmentRegisterLastDonationEditText.setText(x);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @OnClick(R.id.fragment_register_enter_btn)
    public void onFragmentRegisterEnterBtnClicked() {
        name = fragmentRegisterNameEditText.getText().toString().trim();
        email = fragmentRegisterEmailEditText.getText().toString().trim();
        phone = fragmentRegisterPhoneNumberEditText.getText().toString().trim();
        password = fragmentRegisterPasswordEditText.getText().toString();
        passwordConfirmation = fragmentRegisterPasswordAgainEditText.getText().toString();

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
            viewModel.setOnRegister(name, email, birthDate, cityId, phone, donationLastDate, password, passwordConfirmation, bloodTypeId);
        }
    }

    public void goToLoginFragment() {
        LoginFragment loginFragment = new LoginFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.activity_login_fragment_container,
                loginFragment).addToBackStack(null).commit();
    }
}