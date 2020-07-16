package eslam.emad.bloodbank.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.emad.bloodbank.R;

import eslam.emad.bloodbank.data.models.posts.PostData;

public class ArticlesAdapter extends PagedListAdapter<PostData, ArticlesAdapter.ArticlesViewHolder> {

    private Context mCtx;
    private ArticlesAdapter.OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(PostData postData, int position);

        void onFavoriteClick(PostData postData, boolean isChecked, int position);
    }

    public void setOnItemClickListener(ArticlesAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    public ArticlesAdapter(Context mCtx) {
        super(DIFF_CALLBACK);
        this.mCtx = mCtx;
    }

    @NonNull
    @Override
    public ArticlesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.example_post, parent, false);
        ArticlesAdapter.ArticlesViewHolder evh = new ArticlesAdapter.ArticlesViewHolder(view, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ArticlesViewHolder holder, int position) {

        PostData currentPost = getItem(position);

        if (currentPost != null) {
            Glide.with(mCtx)
                    .load(currentPost.getThumbnailFullPath())
                    .into(holder.postBackground);

            if (currentPost.getIsFavourite()) {
                holder.favoriteToggleButton.setImageResource(R.drawable.ic_star_checked);
            }else {
                holder.favoriteToggleButton.setImageResource(R.drawable.ic_star_not_checked);
            }
            holder.postDescription.setText(currentPost.getTitle());
        }
    }

    private static DiffUtil.ItemCallback<PostData> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<PostData>() {
                @Override
                public boolean areItemsTheSame(@NonNull PostData oldItem, @NonNull PostData newItem) {
                    return oldItem.getId().equals(newItem.getId());
                }

                @SuppressLint("DiffUtilEquals")
                @Override
                public boolean areContentsTheSame(@NonNull PostData oldItem, @NonNull PostData newItem) {
                    return oldItem.equals(newItem);
                }
            };

    class ArticlesViewHolder extends RecyclerView.ViewHolder {

        ImageView postBackground;
        TextView postDescription;
        ImageView favoriteToggleButton;

        ArticlesViewHolder(View itemView, final ArticlesAdapter.OnItemClickListener listener) {
            super(itemView);

            postBackground = itemView.findViewById(R.id.post_background);
            favoriteToggleButton = itemView.findViewById(R.id.favorite_t_btn);
            postDescription = itemView.findViewById(R.id.post_description);

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

            favoriteToggleButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onFavoriteClick(getItem(position), getItem(position).getIsFavourite(), position);
                        }
                    }
                }
            });
        }
    }
}
