package com.meet.learnpython.ui.gallery;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.meet.learnpython.R;
import com.meet.learnpython.*;

public class GalleryFragment extends Fragment {
    ListView listView;
    AdView mAdview;
    AdRequest adRequest;
    GridView androidGridView;
    myDbAdapter helper;
    String data;
    CustomGridViewActivity adapterViewAndroid;
    String[] gridViewString = {
            "Quiz 1",
            "Quiz 2",
            "Quiz 3",
            "Quiz 4",
            "Quiz 5",
            "Quiz 6",
            "Quiz 7",
            "Quiz 8",
            "Quiz 9",
            "Quiz 10",
            "Quiz 11",
            "Quiz 12",

    } ;
    String[] gridViewImageId = {
            "Best Score: 0/10",
            "Best Score: 0/10",
            "Best Score: 0/10",
            "Best Score: 0/10",
            "Best Score: 0/10",
            "Best Score: 0/10",
            "Best Score: 0/10",
            "Best Score: 0/10",
            "Best Score: 0/10",
            "Best Score: 0/10",
            "Best Score: 0/10",
            "Best Score: 0/10",
    };
    private MyAdManager adManager;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        mAdview = (AdView) root.findViewById(R.id.adView);

        adManager = new MyAdManager(getActivity());
        loadBannerAd();

        if (!adManager.hasPurchasedRemoveAds()) {
            int paddingInDp = 50;
            int paddingInPx = dpToPx(getActivity(), paddingInDp);
            GridView gdv = (GridView) root.findViewById(R.id.grid_view_image_text);
            gdv.setPadding(0,0,0,paddingInPx);
        }else{
            int paddingInDp = 7;
            int paddingInPx = dpToPx(getActivity(), paddingInDp);
            GridView gdv = (GridView) root.findViewById(R.id.grid_view_image_text);
            gdv.setPadding(0,0,0,paddingInPx);
        }
/*
        listView = (ListView) root.findViewById(R.id.listView);

        String[] values = new String[] {
                "Quiz set 1",
                "Quiz set 2",
                "Quiz set 3",
                "Quiz set 4",
                "Quiz set 5",
                "Quiz set 6",
                "Quiz set 7",
                "Quiz set 8",
                "Quiz set 9",
                "Quiz set 10",
                "Quiz set 11",
                "Quiz set 12",
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                R.layout.listview, values);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition     = position;

                // ListView Clicked item value
                String  itemValue    = (String) listView.getItemAtPosition(position);

                if(itemPosition==0) {
                    Intent i1 = new Intent(getActivity(), py_quiz_1.class);
                    startActivity(i1);
                }else if(itemPosition==1) {
                    Intent i1 = new Intent(getActivity(), py_quiz_2.class);
                    startActivity(i1);
                }else if(itemPosition==2) {
                    Intent i1 = new Intent(getActivity(), py_quiz_3.class);
                    startActivity(i1);
                }else if(itemPosition==3) {
                    Intent i1 = new Intent(getActivity(), py_quiz_4.class);
                    startActivity(i1);
                }else if(itemPosition==4) {
                    Intent i1 = new Intent(getActivity(), py_quiz_5.class);
                    startActivity(i1);
                }else if(itemPosition==5) {
                    Intent i1 = new Intent(getActivity(), py_quiz_6.class);
                    startActivity(i1);
                }else if(itemPosition==6) {
                    Intent i1 = new Intent(getActivity(), py_quiz_7.class);
                    startActivity(i1);
                }else if(itemPosition==7) {
                    Intent i1 = new Intent(getActivity(), py_quiz_8.class);
                    startActivity(i1);
                }else if(itemPosition==8) {
                    Intent i1 = new Intent(getActivity(), py_quiz_9.class);
                    startActivity(i1);
                }else if(itemPosition==9) {
                    Intent i1 = new Intent(getActivity(), py_quiz_10.class);
                    startActivity(i1);
                }else if(itemPosition==10) {
                    Intent i1 = new Intent(getActivity(), py_quiz_11.class);
                    startActivity(i1);
                }else if(itemPosition==11) {
                    Intent i1 = new Intent(getActivity(), py_quiz_12.class);
                    startActivity(i1);
                }
            }

        });
        */
        helper = new myDbAdapter(getActivity());
        view_data();
        add_data();
        get_data();

        adapterViewAndroid = new CustomGridViewActivity(getActivity(), gridViewString, gridViewImageId);
        androidGridView=(GridView)root.findViewById(R.id.grid_view_image_text);
        androidGridView.setAdapter(adapterViewAndroid);
        androidGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int i, long id) {
                if(i==0)
                {
                    Intent i1 = new Intent(getActivity(), py_quiz_1.class);
                    startActivity(i1);
                }
                else if(i==1)
                {
                    Intent i1 = new Intent(getActivity(), py_quiz_2.class);
                    startActivity(i1);
                }
                else if(i==2)
                {
                    Intent i1 = new Intent(getActivity(), py_quiz_3.class);
                    startActivity(i1);
                }
                else if(i==3)
                {
                    Intent i1 = new Intent(getActivity(), py_quiz_4.class);
                    startActivity(i1);
                }
                else if(i==4)
                {
                    Intent i1 = new Intent(getActivity(), py_quiz_5.class);
                    startActivity(i1);
                }
                else if(i==5)
                {
                    Intent i1 = new Intent(getActivity(), py_quiz_6.class);
                    startActivity(i1);
                }
                else if(i==6)
                {
                    Intent i1 = new Intent(getActivity(), py_quiz_7.class);
                    startActivity(i1);
                }
                else if(i==7)
                {
                    Intent i1 = new Intent(getActivity(), py_quiz_8.class);
                    startActivity(i1);
                }
                else if(i==8)
                {
                    Intent i1 = new Intent(getActivity(), py_quiz_9.class);
                    startActivity(i1);
                }
                else if(i==9)
                {
                    Intent i1 = new Intent(getActivity(), py_quiz_10.class);
                    startActivity(i1);
                }
                else if(i==10)
                {
                    Intent i1 = new Intent(getActivity(), py_quiz_11.class);
                    startActivity(i1);
                }
                else if(i==11)
                {
                    Intent i1 = new Intent(getActivity(), py_quiz_12.class);
                    startActivity(i1);
                }


                //Toast.makeText(MainActivity.this, "GridView Item: " + gridViewString[+i], Toast.LENGTH_LONG).show();
            }
        });


        return root;
    }
    public void view_data()
    {
        data = helper.getData("1");
    }

    public void add_data()
    {
        if(data.length()==0) {
            helper.insertData("1", "0");
            helper.insertData("2", "0");
            helper.insertData("3", "0");
            helper.insertData("4", "0");
            helper.insertData("5", "0");
            helper.insertData("6", "0");
            helper.insertData("7", "0");
            helper.insertData("8", "0");
            helper.insertData("9", "0");
            helper.insertData("10", "0");
            helper.insertData("11", "0");
            helper.insertData("12", "0");
        }
    }
    public void get_data()
    {
        gridViewImageId[0]="Best Score: "+helper.getData("1")+"/10";
        gridViewImageId[1]="Best Score: "+helper.getData("2")+"/10";
        gridViewImageId[2]="Best Score: "+helper.getData("3")+"/10";
        gridViewImageId[3]="Best Score: "+helper.getData("4")+"/10";
        gridViewImageId[4]="Best Score: "+helper.getData("5")+"/10";
        gridViewImageId[5]="Best Score: "+helper.getData("6")+"/10";
        gridViewImageId[6]="Best Score: "+helper.getData("7")+"/10";
        gridViewImageId[7]="Best Score: "+helper.getData("8")+"/10";
        gridViewImageId[8]="Best Score: "+helper.getData("9")+"/10";
        gridViewImageId[9]="Best Score: "+helper.getData("10")+"/10";
        gridViewImageId[10]="Best Score: "+helper.getData("11")+"/10";
        gridViewImageId[11]="Best Score: "+helper.getData("12")+"/10";
    }

    public void onStart() {
        get_data();
        androidGridView.setAdapter(adapterViewAndroid);
        super.onStart();
    }

    @Override
    public void onResume() {
        get_data();
        androidGridView.setAdapter(adapterViewAndroid);
        super.onResume();
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
}
