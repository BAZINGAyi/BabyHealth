package com.example.bazinga.babyhealth.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.bazinga.babyhealth.R;

/**
 * Created by bazinga on 2017/3/10.
 */
public class DetailRecommend extends AppCompatActivity {
    TextView textView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recommend_detail);
        textView = (TextView)findViewById(R.id.recommendText);


        String content = "\n\n\t\t\t\t\t\t\t\t孩子的指甲要好好护理\n" +
                "2016-01-14 09:31:53来源:宝宝地带\n" +
                "指甲非常容易伤害人，特别是刚出生的孩子新陈代谢很快，指甲也会长得很快，但是孩子的皮肤却是很稚嫩的。所以，要多多护理孩子的指甲，这样可以防止抓伤自己或者他人。\n" +
                "1、指甲很容易藏东西\n" +
                "孩子的指甲缝很容易藏进去东西，比如说灰尘、污垢、病毒和细菌等等，加上孩子也很喜欢把手放到嘴里吸吮，所以细菌这类的脏东西也就会更容易带到嘴里，从而侵入孩子的身体，导致孩子生病。经常护理孩子的指甲，可以有效避免孩子指甲缝里有残留的细菌，避免细菌危害到孩子的健康。\n" +
                "2、指甲容易断\n" +
                "其实孩子的指甲是很软的，也就是说是非常容易撕裂的。一旦碰到硬物的情况下，就会发生开裂，很容易让孩子的指甲发炎。因此为了孩子的指甲安全，家长要经常帮孩子护理指甲。\n" +
                "那么，如何帮孩子护理指甲呢？\n" +
                "1、选择专用的指甲剪\n" +
                "给孩子护理指甲，一定要选择孩子专用的指甲剪。这是因为给孩子剪指甲很不容易控制力度，而且大人的指甲剪是比较大的。因此帮孩子选择一只专用的小巧指甲剪是很必要的。\n" +
                "2、选择光线充足的地方\n" +
                "给孩子护理指甲一定要选择一个光线充足的地方，这样可以避免光线太暗剪到孩子指甲周围的皮肤。";

        content = content.replace("\n","\n\t\t");

        textView.setText(content);

        textView.setTextSize(20);
    }
}
