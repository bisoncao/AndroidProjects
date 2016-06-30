package com.bisoncao.bcnetworkutil;

import java.io.IOException;
import android.os.Handler;
import android.util.Log;

/**
 * Common functions of network.
 * @author Patrick Cao (alantim110@163.com)
 * @created 3:01 p.m., 06/29/2016
 */
public final class NetworkUtil {

    public static final String TAG = "NetworkUtil";

    /**
     * Default URL of Internet access detection.
     */
    public static final String DEFAULT_DETECT_URL = "www.baidu.com";
    public static final int DEFAULT_DETECT_TIMEOUT = 3000;

    private boolean hasDetectEnd;
    private boolean hasDetectTimeout;
    private Thread mDetectThread;
    private Handler mDetectHandler;

    /**
     * Use default URL and timeout for detection.
     * @see #hasInternetAccess(String, int, Handler, DetectCallback)
     */
    public void hasInternetAccess(Handler handler, DetectCallback callback) {
        hasInternetAccess(DEFAULT_DETECT_URL, DEFAULT_DETECT_TIMEOUT, handler,
                callback);
    }

    /**
     * Use default timeout for detection.
     * @see #hasInternetAccess(String, int, Handler, DetectCallback)
     */
    public void hasInternetAccess(int detectTimeout, Handler handler,
            DetectCallback callback) {
        hasInternetAccess(DEFAULT_DETECT_URL, detectTimeout, handler, callback);
    }

    /**
     * Use default URL for detection.
     * @see #hasInternetAccess(String, int, Handler, DetectCallback)
     */
    public void hasInternetAccess(String detectUrl, Handler handler,
            DetectCallback callback) {
        hasInternetAccess(detectUrl, DEFAULT_DETECT_TIMEOUT, handler, callback);
    }

    /**
     * If has, {@link DetectCallback#onDetectSuccess()} will be called; if not,
     * {@link DetectCallback#onDetectFail()} ()} will be called;
     * if timeout, {@link DetectCallback#onDetectTimeout()} ()} will be called.
     * @see #hasInternetAccessInner(String)
     */
    public void hasInternetAccess(final String detectUrl,
            final int detectTimeout, Handler handler,
            final DetectCallback callback) {
        Log.d(TAG, "Detecting internet access...");
        hasDetectEnd = false;
        hasDetectTimeout = false;
        mDetectHandler = handler;
        mDetectThread = new Thread() {
            @Override
            public void run() {
                /**
                 * Start timeout mechanism. (Will interrupt blocking internet
                 * access detection if timeout occurs.)
                 */
                if (!hasExited()) {
                    mDetectHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            handleDetectTimeout(callback);
                        }
                    }, detectTimeout);
                }

                // Start blocking internet access detection.
                final boolean hasInternetAccess = hasInternetAccessInner(detectUrl);
                if (!hasExited()) {
                    mDetectHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            handleDetectSuccessOrFail(hasInternetAccess,
                                    callback);
                        }
                    });
                }

                Log.d(TAG, "hasInternetAccess() calling end");
            }
        };
        mDetectThread.start();
    }

    /**
     * Judge if really has internet access. Sometimes even though it is
     * connected to WIFI, it
     * cannot access internet normally (maybe because of captive portal).
     * @return true if has; false if not has or an error occurred.
     */
    private boolean hasInternetAccessInner(String detectUrl) {
        String result = null;
        try {
            String ip = detectUrl;// ping 的地址，可以换成任何一种可靠的外网
            Process p = null;// ping网址3次
            Log.d(TAG, "hasInternetAccessInner() start ping");
            p = Runtime.getRuntime().exec("ping -c 3 -w 100 " + ip);
            Log.d(TAG, "hasInternetAccessInner() end ping");
            // ping的状态
            Log.d(TAG, "hasInternetAccessInner() waitFor start");
            int status = p.waitFor();
            Log.d(TAG, "hasInternetAccessInner() waitFor end");
            if (status == 0) {
                result = "success";
                return true;
            } else {
                result = "failed";
            }
        } catch (IOException e) {
            result = "IOException";
        } catch (InterruptedException e) {
            result = "InterruptedException";
        } catch (Exception e) {
            result = "other exception";
        } catch (Error e) {
            result = "error";
        } finally {
            Log.d(TAG, "hasInternetAccessInner() result: " + result);
        }
        return false;
    }

    private void handleDetectSuccessOrFail(boolean success,
            DetectCallback callback) {
        // Do not handle if has exited.
        if (hasExited()) {
            return;
        }
        if (!hasDetectTimeout) {
            hasDetectEnd = true;
            mDetectThread = null;
            if (success) {
                callback.onDetectSuccess();
            } else {
                callback.onDetectFail();
            }
        }
    }

    private void handleDetectTimeout(DetectCallback callback) {
        // Do not handle if has exited.
        if (hasExited()) {
            return;
        }
        if (!hasDetectEnd) {
            hasDetectTimeout = true;
            if (mDetectThread != null) {
                mDetectThread.interrupt();
            }
            mDetectThread = null;
            callback.onDetectTimeout();
        }
    }

    /**
     * Avoid memory leak.
     */
    public void unregister() {
        mDetectHandler = null;
    }

    /**
     * Whether the object on which the handler is based has exited.
     */
    private boolean hasExited() {
        return mDetectHandler == null;
    }

    public interface DetectCallback {
        void onDetectSuccess();

        void onDetectFail();

        void onDetectTimeout();
    }
}
