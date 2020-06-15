package eslam.emad.bloodbank.dataSource.factory;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import eslam.emad.bloodbank.dataSource.NotificationDataSource;

public class NotificationDataSourceFactory extends DataSource.Factory {

    NotificationDataSource notificationDataSource;
    MutableLiveData<NotificationDataSource> notificationDataSourceMutableLiveData;

    public NotificationDataSourceFactory() {
        notificationDataSourceMutableLiveData = new MutableLiveData<>();
    }

    @Override
    public DataSource create() {
        notificationDataSource = new NotificationDataSource();
        notificationDataSourceMutableLiveData.postValue(notificationDataSource);
        return notificationDataSource;
    }

    public MutableLiveData<NotificationDataSource> getNotificationDataSourceMutableLiveData() {
        return notificationDataSourceMutableLiveData;
    }
}
