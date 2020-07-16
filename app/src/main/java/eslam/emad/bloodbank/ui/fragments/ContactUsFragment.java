package eslam.emad.bloodbank.ui.fragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.emad.bloodbank.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContactUsFragment extends Fragment {

    View view;
    @BindView(R.id.fragment_contact_us_message_subject_edit_text)
    EditText fragmentContactUsMessageSubjectEditText;
    @BindView(R.id.fragment_contact_us_message_text_edit_text)
    EditText fragmentContactUsMessageTextEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_contact_us, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.fragment_contact_us_number_tv)
    public void onFragmentContactUsNumberTvClicked() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + Uri.encode("01000000000")));
        startActivity(intent);
    }

    @OnClick(R.id.fragment_contact_us_mail_tv)
    public void onFragmentContactUsMailTvClicked() {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"eslamemad903@gmail.com"});
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getContext(), "You don't have an app for sending emails", Toast.LENGTH_SHORT).show();
        }

    }

    @OnClick(R.id.fragment_contact_us_facebook_imgv)
    public void onFragmentContactUsFacebookImgvClicked() {
        openSocialMediaIntent("https://www.facebook.com/profile.php?id=100018287207041", "com.facebook.katana");
    }

    @OnClick(R.id.fragment_contact_us_instgram_imgv)
    public void onFragmentContactUsInstgramImgvClicked() {
        openSocialMediaIntent("https://www.instagram.com", "com.instagram.android");
    }

    @OnClick(R.id.fragment_contact_us_twitter_imgv)
    public void onFragmentContactUsTwitterImgvClicked() {
        openSocialMediaIntent("https://twitter.com/eslamemad903", "com.twitter.android\"");
    }

    @OnClick(R.id.fragment_contact_us_yotube_imgv)
    public void onFragmentContactUsYotubeImgvClicked() {
        openSocialMediaIntent("https://www.youtube.com/channel/UCbu-6QV84SIOghTLSOfM9rA", "com.google.android.youtube");
    }

    void openSocialMediaIntent(String link, String packageName) {
        Uri uri = Uri.parse(link);
        Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);
        likeIng.setPackage(packageName);

        try {
            startActivity(likeIng);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse(link)));
        }
    }

    @OnClick(R.id.fragment_profile_enter_btn)
    public void onFragmentProfileEnterBtnClicked() {

        String msgSubject = fragmentContactUsMessageSubjectEditText.getText().toString();
        String msgText = fragmentContactUsMessageTextEditText.getText().toString();

        if (msgSubject.equals("") || msgText.equals("")) {
            Toast.makeText(getContext(), "Enter message subject and text", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "sent successfully", Toast.LENGTH_SHORT).show();

            //it must be a web request here

            fragmentContactUsMessageSubjectEditText.setText("");
            fragmentContactUsMessageTextEditText.setText("");
        }
    }

    @OnClick(R.id.fragment_contact_us_back_btn)
    public void onViewClicked() {
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_more_container,
                new MoreScreen()).addToBackStack(null).commit();
    }
}
