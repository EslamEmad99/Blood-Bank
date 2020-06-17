package eslam.emad.bloodbank.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
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
import eslam.emad.bloodbank.data.models.favoritePosts.FavoritePostsModel;
import eslam.emad.bloodbank.data.models.posts.PostData;
import eslam.emad.bloodbank.data.models.setGetFavoritePosts.SetGetFavoritePostsModel;
import eslam.emad.bloodbank.ui.activities.PostAndDonationInformationActivity;
import eslam.emad.bloodbank.ui.viewModels.FavoritePostsViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoritePostsFragment extends Fragment {

    View view;
    @BindView(R.id.favorite_posts_fragment_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.favorite_posts_fragment_swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.favorite_posts_fragment_imgv)
    ImageView favoritePostsFragmentImgv;
    @BindView(R.id.favorite_posts_fragment_tv)
    TextView favoritePostsFragmentTv;
    private ArticlesAdapter mAdapter;
    private FavoritePostsViewModel favoritePostsViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_favorite_posts, container, false);
        ButterKnife.bind(this, view);

        ApiClient.getINSTANCE()
                .getAllFavoritePosts(Constants.API_TOKEN, 0)
                .enqueue(new Callback<FavoritePostsModel>() {
                    @Override
                    public void onResponse(Call<FavoritePostsModel> call, Response<FavoritePostsModel> response) {
                        if (response.body().getFavoritePostsResponseData().getData().size() == 0) {
                            mRecyclerView.setVisibility(View.GONE);
                            favoritePostsFragmentImgv.setVisibility(View.VISIBLE);
                            favoritePostsFragmentTv.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(Call<FavoritePostsModel> call, Throwable t) {

                    }
                });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);

        favoritePostsViewModel = new ViewModelProvider(this).get(FavoritePostsViewModel.class);
        mAdapter = new ArticlesAdapter(getContext());

        favoritePostsViewModel.getFavoritePostPagedList().observe(getViewLifecycleOwner(), new Observer<PagedList<PostData>>() {
            @Override
            public void onChanged(PagedList<PostData> favoritePostsData) {
                mAdapter.submitList(favoritePostsData);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new ArticlesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(PostData postData) {
                Intent intent = new Intent(getActivity(), PostAndDonationInformationActivity.class);
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
                favoritePostsViewModel.refresh();
            }
        });
        return view;
    }
}