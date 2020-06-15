package eslam.emad.bloodbank.ui.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import eslam.emad.bloodbank.data.models.donation.DonationData;
import eslam.emad.bloodbank.dataSource.DonationDataSource;
import eslam.emad.bloodbank.dataSource.factory.DonationDataSourceFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DonationViewModel extends ViewModel {

    LiveData<DonationDataSource> donationDataSourceLiveData;
    private Executor executor;
    private LiveData<PagedList<DonationData>> donationPagedList;
    DonationDataSourceFactory donationDataSourceFactory;

    public DonationViewModel() {

        donationDataSourceFactory = new DonationDataSourceFactory();
        donationDataSourceLiveData = donationDataSourceFactory.getDonationDataSourceMutableLiveData();

        PagedList.Config config =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(true)
                        .setInitialLoadSizeHint(10)
                        .setPageSize(/*ItemDataSource.PAGE_SIZE*/ 10)
                        .setPrefetchDistance(10)
                        .build();

        executor = Executors.newFixedThreadPool(5);
        donationPagedList = (new LivePagedListBuilder<Integer,DonationData>(donationDataSourceFactory,config))
                .setFetchExecutor(executor)
                .build();
    }

    public LiveData<PagedList<DonationData>> getDonationPagedList() {
        return donationPagedList;
    }

    public void refresh() {
        donationDataSourceFactory.getDonationDataSourceMutableLiveData().getValue().invalidate();
    }
}
