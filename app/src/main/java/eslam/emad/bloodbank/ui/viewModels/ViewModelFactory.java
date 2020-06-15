package eslam.emad.bloodbank.ui.viewModels;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ViewModelFactory implements ViewModelProvider.Factory  {

    String keyWord;

    public ViewModelFactory(String keyWord) {
        this.keyWord = keyWord;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new SearchPostsViewModel(keyWord);
    }
}
