package com.loftblog.loftmoney.common.diagram;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.loftblog.loftmoney.R;

public class DiagramView extends View {

    private float expenses = 100 ;
    private float incomes = 300;

    private Paint expensePaint = new Paint();
    private Paint incomesPaint = new Paint();

    public DiagramView(Context context) {
        super(context);
        init();
    }

    public DiagramView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DiagramView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public DiagramView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void update(float expenses, float incomes) {
        this.expenses = expenses;
        this.incomes = incomes;

        invalidate();
    }

    private void init() {
        expensePaint.setColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
        incomesPaint.setColor(ContextCompat.getColor(getContext(), R.color.Add));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float total = expenses + incomes;

        float expenseAngle = 360f * expenses / total;
        float incomesAngle = 360f * incomes / total;

        int space = 13;
        int size = Math.min(getWidth(), getHeight()) - space * 2;
        int xMargin = (getWidth() - size) / 2;
        int yMargin = (getHeight() - size) / 2;

        canvas.drawArc(xMargin - space, yMargin,
                getWidth() - xMargin - space,
                getHeight() - yMargin,
                180 - expenseAngle / 2, expenseAngle,
                true, expensePaint);

        canvas.drawArc(xMargin + space, yMargin, getWidth() - xMargin + space,
                getHeight() - yMargin,
                360 - incomesAngle / 2, incomesAngle,
                true, incomesPaint);
    }
}