package eslam.emad.bloodbank.dataSource.factory;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import eslam.emad.bloodbank.dataSource.PostsDataSource;
import eslam.emad.bloodbank.dataSource.SearchPostsDataSource;

public class SearchPostsDataSourceFactory extends DataSource.Factory {

    SearchPostsDataSource postsDataSource;
    MutableLiveData<SearchPostsDataSource> postsDataSourceMutableLiveData;
    String keyWord;

    public SearchPostsDataSourceFactory(String keyWord) {
        this.keyWord = keyWord;
        postsDataSourceMutableLiveData = new MutableLiveData<>();
    }

    @Override
    public DataSource create() {

        postsDataSource = new SearchPostsDataSource(keyWord);
        postsDataSourceMutableLiveData.postValue(postsDataSource);
        return postsDataSource;
    }

    public MutableLiveData<SearchPostsDataSource> getPostsDataSourceMutableLiveData() {
        return postsDataSourceMutableLiveData;
    }
}
