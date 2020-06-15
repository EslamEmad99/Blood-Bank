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

import eslam.emad.bloodbank.adapters.NotificationsAdapter;
import eslam.emad.bloodbank.data.models.notification.NotificationData;
import eslam.emad.bloodbank.ui.viewModels.NotificationViewModel;

public class NotificationFragment extends Fragment {

    View view;
    private NotificationsAdapter mAdapter;
    private NotificationViewModel notificationViewModel;
    ImageView imageView;
    TextView textView;
    Button button;
    RecyclerView mRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notification, container, false);

        imageView = view.findViewById(R.id.fragment_notification_imgv);
        textView = view.findViewById(R.id.fragment_notification_tv);
        button = view.findViewById(R.id.fragment_notification_btn);
        mRecyclerView = view.findViewById(R.id.fragment_notification_recycler_view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setHasFixedSize(true);

        notificationViewModel = new ViewModelProvider(this).get(NotificationViewModel.class);
        mAdapter = new NotificationsAdapter(getContext());
        notificationViewModel.getNotificationPagedList().observe(getViewLifecycleOwner(), new Observer<PagedList<NotificationData>>() {
            @Override
            public void onChanged(PagedList<NotificationData> notificationData) {
                mAdapter.submitList(notificationData);
                notificationData.addWeakCallback(null, new PagedList.Callback() {
                    @Override
                    public void onChanged(int position, int count) {

                    }

                    @Override
                    public void onInserted(int position, int count) {
                        imageView.setVisibility(View.GONE);
                        textView.setVisibility(View.GONE);
                        button.setVisibility(View.GONE);
                    }

                    @Override
                    public void onRemoved(int position, int count) {

                    }
                });
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
