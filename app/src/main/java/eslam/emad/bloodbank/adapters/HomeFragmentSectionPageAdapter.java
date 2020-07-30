package eslam.emad.bloodbank.adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import eslam.emad.bloodbank.ui.fragments.ArticlesFragment;
import eslam.emad.bloodbank.ui.fragments.FavoritePostsFragment;

public class HomeFragmentSectionPageAdapter extends FragmentPagerAdapter {

    public HomeFragmentSectionPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new FavoritePostsFragment();
                break;
            case 1:
                fragment = new ArticlesFragment();
                break;
        }

        assert fragment != null;
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "المفضلة";
            case 1:
                return "المقالات";
        }
        return null;
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}
