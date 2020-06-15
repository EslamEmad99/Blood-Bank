package eslam.emad.bloodbank.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.emad.bloodbank.R;
import eslam.emad.bloodbank.data.models.ExampleCheckBox;
import java.util.ArrayList;


public class CheckBoxAdapter extends RecyclerView.Adapter <CheckBoxAdapter.CheckBoxViewHolder> {

    private ArrayList<ExampleCheckBox> mExampleList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position, boolean isChecked);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class CheckBoxViewHolder extends RecyclerView.ViewHolder {

        public CheckBox checkBox;

        public CheckBoxViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.example_check_box_checkBox);

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position,isChecked);
                        }
                    }
                }
            });
        }
    }

    public CheckBoxAdapter(ArrayList<ExampleCheckBox> exampleList){
        mExampleList= exampleList;
    }

    @Override
    public CheckBoxViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_check_box,parent,false);
        CheckBoxViewHolder evh = new CheckBoxViewHolder(view, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(CheckBoxViewHolder holder, int position) {
        ExampleCheckBox currentItem = mExampleList.get(position);
        holder.checkBox.setText(currentItem.getText());
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
}