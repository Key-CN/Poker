package net.qjkj.poker.home.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;

import com.orhanobut.logger.Logger;

import net.qjkj.poker.BaseFragment;
import net.qjkj.poker.PokerApplication;
import net.qjkj.poker.R;
import net.qjkj.poker.data.RealmPlayerInfo;
import net.qjkj.poker.gandengyan.ui.GanDengYanActivity;
import net.qjkj.poker.home.IHomeContract;
import net.qjkj.poker.home.adapter.HomeGridViewAdapter;
import net.qjkj.poker.util.ToastUtils;

import java.util.List;

import butterknife.BindView;

import static dagger.internal.Preconditions.checkNotNull;

/**
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragmen
 */
public class HomeFragment extends BaseFragment implements IHomeContract.View {

    @BindView(R.id.krg_home_fmt)
    net.qjkj.poker.widget.KeyRadioGroupV1 krg_home_fmt;
    @BindView(R.id.b_play_home_fragment)
    Button b_play_home_fragment;
    @BindView(R.id.gv_home_fmt)
    GridView gv_home_fmt;
    @BindView(R.id.b_add_home_fmt)
    Button b_add_home_fmt;
    @BindView(R.id.b_delete_home_fmt)
    Button b_delete_home_fmt;

    /**
     * 通过重写第一级基类IBaseView接口的setPresenter()赋值
     */
    private IHomeContract.Presenter mPresenter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private HomeGridViewAdapter homeGridViewAdapter;

    /**
     * 翻译自谷歌官方文档
     * 每一个Fragment必须有一个空的构造方法，这样当Activity恢复状态时Fragment能够被实例化。
     * 强烈建议当我们继承Fragment类时，不要添加带有参数的构造方法，因为当Fragment被重新实例化时，这些构造方法不会被调用。
     * 如果需要给Fragment传递参数，可以调用setArguments(Bundle)方法，然后在Fragment中调用getArguments()来获取参数。
     * 这段话告诉我们两点。第一，Fragment必须要有空的构造方法。第二，最好不要通过构造方法传递参数。
     */
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public int getContentViewId() {
        return R.layout.home_fragment;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        //接口回调的方法
        onButtonPressed("调一下玩玩");
        //activity传进来的参数
        Logger.d("activity传进来的参数显示一下:  " + mParam1 + "---" + mParam2);

        // 获取游戏选手信息
        mPresenter.getPlayerInfoList();

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // 增加按钮的监听，弹窗增加选手
        b_add_home_fmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final View view = View.inflate(mContext, R.layout.home_addplayer_editview, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setIcon(R.mipmap.ic_launcher).setTitle("增加新玩家")
                .setView(view)
                .setNeutralButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            String playerName = ((EditText) view.findViewById(R.id.et_addplayer_editview_home_act)).getText().toString().trim();
                        if (playerName.isEmpty()) {
                            ToastUtils.getToast(mContext, "没有名字 人家记不住你嘛! >_<");
                        } else {
                            mPresenter.addPlayer(playerName);
                        }
                    }
                })
                .show();
            }
        });

        // 删除
        b_delete_home_fmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.deletePlayers();
            }
        });

        // 开始游戏按钮的监听
        b_play_home_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 获取游戏人员
                Logger.d(PokerApplication.realmRoundInfo.getPlayerNameList().toString());
                // 选择游戏
                int id = krg_home_fmt.getCheckedRadioButtonId();
                switch(id) {
                    case R.id.rb_gandengyan_home_fmt:
                        if (PokerApplication.realmRoundInfo.getRealmPlayerInfoList().size() >= 2) {
                            startActivity(new Intent(mContext, GanDengYanActivity.class));
                        } else {
                            ToastUtils.getToast(mContext, "干瞪眼最少两个人才能玩嘛！>_<");
                        }
                        break;
                    default:
                        ToastUtils.getToast(mContext,"暂无");
                        break;
                }
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String string) {
        if (mListener != null) {
            mListener.onFragmentInteraction(string);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            //如果子类没实现回掉方法就抛异常
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //销毁对象时注销接口回调，以防OOM
        mListener = null;
    }

    @Override
    public void setPresenter(@NonNull IHomeContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void updatePlayers(List<RealmPlayerInfo> copyRealmPlayerInfos) {
        //GridView适配器
        homeGridViewAdapter = new HomeGridViewAdapter(mContext, copyRealmPlayerInfos);
        gv_home_fmt.setAdapter(homeGridViewAdapter);
    }

    @Override
    public void updateAdapter(List<RealmPlayerInfo> copyRealmPlayerInfos) {
        homeGridViewAdapter.refresh(copyRealmPlayerInfos);
    }

    /**
     * 回掉接口，子类实现，父类声明该接口为成员变量，赋值为（ = ）自己，因为子类已经实现，所以可以使用子类自己的方法
     * 父类用该接口的方法就等于给子类发消息
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * 此接口必须由包含此fragment的activities来实现
     * 以允许将此fragment中的交互传达给该activity以及该activity中包含的潜在其他fragments。
     * <p> 培训地址：
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(String string);
    }

}
