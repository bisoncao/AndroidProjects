package nom.bisoncao.bcviewfinder.utils;

import java.util.List;

public class TataDebug extends TataDebugParent {

	public static final boolean IS_DEBUG = TUConst.HUIBIN_DEBUG;

	private static TataDebug instance;

	private TataDebug(boolean isDebug) {
		super(isDebug);

	}

	public static TataDebug getInstance() {
		if (instance == null) {
			instance = new TataDebug(IS_DEBUG);
		}
		return instance;
	}

	public static void d(String tag, String msg) {
		getInstance().d2(tag, msg);
	}

    public static void i(String tag, String msg) {
        getInstance().i2(tag, msg);
    }



    public static void e(String tag, String msg) {
		getInstance().e2(tag, msg);
	}

    /**
     * 输出错误信息并打印stack trace
     * @param tag
     * @param msg
     * @param e
     */
    public static void e(String tag, String msg, Exception e) {
        getInstance().e2(tag, msg);
        printStackTrace(e);
    }

	public static void w(String tag, String msg) {
		getInstance().w2(tag, msg);
	}

	public static void printStackTrace(Exception e) {
		getInstance().printStackTrace2(e);
	}

	public static void printStackTraceErr(Error e) {
		getInstance().printStackTraceErr2(e);
	}

	public static void dArr(String tag, String msg, String[] arr) {
		getInstance().dArr2(tag, msg, arr);
	}

	public static void dList(String tag, String msg, List<String> list) {
		getInstance().dList2(tag, msg, list);
	}

}
