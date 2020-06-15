package eslam.emad.bloodbank.dataSource;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import eslam.emad.bloodbank.data.api.ApiClient;
import eslam.emad.bloodbank.data.models.favoritePosts.FavoritePostsModel;
import eslam.emad.bloodbank.data.models.posts.PostData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static eslam.emad.bloodbank.data.Constants.API_TOKEN;
import static eslam.emad.bloodbank.data.Constants.FIRST_PAGE;

public class FavoritePostsDataSource extends PageKeyedDataSource<Integer, PostData> {
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, PostData> callback) {
        ApiClient
                .getINSTANCE()
                .getAllFavoritePosts(API_TOKEN, FIRST_PAGE)
                .enqueue(new Callback<FavoritePostsModel>() {
                    @Override
                    public void onResponse(Call<FavoritePostsModel> call, Response<FavoritePostsModel> response) {
                        if (response.body() != null) {
                            callback.onResult(response.body().getFavoritePostsResponseData().getData(), null, FIRST_PAGE + 1);
                        }
                    }

                    @Override
                    public void onFailure(Call<FavoritePostsModel> call, Throwable t) {

                    }
                });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, PostData> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, PostData> callback) {
        ApiClient
                .getINSTANCE()
                .getAllFavoritePosts(API_TOKEN,params.key)
                .enqueue(new Callback<FavoritePostsModel>() {
                    @Override
                    public void onResponse(Call<FavoritePostsModel> call, Response<FavoritePostsModel> response) {
                        if (response.body().getFavoritePostsResponseData().getData().size() != 0 && response.body().getFavoritePostsResponseData().getData() !=null){
                            callback.onResult(response.body().getFavoritePostsResponseData().getData(), params.key+1);
                        }
                    }

                    @Override
                    public void onFailure(Call<FavoritePostsModel> call, Throwable t) {

                    }
                });
    }


    //    @Override
//    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, FavoritePostsData> callback) {
//        ApiClient
//                .getINSTANCE()
//                .getAllFavoritePosts(API_TOKEN, FIRST_PAGE)
//                .enqueue(new Callback<FavoritePostsModel>() {
//                    @Override
//                    public void onResponse(Call<FavoritePostsModel> call, Response<FavoritePostsModel> response) {
//                        if (response.body() != null) {
//                            callback.onResult(response.body().getFavoritePostsResponseData().getData(), null, FIRST_PAGE + 1);
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<FavoritePostsModel> call, Throwable t) {
//
//                    }
//                });
//    }
//
//    @Override
//    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, FavoritePostsData> callback) {
//
//    }
//
//    @Override
//    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, FavoritePostsData> callback) {
//        ApiClient
//                .getINSTANCE()
//                .getAllFavoritePosts(API_TOKEN,params.key)
//                .enqueue(new Callback<FavoritePostsModel>() {
//                    @Override
//                    public void onResponse(Call<FavoritePostsModel> call, Response<FavoritePostsModel> response) {
//                        if (response.body().getFavoritePostsResponseData().getData().size() != 0 && response.body().getFavoritePostsResponseData().getData() !=null){
//                            callback.onResult(response.body().getFavoritePostsResponseData().getData(), params.key+1);
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<FavoritePostsModel> call, Throwable t) {
//
//                    }
//                });
//    }
}
