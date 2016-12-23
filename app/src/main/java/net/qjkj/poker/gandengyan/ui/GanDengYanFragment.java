package net.qjkj.poker.gandengyan.ui;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import net.qjkj.poker.BaseFragment;
import net.qjkj.poker.PokerApplication;
import net.qjkj.poker.R;
import net.qjkj.poker.data.RealmPlayerScoreInfo;
import net.qjkj.poker.gandengyan.IGanDengYanContract;
import net.qjkj.poker.gandengyan.adapter.ScoreAdapterN;
import net.qjkj.poker.util.ToastUtils;

import butterknife.BindView;

import static dagger.internal.Preconditions.checkNotNull;


/**
 * Created by Key on 2016/11/28 21:43
 * email: MrKey.K@gmail.com
 * description:
 */

public class GanDengYanFragment extends BaseFragment implements IGanDengYanContract.View {

    @BindView(R.id.rv_gandengyan_fmt)
    RecyclerView rv_gandengyan_fmt;
    @BindView(R.id.rv_scorelist_gandengyan_fmt)
    RecyclerView rv_scorelist_gandengyan_fmt;
    @BindView(R.id.rv_namelist_gandengyan_fmt)
    RecyclerView rv_namelist_gandengyan_fmt;
    @BindView(R.id.b_save_gandengyan_fmt)
    Button b_save_gandengyan_fmt; //保存进入下一局

    @BindView(R.id.tv_boom_gandengyan_fmt)
    TextView tv_boom_gandengyan_fmt; //炸弹倍数

    @BindView(R.id.iv_minus_gandengyan_fmt)
    ImageView iv_minus_gandengyan_fmt; //减
    @BindView(R.id.iv_add_gandengyan_fmt)
    ImageView iv_add_gandengyan_fmt; //加


    private PopupWindow popup;
    private View popupContent;
    // 炸弹
    private int boom = -1;
    // 记录赢的人的位置
    private int winnerPosition = -1;
    // 本次游戏人数，多个地方经常要用
    private int playerListSize;

    public GanDengYanFragment() {
        // 公共空构造
    }

    public static GanDengYanFragment newInstance() {
        GanDengYanFragment fragment = new GanDengYanFragment();
        return fragment;
    }

    private IGanDengYanContract.Presenter mPresenter;

    /**
     * @return 布局文件ID
     */
    @Override
    public int getContentViewId() {
        return R.layout.gandengyan_fragment;
    }

    /**
     * TODO 初始化布局文件
     *
     * @param savedInstanceState
     */
    @Override
    protected void initView(Bundle savedInstanceState) {
        playerListSize = PokerApplication.checkedPlayerList.size();
    }

    @Override
    public void setPresenter(@NonNull IGanDengYanContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // popup 填充
        // View popupContent = LayoutInflater.from(mContext).inflate(R.layout.gandengyan_popup, null);
        popupContent = View.inflate(mContext, R.layout.gandengyan_popup, null);

        // MATCH_PARENT = -1;   WRAP_CONTENT = -2; 所有的LayoutParams都继承于ViewGroup
        // 如果PopupWindow中有Editor的话，focusable要为true。
        popup = new PopupWindow(popupContent, -2, -2, true);
        popup.setTouchable(true);
        popup.setOutsideTouchable(true);
        popup.setBackgroundDrawable(new ColorDrawable(0));


        // 计算分数的Recycler填充
        rv_gandengyan_fmt.setLayoutManager(new LinearLayoutManager(mContext));
        // adapter
        rv_gandengyan_fmt.setAdapter(new CommonAdapter<RealmPlayerScoreInfo>(mContext, R.layout.gandengyan_recyclerview_item, PokerApplication.realmRoundInfo.getPlayerScoreList()) {

            private String winner;

            void setWinner() {
                winner = "0";
                for (int i = 0; i < playerListSize; i++) {
                    RealmPlayerScoreInfo scoreInfo = PokerApplication.realmRoundInfo.getPlayerScoreList().get(i);
                    if (!scoreInfo.getRemain().equals("赢")) {
                        winner = Integer.parseInt(winner) - Integer.parseInt(scoreInfo.getScore()) + "";
                    }
                }
            }

            @Override
            protected void convert(final ViewHolder holder, RealmPlayerScoreInfo player, final int position) {

                // 设置监听器
                holder.setOnClickListener(R.id.tv_remain_gandengyan_recycler_item, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        switch (v.getId()) {
                            case R.id.b_0_popup:
                            case R.id.b_1_popup:
                            case R.id.b_2_popup:
                            case R.id.b_3_popup:
                            case R.id.b_4_popup:
                            case R.id.b_5_popup:
                                // 记录手牌
                                String remain = ((TextView) v).getText().toString();
                                // 计算得分并记录
                                // String score = Integer.parseInt((remain.equals("5") ? "10" : remain)) * boom + "";
                                String score = Integer.parseInt(remain) * (remain.equals("5") ? boom * 2 : boom) + "";
                                setText(remain, score);
                                popup.dismiss();
                                break;
                            default:
                                // 用向下弹减去自身高度和锚点高度。等于向上弹。但是第一次还是向下弹的，已经把popupContent在本方法上方初始化了。还是这样
                                popup.showAsDropDown(v, 0, (-popupContent.getHeight() - v.getHeight()));
                                // 从内部创建监听器，这样可以直接使用holder
                                // 由于运行顺序的问题。监听器在这里才赋值成功，只能在这里设置监听器
                                // popup 的监听器
                                for (int i = 0; i < 6; i++) {
                                    ((ViewGroup) popupContent).getChildAt(i).setOnClickListener(this);
                                }
                                break;
                        }
                    }

                    void setText(String remain, String score) {

                        if (winnerPosition == position) {
                            winnerPosition = -1;
                        }

                        if (remain.equals("0")) {
                            remain = "赢";
                            if (winnerPosition != -1) {
                                ToastUtils.getToast(mContext,"只能有一个赢家哦！~");
                                return;
                            }
                        }

                        // 储存得分
                        if (remain.equals("赢")) {
                            winnerPosition = position;
                        } else {
                            PokerApplication.realmRoundInfo.getPlayerScoreList().get(position).setScore(score);
                        }

                        // 储存剩余手牌数
                        PokerApplication.realmRoundInfo.getPlayerScoreList().get(position).setRemain(remain);

                        // 储存赢的人的得分
                        setWinner();
                        if (winnerPosition != -1) {
                            PokerApplication.realmRoundInfo.getPlayerScoreList().get(winnerPosition).setScore(winner);
                            //示赢的人的得分数
                            notifyItemChanged(winnerPosition);
                        }

                        // 显示剩余手牌数
                        // holder.setText(R.id.tv_remain_gandengyan_recycler_item, PokerApplication.realmRoundInfo.getPlayerScoreList().get(position).getRemain());

                        // 显示得分
                        // holder.setText(R.id.tv_score_gandengyan_recycler_item, PokerApplication.realmRoundInfo.getPlayerScoreList().get(position).getScore());

                        // 用刷新代替两个显示
                        notifyItemChanged(position);
                    }
                });

                // 设置名字
                holder.setText(R.id.tv_name_gandengyan_recycler_item, player.getPlayerName());

                // 显示剩余手牌数
                holder.setText(R.id.tv_remain_gandengyan_recycler_item, PokerApplication.realmRoundInfo.getPlayerScoreList().get(position).getRemain());

                // 显示得分
                holder.setText(R.id.tv_score_gandengyan_recycler_item, PokerApplication.realmRoundInfo.getPlayerScoreList().get(position).getScore());

                // 不能用 player.getScore() 代替 PokerApplication.realmRoundInfo.getPlayerScoreList().get(position).getScore()
                // player 是老的数据源
                // PokerApplication.realmRoundInfo.getPlayerScoreList().get(position) 是最新的数据源
                // 不能生效，是传进来的数据源的问题。因为这个CommonAdapter并没有写更新数据源的方法，所以要简化代码还是要自己写一个adapter
            }
        });


        // 得分表名字 填充
        rv_namelist_gandengyan_fmt.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        // 添加分割线     public static final int HORIZONTAL = 0; public static final int VERTICAL = 1;
        rv_namelist_gandengyan_fmt.addItemDecoration(new DividerItemDecoration(mContext, 0));
        rv_namelist_gandengyan_fmt.setAdapter(new CommonAdapter<RealmPlayerScoreInfo>(mContext, R.layout.gandengyan_name_item, PokerApplication.realmGameInfo.getRealmRoundInfoList().get(0).getPlayerScoreList()) {
            @Override
            protected void convert(ViewHolder holder, RealmPlayerScoreInfo realmPlayerScoreInfo, int position) {
                holder.setText(R.id.tv_name_item_gandengyan, realmPlayerScoreInfo.getPlayerName());
                holder.setText(R.id.tv_total_item_gandengyan, realmPlayerScoreInfo.getScore());
            }
        });


        // 得分表名字的填充
        rv_scorelist_gandengyan_fmt.setLayoutManager(new GridLayoutManager(mContext, playerListSize));

        // 添加竖的分割线
        rv_scorelist_gandengyan_fmt.addItemDecoration(new DividerItemDecoration(mContext, GridLayoutManager.HORIZONTAL));

        // 得分表分数的adapter
        rv_scorelist_gandengyan_fmt.setAdapter(new ScoreAdapterN(mContext));  // 无悬停头

        /*
        ScoreAdapterX scoreAdapterX = new ScoreAdapterX(mContext);
        StickyHeaderDecoration stickyHeaderDecoration = new StickyHeaderDecoration(scoreAdapterX);
        LRecyclerViewAdapter lRecyclerViewAdapter = new LRecyclerViewAdapter(scoreAdapterX);

        rv_scorelist_gandengyan_fmt.setAdapter(lRecyclerViewAdapter);
        rv_scorelist_gandengyan_fmt.addItemDecoration(stickyHeaderDecoration);
        */


        // b_save_gandengyan_fmt 保存按钮监听
        b_save_gandengyan_fmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (winnerPosition == -1) {
                    ToastUtils.getToast(mContext, "必须有一个赢家");
                    return;
                }
                mPresenter.saveRound();
            }
        });


        // 炸弹加减
        BoomOnClickListener boomOnClickListener = new BoomOnClickListener();
        iv_minus_gandengyan_fmt.setOnClickListener(boomOnClickListener);
        iv_add_gandengyan_fmt.setOnClickListener(boomOnClickListener);
        tv_boom_gandengyan_fmt.setOnClickListener(boomOnClickListener);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    /**
     * 刷新得分表数据
     */
    @Override
    public void updateScore() {
        // 动画效果感觉像卡顿。放弃
/*        for (int i = 0; i < playerListSize; i++) {
            // 刷新总分
            rv_namelist_gandengyan_fmt.getAdapter().notifyItemChanged(i);
            // 清除得分和手牌数后刷新
            rv_gandengyan_fmt.getAdapter().notifyItemChanged(i);
        }*/

        // 刷新总分
        rv_namelist_gandengyan_fmt.getAdapter().notifyDataSetChanged();
        // 清除得分和手牌数后刷新
        rv_gandengyan_fmt.getAdapter().notifyDataSetChanged();
        // 刷新得分表
        rv_scorelist_gandengyan_fmt.getAdapter().notifyDataSetChanged();
        // 重置赢家
        winnerPosition = -1;
        // 重置炸弹
        boom = -1;
        setBoom();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        PokerApplication.realmGameInfo = null;
    }

    class BoomOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            int oldBoom = boom;
            switch (v.getId()) {
                case R.id.tv_boom_gandengyan_fmt:
                    boom = -1;
                    break;
                case R.id.iv_add_gandengyan_fmt:
                    boom -= 1;
                    break;
                case R.id.iv_minus_gandengyan_fmt:
                    if (boom == -1) return;
                    boom += 1;
                    break;
            }
            setBoom();
            setScore(oldBoom);
        }
    }

    /**
     * 设置炸弹
     */
    public void setBoom() {
        if (boom == -1) {
            tv_boom_gandengyan_fmt.setText("无");
        } else {
            tv_boom_gandengyan_fmt.setText((-boom + ""));
        }
    }

    /**
     * 改变炸弹倍数后，刷新得分
     */
    public void setScore(int oldBoom) {
        for (RealmPlayerScoreInfo info : PokerApplication.realmRoundInfo.getPlayerScoreList()) {
            // 新的得分 = 旧的得分 / 旧的倍数 * 新的倍数
            info.setScore(Integer.parseInt(info.getScore()) / oldBoom * boom + "");
        }
        rv_gandengyan_fmt.getAdapter().notifyDataSetChanged();

        // 换种方式 增加更新动画
        /*
        for (int i = 0; i < playerListSize; i++) {
            RealmPlayerScoreInfo info = PokerApplication.realmRoundInfo.getPlayerScoreList().get(i);
            info.setScore(Integer.parseInt(info.getScore()) / oldBoom * boom + "");
            rv_gandengyan_fmt.getAdapter().notifyItemChanged(i);
        }
        */

    }

}
