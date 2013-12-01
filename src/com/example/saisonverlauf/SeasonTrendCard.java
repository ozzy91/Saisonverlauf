package com.example.saisonverlauf;

import java.util.Map;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SeasonTrendCard extends LinearLayout {
	
	private TextView txtTeamName;
	private SeasonTrendView seasonTrendView;
	
	public SeasonTrendCard(Context context) {
		super(context);
		initView(context);
	}

	public SeasonTrendCard(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public SeasonTrendCard(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
	}
	
	public void initView(final Context context) {
		View.inflate(context, R.layout.card_season_trend, this);
		txtTeamName = (TextView) findViewById(R.id.season_trend_teamname);
		seasonTrendView = (SeasonTrendView) findViewById(R.id.season_trend_view);
		
		txtTeamName.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void onGlobalLayout() {
				txtTeamName.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				float teamNameBottom = txtTeamName.getY() + txtTeamName.getHeight();
				int height = getHeight() - (int) teamNameBottom - 0;
				seasonTrendView.setHeight(height);
				seasonTrendView.setRotation(-90);
			}
		});
	}
	
	public void setData(Map<String, Object> subnode) {
		seasonTrendView.testCode();
	}

}
