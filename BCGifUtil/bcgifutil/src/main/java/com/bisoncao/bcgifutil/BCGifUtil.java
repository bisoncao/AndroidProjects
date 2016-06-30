package com.bisoncao.bcgifutil;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.bisoncao.bccommonutil.BCDebug;
import com.bisoncao.bccommonutil.DeviceUtils;

import java.util.HashMap;

/**
 * Util class for using {@link GifDecoder} & {@link GifAnimationDrawable}
 *
 * @author Bison Cao
 * @created 5:30 PM 03/24/2016
 */
public class BCGifUtil {

    private static final String MKEY_CARRIER = "carrier";
    private static final String MKEY_GIF = "gif";

    public static void playGif(final Context context, final Handler handler, final View vGifCarrier, final int gifResid, final BCGifPlayCallback callback) {
        // 把gif解析放在后台线程，解析完后再回到主线程设置
        new Thread() {
            @Override
            public void run() {
                GifAnimationDrawable gif;
                try {
                    gif = new GifAnimationDrawable(context.getResources()
                            .openRawResource(gifResid));
                } catch (Exception e) {
                    gif = null;
                    e.printStackTrace();
                }

                boolean suc = true;
                final HashMap<String, Object> map = new HashMap<>();
                map.put(MKEY_CARRIER, vGifCarrier);
                if (gif != null) {
                    map.put(MKEY_GIF, gif);

                } else {
                    suc = false;
                }

                final boolean sucRef = suc;
                final GifAnimationDrawable gifRef = gif;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (sucRef) {
                            handleGifDecodeSuc(map);
                            if (callback != null) {
                                callback.onPlayedSuc(gifRef);
                            }

                        } else {
                            handleGifDecodeFail(map);
                            if (callback != null) {
                                callback.onPalyedFail();
                            }

                        }
                    }
                });

            }
        }.start();
    }

    private static void putKeys(HashMap<String, Object> map, TextView tv, GifAnimationDrawable gif) {
        map.put(MKEY_CARRIER, tv);
        if (gif != null) {
            map.put(MKEY_GIF, gif);
        }
    }

    private static View getCarrier(HashMap<String, Object> map) {
        return (View) map.get(MKEY_CARRIER);
    }

    private static GifAnimationDrawable getGif(HashMap<String, Object> map) {
        return (GifAnimationDrawable) map.get(MKEY_GIF);
    }

    private static void handleGifDecodeSuc(HashMap<String, Object> map) {
        try {
            View vGifCarrier = getCarrier(map);
            GifAnimationDrawable gif = getGif(map);

            vGifCarrier.setBackgroundDrawable(gif);
            gif.setOneShot(false);
            gif.setVisible(true, true);
            if (DeviceUtils.hasLollipop()) {
                gif.start();
            }

        } catch (Exception e) {
            BCDebug.printStackTrace(BCDebug.BISON, "handleGifDecodeSuc 时异常", e);
        }
    }

    private static void handleGifDecodeFail(HashMap<String, Object> map) {
        BCDebug.e(BCDebug.BISON, "gif播放出错");
    }
}
