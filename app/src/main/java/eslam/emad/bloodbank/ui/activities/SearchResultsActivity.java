package eslam.emad.bloodbank.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.emad.bloodbank.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import eslam.emad.bloodbank.adapters.ArticlesAdapter;
import eslam.emad.bloodbank.data.Constants;
import eslam.emad.bloodbank.data.api.ApiClient;
import eslam.emad.bloodbank.data.models.posts.PostData;
import eslam.emad.bloodbank.data.models.posts.PostModel;
import eslam.emad.bloodbank.data.models.setGetFavoritePosts.SetGetFavoritePostsModel;
import eslam.emad.bloodbank.ui.viewModels.SearchPostsViewModel;
import eslam.emad.bloodbank.ui.viewModels.viewModelsFactory.ViewModelFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchResultsActivity extends AppCompatActivity {

    @BindView(R.id.search_posts_activity_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.search_posts_activity_swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.search_posts_activity_imgv)
    ImageView searchPostsActivityImgv;
    @BindView(R.id.search_posts_activity_tv)
    TextView searchPostsActivityTv;
    private ArticlesAdapter mAdapter;
    private SearchPostsViewModel searchPostsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);
        ButterKnife.bind(this);
        setTitle(getString(R.string.search_result));

        ApiClient.getINSTANCE()
                .getAllSearchPosts(Constants.API_TOKEN, 0, getIntent().getStringExtra("search"), "")
                .enqueue(new Callback<PostModel>() {
                    @Override
                    public void onResponse(Call<PostModel> call, Response<PostModel> response) {
                        if (response.body().getPostsResponseData().getPostData().size() == 0) {
                            mRecyclerView.setVisibility(View.GONE);
                            searchPostsActivityImgv.setVisibility(View.VISIBLE);
                            searchPostsActivityTv.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(Call<PostModel> call, Throwable t) {

                    }
                });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);

        searchPostsViewModel = new ViewModelProvider(this, new ViewModelFactory(getIntent().getStringExtra("search"))).get(SearchPostsViewModel.class);
        mAdapter = new ArticlesAdapter(this);

        searchPostsViewModel.getItemPagedList().observe(this, new Observer<PagedList<PostData>>() {
            @Override
            public void onChanged(PagedList<PostData> postData) {
                mAdapter.submitList(postData);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new ArticlesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(PostData postData) {
                Intent intent = new Intent(SearchResultsActivity.this, PostAndDonationInformationActivity.class);
                intent.putExtra("post_image", postData.getThumbnailFullPath());
                intent.putExtra("title", postData.getTitle());
                intent.putExtra("content", postData.getContent());
                startActivity(intent);
            }

            @Override
            public void onFavoriteClick(PostData postData, boolean isChecked) {
                postData.setIsFavourite(!isChecked);
                mAdapter.notifyDataSetChanged();
                ApiClient.getINSTANCE()
                        .setFavoritePost(postData.getId(), Constants.API_TOKEN)
                        .enqueue(new Callback<SetGetFavoritePostsModel>() {
                            @Override
                            public void onResponse(Call<SetGetFavoritePostsModel> call, Response<SetGetFavoritePostsModel> response) {

                            }

                            @Override
                            public void onFailure(Call<SetGetFavoritePostsModel> call, Throwable t) {

                            }
                        });
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                searchPostsViewModel.refresh();
            }
        });
    }
}