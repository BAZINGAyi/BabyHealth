package com.example.bazinga.babyhealth.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.bazinga.babyhealth.R;
import com.example.bazinga.babyhealth.ViewHolder.ContactsViewHolder;
import com.example.bazinga.babyhealth.ViewHolder.RecommendViewHolder;
import com.example.bazinga.babyhealth.bean.Constant;
import com.example.bazinga.babyhealth.bean.Contacts;
import com.example.bazinga.babyhealth.ui.DetailRecommend;
import com.example.bazinga.babyhealth.ui.SettingActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by bazinga on 2017/3/10.
 */

public class RecommendAdapter extends RecyclerView.Adapter<RecommendViewHolder> {

    private List<Contacts> datas = new ArrayList<>();

    private Context context;

    public RecommendAdapter(List<Contacts> datas , Context context){

        this.datas = datas ;

        this.context = context;
    }

    @Override
    public RecommendViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommend,parent,false);

        final RecommendViewHolder holder = new RecommendViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(final RecommendViewHolder holder, final int position) {

        holder.contacts_Name.setText(datas.get(position).getName());

        holder.contacts_Phone.setText(datas.get(position).getPhone());

        Random random = new Random();

        int ra = random.nextInt(5);

        holder.contactsImage.setBackgroundResource(Constant.imgs.get(ra));

        holder.contacts_Name.setText(Constant.suggest_head.get(ra));

        holder.contacts_Phone.setText(Constant.suggest.get(ra));

        final int pos = holder.getAdapterPosition();

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context,DetailRecommend.class);

                context.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {

        return datas.size();

    }


    private void showDialogDelete(Context context , final int pos) {

        AlertDialog.Builder builder=new AlertDialog.Builder(context);  //先得到构造器

        builder.setTitle("提示"); //设置标题

        builder.setMessage("是否确认删除?"); //设置内容

        builder.setIcon(R.mipmap.ic_launcher);//设置图标，图片id即可

        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() { //设置确定按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Log.e("ContactsAdapter",pos+"");

                dialog.dismiss(); //关闭dialog

                Constant.datas.remove(pos);

                SettingActivity.contactsAdapter.notifyDataSetChanged();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() { //设置取消按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        //参数都设置完成了，创建并显示出来
        builder.create().show();

    }


}
