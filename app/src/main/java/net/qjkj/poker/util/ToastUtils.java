package net.qjkj.poker.util;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {
    public static Toast toast = null;
    private ToastUtils(){}

    /**
     * 超级连弹土司特，哟哟
     * @param context 上下文
     * @param s 弹出的字符串
     */
    public static void getToast(Context context, String s){
        if(toast == null){
            toast = Toast.makeText(context,"", Toast.LENGTH_SHORT);
        }
        toast.setText(s);
        toast.show();
    }
}
