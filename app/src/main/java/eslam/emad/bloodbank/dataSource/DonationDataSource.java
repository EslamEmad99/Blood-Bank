package eslam.emad.bloodbank.dataSource;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import eslam.emad.bloodbank.data.api.ApiClient;
import eslam.emad.bloodbank.data.models.donation.DonationData;
import eslam.emad.bloodbank.data.models.donation.DonationModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static eslam.emad.bloodbank.data.Constants.FIRST_PAGE;
import static eslam.emad.bloodbank.data.Constants.API_TOKEN;
import static eslam.emad.bloodbank.data.Constants.BLOOD_TYPE_ID;
import static eslam.emad.bloodbank.data.Constants.GOVERNATE_ID;

public class DonationDataSource extends PageKeyedDataSource<Integer, DonationData> {
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, DonationData> callback) {
        ApiClient.getINSTANCE()
                .getDonationByFilter(API_TOKEN,BLOOD_TYPE_ID,GOVERNATE_ID,FIRST_PAGE)
                .enqueue(new Callback<DonationModel>() {
                    @Override
                    public void onResponse(Call<DonationModel> call, Response<DonationModel> response) {
                        if (response.body() != null){
                            callback.onResult(response.body().getDonationResponseData().getData(),null,FIRST_PAGE+1);
                        }
                    }

                    @Override
                    public void onFailure(Call<DonationModel> call, Throwable t) {

                    }
                });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, DonationData> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, DonationData> callback) {
        ApiClient.getINSTANCE()
                .getDonationByFilter(API_TOKEN,BLOOD_TYPE_ID,GOVERNATE_ID,params.key)
                .enqueue(new Callback<DonationModel>() {
                    @Override
                    public void onResponse(Call<DonationModel> call, Response<DonationModel> response) {
                        if (response.body().getDonationResponseData().getData().size() != 0 && response.body().getDonationResponseData().getData() !=null){
                            callback.onResult(response.body().getDonationResponseData().getData(),params.key+1);
                        }
                    }

                    @Override
                    public void onFailure(Call<DonationModel> call, Throwable t) {

                    }
                });
    }
}
