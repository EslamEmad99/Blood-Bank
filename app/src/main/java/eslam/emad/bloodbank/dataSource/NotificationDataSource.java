package eslam.emad.bloodbank.dataSource;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import eslam.emad.bloodbank.data.api.ApiClient;
import eslam.emad.bloodbank.data.models.notification.NotificationData;
import eslam.emad.bloodbank.data.models.notification.NotificationModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static eslam.emad.bloodbank.data.Constants.API_TOKEN;
import static eslam.emad.bloodbank.data.Constants.FIRST_PAGE;

public class NotificationDataSource extends PageKeyedDataSource<Integer, NotificationData> {
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, NotificationData> callback) {
        ApiClient.getINSTANCE().getNotifications(API_TOKEN, FIRST_PAGE)
                .enqueue(new Callback<NotificationModel>() {
                    @Override
                    public void onResponse(Call<NotificationModel> call, Response<NotificationModel> response) {
                        if (response.body() != null) {
                            callback.onResult(response.body().getNotificationResponseData().getData(), null, FIRST_PAGE + 1);
                        }
                    }

                    @Override
                    public void onFailure(Call<NotificationModel> call, Throwable t) {

                    }
                });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, NotificationData> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, NotificationData> callback) {
        ApiClient.getINSTANCE().getNotifications(API_TOKEN, params.key)
                .enqueue(new Callback<NotificationModel>() {
                    @Override
                    public void onResponse(Call<NotificationModel> call, Response<NotificationModel> response) {
                        if (response.body().getNotificationResponseData().getData().size() != 0 && response.body().getNotificationResponseData().getData() != null) {
                            callback.onResult(response.body().getNotificationResponseData().getData(), params.key + 1);
                        }
                    }

                    @Override
                    public void onFailure(Call<NotificationModel> call, Throwable t) {

                    }
                });
    }
}
