package ir.jahanmirbazh.components;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public  class PersianTextView extends TextView {
    FontHelper fontHelper = new FontHelper();
    public PersianTextView(Context context) {
        super(context);
        if (!isInEditMode())
            setTypeface(fontHelper.getInstance(context,"fonts/is.ttf").getPersianTextTypeface());
    }

    public PersianTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode())
            setTypeface(fontHelper.getInstance(context,"fonts/is.ttf").getPersianTextTypeface());
    }

    public PersianTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (!isInEditMode())
            setTypeface(fontHelper.getInstance(context,"fonts/is.ttf").getPersianTextTypeface());
    }

    @Override
    public void setText(CharSequence text, TextView.BufferType type) {
        if (text != null)
            text = FormatHelper.toPersianNumber(text.toString());
        super.setText(text, type);
    }
}
