package eslam.emad.bloodbank.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
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

import eslam.emad.bloodbank.data.MyApplication;
import eslam.emad.bloodbank.ui.activities.SearchResultsActivity;
import eslam.emad.bloodbank.adapters.ArticlesAdapter;
import eslam.emad.bloodbank.data.Constants;
import eslam.emad.bloodbank.data.api.ApiClient;
import eslam.emad.bloodbank.data.models.setGetFavoritePosts.SetGetFavoritePostsModel;
import eslam.emad.bloodbank.data.models.posts.PostData;
import eslam.emad.bloodbank.ui.activities.PostAndDonationInformationActivity;
import eslam.emad.bloodbank.ui.viewModels.ApplicationViewModel;
import eslam.emad.bloodbank.ui.viewModels.PostsViewModel;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ArticlesFragment extends Fragment {
    View view;
    @BindView(R.id.articles_fragment_search_view)
    EditText articlesFragmentSearchView;
    @BindView(R.id.articles_fragment_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.articles_fragment_swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;
    private ArticlesAdapter mAdapter;
    private PostsViewModel postsViewModel;
    private ApplicationViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_articles, container, false);
        ButterKnife.bind(this, view);

        articlesFragmentSearchView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (MyApplication.hasNetwork()) {
                        if (!articlesFragmentSearchView.getText().toString().equals("")) {
                            Intent intent = new Intent(getActivity(), SearchResultsActivity.class);
                            intent.putExtra("search", articlesFragmentSearchView.getText().toString());
                            startActivity(intent);
                        }
                    }else {
                        Toast.makeText(getContext(), "No Internet", Toast.LENGTH_LONG).show();
                    }
                    return true;
                }
                return false;
            }
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);

        postsViewModel = new ViewModelProvider(this).get(PostsViewModel.class);
        viewModel = new ViewModelProvider(this).get(ApplicationViewModel.class);
        mAdapter = new ArticlesAdapter(getContext());

        postsViewModel.getItemPagedList().observe(getViewLifecycleOwner(), new Observer<PagedList<PostData>>() {
            @Override
            public void onChanged(PagedList<PostData> postData) {
                mAdapter.submitList(postData);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        mRecyclerView.setAdapter(mAdapter);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                postsViewModel.refresh();
            }
        });

        mAdapter.setOnItemClickListener(new ArticlesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(PostData postData, int position) {
                Intent intent = new Intent(getActivity(), PostAndDonationInformationActivity.class);
                intent.putExtra("post_image", postData.getThumbnailFullPath());
                intent.putExtra("title", postData.getTitle());
                intent.putExtra("content", postData.getContent());
                startActivity(intent);
            }

            @Override
            public void onFavoriteClick(PostData postData, boolean isChecked, int position) {
                isChecked = !isChecked;
                postData.setIsFavourite(isChecked);
                mAdapter.notifyItemChanged(position);
                viewModel.setSetGetFavoritePost(postData.getId(), Constants.API_TOKEN);
            }
        });
        return view;
    }
}
