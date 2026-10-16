package com.missionsm18;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.os.Handler;
import android.os.Looper;
import java.time.Instant;
import java.util.Locale;

public class MainActivity extends Activity {
    private final Handler handler = new Handler(Looper.getMainLooper());
    private TextView countdown, status, percent;
    private ProgressBar progress;
    private final Runnable ticker = new Runnable() {
        @Override public void run() { render(); handler.postDelayed(this,1000); }
    };

    @Override protected void onCreate(Bundle state) {
        super.onCreate(state);
        Intent service = new Intent(this, CountdownService.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) startForegroundService(service);
        else startService(service);

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(dp(22),dp(28),dp(22),dp(22));
        root.setGravity(Gravity.CENTER_HORIZONTAL);
        root.setBackgroundColor(Color.rgb(248,249,251));

        TextView title = text("Mission SM18",28,true);
        title.setGravity(Gravity.CENTER);
        root.addView(title, lp(-1,-2));
        TextView subtitle = text("COUNTDOWN TO THE TARGET DATE",11,true);
        subtitle.setGravity(Gravity.CENTER);
        subtitle.setLetterSpacing(.08f);
        LinearLayout.LayoutParams slp=lp(-1,-2); slp.topMargin=dp(7); root.addView(subtitle,slp);

        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setGravity(Gravity.CENTER_HORIZONTAL);
        card.setPadding(dp(18),dp(24),dp(18),dp(22));
        GradientDrawable bg = new GradientDrawable();
        bg.setColor(Color.WHITE); bg.setCornerRadius(dp(18)); bg.setStroke(dp(1),0xFFD9DEE7);
        card.setBackground(bg);
        LinearLayout.LayoutParams clp=lp(-1,-2); clp.topMargin=dp(26); root.addView(card,clp);

        status = text("Time remaining",13,false); status.setGravity(Gravity.CENTER);
        card.addView(status,lp(-1,-2));
        countdown = text("",25,true); countdown.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams cdp=lp(-1,-2); cdp.topMargin=dp(15); card.addView(countdown,cdp);
        progress = new ProgressBar(this,null,android.R.attr.progressBarStyleHorizontal);
        progress.setMax(1000);
        LinearLayout.LayoutParams prlp=lp(-1,dp(8)); prlp.topMargin=dp(23); card.addView(progress,prlp);
        percent = text("",12,false); percent.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams plp=lp(-1,-2); plp.topMargin=dp(8); card.addView(percent,plp);

        TextView dates=text("15 OCT 2026  ·  00:00 IST\n15 OCT 2031  ·  00:00 IST",12,false);
        dates.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams dlp=lp(-1,-2); dlp.topMargin=dp(24); root.addView(dates,dlp);

        TextView hint=text("Add the resizable widget from your home-screen widget menu.",12,false);
        hint.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams hlp=lp(-1,-2); hlp.topMargin=dp(20); root.addView(hint,hlp);
        setContentView(root);
    }
    @Override protected void onResume(){super.onResume();handler.removeCallbacks(ticker);handler.post(ticker);}
    @Override protected void onPause(){handler.removeCallbacks(ticker);super.onPause();}
    private void render(){
        Instant now=Instant.now(); Countdown.Parts p=Countdown.parts(now);
        countdown.setText(p.finished?"Mission complete":p.dateLine()+"\n"+p.timeLine());
        status.setText(now.isBefore(Countdown.START)?"Countdown starts 15 October 2026":(p.finished?"Target date reached":"Time remaining"));
        double r=Countdown.progress(now); progress.setProgress((int)Math.round(r*1000)); progress.getProgressDrawable().setTint(Countdown.color(now));
        percent.setText(String.format(Locale.US,"%.2f%% time remaining",r*100));
    }
    private TextView text(String s,int size,boolean bold){
        TextView v=new TextView(this);v.setText(s);v.setTextSize(size);v.setTextColor(Color.BLACK);
        if(bold)v.setTypeface(Typeface.DEFAULT,Typeface.BOLD);return v;
    }
    private LinearLayout.LayoutParams lp(int w,int h){return new LinearLayout.LayoutParams(w<0?w:dp(w),h<0?h:dp(h));}
    private int dp(int n){return (int)(n*getResources().getDisplayMetrics().density+.5f);}
}
