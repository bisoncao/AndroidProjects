package nom.bisoncao.bcviewfinder.utils;

import android.util.Log;

import java.util.List;

public class TataDebugParent {
	public static final String HUIBIN = "huibin";

	private boolean mDebug;

	public TataDebugParent(boolean isDebug) {
		mDebug = isDebug;
	}

	/**
	 * 输出Debug级信息（仅当DEBUG为true时）
	 * 
	 * @param tag
	 * @param msg
	 */
	public void d2(String tag, String msg) {
		if (mDebug) {
			Log.d(tag, msg + "");
		}
	}

    /**
     * 输出Info级信息（仅当DEBUG为true时）
     *
     * @param tag
     * @param msg
     */
    public void i2(String tag, String msg) {
        if (mDebug) {
            Log.i(tag, msg + "");
        }
    }

     /**
	 * 输出Error级信息（仅当DEBUG为true时）
	 * 
	 * @param tag
	 * @param msg
	 */
	public void e2(String tag, String msg) {
		if (mDebug) {
			Log.e(tag, msg + "");
		}
	}

	/**
	 * 输出Warning级信息（仅当DEBUG为true时）
	 */
	public void w2(String tag, String msg) {
		if (mDebug) {
			Log.w(tag, msg + "");
		}
	}

	/**
	 * 输出异常轨迹
	 */
	public void printStackTrace2(Exception e) {
		if (mDebug) {
			e.printStackTrace();
		}
	}

	/**
	 * 输出错误轨迹
	 */
	public void printStackTraceErr2(Error e) {
		if (mDebug) {
			e.printStackTrace();
		}
	}

	/**
	 * 输出含字符串数组的Debug级信息（仅当DEBUG为true时）
	 */
	public void dArr2(String tag, String msg, String[] arr) {
		if (mDebug) {
			Log.d(tag, msg);
			String res = "";
			if (arr != null) {
				for (String string : arr) {
					res += string + ";";
				}
				res = res.substring(0, res.length() - 1);
			}
			Log.d(tag, res);

		}
	}

	/**
	 * 输出含字符串list的Debug级信息（仅当DEBUG为true时）
	 */
	public void dList2(String tag, String msg, List<String> list) {
		if (mDebug) {
			String res = msg;
			if (list != null && list.size() != 0) {
				for (String string : list) {
					res += string + ";";
				}
				res = res.substring(0, res.length() - 1);
			}
			Log.d(tag, res);

		}
	}

}
