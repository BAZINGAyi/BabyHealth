package com.example.bazinga.babyhealth.ui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.example.bazinga.babyhealth.R;
import com.example.bazinga.babyhealth.adapter.ContactsAdapter;
import com.example.bazinga.babyhealth.bean.Constant;
import com.example.bazinga.babyhealth.bean.Contacts;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends AppCompatActivity {


    @BindView(R.id.contacts)
    RecyclerView contacts;


    @BindView(R.id.fab)
    FloatingActionButton fab;

    public static ContactsAdapter contactsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setFullScreen();

        setContentView(R.layout.activity_setting);

        ButterKnife.bind(this);

        getData();

        initRecyclerView();

    }

    private void initRecyclerView() {

        LinearLayoutManager ll = new LinearLayoutManager(this);

        ll.setOrientation(LinearLayoutManager.VERTICAL);

        contacts.setLayoutManager(ll);

        contactsAdapter = new ContactsAdapter(Constant.datas,SettingActivity.this);

        contacts.setAdapter(contactsAdapter);
    }

    private void getData() {

        for (int i = 0; i < 6; i++) {

            Contacts con = new Contacts();

            con.setName("张" + i + "哦");

            con.setImage(" ");

            con.setPhone("13603215675" + i);

            Constant.datas.add(con);

        }
    }

    @OnClick(R.id.fab)
    void onClickWithwidget(View view) {

        showDialogContacts();
    }

    private void showDialogContacts() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();

        final View layout = inflater.inflate(R.layout.dialog_contacts, null);//获取自定义布局

        builder.setView(layout);

        builder.setIcon(R.mipmap.health);//设置标题图标

        builder.setTitle("请输入联系人方式");//设置标题内容


        final EditText dialogName = (EditText) layout.findViewById(R.id.dialog_name);


        final EditText dialogCall  = (EditText) layout.findViewById(R.id.dialog_call);

        //确认按钮
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {

                Contacts contact = new Contacts();

                contact.setName(dialogName.getText().toString());

                contact.setPhone(dialogCall.getText().toString());

                Constant.datas.add(contact);

                contacts.scrollToPosition(Constant.datas.size()-1);

                contactsAdapter.notifyItemInserted(Constant.datas.size()-1);
            }
        });

        builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                // TODO Auto-generated method stub

            }
        });
        final AlertDialog dlg = builder.create();
        dlg.show();

    }

    private void setFullScreen() {

        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }


}
