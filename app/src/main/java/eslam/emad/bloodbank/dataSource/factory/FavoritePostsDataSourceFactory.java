package eslam.emad.bloodbank.dataSource.factory;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import eslam.emad.bloodbank.dataSource.FavoritePostsDataSource;
import eslam.emad.bloodbank.dataSource.PostsDataSource;

public class FavoritePostsDataSourceFactory  extends DataSource.Factory {

    FavoritePostsDataSource favoritePostsDataSource;
    MutableLiveData<FavoritePostsDataSource> favoritePostsDataSourceMutableLiveData;

    public FavoritePostsDataSourceFactory() {
        favoritePostsDataSourceMutableLiveData = new MutableLiveData<>();
    }

    @Override
    public DataSource create() {
        favoritePostsDataSource = new FavoritePostsDataSource();
        favoritePostsDataSourceMutableLiveData.postValue(favoritePostsDataSource);
        return favoritePostsDataSource;
    }

    public MutableLiveData<FavoritePostsDataSource> getFavoritePostsDataSourceMutableLiveData() {
        return favoritePostsDataSourceMutableLiveData;
    }
}