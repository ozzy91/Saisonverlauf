package com.example.saisonverlauf;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

public class SeasonTrendView extends View {

	private static int SQUARE_SIZE;

	private static int SMALL_MARGIN;
	private static int BIG_MARGIN;

	private Context context;

	private Paint testPaint;

	private int height = 888;

	public SeasonTrendView(Context context) {
		super(context);
		initView(context);
	}

	public SeasonTrendView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public SeasonTrendView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
	}

	public void initView(Context context) {
		this.context = context;
		initValues();
		initPaint();
	}

	public void initValues() {
		Resources res = getResources();

		SMALL_MARGIN = (int) res
				.getDimension(R.dimen.season_trend_margin_small);
		BIG_MARGIN = (int) res.getDimension(R.dimen.season_trend_margin_big);
	}

	public void initPaint() {
		testPaint = new Paint();
		testPaint.setColor(Color.RED);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		int height = getHeight();
		int width = getWidth();

		for (int i = 0; i < 34; i++) {
			if (i <= 16)
				canvas.drawRect(i * SQUARE_SIZE, height - SQUARE_SIZE + SMALL_MARGIN, (i + 1) * SQUARE_SIZE - SMALL_MARGIN, height, testPaint);
			else
				canvas.drawRect(i * SQUARE_SIZE + BIG_MARGIN, height - SQUARE_SIZE + SMALL_MARGIN, (i + 1) * SQUARE_SIZE - SMALL_MARGIN + BIG_MARGIN, height, testPaint);
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int parentWidth = ((View) getParent()).getWidth();
		setMeasuredDimension(height, parentWidth);

		SQUARE_SIZE = (height - BIG_MARGIN) / 34;
	}

	@Override
	public void setRotation(final float rotation) {
		super.setRotation(rotation);
		if (rotation == 90 || rotation == -90 || rotation == 270
				|| rotation == -270) {
			getViewTreeObserver().addOnGlobalLayoutListener(
					new OnGlobalLayoutListener() {
						@SuppressWarnings("deprecation")
						public void onGlobalLayout() {
							getViewTreeObserver().removeGlobalOnLayoutListener(
									this);
							float offset = (getHeight() - getWidth()) / 2;
							if (rotation == -90 || rotation == 270) {
								setX(getX() + offset);
								setY(getY() - offset);
							} else if (rotation == 90 || rotation == -270) {
								setX(getX() + offset);
								setY(getY() - offset);
							}
						}
					});
		}
	}

	public void setHeight(int height) {
		this.height = height;
	}

}
