package com.example.saisonverlauf;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.LinearInterpolator;

public class SeasonTrendView extends View {

	private static int SQUARE_SIZE;

	private static int SMALL_MARGIN;
	private static int BIG_MARGIN;
	private static int LEGEND_LEFT_WIDTH;
	private static int LEGEND_LEFT_MARGIN;
	private static int LEGEND_BOTTOM_MARGIN;
	private static int TEXT_SIZE;
	private static int COLOR_LEGEND_TEXT_MARGIN;
	private static int COLOR_LEGEND_MARGIN_TOP;
	private static int COLOR_LEGEND_MARGIN_RIGHT;
	private static int COLOR_LEGEND_RECT_MARGIN_TOP;
	private static int COLOR_LEGEND_LINE_MARGIN;

	private Paint backgroundPaint;
	private TextPaint textPaint;
	
	private int[] data = new int[34];
	private float drawingValue = 19;
	
	private String champLegend;
	private String champQualiLegend;
	private String zweiteLigaLegend;
	private String euLegend;
	private String relLegend;
	private String abstiegLegend;
	
	private boolean showBar;
	private boolean animationStarted;

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
		COLOR_LEGEND_MARGIN_TOP = (int) res.getDimension(R.dimen.season_trend_color_legend_margin_top);
		COLOR_LEGEND_TEXT_MARGIN = (int) res.getDimension(R.dimen.season_trend_color_legend_text_margin);
		COLOR_LEGEND_MARGIN_RIGHT = (int) res.getDimension(R.dimen.season_trend_color_legend_margin_right);
		COLOR_LEGEND_RECT_MARGIN_TOP = (int) res.getDimension(R.dimen.season_trend_color_legend_rect_margin_top);
		COLOR_LEGEND_LINE_MARGIN = (int) res.getDimension(R.dimen.season_trend_color_legend_line_margin);
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
		for (int y = 17; y >= 0; y--) {
			float currentY = getCurrentY(y, true);
			backgroundPaint.setAlpha(30);
			
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
		
		float legendSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());;
		
		float legendX = LEGEND_LEFT_WIDTH;
		float legendY = horizontalTextY + COLOR_LEGEND_MARGIN_TOP;
		if (champLegend != null) {
			backgroundPaint.setColor(Color.parseColor("#BC2856"));
			canvas.drawRect(legendX, legendY + COLOR_LEGEND_RECT_MARGIN_TOP, legendX + legendSize, legendY + COLOR_LEGEND_RECT_MARGIN_TOP + legendSize, backgroundPaint);
			legendX += legendSize + COLOR_LEGEND_TEXT_MARGIN;
			canvas.drawText(champLegend, legendX, legendY + TEXT_SIZE, textPaint);
			legendX += COLOR_LEGEND_MARGIN_RIGHT + textPaint.measureText(champLegend);
		}
		if (champQualiLegend != null) {
			backgroundPaint.setColor(Color.parseColor("#207A24"));
			canvas.drawRect(legendX, legendY + COLOR_LEGEND_RECT_MARGIN_TOP, legendX + legendSize, legendY + COLOR_LEGEND_RECT_MARGIN_TOP + legendSize, backgroundPaint);
			legendX += legendSize + COLOR_LEGEND_TEXT_MARGIN;
			canvas.drawText(champQualiLegend, legendX, legendY + TEXT_SIZE, textPaint);
			legendX += COLOR_LEGEND_MARGIN_RIGHT + textPaint.measureText(champQualiLegend);
		}
		if (zweiteLigaLegend != null) {
			backgroundPaint.setColor(Color.parseColor("#BAAC2A"));
			canvas.drawRect(legendX, legendY + COLOR_LEGEND_RECT_MARGIN_TOP, legendX + legendSize, legendY + COLOR_LEGEND_RECT_MARGIN_TOP + legendSize, backgroundPaint);
			legendX += legendSize + COLOR_LEGEND_TEXT_MARGIN;
			canvas.drawText(zweiteLigaLegend, legendX, legendY + TEXT_SIZE, textPaint);
			legendX += COLOR_LEGEND_MARGIN_RIGHT + textPaint.measureText(zweiteLigaLegend);
		}
		
		legendX = LEGEND_LEFT_WIDTH;
		legendY = horizontalTextY + COLOR_LEGEND_MARGIN_TOP + legendSize + COLOR_LEGEND_RECT_MARGIN_TOP + COLOR_LEGEND_LINE_MARGIN;
		if (euLegend != null) {
			backgroundPaint.setColor(Color.parseColor("#BAAC2A"));
			canvas.drawRect(legendX, legendY + COLOR_LEGEND_RECT_MARGIN_TOP, legendX + legendSize, legendY + COLOR_LEGEND_RECT_MARGIN_TOP + legendSize, backgroundPaint);
			legendX += legendSize + COLOR_LEGEND_TEXT_MARGIN;
			canvas.drawText(euLegend, legendX, legendY + TEXT_SIZE, textPaint);
			legendX += COLOR_LEGEND_MARGIN_RIGHT + textPaint.measureText(euLegend);
		}
		if (relLegend != null) {
			backgroundPaint.setColor(Color.parseColor("#A00000"));
			canvas.drawRect(legendX, legendY + COLOR_LEGEND_RECT_MARGIN_TOP, legendX + legendSize, legendY + COLOR_LEGEND_RECT_MARGIN_TOP + legendSize, backgroundPaint);
			legendX += legendSize + COLOR_LEGEND_TEXT_MARGIN;
			canvas.drawText(relLegend, legendX, legendY + TEXT_SIZE, textPaint);
			legendX += COLOR_LEGEND_MARGIN_RIGHT + textPaint.measureText(relLegend);
		}
		if (abstiegLegend != null) {
			backgroundPaint.setColor(Color.parseColor("#FF0000"));
			canvas.drawRect(legendX, legendY + COLOR_LEGEND_RECT_MARGIN_TOP, legendX + legendSize, legendY + COLOR_LEGEND_RECT_MARGIN_TOP + legendSize, backgroundPaint);
			legendX += legendSize + COLOR_LEGEND_TEXT_MARGIN;
			canvas.drawText(abstiegLegend, legendX, legendY + TEXT_SIZE, textPaint);
			legendX += COLOR_LEGEND_MARGIN_RIGHT + textPaint.measureText(abstiegLegend);
		}
		
		
		
		if (showBar) {
			if (!animationStarted) {
				ObjectAnimator barAnimator = ObjectAnimator.ofFloat(this, "drawingValue", 19, -34);
				barAnimator.setDuration(1600);
				barAnimator.setInterpolator(new LinearInterpolator());
				barAnimator.setStartDelay(500);
				barAnimator.start();
				animationStarted = true;
			}
			for (int x = 0; x < 34; x++) {
				int tableposition = data[x];
				if (tableposition > 0) {
					float currentX = LEGEND_LEFT_WIDTH + x * SQUARE_SIZE;
					backgroundPaint.setColor(Color.parseColor("#929292"));
					float currentDrawingValue = drawingValue + x;
					int fullSquares = (int) currentDrawingValue + 1;
					
					float currentY = 0;
					if (fullSquares <= tableposition) {
						fullSquares = tableposition;
					}
					for (int y = 17; y >= fullSquares - 1; y--) {
						if (y == tableposition - 1)
							currentY = getCurrentY(y, true);
						else
							currentY = getCurrentY(y, false);
						if (x <= 16)
							canvas.drawRect(currentX, currentY, currentX + SQUARE_SIZE - SMALL_MARGIN, currentY + SQUARE_SIZE - SMALL_MARGIN, backgroundPaint);
						else
							canvas.drawRect(currentX + BIG_MARGIN, currentY, currentX + SQUARE_SIZE - SMALL_MARGIN + BIG_MARGIN, currentY + SQUARE_SIZE - SMALL_MARGIN, backgroundPaint);
					}
					if (fullSquares <= 19 && fullSquares != tableposition) {
						float lastSquare = currentDrawingValue - (fullSquares - 1);
						if (fullSquares == tableposition + 1)
							currentY = getCurrentY(fullSquares - 2, true);
						else
							currentY = getCurrentY(fullSquares - 2, false);
						if (x <= 16)
							canvas.drawRect(currentX, currentY + lastSquare * (SQUARE_SIZE - SMALL_MARGIN), currentX + SQUARE_SIZE - SMALL_MARGIN, currentY + SQUARE_SIZE - SMALL_MARGIN, backgroundPaint);
						else
							canvas.drawRect(currentX + BIG_MARGIN, currentY + lastSquare * (SQUARE_SIZE - SMALL_MARGIN), currentX + SQUARE_SIZE - SMALL_MARGIN + BIG_MARGIN, currentY + SQUARE_SIZE - SMALL_MARGIN, backgroundPaint);
					}
				}
			}
		}
	}

	private float getCurrentY(int y, boolean changeColors) {
		float currentY = y * SQUARE_SIZE;
		
		if (changeColors && y < 3)
			backgroundPaint.setColor(Color.parseColor("#BC2856"));
		if (y >= 3) {
			currentY += BIG_MARGIN;
			if (changeColors)
				backgroundPaint.setColor(Color.parseColor("#207A24"));
		}
		if (y >= 4) {
			currentY += BIG_MARGIN;
			if (changeColors)
				backgroundPaint.setColor(Color.parseColor("#BAAC2A"));
		}
		if (y >= 6) {
			currentY += BIG_MARGIN;
			if (changeColors)
				backgroundPaint.setColor(Color.parseColor("#e2e2e2"));
		}
		if (y >= 15) {
			currentY += BIG_MARGIN;
			if (changeColors)
				backgroundPaint.setColor(Color.parseColor("#a00000"));
		}
		if (y >= 16) {
			currentY += BIG_MARGIN;
			if (changeColors)
				backgroundPaint.setColor(Color.parseColor("#ff0000"));
		}
		
		return currentY;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int parentWidth = ((View) getParent()).getWidth();
		setMeasuredDimension(height, parentWidth);
		getLayoutParams().width = height;
		getLayoutParams().height = parentWidth;

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
	
	public float getDrawingValue() {
		return drawingValue;
	}

	public void setDrawingValue(float drawingValue) {
		this.drawingValue = drawingValue;
		invalidate();
	}
	
	public void setLegends(String champLegend, String champQualiLegend, String zweiteLigaLegend, String euLegend, String relLegend, String abstiegLegend) {
		this.champLegend = champLegend;
		this.champQualiLegend = champQualiLegend;
		this.zweiteLigaLegend = zweiteLigaLegend;
		this.euLegend = euLegend;
		this.relLegend = relLegend;
		this.abstiegLegend = abstiegLegend;
	}

	public void testCode() {
		System.out.println("testCode");
		data[0] = 3;
		data[1] = 2;
		data[2] = 3;
		data[3] = 2;
		data[4] = 2;
		data[5] = 2;
		data[6] = 2;
		data[7] = 1;
		data[8] = 1;
		data[9] = 1;
		data[10] = 1;
		data[11] = 1;
		data[12] = 1;
		data[13] = 1;
		data[14] = 4;
		data[15] = 5;
		data[16] = 9;
		data[17] = 16;
		data[18] = 18;
		
		setLegends("Champions League", "Champions-League-Qualifikation", null, "Europa-League", "Relegation", "Abstieg");
		
		showBar = true;
//		invalidate();
	}

}
