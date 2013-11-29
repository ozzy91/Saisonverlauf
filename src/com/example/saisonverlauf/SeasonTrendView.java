package com.example.saisonverlauf;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

public class SeasonTrendView extends View {

	private static int SQUARE_SIZE;

	private static int SMALL_MARGIN;
	private static int BIG_MARGIN;
	private static int LEGEND_LEFT_WIDTH;
	private static int LEGEND_LEFT_MARGIN;
	private static int LEGEND_BOTTOM_MARGIN;
	private static int TEXT_SIZE;

	private Paint backgroundPaint;
	private TextPaint textPaint;

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
		initValues();
		initPaint();
	}

	public void initValues() {
		Resources res = getResources();

		SMALL_MARGIN = (int) res
				.getDimension(R.dimen.season_trend_margin_small);
		BIG_MARGIN = (int) res.getDimension(R.dimen.season_trend_margin_big);
		LEGEND_LEFT_WIDTH = (int) res.getDimension(R.dimen.season_trend_legend_left_width);
		LEGEND_LEFT_MARGIN = (int) res.getDimension(R.dimen.season_trend_legend_left_margin);
		LEGEND_BOTTOM_MARGIN = (int) res.getDimension(R.dimen.season_trend_legend_bottom_margin);
		TEXT_SIZE = (int) res.getDimension(R.dimen.season_trend_text_size);
	}

	public void initPaint() {
		backgroundPaint = new Paint();
		backgroundPaint.setColor(Color.parseColor("#e2e2e2"));
		backgroundPaint.setAlpha(70);
		
		textPaint = new TextPaint();
		textPaint.setColor(Color.parseColor("#676767"));
		textPaint.setTextSize(TEXT_SIZE);
		textPaint.setFlags(Paint.SUBPIXEL_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		int height = getHeight();
		int width = getWidth();

		float horizontalTextY = 18 * SQUARE_SIZE + 5 * BIG_MARGIN + LEGEND_BOTTOM_MARGIN + TEXT_SIZE;
		float currentY = 0;
		for (int y = 17; y >= 0; y--) {
			currentY = y * SQUARE_SIZE;
			
			if (y < 3) {
				backgroundPaint.setColor(Color.parseColor("#44BC2856"));
			}
			if (y >= 3) {
				currentY += BIG_MARGIN;
				backgroundPaint.setColor(Color.parseColor("#44207A24"));
			}
			if (y >= 4) {
				currentY += BIG_MARGIN;
				backgroundPaint.setColor(Color.parseColor("#44BAAC2A"));
			}
			if (y >= 6) {
				currentY += BIG_MARGIN;
				backgroundPaint.setColor(Color.parseColor("#44e2e2e2"));
			}
			if (y >= 15) {
				currentY += BIG_MARGIN;
				backgroundPaint.setColor(Color.parseColor("#44a00000"));
			}
			if (y >= 16) {
				currentY += BIG_MARGIN;
				backgroundPaint.setColor(Color.parseColor("#44ff0000"));
			}
			
			if (y == 17)
				canvas.drawText("18", LEGEND_LEFT_WIDTH - LEGEND_LEFT_MARGIN - textPaint.measureText("18"), currentY + TEXT_SIZE, textPaint);
			else if (y == 14)
				canvas.drawText("15", LEGEND_LEFT_WIDTH - LEGEND_LEFT_MARGIN - textPaint.measureText("15"), currentY + TEXT_SIZE, textPaint);
			else if (y == 9)
				canvas.drawText("10", LEGEND_LEFT_WIDTH - LEGEND_LEFT_MARGIN - textPaint.measureText("10"), currentY + TEXT_SIZE, textPaint);
			else if (y == 4)
				canvas.drawText("5", LEGEND_LEFT_WIDTH - LEGEND_LEFT_MARGIN - textPaint.measureText("5"), currentY + TEXT_SIZE, textPaint);
			else if (y == 0)
				canvas.drawText("1", LEGEND_LEFT_WIDTH - LEGEND_LEFT_MARGIN - textPaint.measureText("1"), currentY + TEXT_SIZE, textPaint);
			
			
			for (int x = 0; x < 34; x++) {
				float currentX = LEGEND_LEFT_WIDTH + x * SQUARE_SIZE;
				if (x <= 16)
					canvas.drawRect(currentX, currentY, currentX + SQUARE_SIZE - SMALL_MARGIN, currentY + SQUARE_SIZE - SMALL_MARGIN, backgroundPaint);
				else
					canvas.drawRect(currentX + BIG_MARGIN, currentY, currentX + SQUARE_SIZE - SMALL_MARGIN + BIG_MARGIN, currentY + SQUARE_SIZE - SMALL_MARGIN, backgroundPaint);
			
				if (x == 0)
					canvas.drawText("1. Spieltag", currentX, horizontalTextY, textPaint);
				if (x == 9)
					canvas.drawText("10.", currentX, horizontalTextY, textPaint);
				if (x == 14)
					canvas.drawText("15.", currentX, horizontalTextY, textPaint);
				if (x == 19)
					canvas.drawText("20.", currentX, horizontalTextY, textPaint);
				if (x == 24)
					canvas.drawText("25.", currentX, horizontalTextY, textPaint);
				if (x == 29)
					canvas.drawText("30.", currentX, horizontalTextY, textPaint);
				if (x == 33)
					canvas.drawText("34.", currentX, horizontalTextY, textPaint);
			}
			
			
		}
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int parentWidth = ((View) getParent()).getWidth();
		setMeasuredDimension(height, parentWidth);

		int verticalSize = (parentWidth - 5 * BIG_MARGIN) / 18;
		int horizontalSize = (height - LEGEND_LEFT_WIDTH - BIG_MARGIN) / 34;
		SQUARE_SIZE = Math.min(verticalSize, horizontalSize);
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
