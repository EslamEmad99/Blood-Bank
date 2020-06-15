package eslam.emad.bloodbank.ui.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import eslam.emad.bloodbank.data.models.notification.NotificationData;
import eslam.emad.bloodbank.dataSource.NotificationDataSource;
import eslam.emad.bloodbank.dataSource.factory.NotificationDataSourceFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class NotificationViewModel extends ViewModel {

    LiveData<NotificationDataSource> notificationDataSourceLiveData;
    private Executor executor;
    private LiveData<PagedList<NotificationData>> notificationPagedList;
    NotificationDataSourceFactory notificationDataSourceFactory;

    public NotificationViewModel() {

        notificationDataSourceFactory = new NotificationDataSourceFactory();
        notificationDataSourceLiveData = notificationDataSourceFactory.getNotificationDataSourceMutableLiveData();

        PagedList.Config config =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setInitialLoadSizeHint(10)
                        .setPageSize(/*ItemDataSource.PAGE_SIZE*/ 10)
                        .setPrefetchDistance(10)
                        .build();

        executor = Executors.newFixedThreadPool(5);
        notificationPagedList = (new LivePagedListBuilder<Integer,NotificationData>(notificationDataSourceFactory,config))
                .setFetchExecutor(executor)
                .build();
    }

    public LiveData<PagedList<NotificationData>> getNotificationPagedList() {
        return notificationPagedList;
    }

    public void refresh() {
        notificationDataSourceFactory.getNotificationDataSourceMutableLiveData().getValue().invalidate();
    }
}
