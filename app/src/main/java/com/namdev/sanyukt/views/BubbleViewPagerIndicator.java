package com.namdev.sanyukt.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by Harish on 3/27/2017.
 */

public class BubbleViewPagerIndicator extends LinearLayout {

    private static final int DIA = 6;
    private static final int MARGIN = 3;

    public static final String TAG = BubbleViewPagerIndicator.class.getSimpleName();

    private ArrayList<View> bubbles;

    private int selectedBubblePosition = 0;
    private int bubble_count = 0;

    public BubbleViewPagerIndicator(Context context) {
        super(context);
        bubbles = new ArrayList<View>();
    }

    public BubbleViewPagerIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        bubbles = new ArrayList<View>();
    }

    public BubbleViewPagerIndicator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        bubbles = new ArrayList<View>();
    }

    public void makeBubbles(int resId, int count){
        bubble_count = count;
        this.removeAllViews();
        this.clearDisappearingChildren();
        int bubbleDiameter = (int) (getResources().getDisplayMetrics().density * DIA);
        int bubbleMargin = (int) (getResources().getDisplayMetrics().density * MARGIN);
        for (int i=0; i< bubble_count;i++){
            View view = new View(getContext());
            LayoutParams params = new LayoutParams(bubbleDiameter, bubbleDiameter);
            params.setMargins(bubbleMargin, bubbleMargin, bubbleMargin, bubbleMargin);
            view.setLayoutParams(params);
            view.setEnabled(false);

            view.setBackgroundResource(resId);

            bubbles.add(view);
            this.addView(view);
        }
        if (bubbles.size() > 0){
            bubbles.get(0).setEnabled(true);
        }

    }

    public void setBubbleActive(int position){
        for (int i=0; i< bubble_count;i++){
            if (i == position){
                bubbles.get(i).setEnabled(true);
            }else{
                bubbles.get(i).setEnabled(false);
            }
        }
    }
}

