package com.example.myzakat;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class AboutActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);


            TextView appWebsiteTextView = findViewById(R.id.textViewAppWebsite);

            SpannableString spannableString = new SpannableString(appWebsiteTextView.getText());
            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    String githubUrl = "https://github.com/shahrilazri123";
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(githubUrl));
                    startActivity(intent);
                }
            };

        spannableString.setSpan(clickableSpan, 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        appWebsiteTextView.setText(spannableString);
        appWebsiteTextView.setMovementMethod(android.text.method.LinkMovementMethod.getInstance());
        }

    }




