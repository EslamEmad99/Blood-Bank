package eslam.emad.bloodbank.dataSource;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import eslam.emad.bloodbank.data.api.ApiClient;
import eslam.emad.bloodbank.data.models.posts.PostData;
import eslam.emad.bloodbank.data.models.posts.PostModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static eslam.emad.bloodbank.data.Constants.API_TOKEN;
import static eslam.emad.bloodbank.data.Constants.FIRST_PAGE;

public class SearchPostsDataSource extends PageKeyedDataSource<Integer, PostData> {

    String keyWord;

    public SearchPostsDataSource(String keyWord) {
        this.keyWord = keyWord;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, PostData> callback) {
        ApiClient.getINSTANCE()
                .getAllSearchPosts(API_TOKEN, FIRST_PAGE, keyWord," ")
                .enqueue(new Callback<PostModel>() {
                    @Override
                    public void onResponse(Call<PostModel> call, Response<PostModel> response) {
                        if (response.body() != null){
                            callback.onResult(response.body().getPostsResponseData().getPostData(),null,FIRST_PAGE+1);
                        }
                    }

                    @Override
                    public void onFailure(Call<PostModel> call, Throwable t) {

                    }
                });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, PostData> callback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, PostData> callback) {
        ApiClient.getINSTANCE()
                .getAllSearchPosts(API_TOKEN, params.key, keyWord, " ")
                .enqueue(new Callback<PostModel>() {
                    @Override
                    public void onResponse(Call<PostModel> call, Response<PostModel> response) {
                        if (response.body().getPostsResponseData().getPostData().size() != 0 && response.body().getPostsResponseData().getPostData() !=null){
                            callback.onResult(response.body().getPostsResponseData().getPostData(),params.key+1);
                        }
                    }

                    @Override
                    public void onFailure(Call<PostModel> call, Throwable t) {

                    }
                });
    }
}
