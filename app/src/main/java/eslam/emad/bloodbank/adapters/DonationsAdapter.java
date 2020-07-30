package eslam.emad.bloodbank.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.emad.bloodbank.R;
import eslam.emad.bloodbank.data.models.donation.DonationData;

public class DonationsAdapter extends PagedListAdapter<DonationData, DonationsAdapter.DonationsViewHolder> {

    private Context mCtx;
    private DonationsAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(DonationData donationData, int position);
    }

    public void setOnItemClickListener(DonationsAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    public DonationsAdapter(Context mCtx) {
        super(DIFF_CALLBACK);
        this.mCtx = mCtx;
    }

    @NonNull
    @Override
    public DonationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.example_donation, parent, false);
        return new DonationsViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull DonationsAdapter.DonationsViewHolder holder, int position) {

        DonationData current = getItem(position);

        if (current != null) {
            holder.nameTv.setText(current.getClient().getName());
            holder.phoneTV.setText(current.getClient().getPhone());
            holder.bloodTypeTV.setText(current.getBloodType().getName());
        }
    }

    private static DiffUtil.ItemCallback<DonationData> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<DonationData>() {
                @Override
                public boolean areItemsTheSame(@NonNull DonationData oldItem, @NonNull DonationData newItem) {
                    return oldItem.getId().equals(newItem.getId());
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(@NonNull DonationData oldItem, @NonNull DonationData newItem) {
                    return oldItem.equals(newItem);
                }
            };

    class DonationsViewHolder extends RecyclerView.ViewHolder {
        TextView nameTv, phoneTV, bloodTypeTV;

        public DonationsViewHolder(@NonNull View itemView, final DonationsAdapter.OnItemClickListener listener) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.example_donation_name_tv);
            phoneTV = itemView.findViewById(R.id.example_donation_phone_tv);
            bloodTypeTV = itemView.findViewById(R.id.example_donation_blood_type_tv);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(getItem(position), position);
                        }
                    }
                }
            });
        }
    }
}
