package eslam.emad.bloodbank.ui.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import eslam.emad.bloodbank.data.models.posts.PostData;
import eslam.emad.bloodbank.dataSource.PostsDataSource;
import eslam.emad.bloodbank.dataSource.SearchPostsDataSource;
import eslam.emad.bloodbank.dataSource.factory.PostDataSourceFactory;
import eslam.emad.bloodbank.dataSource.factory.SearchPostsDataSourceFactory;

public class SearchPostsViewModel extends ViewModel {

    LiveData<SearchPostsDataSource> postDataSourceLiveData;
    private Executor executor;
    private LiveData<PagedList<PostData>> postPagedList;
    SearchPostsDataSourceFactory postDataSourceFactory;

    public SearchPostsViewModel(String keyword) {

        postDataSourceFactory = new SearchPostsDataSourceFactory(keyword);
        postDataSourceLiveData = postDataSourceFactory.getPostsDataSourceMutableLiveData();

        PagedList.Config config =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(true)
                        .setInitialLoadSizeHint(10)
                        .setPageSize(/*ItemDataSource.PAGE_SIZE*/ 10)
                        .setPrefetchDistance(10)
                        .build();

        executor = Executors.newFixedThreadPool(5);
        postPagedList = (new LivePagedListBuilder<Integer, PostData>(postDataSourceFactory, config))
                .setFetchExecutor(executor)
                .build();
    }

    public LiveData<PagedList<PostData>> getItemPagedList() {
        return postPagedList;
    }

    public void refresh() {
        postDataSourceFactory.getPostsDataSourceMutableLiveData().getValue().invalidate();
    }
}
