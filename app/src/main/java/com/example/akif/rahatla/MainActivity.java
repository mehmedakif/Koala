package com.example.akif.rahatla;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.AdView;
import android.animation.ObjectAnimator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.media.MediaPlayer;
import android.widget.Button;
import android.view.View;
import android.view.animation.LinearInterpolator;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;




public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // xml dosyasindaki objelerin tanimlamasini ve ilanini gerceklestirdik.
    private SeekBar orman_ses_seek;
    private SeekBar nehir_ses_seek;
    private SeekBar ates_ses_seek;
    private SeekBar ney_ses_seek;
    private SeekBar rain_ses_seek;
    private SeekBar sea_ses_seek;
    private SeekBar thunder_ses_seek;
    private MediaPlayer ormansesiM;
    private MediaPlayer nehirsesiM;
    private MediaPlayer atessesiM;
    private MediaPlayer neysesiM;
    private MediaPlayer rainsesiM;
    private MediaPlayer seasesiM;
    private MediaPlayer thundersesiM;

    private InterstitialAd mInterstitialAd;
    private RewardedVideoAd mRewardedVideoAd;

    ImageView help;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeVolumeController();

        //Buradaki satirlar serverden yeni reklam cekilmesini saglayan fonksiyon.
        MobileAds.initialize(this, "\n" + "ca-app-pub-1435076129396602~1499361308");
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //Header olan fotografin tanimlanmasi ve one getirilme fonksiyonu
        ImageView header_view = findViewById(R.id.header);
        header_view.bringToFront();

        ormansesiM = MediaPlayer.create(this, R.raw.orman);
        nehirsesiM = MediaPlayer.create(this, R.raw.nehir);
        atessesiM = MediaPlayer.create(this, R.raw.ates);
        neysesiM = MediaPlayer.create(this, R.raw.sufi);
        rainsesiM = MediaPlayer.create(this, R.raw.rain);
        seasesiM = MediaPlayer.create(this, R.raw.sea);
        thundersesiM = MediaPlayer.create(this, R.raw.thunder);

        rainsesiM.setLooping(true);
        nehirsesiM.setLooping(true);
        atessesiM.setLooping(true);
        neysesiM.setLooping(true);
        seasesiM.setLooping(true);
        ormansesiM.setLooping(true);
        thundersesiM.setLooping(true);

        ImageView image1 = findViewById(R.id.orman_foto);
        ImageView image2 = findViewById(R.id.nehir_foto);
        ImageView image3 = findViewById(R.id.ates_foto);
        ImageView image4 = findViewById(R.id.ney_foto);
        ImageView image5 = findViewById(R.id.sea_foto);
        ImageView image6 = findViewById(R.id.rain_foto);
        ImageView image7 = findViewById(R.id.thunder_foto);
        image1.setOnClickListener(this);
        image2.setOnClickListener(this);
        image3.setOnClickListener(this);
        image4.setOnClickListener(this);
        image5.setOnClickListener(this);
        image6.setOnClickListener(this);
        image7.setOnClickListener(this);

        help = findViewById(R.id.help_screen);

        Button button_yardim = findViewById(R.id.yardim_butonu);
        Button button_ayar = findViewById(R.id.ayar_butonu);
        Button button_durdur = findViewById(R.id.durdur_butonu);

        //button_yardim.setOnClickListener(this);
        button_durdur.setOnClickListener(this);
        button_ayar.setOnClickListener(this);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-1435076129396602/7474603790");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.show();

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {

                mInterstitialAd.show();
            }

        });

        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        loadRewardedVideoAd();
    }


    boolean isPlaying = false;
    boolean ormanState=false;
    boolean nehirState=false;
    boolean atesState=false;
    boolean neyState=false;
    boolean rainState=false;
    boolean seaState=false;
    boolean thunderState=false;
    boolean helpState=false;

    //Ekranimizdaki tum tuslarin tepkilerini yoneten onClick fonksiynumuz.
    @Override
    public void onClick(View v){
        // getId ile hangi tusa tiklandigini taniyoruz ve ona gore islem yaptiriyoruz.
    switch(v.getId()){
        //Durdurmaya yarayan butona basildiginda yapilacak islemler.
        case R.id.durdur_butonu:
            if (!isPlaying) {
                if(ormanState){ormansesiM.start();}
                if(nehirState){nehirsesiM.start();}
                if(neyState){neysesiM.start();}
                if(rainState){rainsesiM.start();}
                if(atesState){atessesiM.start();}
                if(seaState){seasesiM.start();}
                if(thunderState){thundersesiM.start();}
                isPlaying=true;


            }else{
                if(neyState){neysesiM.pause();}
                if(ormanState){ormansesiM.pause();}
                if(nehirState){nehirsesiM.pause();}
                if(rainState){rainsesiM.pause();}
                if(seaState){seasesiM.pause();}
                if(atesState){atessesiM.pause();}
                if(thunderState){thundersesiM.pause();}
                isPlaying=false;
                /*Button dur = findViewById(R.id.durdur_butonu);
                dur.setBackgroundResource(R.drawable.ic_play_arrow_black_24dp);*/
                stopAnimation(orman_ses_seek,orman_ses_seek.getProgress());
                stopAnimation(nehir_ses_seek,nehir_ses_seek.getProgress());
                stopAnimation(ates_ses_seek,ates_ses_seek.getProgress());
                stopAnimation(ney_ses_seek,ney_ses_seek.getProgress());
                stopAnimation(rain_ses_seek,rain_ses_seek.getProgress());
                stopAnimation(sea_ses_seek,sea_ses_seek.getProgress());
                stopAnimation(thunder_ses_seek,thunder_ses_seek.getProgress());

            }
            break;


        //Yardim butonuna basildiginda yapilacak islemler.
        /*case R.id.yardim_butonu:
            if(!helpState){
            help.setVisibility(View.VISIBLE);
            help.bringToFront();
            helpState=true;
                break;}
             else{
                help.setVisibility(View.INVISIBLE);
                helpState=false;
                break;
            }*/

        case R.id.ayar_butonu:
            //Intent gecis = new Intent(this,settings_activity.class);
            //startActivity(gecis);
            //overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            mRewardedVideoAd.show();
            break;
        case R.id.orman_foto:
            if(!ormanState){
            ormansesiM.setVolume(0.5f,0.5f);
            ormansesiM.start();
            startAnimation(orman_ses_seek);
            ormanState=true;
            isPlaying=true;
            break;}
            else{
                ormansesiM.pause();
                stopAnimation(orman_ses_seek,orman_ses_seek.getProgress());
                ormanState=false;
                break;
            }
        case R.id.nehir_foto:
            if(!nehirState){
                nehirsesiM.setVolume(0.5f,0.5f);
                nehirsesiM.start();
                startAnimation(nehir_ses_seek);
                nehirState=true;
                isPlaying=true;
                break;}
            else{
                nehirsesiM.pause();
                stopAnimation(nehir_ses_seek,nehir_ses_seek.getProgress());
                nehirState=false;
                break;
            }

        case R.id.ates_foto:
            if(!atesState){
                atessesiM.setVolume(0.5f,0.5f);
                atessesiM.start();
                startAnimation(ates_ses_seek);
                atesState=true;
                isPlaying=true;
                break;}
            else{
                atessesiM.pause();
                stopAnimation(ates_ses_seek,ates_ses_seek.getProgress());
                atesState=false;
                break;
            }

        case R.id.ney_foto:
            if(!neyState){
                neysesiM.setVolume(0.5f,0.5f);
                neysesiM.start();
                startAnimation(ney_ses_seek);
                neyState=true;
                isPlaying=true;
                break;}
            else{
                neysesiM.pause();
                stopAnimation(ney_ses_seek,ney_ses_seek.getProgress());
                neyState=false;
                break;
            }
        case R.id.rain_foto:
            if(!rainState){
                rainsesiM.setVolume(0.5f,0.5f);
                rainsesiM.start();
                startAnimation(rain_ses_seek);
                rainState=true;
                isPlaying=true;
                break;}
            else{
                rainsesiM.pause();
                stopAnimation(rain_ses_seek,rain_ses_seek.getProgress());
                rainState=false;
                break;
            }
        case R.id.sea_foto:
            if(!seaState){
                seasesiM.setVolume(0.5f,0.5f);
                seasesiM.start();
                startAnimation(sea_ses_seek);
                seaState=true;
                isPlaying=true;
                break;}
            else{
                seasesiM.pause();
                stopAnimation(sea_ses_seek,sea_ses_seek.getProgress());
                seaState=false;
                break;
            }
        case R.id.thunder_foto:
            if(!thunderState){
                thundersesiM.setVolume(0.5f,0.5f);
                thundersesiM.start();
                startAnimation(thunder_ses_seek);
                thunderState=true;
                isPlaying=true;
                break;}
            else{
                thundersesiM.pause();
                stopAnimation(thunder_ses_seek,thunder_ses_seek.getProgress());
                thunderState=false;
                break;
            }


    }

}
    private void startAnimation(ProgressBar mProgressBar){
        ObjectAnimator progressAnimator = ObjectAnimator.ofInt(mProgressBar, "progress", 0, 50);
        progressAnimator.setDuration(700);
        progressAnimator.setInterpolator(new LinearInterpolator());
        progressAnimator.start();
    }
    private void stopAnimation(ProgressBar mProgressBar,int a){
        ObjectAnimator progressAnimator = ObjectAnimator.ofInt(mProgressBar, "progress", a, 0);
        progressAnimator.setDuration(700);
        progressAnimator.setInterpolator(new LinearInterpolator());
        progressAnimator.start();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        kill_activity();
    }
    void kill_activity()
    {
        finish();
    }
    //Tum sesleri kontrol eden fonksiyon.
    private void initializeVolumeController () {

    //-----------------------------
    //Orman sesi icin fonksiyonumuz.
        try {
            orman_ses_seek = findViewById(R.id.orman_ses_seek);
            orman_ses_seek.setMax(100);
            orman_ses_seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()

            {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                    float log1 = (float) (Math.log(100 - progress) / Math.log(100));
                    ormansesiM.setVolume(1 - log1, 1 - log1);
                    ormansesiM.start();
                    ormanState=true;
                    isPlaying=true;

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    ormansesiM.start();
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            orman_ses_seek.setProgress(0);
        }
        catch (Exception e) { Log.e("Exception", e.getMessage());}

    //-----------------------------
    //Nehir sesi icin fonksiyonumuz.
            try {
                nehir_ses_seek = findViewById(R.id.nehir_ses_seek);
                nehir_ses_seek.setMax(100);
                nehir_ses_seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()

                {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                        float log1 = (float) (Math.log(100 - progress) / Math.log(100));
                        nehirsesiM.setVolume(1 - log1, 1 - log1);
                        nehirsesiM.start();
                        nehirState=true;
                        isPlaying=true;

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });
                nehir_ses_seek.setProgress(0);
            }
            catch (Exception e) { Log.e("Exception", e.getMessage());}
//----------------------------
//Ates sesi icin fonksiyonumuz.
        try {
            ates_ses_seek = findViewById(R.id.ates_ses_seek);
            ates_ses_seek.setMax(100);
            ates_ses_seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()

            {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                    float log1 = (float) (Math.log(100 - progress) / Math.log(100));
                    atessesiM.setVolume(1 - log1, 1 - log1);
                    atessesiM.start();
                    atesState=true;
                    isPlaying=true;

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            ates_ses_seek.setProgress(0);
        }
        catch (Exception e) { Log.e("Exception", e.getMessage());}
//--------------------------
//Ney sesi icin fonksiyoumuz.
        try {
            ney_ses_seek = findViewById(R.id.ney_ses_seek);
            ney_ses_seek.setMax(100);
            ney_ses_seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()

            {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                    float log1 = (float) (Math.log(100 - progress) / Math.log(100));
                    neysesiM.setVolume(1 - log1, 1 - log1);
                    neysesiM.start();
                    neyState=true;
                    isPlaying=true;

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            ney_ses_seek.setProgress(0);
        }
        catch (Exception e) { Log.e("Exception", e.getMessage());}
//--------------------------
//Yagmur sesi icin fonksiyoumuz.
        try {
            rain_ses_seek = findViewById(R.id.rain_ses_seek);
            rain_ses_seek.setMax(100);
            rain_ses_seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()

            {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                    float log1 = (float) (Math.log(100 - progress) / Math.log(100));
                    rainsesiM.setVolume(1 - log1, 1 - log1);
                    rainsesiM.start();
                    rainState=true;
                    isPlaying=true;
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            rain_ses_seek.setProgress(0);
        }
        catch (Exception e) { Log.e("Exception", e.getMessage());}
//--------------------------
//Deniz sesi icin fonksiyoumuz.
        try {
            sea_ses_seek = findViewById(R.id.sea_ses_seek);
            sea_ses_seek.setMax(100);
            sea_ses_seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()

            {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                    float log1 = (float) (Math.log(100 - progress) / Math.log(100));
                    seasesiM.setVolume(1 - log1, 1 - log1);
                    seasesiM.start();
                    seaState=true;
                    isPlaying=true;

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            sea_ses_seek.setProgress(0);
        }
        catch (Exception e) { Log.e("Exception", e.getMessage());}


        try {
            thunder_ses_seek = findViewById(R.id.thunder_ses_seek);
            thunder_ses_seek.setMax(100);
            thunder_ses_seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()

            {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                    float log1 = (float) (Math.log(100 - progress) / Math.log(100));
                    thundersesiM.setVolume(1 - log1, 1 - log1);
                    thundersesiM.start();
                    thunderState=true;
                    isPlaying=true;

                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            thunder_ses_seek.setProgress(0);
        }
        catch (Exception e) { Log.e("Exception", e.getMessage());}

        }
    private void loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd("ca-app-pub-1435076129396602/8883537395",
                new AdRequest.Builder().build());
    }

    }

