package eslam.emad.bloodbank.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

import com.emad.bloodbank.R;
import eslam.emad.bloodbank.data.models.OnBoardingModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OnBoardingPagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<OnBoardingModel> mListScreen;
    @Nullable @BindView(R.id.screen_on_boarding_imgv)
    ImageView imgSlide;
    @Nullable @BindView(R.id.screen_on_boarding_tv)
    TextView description;

    public OnBoardingPagerAdapter(Context mContext, List<OnBoardingModel> mListScreen) {
        this.mContext = mContext;
        this.mListScreen = mListScreen;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layoutScreen = inflater.inflate(R.layout.screen_on_boarding, null);
        ButterKnife.bind(this ,layoutScreen);
        assert description != null;
        description.setText(mListScreen.get(position).getDescription());
        assert imgSlide != null;
        imgSlide.setImageResource(mListScreen.get(position).getScreenImg());
        container.addView(layoutScreen);
        return layoutScreen;
    }

    @Override
    public int getCount() {
        return mListScreen.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
