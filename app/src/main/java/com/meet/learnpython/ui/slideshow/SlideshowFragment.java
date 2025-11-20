package com.meet.learnpython.ui.slideshow;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.meet.learnpython.ExpandableListAdapter;
import com.meet.learnpython.ExpandableListDataPump;
import com.meet.learnpython.MyAdManager;
import com.meet.learnpython.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SlideshowFragment extends Fragment {
    AdView mAdview;
    AdRequest adRequest;

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List expandableListTitle;
    HashMap<String, List<String>> expandableListDetail;
    private MyAdManager adManager;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);

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
        expandableListDetail = ExpandableListDataPump.getData();
        expandableListTitle = new ArrayList(expandableListDetail.keySet());
        expandableListAdapter = new ExpandableListAdapter(getActivity(), expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {

            }
        });

        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {


            }
        });

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

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
