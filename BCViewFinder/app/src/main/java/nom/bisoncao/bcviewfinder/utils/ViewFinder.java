package nom.bisoncao.bcviewfinder.utils;

import android.view.View;

import com.tataufo.tatalib.utils.NullUtil;
import com.tataufo.tatalib.utils.TataDebug;

import java.lang.reflect.Field;

/**
 * 省去使用 findViewById 方法需要转换类型的问题，弥补 ButterKnife 所有 Field 只能绑定一个 View 的问题
 * 注意：需要配合使用 {@link ViewFinderBind} 注解
 *
 * @author Bison Cao
 * @created 15:26 01/26/2016
 */
public class ViewFinder {

    /**
     * @param thisObject 使用的类的实例，例如一个activity
     * @param category   注解中的分组
     */
    public static void bind(Object thisObject, String category, View parent) {
        try {
            Class myClass = Class.forName(thisObject.getClass().getName());
            if (myClass != null) {
                Field[] fields = myClass.getFields();
                if (NullUtil.isNotNullArr(fields)) {
                    for (Field field : fields) {
                        bindView(thisObject, category, parent, field);
                    }
                }
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void bind(Object thisObject, View parent) {
        bind(thisObject, ViewFinderBind.DEFAULT_CATEGORY, parent);
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
            category = ViewFinderBind.DEFAULT_CATEGORY;
        }
        if (field.isAnnotationPresent(ViewFinderBind.class)) {
            ViewFinderBind bind = field.getAnnotation(ViewFinderBind.class);
            if (category.equals(bind.category())) {
                TataDebug.d("ViewFinderBind", field.getName());
                try {
                    field.set(thisObject, getView(parent, bind));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
