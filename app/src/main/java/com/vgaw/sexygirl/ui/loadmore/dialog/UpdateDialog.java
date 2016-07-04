package com.vgaw.sexygirl.ui.loadmore.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Environment;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.FileAsyncHttpResponseHandler;
import com.vgaw.sexygirl.HttpUtil;
import com.vgaw.sexygirl.R;
import com.vgaw.sexygirl.Utils.Utils;
import com.vgaw.sexygirl.bean.FOTABean;

import java.io.File;

import cz.msebera.android.httpclient.Header;

/**
 * Created by caojin on 2016/7/3.
 */
public class UpdateDialog {
    private final String SAVEPATH = Environment.getExternalStorageDirectory().getPath() + File.separator;//保存的路径

    private Context context;
    private AlertDialog dialog;
    private ProgressBar progressbar;
    private TextView update;

    private FOTABean bean;
    private String path;

    public UpdateDialog(Context context, FOTABean bean){
        this.context = context;
        this.bean = bean;
        path = SAVEPATH + "SexyGirl_" + bean.getVersion() + ".apk";
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_update, null);
        dialog = new AlertDialog.Builder(context)
                .setView(view)
                .create();

        TextView message = (TextView) view.findViewById(R.id.update_message);
        message.setText("更新内容:\n" + bean.getChangelog());
        progressbar = (ProgressBar) view.findViewById(R.id.update_progressbar);
        update = (TextView) view.findViewById(R.id.update_btn);

        ColorStateList redColors = ColorStateList.valueOf(context.getResources().getColor(R.color.gray));
        SpannableStringBuilder spanBuilder = new SpannableStringBuilder("立即更新(" + bean.getBinary().getFsize() + "M)");
        //style 为0 即是正常的，还有Typeface.BOLD(粗体) Typeface.ITALIC(斜体)等
        //size  为0 即采用原始的正常的 size大小
        spanBuilder.setSpan(new TextAppearanceSpan(null, 0, context.getResources().getDimensionPixelSize(R.dimen.txt_14), redColors, null), 4, spanBuilder.length(), Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

        update.setText(spanBuilder);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressbar.setVisibility(View.VISIBLE);
                update.setOnClickListener(null);
                installApk();//在这里进行检查安装,如有安装包的话
                downloadApk();
            }
        });
    }

    //安装apk
    private void installApk() {
        File apkfile = new File(path);
        if (!apkfile.exists()) {
            return;
        }
        // 通过Intent安装APK文件
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
                "application/vnd.android.package-archive");
        context.startActivity(i);
        android.os.Process.killProcess(android.os.Process.myPid());// 如果不加上这句的话在apk安装完成之后点击点开会崩溃
    }

    //下载文件
    private void downloadApk() {
        if (bean.getInstall_url() != null) {
            if (Utils.getSDFreeSize(1024 * 1024) > bean.getBinary().getFsize()) {
                downloadFile(bean.getInstall_url());
            } else {
                Toast.makeText(context, "手机存储空间不足,请清理一些文件后重试", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void downloadFile(String updateUrl){
        HttpUtil.get(updateUrl, null, new FileAsyncHttpResponseHandler(new File(path)) {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                Utils.showToast(context, "下载失败,请检查网络后重试");
                update.setText("啊哦!下载失败了");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, File file) {
                installApk();
            }

            @Override
            public void onProgress(long bytesWritten, long totalSize) {
                super.onProgress(bytesWritten, totalSize);
                update.setText("正在努力下载中...");
                progressbar.setMax((int) totalSize);
                progressbar.setProgress((int) bytesWritten);
            }
        });
    }

    public void show(){
        dialog.show();
    }

}
