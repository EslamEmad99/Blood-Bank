package eslam.emad.bloodbank.dataSource.factory;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import eslam.emad.bloodbank.dataSource.DonationDataSource;

public class DonationDataSourceFactory extends DataSource.Factory {

    DonationDataSource donationDataSource;
    MutableLiveData<DonationDataSource> donationDataSourceMutableLiveData;

    public DonationDataSourceFactory() {
        donationDataSourceMutableLiveData = new MutableLiveData<>();
    }

    @Override
    public DataSource create() {
        donationDataSource = new DonationDataSource();
        donationDataSourceMutableLiveData.postValue(donationDataSource);
        return donationDataSource;
    }

    public MutableLiveData<DonationDataSource> getDonationDataSourceMutableLiveData() {
        return donationDataSourceMutableLiveData;
    }
}
