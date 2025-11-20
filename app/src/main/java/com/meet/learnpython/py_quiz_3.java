package com.meet.learnpython;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowInsetsController;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.ads.nativetemplates.NativeTemplateStyle;
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.material.appbar.AppBarLayout;


import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class py_quiz_3 extends AppCompatActivity {
    AdView mAdview;
    AdRequest adRequest;
    TextView tv,high_score;
    Button submitbutton, quitbutton,checkbutton;
    RadioGroup radio_g;
    RadioButton rb1,rb2,rb3,rb4;

    String questions[] = {
            "In which part of memory does the system stores the parameter and local variables of funtion call?.",
            "Lambda is a function in python?.",
            "What is a variable defined outside a function referred to as?.",
            "What is the output of the following program?\n"+
                    "z = lambda x : x * x\n" +
                    "\n" +
                    "print(z(5))",
            "Which of the following is not a relational opeartor in Python?.",
            "Which of the following is incorrect?.",
            "Which statement is correct?",
            "If a='hi', b='hello' then which of the following operation would show 'hihello' as output?",
            "Suppose a list with name list1, contains 5 elements. You can get the 3rd element from the list using:",
            "How to copy one list to another in python?."
    };
    String answers[] = {"stack","True","global variable","25","=","List can only have elements of same data type.","List is mutable && Tuple is immutable","All of the above","list1[2]","list1[] = list2[:]"};
    String opt[] = {
            "heap","stack","Uninitialized data segment","None of the above",
            "True", "False","Lambda is a function in python but user can not use it.", "None of the above",
            "local variable","global variable", "static Variable","automatic variable",
            "6","25","0","error",
            ">=","<=","=","!=",
            "List is a built-in data structure in Python","List can only have elements of same data type.","List is mutable.","Index of lists can be positive as well as negative.",
            "List is mutable && Tuple is immutable","List is immutable && Tuple is mutable","Both are Mutable.", "Both are Immutable",
            " a+b","a+' '+b","a+\" \"+b","All of the above",
            "list1[3]","list1[2]","list1[-1]", "list[1]",
            "list1[ ] = list2[ ]","list1[ ] = list2","list1[ ] = list2[:]","list1 = list2"
    };
    int flag=0,flag2=0;
    Random random;
    myDbAdapter helper;
    ArrayList<Integer> arrayList;
    Vibrator vibe;
    public static int marks=0,correct=0,wrong=0;
    static int flag1=0;
    private MyAdManager adManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setupEdgeToEdge();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_py_quiz);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("Quiz 3");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RelativeLayout rootLayout = findViewById(R.id.quiz_main);
        AppBarLayout appBarLayout = findViewById(R.id.appBar);

        ViewCompat.setOnApplyWindowInsetsListener(rootLayout, (view, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars() | WindowInsetsCompat.Type.displayCutout());
            view.setPadding(systemBars.left,0,systemBars.right, systemBars.bottom);

            Insets appbarInsets = insets.getInsets(WindowInsetsCompat.Type.statusBars());
            appBarLayout.setPadding(0,appbarInsets.top,0,0);

            return WindowInsetsCompat.CONSUMED;
        });

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
        if (!adManager.hasPurchasedRemoveAds()) {
            AdLoader adLoader = new AdLoader.Builder(this, getResources().getString(R.string.PY_native))
                    .forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
                        private ColorDrawable background;@Override
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
        }else{
            template.setVisibility(View.GONE);
        }

        vibe=(Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        high_score=(TextView)findViewById(R.id.high_score);
        helper = new myDbAdapter(this);
        view_data();
        randon_num();

        final TextView score = (TextView) findViewById(R.id.textView4);

        submitbutton = (Button) findViewById(R.id.button3);
        quitbutton = (Button) findViewById(R.id.buttonquit);
        tv = (TextView) findViewById(R.id.tvque);

        flag=arrayList.get(flag2);
        radio_g = (RadioGroup) findViewById(R.id.answersgrp);
        rb1 = (RadioButton) findViewById(R.id.radioButton);
        rb2 = (RadioButton) findViewById(R.id.radioButton2);
        rb3 = (RadioButton) findViewById(R.id.radioButton3);
        rb4 = (RadioButton) findViewById(R.id.radioButton4);
        tv.setText(flag2+1+". "+questions[flag]);
        rb1.setText(opt[flag * 4]);
        rb2.setText(opt[flag * 4 + 1]);
        rb3.setText(opt[flag * 4 + 2]);
        rb4.setText(opt[flag * 4 + 3]);

        rb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton uans = (RadioButton) findViewById(radio_g.getCheckedRadioButtonId());
                String ansText = uans.getText().toString();
                if (ansText.equals(answers[flag])) {
                    rb1.setBackgroundResource(R.drawable.radiobutton_border_1);
                    rb2.setEnabled(false);
                    rb3.setEnabled(false);
                    rb4.setEnabled(false);
                } else {
                    rb1.setBackgroundResource(R.drawable.radiobutton_border_2);
                    rb2.setEnabled(false);
                    rb3.setEnabled(false);
                    rb4.setEnabled(false);
                    vibrate();
                }
            }
        });
        rb2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton uans = (RadioButton) findViewById(radio_g.getCheckedRadioButtonId());
                String ansText = uans.getText().toString();
                if (ansText.equals(answers[flag])) {
                    rb2.setBackgroundResource(R.drawable.radiobutton_border_1);
                    rb1.setEnabled(false);
                    rb3.setEnabled(false);
                    rb4.setEnabled(false);
                }
                else {
                    rb2.setBackgroundResource(R.drawable.radiobutton_border_2);
                    rb1.setEnabled(false);
                    rb3.setEnabled(false);
                    rb4.setEnabled(false);
                    vibrate();
                }
            }
        });
        rb3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton uans = (RadioButton) findViewById(radio_g.getCheckedRadioButtonId());
                String ansText = uans.getText().toString();
                if (ansText.equals(answers[flag])) {
                    rb3.setBackgroundResource(R.drawable.radiobutton_border_1);
                    rb2.setEnabled(false);
                    rb1.setEnabled(false);
                    rb4.setEnabled(false);
                } else {
                    rb3.setBackgroundResource(R.drawable.radiobutton_border_2);
                    rb2.setEnabled(false);
                    rb1.setEnabled(false);
                    rb4.setEnabled(false);
                    vibrate();
                }
            }
        });
        rb4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton uans = (RadioButton) findViewById(radio_g.getCheckedRadioButtonId());
                String ansText = uans.getText().toString();
                if (ansText.equals(answers[flag])) {
                    rb4.setBackgroundResource(R.drawable.radiobutton_border_1);
                    rb2.setEnabled(false);
                    rb3.setEnabled(false);
                    rb1.setEnabled(false);
                } else {
                    rb4.setBackgroundResource(R.drawable.radiobutton_border_2);
                    rb2.setEnabled(false);
                    rb3.setEnabled(false);
                    rb1.setEnabled(false);
                    vibrate();
                }
            }
        });

        submitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radio_g.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(getApplicationContext(), "Please select option", Toast.LENGTH_SHORT).show();
                    return;
                }
                RadioButton uans = (RadioButton) findViewById(radio_g.getCheckedRadioButtonId());
                String ansText = uans.getText().toString();
                if (ansText.equals(answers[flag])) {
                    correct++;
                } else {
                    wrong++;
                }
                rb1.setBackgroundResource(R.drawable.radiobutton_border);
                rb2.setBackgroundResource(R.drawable.radiobutton_border);
                rb3.setBackgroundResource(R.drawable.radiobutton_border);
                rb4.setBackgroundResource(R.drawable.radiobutton_border);
                rb1.setEnabled(true);
                rb2.setEnabled(true);
                rb3.setEnabled(true);
                rb4.setEnabled(true);

                flag2++;

                if (score != null)
                    score.setText(correct + "/10");

                if (flag2 < questions.length) {
                    flag=arrayList.get(flag2);
                    tv.setText(flag2+1+". "+questions[flag]);
                    rb1.setText(opt[flag * 4]);
                    rb2.setText(opt[flag * 4 + 1]);
                    rb3.setText(opt[flag * 4 + 2]);
                    rb4.setText(opt[flag * 4 + 3]);
                } else {
                    marks = correct;

                    String data = helper.getData("3");
                    int a=Integer.parseInt(data.trim());
                    if(a<marks)
                    {
                        helper.updateName("3",marks+"");
                    }

                    if (marks >= 7) {
                        showDialog("Your Score: " + marks + "/10", "Congratulation your Score is above or equal to 70%.");
                        marks = correct = 0;
                    } else {
                        showDialog("Your Score: " + marks + "/10", "Bad Luck your Score is below 70%.");
                        marks = correct = 0;
                    }

                }
                radio_g.clearCheck();
            }
        });

        quitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                py_quiz_3.correct = 0;
                py_quiz_3.marks = 0;
            }
        });
    }
    public void vibrate()
    {
        if (Build.VERSION.SDK_INT >= 26) {
            vibe.vibrate(VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            vibe.vibrate(100);
        }
    }
    public void view_data()
    {
        String data = helper.getData("3");
        high_score.setText(data+"/10");
    }
    public void randon_num()
    {
        random = new Random();
        arrayList = new ArrayList<Integer>();

        while (arrayList.size() < 10) {
            int a = random.nextInt(10);
            if (!arrayList.contains(a)) {
                arrayList.add(a);
            }
        }
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                py_quiz_3.correct = 0;
                py_quiz_3.marks = 0;
                this.finish();
                return true;
            default:
                py_quiz_3.correct = 0;
                py_quiz_3.marks = 0;
                return super.onOptionsItemSelected(item);
        }
    }

    public void onBackPressed() {
        py_quiz_3.correct = 0;
        py_quiz_3.marks = 0;
        super.onBackPressed();
    }

    public void showDialog(String title, String des) {

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button cancelBTN = (Button) dialog.findViewById(R.id.cancelBTN);
        TextView tvTitle = (TextView) dialog.findViewById(R.id.title);
        TextView tvDescription = (TextView) dialog.findViewById(R.id.description);
        ImageView ivIcon = (ImageView) dialog.findViewById(R.id.icon);

        tvTitle.setText(title);
        tvDescription.setText(des);


        cancelBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                dialog.dismiss();
            }
        });

        dialog.show();
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
