package com.meet.learnpython.ui.example;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.meet.learnpython.Editor;
import com.meet.learnpython.ExpandableListAdapter;
import com.meet.learnpython.ExpandableListData;
import com.meet.learnpython.MyAdManager;
import com.meet.learnpython.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


public class PyExampleFragment extends Fragment {
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> expandableListTitle;
    LinkedHashMap<String, List<String>> expandableListDetail;
    AdView mAdview;
    AdRequest adRequest;
    private MyAdManager adManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_py_example, container, false);

        mAdview = (AdView) root.findViewById(R.id.adView);

        adManager = new MyAdManager(getActivity());
        loadBannerAd();

        if (!adManager.hasPurchasedRemoveAds()) {
            int paddingInDp = 50;
            int paddingInPx = dpToPx(getActivity(), paddingInDp);
            ExpandableListView elt = (ExpandableListView) root.findViewById(R.id.expandableListView);
            elt.setPadding(0,0,0,paddingInPx);
        }else{
            int paddingInDp = 7;
            int paddingInPx = dpToPx(getActivity(), paddingInDp);
            ExpandableListView elt = (ExpandableListView) root.findViewById(R.id.expandableListView);
            elt.setPadding(0,0,0,paddingInPx);
        }

        expandableListView = (ExpandableListView) root.findViewById(R.id.expandableListView);
        expandableListDetail = ExpandableListData.getData();
        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new ExpandableListAdapter(getActivity(), expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);


        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                Intent i = new Intent(getActivity(), Editor.class);
                i.putExtra("key",""+groupPosition);
                i.putExtra("key1",""+childPosition);
                i.putExtra("key2",""+expandableListDetail.get(expandableListTitle.get(groupPosition)).get(childPosition));
                startActivity(i);

                return false;
            }
        });

        return root;
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