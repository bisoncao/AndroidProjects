package com.bisoncao.bcviewfinder;

import android.view.View;

import com.bisoncao.bccommonutil.BCDebug;
import com.bisoncao.bccommonutil.BCNullUtil;

import java.lang.reflect.Field;

/**
 * A plus for ButterKnife. Can bind member variables to different view by groups.
 * Attention: should use with {@link ViewFinderBind} annotation
 *
 * @created 3:26 PM 01/26/2016
 * @author Bison Cao
 */
public class ViewFinder {

    private static final String TAG = "ViewFinder";

    /**
     * bind all member variable annotated with ViewFinderBind with specified group field
     *
     * @param thisObject an instance of a class, e.g. an activity
     * @param group      group field in {@link ViewFinderBind} annotation
     * @param parent     parent view of views which are bound with member variables
     */
    public static void bind(Object thisObject, String group, View parent) {
        try {
            Class myClass = Class.forName(thisObject.getClass().getName());
            if (myClass != null) {
                Field[] fields = myClass.getDeclaredFields();
                if (BCNullUtil.isNotNullArr(fields)) {
                    for (Field field : fields) {
                        bindView(thisObject, group, parent, field);
                    }
                }
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * bind all member variable annotated with ViewFinderBind without group field
     * For example: you can use "ViewFinder.bind(this, this.getWindow().getDecorView())"
     * in OnCreate method after setContentView()
     *
     * @param thisObject an instance of a class, e.g. an activity
     * @param parent     parent view of views which are bound with member variables
     */
    public static void bind(Object thisObject, View parent) {
        bind(thisObject, ViewFinderBind.DEFAULT_GROUP, parent);
    }

    public static <T> T getView(View parent, Field field) {
        if (field.isAnnotationPresent(ViewFinderBind.class)) {
            return getView(parent, field.getAnnotation(ViewFinderBind.class));
        } else {
            throw new NullPointerException("Must defind ViewFinderBind annotation when using ViewFinder and field should not be" +
                    " private: " + field.getName());
        }

    }

    private static <T> T getView(View parent, ViewFinderBind bind) {
        return (T) parent.findViewById(bind.value());
    }

    private static void bindView(Object thisObject, String category, View parent, Field field) {
        if (category == null) {
            category = ViewFinderBind.DEFAULT_GROUP;
        }
        field.setAccessible(true);
        if (field.isAnnotationPresent(ViewFinderBind.class)) {
            ViewFinderBind bind = field.getAnnotation(ViewFinderBind.class);
            if (category.equals(bind.group())) {
                BCDebug.d(TAG, field.getName());
                try {
                    field.set(thisObject, getView(parent, bind));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
