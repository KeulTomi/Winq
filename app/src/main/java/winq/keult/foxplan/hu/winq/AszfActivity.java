package winq.keult.foxplan.hu.winq;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class AszfActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Fullscreent adunk az activitynek, hogy ne látszódjon a notificationbar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_aszf);

        WebView webView = (WebView) findViewById(R.id.aszf_web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("http://appwinq.com/m_aszf");

        TextView backText = (TextView) findViewById(R.id.aszf_back_btn);
        backText.setOnClickListener(this);

        ScaleHelper.scaleViewAndChildren(findViewById(R.id.aszf_root), Winq.getScaleX(), Winq.getScaleY());
    }

    @Override
    public void onClick(View v) {
        finish();
        overridePendingTransition(R.anim.activity_stay, R.anim.activity_slide_down);
    }
}
