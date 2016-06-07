package com.yoloho.lv.httpproject.actmenus.dropdownmenu;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.TextView;

import com.yoloho.lv.httpproject.R;

/**
 * Created by lv on 2016/5/25.
 */
public class DropdownListItemView extends TextView {
    public DropdownListItemView(Context context) {
        this(context,null);
    }

    public DropdownListItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public DropdownListItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void bind(CharSequence text,boolean checked){
        setText(text);
        if (checked){
            Drawable icon = getResources().getDrawable(R.drawable.ic_task_status_list_check);
            setCompoundDrawablesWithIntrinsicBounds(null,null,icon,null);
        }else{
            setCompoundDrawablesWithIntrinsicBounds(null,null,null,null);
        }
    }


}
