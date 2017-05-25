package com.provid15.utils;

import java.lang.reflect.Method;
import java.util.ArrayList;

import android.util.Log;
import com.provid15.BuildConfig;

public class LogUtil {

    private static String classname;

    private static ArrayList<String> methods;

    static {
        classname = LogUtil.class.getName();
        methods = new ArrayList<String>();

        Method[] ms = LogUtil.class.getDeclaredMethods();
        for (Method m : ms) {
            methods.add(m.getName());
        }
    }


    public static void d(String msg) {
        if (BuildConfig.DEBUG) {
            String[] content = getMsgAndTagWithLineNumber(msg);
            Log.d(content[0], content[1]);
        }
    }


    public static void d(String tag, String msg) {
        if (BuildConfig.DEBUG) {

            Log.d(tag, getMsgWithLineNumber(msg));
        }
    }


    public static void d(Throwable t) {
        e(t.getMessage());
    }


    public static void e(String msg) {
        if (BuildConfig.DEBUG) {
            String[] content = getMsgAndTagWithLineNumber(msg);
            Log.e(content[0], content[1]);
        }
    }


    public static void e(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, getMsgWithLineNumber(msg));
        }
    }

    public static void e(Throwable t) {
        if (BuildConfig.DEBUG) {
            if (t != null) {
                t.printStackTrace();
            }
        }
    }

    public static void i(String msg) {
        if (BuildConfig.DEBUG) {
            String[] content = getMsgAndTagWithLineNumber(msg);
            Log.i(content[0], content[1]);
        }
    }


    public static void i(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Log.i(tag, getMsgWithLineNumber(msg));
        }
    }


    public static void i(Throwable t) {
        e(t.getMessage());
    }


    public static void v(String msg) {
        if (BuildConfig.DEBUG) {
            String[] content = getMsgAndTagWithLineNumber(msg);
            Log.v(content[0], content[1]);
        }
    }


    public static void v(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Log.v(tag, getMsgWithLineNumber(msg));
        }
    }


    public static void v(Throwable t) {
        e(t.getMessage());
    }


    public static void w(String msg) {
        if (BuildConfig.DEBUG) {
            String[] content = getMsgAndTagWithLineNumber(msg);
            Log.w(content[0], content[1]);
        }
    }

    public static void w(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Log.w(tag, getMsgWithLineNumber(msg));
        }
    }


    public static void w(Throwable t) {
        e(t.getMessage());
    }


    private static String[] getMsgAndTagWithLineNumber(String msg) {
        try {
            for (StackTraceElement st : (new Throwable()).getStackTrace()) {
                if (classname.equals(st.getClassName()) || methods.contains(st.getMethodName())) {
                    continue;
                } else {
                    int b = st.getClassName().lastIndexOf(".") + 1;
                    String tag = st.getClassName().substring(b);
                    String message = st.getMethodName() + "():" + st.getLineNumber() + "->" + msg;
                    String[] content = new String[] {tag, message};
                    return content;
                }

            }
        } catch (Exception e) {
            LogUtil.e(e);
        }
        return new String[] {"MoboGenie", msg};
    }


    private static String getMsgWithLineNumber(String msg) {
        try {
            for (StackTraceElement st : (new Throwable()).getStackTrace()) {
                if (classname.equals(st.getClassName()) || methods.contains(st.getMethodName())) {
                    continue;
                } else {
                    int b = st.getClassName().lastIndexOf(".") + 1;
                    String tag = st.getClassName().substring(b);
                    String message = tag + "->" + st.getMethodName() + "():" + st.getLineNumber() + "->" + msg;
                    return message;
                }

            }
        } catch (Exception e) {
            LogUtil.e(e);
        }
        return msg;
    }

    public static void p(String value) {
        d("mobopush", value);
    }

}
