package net.qjkj.poker.home.ui;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import com.orhanobut.logger.Logger;

import net.qjkj.poker.R;
import net.qjkj.poker.util.KeyboardUtils;
import net.qjkj.poker.util.ToastUtils;

import java.util.Timer;
import java.util.TimerTask;

import static net.qjkj.poker.R.layout.dialog;

/**
 * Created by Key on 2016/12/21 15:30
 * email: MrKey.K@gmail.com
 * description:
 */

public class AddDialogFragment extends DialogFragment {

    private OnAddPlayerListener mListener;

    // 这个可以全部显示。可行！！
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Logger.d("tag: " + getTag());

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);


        View view = inflater.inflate(dialog, container);

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0));

        final EditText nameEditText = (EditText) view.findViewById(R.id.et_home_dialog);

        // 也可以在XML中设置 android:imeOptions="actionDone"  但是不知道为什么不生效？ 因为第三方输入法 不起作用
        nameEditText.setImeOptions(EditorInfo.IME_ACTION_DONE);

        nameEditText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // 按键为回车，并且活动为抬起时执行， 必须要判断按下或抬起，不然会进两次 if 执行两次方法
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    checkName(nameEditText);
                    dismiss();
                    return true;
                }
                return false;
            }
        });

/*        nameEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // 必须要判断按下或抬起，不然会进两次 if 执行两次方法
                if (actionId == EditorInfo.IME_ACTION_DONE && event.getAction() == KeyEvent.ACTION_UP) {
                    checkName(nameEditText);
                    dismiss();
                    return true;
                }
                return false;
            }
        });*/

        // 增加按钮的监听
        view.findViewById(R.id.b_home_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkName(nameEditText);
                dismiss();
            }
        });

        // 叉叉的监听
        view.findViewById(R.id.iv_home_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        // dialog 初始化完成前，打开软件是不会生效的，所以加个定时器
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                KeyboardUtils.openKeyboard(getContext(), nameEditText);
            }
        }, 150);


        // 写不写一样， API 23 不会抬起
        getDialog().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);                  //这两段起作用
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);    //这两段起作用

        return view;
    }


    private void checkName(EditText nameEditText) {
        String playerName = nameEditText.getText().toString().trim();
        if (TextUtils.isEmpty(playerName)) {
            ToastUtils.getToast(getContext(), "没有名字 人家记不住你嘛! >_<");
        } else {
            mListener.onAddPlayer(playerName);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AddDialogFragment.OnAddPlayerListener) {
            mListener = (AddDialogFragment.OnAddPlayerListener) context;
        } else {
            //如果子类没实现回掉方法就抛异常
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    /**
     * 子类实现增加选手的方法
     * 其实这个接口写在哪里都可以，不一定要写成内部的
     */
    public interface OnAddPlayerListener {
        /**
         * 增加选手
         * @param playerName
         */
        void onAddPlayer(String playerName);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //销毁对象时注销接口回调，以防OOM
        mListener = null;
    }

    // 依旧只能到EditText下方为止
/*    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View view = View.inflate(getContext(), R.layout.dialog, null);
        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).setView(view).create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));      // 第二套dialog


//        View view = View.inflate(getContext(), R.layout.home_addplayer_editview, null);
//
//        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).setIcon(R.mipmap.ic_launcher).setTitle("增加新玩家")
//                .setView(view)
//                .setNeutralButton("取消", null)
//                .setPositiveButton("确定", null)
//                .create();

        return alertDialog;
    }*/
}
