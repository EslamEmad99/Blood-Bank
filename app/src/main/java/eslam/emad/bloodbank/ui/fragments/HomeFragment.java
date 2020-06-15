package eslam.emad.bloodbank.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.emad.bloodbank.R;
import eslam.emad.bloodbank.adapters.HomeFragmentSectionPageAdapter;
import com.google.android.material.tabs.TabLayout;

import butterknife.BindView;

public class HomeFragment extends Fragment {
    View view;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //getActivity().getActionBar().hide();
        // Hide the status bar.
//        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
//        view.setSystemUiVisibility(uiOptions);
// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.
        //((AppCompatActivity) getActivity()).getSupportActionBar().hide();
//
//        HomeActivity csActivity;
//        csActivity = (HomeActivity) getActivity();
//        csActivity.getSupportActionBar().hide();
        view = inflater.inflate(R.layout.fragment_home, container, false);

        HomeFragmentSectionPageAdapter homeFragmentSectionPageAdapter = new HomeFragmentSectionPageAdapter(getChildFragmentManager());
        ViewPager viewPager = view.findViewById(R.id.view_pager);
        viewPager.setAdapter(homeFragmentSectionPageAdapter);
        TabLayout tabs = view.findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(1);
        return view;
    }


}
