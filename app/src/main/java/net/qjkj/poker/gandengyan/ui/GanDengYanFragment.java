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
import android.widget.Button;
import android.widget.PopupWindow;

import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import net.qjkj.poker.BaseFragment;
import net.qjkj.poker.PokerApplication;
import net.qjkj.poker.R;
import net.qjkj.poker.gandengyan.IGanDengYanContract;

import java.util.ArrayList;
import java.util.List;

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
    Button b_save_gandengyan_fmt;

    private PopupWindow popup;

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

    }

    @Override
    public void setPresenter(@NonNull IGanDengYanContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // popup 填充
        // View v1 = LayoutInflater.from(mContext).inflate(R.layout.gandengyan_popup, null);
        View v2 = View.inflate(mContext, R.layout.gandengyan_popup, null);
        // MATCH_PARENT = -1;   WRAP_CONTENT = -2; 所有的LayoutParams都继承于ViewGroup
        // 如果PopupWindow中有Editor的话，focusable要为true。
        popup = new PopupWindow(v2, -2, -2);
        popup.setTouchable(true);
        popup.setOutsideTouchable(true);
        popup.setBackgroundDrawable(new ColorDrawable(0));

        // 计算分数的Recycler填充
        rv_gandengyan_fmt.setLayoutManager(new LinearLayoutManager(mContext));
        rv_gandengyan_fmt.setAdapter(new CommonAdapter<String>(mContext, R.layout.gandengyan_recyclerview_item, PokerApplication.checkedPlayers) {
            @Override
            protected void convert(final ViewHolder holder, String s, int position) {
                holder.setText(R.id.tv_name_gandengyan_recycler_item, s);
                holder.setOnClickListener(R.id.tv_remain_gandengyan_recycler_item, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //
                        popup.showAsDropDown(v);
                        // holder.setText(R.id.tv_score_gandengyan_recycler_item, "");

                    }
                });
            }
        });

        // 姓名表填充
        rv_namelist_gandengyan_fmt.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        rv_namelist_gandengyan_fmt.setAdapter(new CommonAdapter<String>(mContext, R.layout.gandengyan_name_item, PokerApplication.checkedPlayers) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                holder.setText(R.id.tv_name_item_gandengyan, s);
            }
        });

        // 得分表的填充
        List<String> l = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            l.add(i+"");
        }
        rv_scorelist_gandengyan_fmt.setLayoutManager(new GridLayoutManager(mContext, PokerApplication.checkedPlayers.size()));
        DividerItemDecoration decor = new DividerItemDecoration(mContext, GridLayoutManager.HORIZONTAL);

        rv_scorelist_gandengyan_fmt.addItemDecoration(decor);
        rv_scorelist_gandengyan_fmt.setAdapter(new CommonAdapter<String>(mContext, R.layout.gandengyan_name_item, l) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                holder.setText(R.id.tv_name_item_gandengyan, s);
            }
        });

    }

    interface RemainCallback {

    }
}