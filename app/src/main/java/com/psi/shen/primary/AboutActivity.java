package com.psi.shen.primary;

import android.app.Dialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.allenliu.versionchecklib.v2.AllenVersionChecker;
import com.allenliu.versionchecklib.v2.builder.DownloadBuilder;
import com.allenliu.versionchecklib.v2.builder.UIData;
import com.allenliu.versionchecklib.v2.callback.CustomDownloadFailedListener;
import com.allenliu.versionchecklib.v2.callback.CustomDownloadingDialogListener;
import com.allenliu.versionchecklib.v2.callback.CustomVersionDialogListener;
import com.allenliu.versionchecklib.v2.callback.RequestVersionListener;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AboutActivity extends AppCompatActivity {
    private TopBar topBar;
    private RelativeLayout developersRL;
    private RelativeLayout introRL;
    private RelativeLayout feedbackRL;
    private RelativeLayout updateRL;

    private DownloadBuilder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        developersRL = findViewById(R.id.developersRL);
        introRL = findViewById(R.id.introRL);
        feedbackRL = findViewById(R.id.feedbackRL);
        updateRL = findViewById(R.id.updateRL);
        topBar = findViewById(R.id.aboutTopbar);
        topBar.setLeftAndRightListener(new TopBar.LeftAndRightListener() {
            @Override
            public void leftListener() {
                AboutActivity.this.finish();
            }

            @Override
            public void rightListener() {

            }
        });

        developersRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutActivity.this, Developers.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
        introRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutActivity.this, AppIntro.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
        feedbackRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AboutActivity.this, Feedback.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });
        updateRL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                builder = AllenVersionChecker
                        .getInstance()
                        .requestVersion()
                        .setRequestUrl("http://118.25.122.232/android_connect/update/update.txt")
                        .request(new RequestVersionListener() {
                            @Nullable
                            @Override
                            public UIData onRequestVersionSuccess(String result) {
                                String[] strings = result.split("\r\n");
                                Map<String, String> map = new HashMap<>();
                                for(int i = 0; i < strings.length; i++) {
                                    String[] temp = strings[i].split("=", 2);
                                    map.put(temp[0], temp[1]);
                                }
                                if(map.get("update").equals("1")) {
                                    String path = map.get("path");
                                    UIData uiData = UIData.create();
                                    uiData.setTitle(getString(R.string.updateTitle));
                                    uiData.setContent("Version " + map.get("version") + " is released!!!");
                                    uiData.setDownloadUrl("http://118.25.122.232" + path);
                                    uiData.getVersionBundle().putString("version", map.get("version"));
                                    return uiData;
                                } else {
                                    Toast.makeText(AboutActivity.this, "This is the latest version.", Toast.LENGTH_SHORT).show();
                                    return null;
                                }
                            }

                            @Override
                            public void onRequestVersionFailure(String message) {
                                Toast.makeText(AboutActivity.this, "Request failed", Toast.LENGTH_SHORT).show();
                            }
                        });
                builder.setCustomVersionDialogListener(new CustomVersionDialogListener() {
                    @Override
                    public Dialog getCustomVersionDialog(Context context, UIData versionBundle) {
                        Dialog dialog = new Dialog(context, R.style.Theme_AppCompat_Dialog);
                        View view = View.inflate(context, R.layout.update_dialog, null);
                        dialog.setContentView(view);
                        dialog.setCanceledOnTouchOutside(true);
                        view.setMinimumHeight((int)(ScreenSizeUtils.getInstance(context).getScreenHeight() * 0.23f));
                        Window dialogWindow =dialog.getWindow();
                        dialogWindow.setBackgroundDrawableResource(android.R.color.transparent);
                        final WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                        lp.width = (int)(ScreenSizeUtils.getInstance(context).getScreenWidth());
                        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                        lp.gravity = Gravity.CENTER;
                        dialogWindow.setAttributes(lp);
                        TextView title = dialog.findViewById(R.id.updateTitleTV);
                        TextView content = dialog.findViewById(R.id.updateContentTV);
                        title.setText(versionBundle.getTitle());
                        content.setText(versionBundle.getContent());
                        return dialog;
                    }
                });
                builder.setCustomDownloadingDialogListener(new CustomDownloadingDialogListener() {
                    @Override
                    public Dialog getCustomDownloadingDialog(Context context, int progress, UIData versionBundle) {
                        Dialog dialog = new Dialog(context, R.style.Theme_AppCompat_Dialog);
                        View view = View.inflate(context, R.layout.download_dialog, null);
                        dialog.setContentView(view);
                        dialog.setCanceledOnTouchOutside(false);
                        view.setMinimumHeight((int)(ScreenSizeUtils.getInstance(context).getScreenHeight() * 0.23f));
                        Window dialogWindow =dialog.getWindow();
                        dialogWindow.setBackgroundDrawableResource(android.R.color.transparent);
                        final WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                        lp.width = (int)(ScreenSizeUtils.getInstance(context).getScreenWidth());
                        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                        lp.gravity = Gravity.CENTER;
                        dialogWindow.setAttributes(lp);
                        return dialog;
                    }

                    @Override
                    public void updateUI(Dialog dialog, int progress, UIData versionBundle) {
                        TextView progressTV = dialog.findViewById(R.id.progressTV);
                        ProgressBar progressBar = dialog.findViewById(R.id.progressBar);
                        progressBar.setProgress(progress);
                        progressTV.setText(getString(R.string.versionchecklib_progress, progress));
                    }
                });
                builder.setCustomDownloadFailedListener(new CustomDownloadFailedListener() {
                    @Override
                    public Dialog getCustomDownloadFailed(Context context, UIData versionBundle) {
                        Dialog dialog = new Dialog(context, R.style.Theme_AppCompat_Dialog);
                        View view = View.inflate(context, R.layout.download_fail_dialog, null);
                        dialog.setContentView(view);
                        dialog.setCanceledOnTouchOutside(false);
                        view.setMinimumHeight((int)(ScreenSizeUtils.getInstance(context).getScreenHeight() * 0.23f));
                        Window dialogWindow =dialog.getWindow();
                        dialogWindow.setBackgroundDrawableResource(android.R.color.transparent);
                        final WindowManager.LayoutParams lp = dialogWindow.getAttributes();
                        lp.width = (int)(ScreenSizeUtils.getInstance(context).getScreenWidth());
                        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                        lp.gravity = Gravity.CENTER;
                        dialogWindow.setAttributes(lp);
                        return dialog;
                    }
                });
                builder.setForceRedownload(true);
                builder.setDownloadAPKPath("/storage/emulated/0/AlloyProject/");
                builder.executeMission(AboutActivity.this);
            }
        });
    }
}
