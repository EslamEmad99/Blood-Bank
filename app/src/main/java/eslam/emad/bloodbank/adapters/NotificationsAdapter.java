package eslam.emad.bloodbank.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.emad.bloodbank.R;
import eslam.emad.bloodbank.data.models.notification.NotificationData;

public class NotificationsAdapter extends PagedListAdapter<NotificationData, NotificationsAdapter.NotificationsViewHolder> {

    private Context mCtx;
    private NotificationsAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(NotificationData notification);
    }

    public void setOnItemClickListener(NotificationsAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    public NotificationsAdapter(Context mCtx) {
        super(DIFF_CALLBACK);
        this.mCtx = mCtx;
    }

    @NonNull
    @Override
    public NotificationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.example_notification, parent, false);
        NotificationsAdapter.NotificationsViewHolder evh = new NotificationsAdapter.NotificationsViewHolder(view, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationsViewHolder holder, int position) {

        NotificationData currentPost = getItem(position);

        if (currentPost.getUpdatedAt() != null) {
            holder.notificationDate.setText(currentPost.getUpdatedAt());
        }
        holder.notificationText.setText(currentPost.getTitle());
        if (currentPost.getPivot().getIsRead().equals("0")){
            holder.notificationAlarm.setImageResource(R.drawable.ic_notification_none);
        }else {
            holder.notificationAlarm.setImageResource(R.drawable.ic_notification);
        }
    }

    private static DiffUtil.ItemCallback<NotificationData> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<NotificationData>() {
                @Override
                public boolean areItemsTheSame(@NonNull NotificationData oldItem, @NonNull NotificationData newItem) {
                    return oldItem.getId().equals(newItem.getId());
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(@NonNull NotificationData oldItem, @NonNull NotificationData newItem) {
                    return oldItem.equals(newItem);
                }
            };

    class NotificationsViewHolder extends RecyclerView.ViewHolder {

        TextView notificationDate;
        TextView notificationText;
        ImageView notificationAlarm;

        NotificationsViewHolder(View itemView, final NotificationsAdapter.OnItemClickListener listener) {
            super(itemView);

            notificationDate = itemView.findViewById(R.id.example_notification_date_tv);
            notificationText = itemView.findViewById(R.id.example_notification_text_tv);
            notificationAlarm = itemView.findViewById(R.id.example_notification_alarm_img);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(getItem(position));
                        }
                    }
                }
            });
        }
    }
}
