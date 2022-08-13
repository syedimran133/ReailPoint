package com.reail.point;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class UrlWebView extends Fragment {

    public WebView mWebView;
    private View v;
    private TextView title;
    private LinearLayout main;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_web_view, container, false);
        title = v.findViewById(R.id.tvTitle);
        title.setText(Constant.TITLE);
        main = v.findViewById(R.id.main);
        v.findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FlowOrganizer.getInstance().popUpBackTo(1);
            }
        });
        mWebView = v.findViewById(R.id.web_view);
        if (Constant.TITLE.equalsIgnoreCase("About Us")) {
            mWebView.loadUrl("https://reliapoint.co.uk/");
        } else if (Constant.TITLE.equalsIgnoreCase("Terms & Conditions")) {
            mWebView.loadUrl("https://reliapoint.co.uk/terms/");
        } else if (Constant.TITLE.equalsIgnoreCase("Privacy Policy")) {
            mWebView.loadUrl("https://reliapoint.co.uk/privacy-policy/");
        }
        // Enable Javascript
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        // Force links and redirects to open in the WebView instead of in a browser
        mWebView.setWebViewClient(new WebViewClient());

        return v;
    }
}
