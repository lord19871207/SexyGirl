package com.vgaw.sexygirl.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.vgaw.sexygirl.BugHD;
import com.vgaw.sexygirl.R;
import com.vgaw.sexygirl.Utils.Utils;

/**
 * Created by caojin on 2016/7/3.
 */
public class FeedbackActivity extends BaseActivity {
    private EditText et_feedback;
    private Button btn_positive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        et_feedback = (EditText) findViewById(R.id.et_feedback);
        btn_positive = (Button) findViewById(R.id.btn_positive);
        ((TextView)findViewById(R.id.tv_title)).setText("意见反馈");
        findViewById(R.id.v_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String info = et_feedback.getText().toString();
                if ("".equals(info)){
                    Utils.showToast(FeedbackActivity.this, "意见不能为空");
                    return;
                }
                BugHD.sendUDInfo(info);
                Utils.showToast(FeedbackActivity.this, "意见提交成功，感谢您的反馈");
            }
        });
    }
}
