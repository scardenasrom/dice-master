package com.herkiusdev.dicemaster.fragment;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.widget.ImageButton;

import com.herkiusdev.dicemaster.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.fragment_about)
public class AboutFragment extends Fragment {

    @ViewById(R.id.card_author_button)
    ImageButton exampleButton;

    @AfterViews
    public void initViews() {

    }

    @Click(R.id.card_author_button)
    public void authorButtonClick() {
        Snackbar.make(exampleButton, "Curriculum", Snackbar.LENGTH_SHORT).show();
    }

    @Click(R.id.card_photos_button)
    public void photosButtonClick() {
        Snackbar.make(exampleButton, "Fotografías", Snackbar.LENGTH_SHORT).show();
    }

    @Click(R.id.card_fonts_button)
    public void fontsButtonClick() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getText(R.string.card_fonts_url).toString()));
        startActivity(browserIntent);
    }

    @Click(R.id.card_arc_layout_button)
    public void arcLayoutButtonClick() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getText(R.string.card_arc_layout_url).toString()));
        startActivity(browserIntent);
    }

    @Click(R.id.card_calligraphy_button)
    public void calligraphyButtonClick() {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getText(R.string.card_calligraphy_url).toString()));
        startActivity(browserIntent);
    }

}
