package eslam.emad.bloodbank.dataSource.factory;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import eslam.emad.bloodbank.dataSource.PostsDataSource;

public class PostDataSourceFactory extends DataSource.Factory {

    PostsDataSource postsDataSource;
    MutableLiveData<PostsDataSource> postsDataSourceMutableLiveData;

    public PostDataSourceFactory() {
        postsDataSourceMutableLiveData = new MutableLiveData<>();
    }

    @Override
    public DataSource create() {

        postsDataSource = new PostsDataSource();
        postsDataSourceMutableLiveData.postValue(postsDataSource);
        return postsDataSource;
    }

    public MutableLiveData<PostsDataSource> getPostsDataSourceMutableLiveData() {
        return postsDataSourceMutableLiveData;
    }
}
