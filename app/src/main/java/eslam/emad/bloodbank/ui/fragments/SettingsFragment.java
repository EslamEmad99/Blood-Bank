package eslam.emad.bloodbank.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.emad.bloodbank.R;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;
import eslam.emad.bloodbank.adapters.CheckBoxAdapter;
import eslam.emad.bloodbank.data.Constants;
import eslam.emad.bloodbank.data.api.ApiClient;
import eslam.emad.bloodbank.data.models.ExampleCheckBox;
import eslam.emad.bloodbank.data.models.bloodType.BloodTypeModel;
import eslam.emad.bloodbank.data.models.governate.GovernateModel;
import eslam.emad.bloodbank.data.models.notificationSettings.NotificationSettingsModel;
import eslam.emad.bloodbank.ui.viewModels.ApplicationViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsFragment extends Fragment {

    View view;
    ArrayList<String> bloodTypeArray = new ArrayList<>();
    ArrayList<String> governateArray = new ArrayList<>();
    private ApplicationViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, view);
        viewModel = new ViewModelProvider(this).get(ApplicationViewModel.class);

        createBloodTypeRecyclerView();
        createGovernateRecyclerView();
        return view;
    }

    private void createBloodTypeRecyclerView() {
        ArrayList<ExampleCheckBox> bloodTypeCheckBoxArrayList = new ArrayList<>();
        CheckBoxAdapter checkBoxAdapter = new CheckBoxAdapter(bloodTypeCheckBoxArrayList);

        viewModel.setBloodType();
        viewModel.getBloodType().observe(getViewLifecycleOwner(), new Observer<BloodTypeModel>() {
            @Override
            public void onChanged(BloodTypeModel bloodTypeModel) {
                if (bloodTypeModel.getStatus() == 1) {
                    for (int i = 0; i < bloodTypeModel.getData().size(); i++) {
                        bloodTypeCheckBoxArrayList.add(new ExampleCheckBox(bloodTypeModel.getData().get(i).getName(), bloodTypeModel.getData().get(i).getId()));
                        checkBoxAdapter.notifyDataSetChanged();
                    }
                }
            }
        });
//        ApiClient.getINSTANCE().getBloodType().enqueue(new Callback<BloodTypeModel>() {
//            @Override
//            public void onResponse(Call<BloodTypeModel> call, Response<BloodTypeModel> response) {
//                if (response.body().getStatus() == 1) {
//                    for (int i = 0; i < response.body().getData().size(); i++) {
//                        bloodTypeCheckBoxArrayList.add(new ExampleCheckBox(response.body().getData().get(i).getName(), response.body().getData().get(i).getId()));
//                        checkBoxAdapter.notifyDataSetChanged();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<BloodTypeModel> call, Throwable t) {
//
//            }
//        });

        RecyclerView bloodTypeRecyclerView = view.findViewById(R.id.fragment_settings_blood_type_recycler_view);
        bloodTypeRecyclerView.setHasFixedSize(true); //important
        bloodTypeRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        bloodTypeRecyclerView.setAdapter(checkBoxAdapter);

        checkBoxAdapter.setOnItemClickListener(new CheckBoxAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ExampleCheckBox exampleCheckBox, boolean isChecked, int position) {
                if (isChecked) {
                    bloodTypeArray.add(String.valueOf(exampleCheckBox.getId()));
                } else {
                    bloodTypeArray.remove(String.valueOf(exampleCheckBox.getId()));
                }
            }
        });
    }

    private void createGovernateRecyclerView() {
        ArrayList<ExampleCheckBox> goernateCheckBoxArrayList = new ArrayList<>();
        CheckBoxAdapter checkBoxAdapter = new CheckBoxAdapter(goernateCheckBoxArrayList);

        viewModel.setGovernate();
        viewModel.getGovernate().observe(getViewLifecycleOwner(), new Observer<GovernateModel>() {
            @Override
            public void onChanged(GovernateModel governateModel) {
                if (governateModel.getStatus() == 1) {
                    for (int i = 0; i < governateModel.getData().size(); i++) {
                        goernateCheckBoxArrayList.add(new ExampleCheckBox(governateModel.getData().get(i).getName(), governateModel.getData().get(i).getId()));
                        checkBoxAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

//        ApiClient.getINSTANCE().getGovernate().enqueue(new Callback<GovernateModel>() {
//            @Override
//            public void onResponse(Call<GovernateModel> call, Response<GovernateModel> response) {
//                if (response.body().getStatus() == 1) {
//                    for (int i = 0; i < response.body().getData().size(); i++) {
//                        goernateCheckBoxArrayList.add(new ExampleCheckBox(response.body().getData().get(i).getName(), response.body().getData().get(i).getId()));
//                        checkBoxAdapter.notifyDataSetChanged();
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<GovernateModel> call, Throwable t) {
//
//            }
//        });

        RecyclerView governateRecyclerView = view.findViewById(R.id.fragment_settings_governate_recycler_view);
        governateRecyclerView.setHasFixedSize(true); //important
        governateRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        governateRecyclerView.setAdapter(checkBoxAdapter);

        checkBoxAdapter.setOnItemClickListener(new CheckBoxAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ExampleCheckBox exampleCheckBox, boolean isChecked, int position) {
                if (isChecked) {
                    governateArray.add(String.valueOf(exampleCheckBox.getId()));
                } else {
                    governateArray.remove(String.valueOf(exampleCheckBox.getId()));
                }
            }
        });
    }

    @OnClick(R.id.fragment_settings_modify_btn)
    public void fragmentSettingsModifyBtnClicked() {
        viewModel.setNotificationSettings(Constants.API_TOKEN, governateArray, bloodTypeArray);
        viewModel.getNotificationSettings().observe(getViewLifecycleOwner(), new Observer<NotificationSettingsModel>() {
            @Override
            public void onChanged(NotificationSettingsModel notificationSettingsModel) {
                String content = "";
                content += "MSG : " + notificationSettingsModel.getMsg() + "\n";
                content += "status " + notificationSettingsModel.getStatus().toString() + "\n";
                content += "governate " + notificationSettingsModel.getData().getGovernorates().toString() + "\n";
                content += "blood " + notificationSettingsModel.getData().getBloodTypes().toString() + "\n";

                Toast.makeText(getContext(), content, Toast.LENGTH_SHORT).show();
            }
        });
//        ApiClient.getINSTANCE().setNotificationSettings(
//                Constants.API_TOKEN,
//                governateArray,
//                bloodTypeArray).enqueue(new Callback<NotificationSettingsModel>() {
//            @Override
//            public void onResponse(Call<NotificationSettingsModel> call, Response<NotificationSettingsModel> response) {
//                String content = "";
//                content += "MSG : " + response.body().getMsg() + "\n";
//                content += "status " + response.body().getStatus().toString() + "\n";
//                content += "governate " + response.body().getData().getGovernorates().toString() + "\n";
//                content += "blood " + response.body().getData().getBloodTypes().toString() + "\n";
//                Toast.makeText(getContext(), content, Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(Call<NotificationSettingsModel> call, Throwable t) {
//                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    @OnClick(R.id.fragment_settings_back_btn)
    public void onViewClicked() {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_more_container,
                new MoreScreen()).addToBackStack(null).commit();
    }
}