package eslam.emad.bloodbank.ui.viewModels.viewModelsFactory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import eslam.emad.bloodbank.ui.viewModels.SearchPostsViewModel;

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
