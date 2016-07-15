package com.vgaw.sexygirl.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.vgaw.sexygirl.R;
import com.vgaw.sexygirl.Utils.Utils;

/**
 * from : Volodymyr
 * to : caojinmail@163.com
 * me : github.com/VolodymyrCj/
 */
public class IntroActivity extends Activity {
    private View v_back;
    private TextView tv_title;
    private ImageView iv_shoe;

    private int count = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        v_back = findViewById(R.id.v_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        iv_shoe = (ImageView) findViewById(R.id.iv_shoe);
        tv_title = (TextView) findViewById(R.id.tv_title);

        tv_title.setText("功能简介");
        v_back.setOnClickListener(clickListener);
        iv_shoe.setOnClickListener(clickListener);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.v_back:
                    finish();
                    break;
                case R.id.iv_shoe:
                    shoeTalk();
                    break;
            }
        }
    };

    private void shoeTalk(){
        switch (count){
            case 1:
                Utils.showToast(this, "喂，你踩到我啦！");
                break;
            case 2:
                Utils.showToast(this, "真不要脸！");
                break;
            case 3:
                Utils.showToast(this, "好吧，你赢了！");
                break;
            default:
                Utils.showToast(this, "感谢您使用本软件，祝您天天开心！");
                break;
        }
        count++;
    }
}
