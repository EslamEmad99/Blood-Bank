package eslam.emad.bloodbank.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.emad.bloodbank.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import eslam.emad.bloodbank.adapters.NotificationsAdapter;
import eslam.emad.bloodbank.data.Constants;
import eslam.emad.bloodbank.data.api.ApiClient;
import eslam.emad.bloodbank.data.models.notification.NotificationData;
import eslam.emad.bloodbank.data.models.notification.NotificationModel;
import eslam.emad.bloodbank.ui.viewModels.NotificationViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationFragment extends Fragment {

    View view;
    @BindView(R.id.fragment_notification_imgv)
    ImageView imageView;
    @BindView(R.id.fragment_notification_tv)
    TextView textView;
    @BindView(R.id.fragment_notification_btn)
    Button button;
    @BindView(R.id.fragment_notification_recycler_view)
    RecyclerView mRecyclerView;
    private NotificationsAdapter mAdapter;
    private NotificationViewModel notificationViewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notification, container, false);
        ButterKnife.bind(this,view);

        ApiClient.getINSTANCE()
                .getNotifications(Constants.API_TOKEN, 0)
                .enqueue(new Callback<NotificationModel>() {
                    @Override
                    public void onResponse(Call<NotificationModel> call, Response<NotificationModel> response) {
                        if (response.body().getNotificationResponseData().getData().size() == 0) {
                            mRecyclerView.setVisibility(View.GONE);
                            imageView.setVisibility(View.VISIBLE);
                            textView.setVisibility(View.VISIBLE);
                            button.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(Call<NotificationModel> call, Throwable t) {

                    }
                });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);

        notificationViewModel = new ViewModelProvider(this).get(NotificationViewModel.class);
        mAdapter = new NotificationsAdapter(getContext());
        notificationViewModel.getNotificationPagedList().observe(getViewLifecycleOwner(), new Observer<PagedList<NotificationData>>() {
            @Override
            public void onChanged(PagedList<NotificationData> notificationData) {
                mAdapter.submitList(notificationData);
            }
        });

        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new NotificationsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(NotificationData notification) {
                Toast.makeText(getContext(), notification.getContent(), Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
