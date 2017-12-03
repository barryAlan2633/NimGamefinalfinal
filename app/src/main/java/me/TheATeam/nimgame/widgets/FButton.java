package me.TheATeam.nimgame.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import info.hoang8f.fbutton.R;


public class FButton extends android.support.v7.widget.AppCompatButton implements View.OnTouchListener {

    //Custom values
    private boolean isShadowEnabled = true;
    private int buttonColor;
    private int shadowColor;
    private int heightOfShadow;
    private int roundedCorer;
    //Native values
    private int leftPadding;
    private int rightPadding;
    private int topPadding;
    private int bottomPadding;

    private Drawable onClickGir;
    private Drawable unClickedGir;

    boolean isShadowColorDefined = false;

    public FButton(Context context) {
        super(context);
        initialization();
        this.setOnTouchListener(this);
    }

    public FButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialization();
        parseAttrs(context, attrs);
        this.setOnTouchListener(this);
    }

    public FButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialization();
        parseAttrs(context, attrs);
        this.setOnTouchListener(this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        refresh();
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                updateBackground(onClickGir);
                this.setPadding(leftPadding, topPadding + heightOfShadow, rightPadding, bottomPadding);
                break;
            case MotionEvent.ACTION_MOVE:
                Rect r = new Rect();
                view.getLocalVisibleRect(r);
                if (!r.contains((int) motionEvent.getX(), (int) motionEvent.getY() + 3 * heightOfShadow) &&
                        !r.contains((int) motionEvent.getX(), (int) motionEvent.getY() - 3 * heightOfShadow)) {
                    updateBackground(unClickedGir);
                    this.setPadding(leftPadding, topPadding + heightOfShadow, rightPadding, bottomPadding + heightOfShadow);
                }
                break;
            case MotionEvent.ACTION_OUTSIDE:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                updateBackground(unClickedGir);
                this.setPadding(leftPadding, topPadding + heightOfShadow, rightPadding, bottomPadding + heightOfShadow);
                break;
        }
        return false;
    }

    private void initialization() {
        isShadowEnabled = true;
        Resources resources = getResources();
        if (resources == null) return;
        shadowColor = resources.getColor(R.color.fbutton_default_shadow_color);
        buttonColor = resources.getColor(R.color.fbutton_default_color);
        heightOfShadow = resources.getDimensionPixelSize(R.dimen.fbutton_default_shadow_height);
        roundedCorer = resources.getDimensionPixelSize(R.dimen.fbutton_default_conner_radius);
    }

    @SuppressLint("ResourceAsColor")
    private void parseAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FButton);
        if (typedArray == null) return;
        for (int i = 0; i < typedArray.getIndexCount(); i++) {
            int attr = typedArray.getIndex(i);
            if (attr == R.styleable.FButton_shadowEnabled) {
                isShadowEnabled = typedArray.getBoolean(attr, true); //Default is true
            } else if (attr == R.styleable.FButton_buttonColor) {
                buttonColor = typedArray.getColor(attr, R.color.fbutton_default_color);
            } else if (attr == R.styleable.FButton_shadowColor) {
                shadowColor = typedArray.getColor(attr, R.color.fbutton_default_shadow_color);
                isShadowColorDefined = true;
            } else if (attr == R.styleable.FButton_shadowHeight) {
                heightOfShadow = typedArray.getDimensionPixelSize(attr, R.dimen.fbutton_default_shadow_height);
            } else if (attr == R.styleable.FButton_cornerRadius) {
                roundedCorer = typedArray.getDimensionPixelSize(attr, R.dimen.fbutton_default_conner_radius);
            }
        }
        typedArray.recycle();

        //Get paddingLeft, paddingRight
        int[] attrsArray = new int[]{
                android.R.attr.paddingLeft,
                android.R.attr.paddingRight,
        };
        TypedArray ta = context.obtainStyledAttributes(attrs, attrsArray);
        if (ta == null) return;
        leftPadding = ta.getDimensionPixelSize(0, 0);
        rightPadding = ta.getDimensionPixelSize(1, 0);
        ta.recycle();

        //Get paddingTop, paddingBottom
        int[] attrsArray2 = new int[]{
                android.R.attr.paddingTop,
                android.R.attr.paddingBottom
        };
        TypedArray ta1 = context.obtainStyledAttributes(attrs, attrsArray2);
        if (ta1 == null) return;
        topPadding = ta1.getDimensionPixelSize(0, 0);
        bottomPadding = ta1.getDimensionPixelSize(1, 0);
        ta1.recycle();
    }

    public void refresh() {
        int alpha = Color.alpha(buttonColor);
        float[] hsv = new float[3];
        Color.colorToHSV(buttonColor, hsv);
        hsv[2] *= 0.8f;
        if (!isShadowColorDefined) {
            shadowColor = Color.HSVToColor(alpha, hsv);
        }
        //creates the look and feel of when you click and unclick a gir image
        if (this.isEnabled()) {
            if (isShadowEnabled) {
                onClickGir = createDrawable(roundedCorer, Color.TRANSPARENT, buttonColor);
                unClickedGir = createDrawable(roundedCorer, buttonColor, shadowColor);
            } else {
                heightOfShadow = 0;
                onClickGir = createDrawable(roundedCorer, shadowColor, Color.TRANSPARENT);
                unClickedGir = createDrawable(roundedCorer, buttonColor, Color.TRANSPARENT);
            }
        } else {
            Color.colorToHSV(buttonColor, hsv);
            hsv[1] *= 0.25f; //saturation cause we fancy
            int disabledColor = shadowColor = Color.HSVToColor(alpha, hsv);

            onClickGir = createDrawable(roundedCorer, disabledColor, Color.TRANSPARENT);
            unClickedGir = createDrawable(roundedCorer, disabledColor, Color.TRANSPARENT);
        }
        updateBackground(unClickedGir);
        //this sets the padding
        this.setPadding(leftPadding, topPadding + heightOfShadow, rightPadding, bottomPadding + heightOfShadow);
    }

    private void updateBackground(Drawable background) {
        if (background == null) return;
        //Set button background
        if (Build.VERSION.SDK_INT >= 16) {
            this.setBackground(background);
        } else {
            this.setBackgroundDrawable(background);
        }
    }
    private LayerDrawable createDrawable(int radius, int topColor, int bottomColor) {

        float[] outerRadius = new float[]{radius, radius, radius, radius, radius, radius, radius, radius};

        //Top
        RoundRectShape topRoundRect = new RoundRectShape(outerRadius, null, null);
        ShapeDrawable topShapeDrawable = new ShapeDrawable(topRoundRect);
        topShapeDrawable.getPaint().setColor(topColor);

        //Bottom
        RoundRectShape roundRectShape = new RoundRectShape(outerRadius, null, null);
        ShapeDrawable bottomShapeDrawable = new ShapeDrawable(roundRectShape);
        bottomShapeDrawable.getPaint().setColor(bottomColor);
        Drawable[] drawArray = {bottomShapeDrawable, topShapeDrawable};
        LayerDrawable layerDrawable = new LayerDrawable(drawArray);

        //Set shadow height
        if (isShadowEnabled && topColor != Color.TRANSPARENT) {
            //unpressed drawable
            layerDrawable.setLayerInset(0, 0, 0, 0, 0);  /*index, left, top, right, bottom*/
        } else {
            //pressed drawable
            layerDrawable.setLayerInset(0, 0, heightOfShadow, 0, 0);  /*index, left, top, right, bottom*/
        }
        layerDrawable.setLayerInset(1, 0, 0, 0, heightOfShadow);  /*index, left, top, right, bottom*/

        return layerDrawable;
    }



    public void setShadowEnabled(boolean isShadowEnabled) {
        this.isShadowEnabled = isShadowEnabled;
        setShadowHeight(0);
        refresh();
    }

    public void setButtonColor(int buttonColor) {
        this.buttonColor = buttonColor;
        refresh();
    }

    public void setShadowColor(int shadowColor) {
        this.shadowColor = shadowColor;
        isShadowColorDefined = true;
        refresh();
    }

    public void setShadowHeight(int shadowHeight) {
        this.heightOfShadow = shadowHeight;
        refresh();
    }

    public void setCornerRadius(int cornerRadius) {
        this.roundedCorer = cornerRadius;
        refresh();
    }

    public void setFButtonPadding(int left, int top, int right, int bottom) {
        leftPadding = left;
        rightPadding = right;
        topPadding = top;
        bottomPadding = bottom;
        refresh();
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        refresh();
    }

    public boolean isShadowEnabled() {
        return isShadowEnabled;
    }

    public int getButtonColor() {
        return buttonColor;
    }

    public int getShadowColor() {
        return shadowColor;
    }

    public int getShadowHeight() {
        return heightOfShadow;
    }

    public int getCornerRadius() {
        return roundedCorer;
    }
}
