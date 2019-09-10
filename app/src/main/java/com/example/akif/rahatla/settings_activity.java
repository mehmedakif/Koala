package com.example.akif.rahatla;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;


public class settings_activity extends AppCompatActivity implements View.OnClickListener, RewardedVideoAdListener {
    private RewardedVideoAd mRewardedVideoAd;
    private AdView mAdView2;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        MobileAds.initialize(this, "\n" + "ca-app-pub-1435076129396602~1499361308");
        Button home_butonu = findViewById(R.id.home_butonu);
        Button reklam_butonu = findViewById(R.id.reklam_izle);
        reklam_butonu.setOnClickListener(this);
        reklam_butonu.setVisibility(View.INVISIBLE);

        mAdView2 = findViewById(R.id.adView2);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView2.loadAd(adRequest);

        home_butonu.setOnClickListener(this);
// Use an activity context to get the rewarded video instance.
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(this);

    }

    @Override
    public void onStart(){
        super.onStart();
        loadRewardedVideoAd();
    }

    public void onClick(View view){
        switch(view.getId()) {
            case R.id.home_butonu:
                openHome();
                break;
            case R.id.reklam_izle:
                    mRewardedVideoAd.show();
                break;
        }

    }

    private void loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd("ca-app-pub-1435076129396602/8883537395",
                new AdRequest.Builder().build());
    }

        public void openHome () {
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void onRewardedVideoAdLoaded() {
        Button reklam_butonu = findViewById(R.id.reklam_izle);
        reklam_butonu.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoAdClosed() {
        Button reklam_butonu = findViewById(R.id.reklam_izle);
        reklam_butonu.setVisibility(View.INVISIBLE);
        loadRewardedVideoAd();
    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
    }

    @Override
    public void onRewardedVideoAdLeftApplication() {

    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {

        loadRewardedVideoAd();
    }

    @Override
    public void onRewardedVideoCompleted() {

        loadRewardedVideoAd();
    }
}