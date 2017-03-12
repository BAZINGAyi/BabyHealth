package com.example.bazinga.babyhealth.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.bazinga.babyhealth.R;
import com.example.bazinga.babyhealth.adapter.ContactsAdapter;
import com.example.bazinga.babyhealth.adapter.RecommendAdapter;
import com.example.bazinga.babyhealth.bean.Constant;
import com.example.bazinga.babyhealth.bean.Contacts;
import com.example.bazinga.babyhealth.ui.SettingActivity;

import butterknife.BindView;

/**
 * Created by bazinga on 2017/3/10.
 */

public class RecommendFragment extends Fragment {

    private Button navButton;

    private Button backHome;

    public DrawerLayout drawerLayout;

    public TextView textView;

    private RecyclerView recyclerView;

    private RecommendAdapter recommendAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.recommend_fragment, container, false);

        initView(view);

        addImgData();

        getData();

        initRecyclerView();

        return view;
    }

    private void addImgData() {

        Constant.imgs.add(R.mipmap.item_recmmend);
        Constant.imgs.add(R.mipmap.item_recmmend1);
        Constant.imgs.add(R.mipmap.item_recmmend2);
        Constant.imgs.add(R.mipmap.item_recmmend3);
        Constant.imgs.add(R.mipmap.item_recmmend4);


        Constant.suggest.add("孩子在某些岁数的时候就会出现一些特定的行为，不同的年龄段，其发展、学习和行为都存在差异，爸爸妈妈需要理解为什么孩子表现出某种方式，这能帮助爸爸妈妈采取合适的方法来引导孩子。");
        Constant.suggest.add("孩子三岁的时候非常有创造性，会表现出惊人的想象力。");
        Constant.suggest.add("三岁的孩子有着非常浓重的好奇精神，总是会做一些你看起来很危险的事情。孩子可能爬上椅子去够高处的饼干罐，或骑在车上假装是开车去商店。爸爸妈妈应该给孩子探索和尝试新事物的机会，但同时要确保孩子的安全。");
        Constant.suggest.add("上课不用心听讲的孩子。此类孩子主要表现为上课多动、好玩、爱讲话，甚至在家中学习也表现出心不在焉对此类孩子的教育，有的家长说，“那是学校的事，不该我来管，我又不能坐在孩子旁边。”实际上，练习孩子用心听讲，要从日常糊口入手，由于糊口习惯和学习习惯是紧密相关的。");
        Constant.suggest.add("实际上，练习孩子用心听讲，要从日常糊口入手，由于糊口习惯和学习习惯是紧密相关的。");

        Constant.suggest_head.add("练习孩子用心听讲");
        Constant.suggest_head.add("三岁的孩子有着非常浓重的好奇精神");
        Constant.suggest_head.add("要从日常糊口入手");
        Constant.suggest_head.add("上课不用心听讲的孩子");
        Constant.suggest_head.add("表现出惊人的想象力");

    }

    private void initRecyclerView() {

        StaggeredGridLayoutManager ll = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);

        ll.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(ll);

        recommendAdapter = new RecommendAdapter(Constant.dataRecommend,getContext());

        recyclerView.setAdapter(recommendAdapter);

    }

    private void getData() {

        for (int i = 0; i < 16; i++) {

            Contacts con = new Contacts();

            con.setName("张" + i + "哦");

            con.setImage(" ");

            con.setPhone("在人的生长发育过程中，共有两次生长高峰——婴儿期和青春前期。" + i);

            Constant.dataRecommend.add(con);

        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void initView(View view) {

        navButton = (Button) view.findViewById(R.id.nav_button) ;

        backHome = (Button) view.findViewById(R.id.titleBackButton) ;

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        drawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);

        textView = (TextView)view.findViewById(R.id.title_baby);

        textView.setText("育儿推荐");

        navButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        backHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

}
