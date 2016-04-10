package com.bisoncao.bcviewfinder;

import android.app.Activity;
import android.view.View;

import com.bisoncao.bccommonutil.BCDebug;
import com.bisoncao.bccommonutil.BCNullUtil;

import java.lang.reflect.Field;

/**
 * An improvement for ButterKnife. It can bind member variables to different views by {@link ViewFinderBind#group()}.
 * Attention: should be used along with {@link ViewFinderBind} annotation
 *
 * @author Bison Cao
 * @created 3:26 PM 01/26/2016
 */
public class ViewFinder {

    private static final String TAG = "ViewFinder";

    /**
     * Bind all member variables annotated with {@link ViewFinderBind} with specified group field.
     *
     * @param thisObject An instance of a class, e.g. an activity
     * @param group      Group field in {@link ViewFinderBind} annotation
     * @param parent     Parent view of views which are bound with member variables
     */
    public static void bind(Object thisObject, String group, View parent) {
        try {
            Class myClass = Class.forName(thisObject.getClass().getName());
            if (myClass != null) {
                // Using getDeclaredFields() instead of using getFields() for accessing non-public fields
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
     * Bind all member variable annotated with ViewFinderBind without group field.
     * For example: you can use "ViewFinder.bind(this, this.getWindow().getDecorView())"
     * in OnCreate() after setContentView()
     *
     * @param thisObject An instance of a class, e.g. an activity
     * @param parent     Parent view of views which are bound with member variables
     */
    public static void bind(Object thisObject, View parent) {
        bind(thisObject, ViewFinderBind.DEFAULT_GROUP, parent);
    }

    /**
     * A convenient way of using {@link ViewFinder#bind(Object, View)} when it is
     * an activity.
     */
    public static void bind(Activity activity) {
        bind(activity, ViewFinderBind.DEFAULT_GROUP, activity.getWindow().getDecorView());
    }

    public static <T> T getView(View parent, Field field) {
        if (field.isAnnotationPresent(ViewFinderBind.class)) {
            return getView(parent, field.getAnnotation(ViewFinderBind.class));
        } else {
            throw new NullPointerException("Must define ViewFinderBind annotation: field - " + field.getName());
        }

    }

    private static <T> T getView(View parent, ViewFinderBind bind) {
        return (T) parent.findViewById(bind.value());
    }

    private static void bindView(Object thisObject, String category, View parent, Field field) {
        if (category == null) {
            category = ViewFinderBind.DEFAULT_GROUP;
        }
        field.setAccessible(true); // makes non-public field accessible
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
