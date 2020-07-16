package eslam.emad.bloodbank.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.emad.bloodbank.R;

import eslam.emad.bloodbank.adapters.DonationsAdapter;
import eslam.emad.bloodbank.data.Constants;
import eslam.emad.bloodbank.data.api.ApiClient;
import eslam.emad.bloodbank.data.models.bloodType.BloodTypeData;
import eslam.emad.bloodbank.data.models.bloodType.BloodTypeModel;
import eslam.emad.bloodbank.data.models.city.CityData;
import eslam.emad.bloodbank.data.models.donation.DonationData;
import eslam.emad.bloodbank.data.models.governate.GovernateData;
import eslam.emad.bloodbank.data.models.governate.GovernateModel;
import eslam.emad.bloodbank.ui.activities.CreateDonationRequestActivity;
import eslam.emad.bloodbank.ui.activities.PostAndDonationInformationActivity;
import eslam.emad.bloodbank.ui.viewModels.ApplicationViewModel;
import eslam.emad.bloodbank.ui.viewModels.DonationViewModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DonationRequestFragment extends Fragment {

    private View view;
    @BindView(R.id.fragment_donation_blood_type_spinner)
    Spinner bloodTypeSpinner;
    @BindView(R.id.fragment_donation_governate_spinner)
    Spinner governateSpinner;
    @BindView(R.id.fragment_donation_search_btn)
    Button searchBtn;
    @BindView(R.id.fragment_donation_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.fragment_donation_swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private ArrayList<BloodTypeData> bloodTypesList;
    private ArrayList<GovernateData> governateList;
    private String bloodtypeString;
    private String governateString;
    private DonationsAdapter donationsAdapter;
    private DonationViewModel donationViewModel;
    private ApplicationViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_donation_request, container, false);
        ButterKnife.bind(this, view);

        donationViewModel = new ViewModelProvider(this).get(DonationViewModel.class);
        viewModel = new ViewModelProvider(this).get(ApplicationViewModel.class);

        setDataInBloodTypeSpinner();
        setDataInGovernateSpinner();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.requestFocus();
        //mRecyclerView.setNestedScrollingEnabled(true);

        donationsAdapter = new DonationsAdapter(getContext());

        donationViewModel.getDonationPagedList().observe(getViewLifecycleOwner(), new Observer<PagedList<DonationData>>() {
            @Override
            public void onChanged(PagedList<DonationData> donationData) {
                donationsAdapter.submitList(donationData);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        mRecyclerView.setAdapter(donationsAdapter);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Constants.BLOOD_TYPE_ID = "0";
                Constants.GOVERNATE_ID = "0";
                donationViewModel.refresh();
                bloodTypeSpinner.setSelection(0);
                governateSpinner.setSelection(0);
            }
        });

        donationsAdapter.setOnItemClickListener(new DonationsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DonationData donationData, int position) {
                String name = donationData.getClient().getName();
                String age = donationData.getPatientAge();
                String phone = donationData.getPhone();
                String hospital = donationData.getHospitalName();
                String blood = donationData.getBloodType().getName();
                String latitude = donationData.getLatitude();
                String longitude = donationData.getLongitude();

                Intent intent = new Intent(getActivity(), PostAndDonationInformationActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("age", age);
                intent.putExtra("phone", phone);
                intent.putExtra("hospital", hospital);
                intent.putExtra("blood", blood);
                intent.putExtra("latitude", latitude);
                intent.putExtra("longitude", longitude);
                startActivity(intent);
            }
        });

        bloodTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (id != 0) {
                    bloodtypeString = String.valueOf(((BloodTypeData) parent.getSelectedItem()).getId());
                } else {
                    bloodtypeString = String.valueOf(id);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        governateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (id != 0) {
                    governateString = String.valueOf(((GovernateData) parent.getSelectedItem()).getId());
                } else {
                    governateString = String.valueOf(id);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return view;
    }

    private void setDataInBloodTypeSpinner() {
        bloodTypesList = new ArrayList<>();
        bloodTypesList.add(new BloodTypeData(0, "فصيلة الدم"));
        viewModel.setBloodType();
        viewModel.getBloodType().observe(getViewLifecycleOwner(), new Observer<BloodTypeModel>() {
            @Override
            public void onChanged(BloodTypeModel bloodTypeModel) {
                if (bloodTypeModel != null) {
                    if (bloodTypeModel.getStatus() == 1) {
                        bloodTypesList.addAll(bloodTypeModel.getData());
                    }
                }
            }
        });
//        ApiClient.getINSTANCE().getBloodType()
//                .enqueue(new Callback<BloodTypeModel>() {
//                    @Override
//                    public void onResponse(Call<BloodTypeModel> call, Response<BloodTypeModel> response) {
//                        if (response.body().getStatus() == 1) {
//                            bloodTypesList.addAll(response.body().getData());
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<BloodTypeModel> call, Throwable t) {
//
//                    }
//                });
        ArrayAdapter<BloodTypeData> bloodTypeAdapter = new ArrayAdapter<>(getContext(), R.layout.custom_spinner_layout, bloodTypesList);
        bloodTypeAdapter.setDropDownViewResource(R.layout.custom_dropdown_list);
        bloodTypeSpinner.setAdapter(bloodTypeAdapter);
    }

    private void setDataInGovernateSpinner() {
        governateList = new ArrayList<>();
        governateList.add(new GovernateData(0, "المحافظة"));
        viewModel.setGovernate();
        viewModel.getGovernate().observe(getViewLifecycleOwner(), new Observer<GovernateModel>() {
            @Override
            public void onChanged(GovernateModel governateModel) {
                if (governateModel != null) {
                    if (governateModel.getStatus() == 1) {
                        governateList.addAll(governateModel.getData());
                    }
                }
            }
        });
//        ApiClient.getINSTANCE().getGovernate()
//                .enqueue(new Callback<GovernateModel>() {
//                    @Override
//                    public void onResponse(Call<GovernateModel> call, Response<GovernateModel> response) {
//                        if (response.body().getStatus() == 1) {
//                            governateList.addAll(response.body().getData());
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<GovernateModel> call, Throwable t) {
//
//                    }
//                });

        ArrayAdapter<GovernateData> governateAdapter = new ArrayAdapter<>(getContext(), R.layout.custom_spinner_layout, governateList);
        governateAdapter.setDropDownViewResource(R.layout.custom_dropdown_list);
        governateSpinner.setAdapter(governateAdapter);
    }

    @OnClick(R.id.fragment_donation_search_btn)
    public void onFragmentDonationSearchBtnClicked() {
        if (bloodtypeString.equals("0") && governateString.equals("0")) {
            Toast.makeText(getContext(), "Please choose Blood Type and Governate first", Toast.LENGTH_SHORT).show();
        } else {
            mSwipeRefreshLayout.setRefreshing(true);
            Constants.BLOOD_TYPE_ID = bloodtypeString;
            Constants.GOVERNATE_ID = governateString;
            donationViewModel.refresh();
        }
    }

    @OnClick(R.id.donation_fragment_button_add_post)
    public void onDonationFragmentButtonAddPostClicked() {
        Intent intent = new Intent(getActivity(), CreateDonationRequestActivity.class);
        startActivity(intent);
    }
}
