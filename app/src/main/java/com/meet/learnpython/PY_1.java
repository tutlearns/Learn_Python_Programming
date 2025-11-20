package com.meet.learnpython;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowInsetsController;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.material.appbar.AppBarLayout;


import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PY_1 extends AppCompatActivity {
    static int flag=0;
    AdView mAdview;
    AdRequest adRequest;
    String str1,str2,str3;
    private InterstitialAd mInterstitialAd;
    private MyAdManager adManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupEdgeToEdge();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_p_y_1);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle(getIntent().getExtras().getString("key1"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RelativeLayout rootLayout = findViewById(R.id.py_main);
        AppBarLayout appBarLayout = findViewById(R.id.appBar);

        ViewCompat.setOnApplyWindowInsetsListener(rootLayout, (view, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout());
            view.setPadding(systemBars.left,0,systemBars.right, systemBars.bottom);

            Insets appbarInsets = insets.getInsets(WindowInsetsCompat.Type.statusBars());
            appBarLayout.setPadding(0,appbarInsets.top,0,0);

            return WindowInsetsCompat.CONSUMED;
        });

        String str = getIntent().getExtras().getString("key");
        if(str.equals("0")){
            str1="1_Pyintro.html";
            str2="1_Pyintro1.html";
        }else if(str.equals("1")){
            str1="2_Pyfeature.html";
            str2="2_Pyfeature1.html";
        }else if(str.equals("2")){
            str1="3_Pyenvironment.html";
            str2="3_Pyenvironment1.html";
        }else if(str.equals("3")){
            str1="4_Pyfirstexample.html";
            str2="4_Pyfirstexample1.html";
        }else if(str.equals("4")){
            str1="5_Pyvariable.html";
            str2="5_Pyvariable1.html";
        }else if(str.equals("5")){
            str1="6_Pyinputoutput.html";
            str2="6_Pyinputoutput1.html";
        }else if(str.equals("6")){
            str1="7_Pycomment.html";
            str2="7_Pycomment1.html";
        }else if(str.equals("7")){
            str1="8_Pydatatypes.html";
            str2="8_Pydatatypes1.html";
        }else if(str.equals("8")){
            str1="8_typeconversion.html";
            str2="8_typeconversion1.html";
        }else if(str.equals("9")){
            str1="9_Pykeywords.html";
            str2="9_Pykeywords1.html";
        }else if(str.equals("10")){
            str1="10_Pyliterals.html";
            str2="10_Pyliterals1.html";
        }else if(str.equals("11")){
            str1="11_operator.html";
            str2="11_operator1.html";
        }else if(str.equals("12")){
            str1="12_ifelse.html";
            str2="12_ifelse1.html";
        }else if(str.equals("13")){
            str1="13_loop.html";
            str2="13_loop1.html";
        }else if(str.equals("14")){
            str1="14_break.html";
            str2="14_break1.html";
        }else if(str.equals("15")){
            str1="15_pass.html";
            str2="15_pass1.html";
        }else if(str.equals("16")){
            str1="16_number.html";
            str2="16_number1.html";
        }else if(str.equals("17")){
            str1="17_list.html";
            str2="17_list1.html";
        }else if(str.equals("18")){
            str1="18_tuple.html";
            str2="18_tuple1.html";
        }else if(str.equals("19")){
            str1="19_string.html";
            str2="19_string1.html";
        }else if(str.equals("20")){
            str1="20_set.html";
            str2="20_set1.html";
        }else if(str.equals("21")){
            str1="21_dictionary.html";
            str2="21_dictionary1.html";
        }else if(str.equals("22")){
            str1="22_Pyfunction.html";
            str2="22_Pyfunction1.html";
        }else if(str.equals("23")){
            str1="23_Pyrecursion.html";
            str2="23_Pyrecursion1.html";
        }else if(str.equals("24")){
            str1="24_Pylamdafunction.html";
            str2="24_Pylamdafunction1.html";
        }else if(str.equals("25")){
            str1="25_Pyvariablescope.html";
            str2="25_Pyvariablescope1.html";
        }else if(str.equals("26")){
            str1="26_Pymodule.html";
            str2="26_Pymodule1.html";
        }else if(str.equals("27")){
            str1="27_Pypackages.html";
            str2="27_Pypackages1.html";
        }else if(str.equals("28")){
            str1="28_Pyfilehandling.html";
            str2="28_Pyfilehandling1.html";
        }else if(str.equals("29")){
            str1="29_Pyexceptionhandling.html";
            str2="29_Pyexceptionhandling1.html";
        }else if(str.equals("30")){
            str1="30_Pyuserdefinedexception.html";
            str2="30_Pyuserdefinedexception1.html";
        }else if(str.equals("31")){
            str1="31_Pydate.html";
            str2="31_Pydate1.html";
        }else if(str.equals("32")){
            str1="32_Pymath.html";
            str2="32_Pymath1.html";
        }else if(str.equals("33")){
            str1="33_Pyregex.html";
            str2="33_Pyregex1.html";
        }else if(str.equals("34")){
            str1="34_Pyclass.html";
            str2="34_Pyclass1.html";
        }else if(str.equals("35")){
            str1="35_Pyobject.html";
            str2="35_Pyobject1.html";
        }else if(str.equals("36")){
            str1="36_Pyconstructor.html";
            str2="36_Pyconstructor1.html";
        }else if(str.equals("37")){
            str1="37_Pyoverloading.html";
            str2="37_Pyoverloading1.html";
        }else if(str.equals("38")){
            str1="38_pyinheritance.html";
            str2="38_pyinheritance1.html";
        }else if(str.equals("39")){
            str1="39_Pyencapsulation.html";
            str2="39_Pyencapsulation1.html";
        }else if(str.equals("40")){
            str1="40_Pyabstraction.html";
            str2="40_Pyabstraction1.html";
        }else if(str.equals("41")){
            str1="41_Pypolynorphism.html";
            str2="41_Pypolynorphism1.html";
        }else if(str.equals("42")){
            str1="42_Pythread.html";
            str2="42_Pythread1.html";
        }else if(str.equals("43")){
            str1="43_Pysocket.html";
            str2="43_Pysocket1.html";
        }else if(str.equals("44")){
            str1="44_iterator.html";
            str2="44_iterator1.html";
        }else if(str.equals("45")){
            str1="45_generator.html";
            str2="45_generator1.html";
        }else if(str.equals("46")){
            str1="46_Pyclosure.html";
            str2="46_Pyclosure1.html";
        }else if(str.equals("47")){
            str1="47_Pydecorator.html";
            str2="47_Pydecorator1.html";
        }else if(str.equals("48")){
            str1="48_Pyproperty.html";
            str2="48_Pyproperty1.html";
        }else if(str.equals("49")){
            str1="searching.html";
            str2="searching_1.html";
        }else if(str.equals("50")){
            str1="bubble_sort.html";
            str2="bubble_sort_1.html";
        }else if(str.equals("51")){
            str1="insertion_sort.html";
            str2="insertion_sort_1.html";
        }else if(str.equals("52")){
            str1="merge_sort.html";
            str2="merge_sort_1.html";
        }else if(str.equals("53")){
            str1="selection_sort.html";
            str2="selection_sort_1.html";
        }

        WebView webView = (WebView) findViewById(R.id.webView);
        webView.loadUrl("file:///android_asset/"+str1);
        WebSettings webSettings = webView.getSettings();
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setDisplayZoomControls(false);

        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        webView.setLongClickable(false);

        WebView webView1 = (WebView) findViewById(R.id.webView1);
        webView1.loadUrl("file:///android_asset/"+str2);
        WebSettings webSettings1 = webView.getSettings();
        webView1.setWebViewClient(new WebViewClient());
        webView1.setWebChromeClient(new WebChromeClient());
        webView1.getSettings().setJavaScriptEnabled(true);
        webSettings1.setDomStorageEnabled(true);
        webView1.getSettings().setBuiltInZoomControls(true);
        webView1.getSettings().setDisplayZoomControls(false);

        webView1.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return true;
            }
        });
        webView1.setLongClickable(false);

        mAdview = (AdView) findViewById(R.id.adView);

        adManager = new MyAdManager(this);
        loadBannerAd();

        if (!adManager.hasPurchasedRemoveAds()) {
            int paddingInDp = 50;
            int paddingInPx = dpToPx(this, paddingInDp);
            ScrollView sc = (ScrollView) findViewById(R.id.scrollView);
            sc.setPadding(0,0,0,paddingInPx);
        }else{
            int paddingInDp = 7;
            int paddingInPx = dpToPx(this, paddingInDp);
            ScrollView sc = (ScrollView) findViewById(R.id.scrollView);
            sc.setPadding(0,0,0,paddingInPx);
        }

        TemplateView template = findViewById(R.id.my_template);
        TemplateView template1 = findViewById(R.id.my_template1);

        if (!adManager.hasPurchasedRemoveAds()) {
            AdLoader adLoader = new AdLoader.Builder(this, getResources().getString(R.string.PY_native))
                    .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                        private ColorDrawable background;

                        @Override
                        public void onNativeAdLoaded(NativeAd nativeAd) {
                            NativeTemplateStyle styles = new
                                    NativeTemplateStyle.Builder().withMainBackgroundColor(background).build();
                            template.setStyles(styles);
                            template.setNativeAd(nativeAd);
                            template.setVisibility(View.VISIBLE);
                        }
                    })
                    .build();
            adLoader.loadAd(adRequest);

            AdLoader adLoader1 = new AdLoader.Builder(this, getResources().getString(R.string.PY_native1))
                    .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                        private ColorDrawable background;

                        @Override
                        public void onNativeAdLoaded(NativeAd nativeAd) {
                            NativeTemplateStyle styles = new
                                    NativeTemplateStyle.Builder().withMainBackgroundColor(background).build();
                            template1.setStyles(styles);
                            template1.setNativeAd(nativeAd);
                            template1.setVisibility(View.VISIBLE);
                        }
                    })
                    .build();
            adLoader1.loadAd(adRequest);
        }
        else{
            template.setVisibility(View.GONE);
            template1.setVisibility(View.GONE);
        }

        if (!adManager.hasPurchasedRemoveAds()) {
            showAd();

            ScheduledExecutorService scheduler =
                    Executors.newSingleThreadScheduledExecutor();
            scheduler.scheduleWithFixedDelay(new Runnable() {

                public void run() {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            if (flag == 0) {
                                if (mInterstitialAd != null) {
                                    mInterstitialAd.show(PY_1.this);
                                }
                                showAd();
                            }
                        }
                    });

                }
            }, 150, 150, TimeUnit.SECONDS);
        }

    }
    public void showAd(){
        InterstitialAd.load(this,getResources().getString(R.string.PY_interstitial), adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        mInterstitialAd = interstitialAd;
                    }
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        mInterstitialAd = null;
                    }
                });
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void onStart() {
        super.onStart();
        flag=0;
    }
    protected void onRestart() {
        super.onRestart();
        flag=0;
    }
    protected void onResume() {
        super.onResume();
        flag=0;
    }
    public void onStop() {
        super.onStop();
        flag=1;
    }
    protected void onDestroy() {
        super.onDestroy();
        flag=1;
    }
    protected void onPause() {
        super.onPause();
        flag=1;
    }
    private void loadBannerAd() {
        if (!adManager.hasPurchasedRemoveAds()) {
            // Ads are enabled, load the banner ad
            adRequest=new AdRequest.Builder().build();
            mAdview.loadAd(adRequest);
            mAdview.setVisibility(View.VISIBLE);
        } else {
            // Ads are disabled, hide the banner ad
            mAdview.setVisibility(View.GONE);
        }
    }
    public int dpToPx(Context context, int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
    private void setupEdgeToEdge() {
        WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView()).setAppearanceLightNavigationBars(false);
        WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView()).setAppearanceLightStatusBars(false);
        Window window = getWindow();

        // Set status bar color as per theme
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.setStatusBarColor(getColor(R.color.colorPrimaryDark));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // Prevent Android from forcing white nav background for 3-button navigation
            window.setNavigationBarContrastEnforced(false);

            WindowInsetsController controller = window.getInsetsController();
            if (controller != null) {
                // Adjust navigation bar icons (light/dark) based on your theme
                controller.setSystemBarsAppearance(
                        WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS,
                        WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS
                );
            }
        } else {
            // For Android 10 and below → ensure nav bar is edge-to-edge
            window.getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            );
        }
    }
}