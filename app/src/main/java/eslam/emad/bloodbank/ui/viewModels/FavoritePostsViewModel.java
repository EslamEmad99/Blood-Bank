package eslam.emad.bloodbank.ui.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import eslam.emad.bloodbank.data.models.posts.PostData;
import eslam.emad.bloodbank.dataSource.FavoritePostsDataSource;
import eslam.emad.bloodbank.dataSource.factory.FavoritePostsDataSourceFactory;

public class FavoritePostsViewModel extends ViewModel {
    LiveData<FavoritePostsDataSource> favoritePostsDataSourceLiveData;
    private Executor executor;
    private LiveData<PagedList<PostData>> favoritePostPagedList;
    FavoritePostsDataSourceFactory favoritePostsDataSourceFactory;

    public FavoritePostsViewModel() {

        favoritePostsDataSourceFactory = new FavoritePostsDataSourceFactory();
        favoritePostsDataSourceLiveData = favoritePostsDataSourceFactory.getFavoritePostsDataSourceMutableLiveData();

        PagedList.Config config =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(true)
                        .setInitialLoadSizeHint(10)
                        .setPageSize(/*ItemDataSource.PAGE_SIZE*/ 10)
                        .setPrefetchDistance(10)
                        .build();

        executor = Executors.newFixedThreadPool(5);
        favoritePostPagedList = (new LivePagedListBuilder<Integer,PostData>(favoritePostsDataSourceFactory,config))
                .setFetchExecutor(executor)
                .build();
    }

    public LiveData<PagedList<PostData>> getFavoritePostPagedList() {
        return favoritePostPagedList;
    }

    public void refresh() {
        favoritePostsDataSourceFactory.getFavoritePostsDataSourceMutableLiveData().getValue().invalidate();
    }
}
