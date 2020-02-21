package com.xiaozhu.testproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.netease.nis.captcha.Captcha;
import com.netease.nis.captcha.CaptchaListener;

/**
 * Description
 *
 * @author zhangyunxiang
 * Date 2018/12/4 14:10
 */
public class CloudProtectActivity extends AppCompatActivity {
    private final static String TAG = "Captcha";
    private static final String CAPTCHA_ID = "8c35527a6e7f4dcbba3768634a3bc61f";
    Captcha mCaptcha = null;
    private EditText edit_password, edit_name;
    private Button button_login;
    CaptchaListener captchaListener = new CaptchaListener() {
        @Override
        public void onReady(boolean b) {
            //该为调试接口，ret为true表示加载Sdk完成
            if (b) {
                toastMsg("验证码sdk加载成功");
            }
        }

        @Override
        public void closeWindow() {
            toastMsg("关闭页面");
        }

        @Override
        public void onError(String errormsg) {
            toastMsg("错误信息：" + errormsg);
        }

        @Override
        public void onValidate(String result, String validate, String message) {
            //验证结果，valiadte，可以根据返回的三个值进行用户自定义二次验证
            if (validate.length() > 0) {
                toastMsg("验证成功，validate = " + validate);
            } else {
                toastMsg("验证失败：result = " + result + ", validate = " + validate + ", message = " + message);

            }
        }

        @Override
        public void onCancel() {
            toastMsg("取消线程");
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cloud_protect);
        initView();
        if(mCaptcha == null){
            mCaptcha = new Captcha(this);
        }
        mCaptcha.setCaptchaId(CAPTCHA_ID);
        mCaptcha.setCaListener(captchaListener);
        //可选:设置验证码语言为英文，默认为中文
        //mCaptcha.setEnglishLanguage();
        //可选：开启debug
        mCaptcha.setDebug(false);
        //可选：设置超时时间
        mCaptcha.setTimeout(10000);
        //设置验证码弹框的坐标位置: 只能设置left，top和宽度，高度为自动计算。默认无须设置为窗口居中。
        //mCaptcha.setPosition(1, 200, 1040, -1);
        //设置弹框时背景页面是否模糊，默认无须设置，默认显示弹框时背景页面模糊，Android默认风格。
        //mCaptcha.setBackgroundDimEnabled(false);
        //设置弹框时点击对话框之外区域是否自动消失，默认为消失。如果设置不自动消失请设置为false。
        //mCaptcha.setCanceledOnTouchOutside(false);
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCaptcha.start();
                mCaptcha.Validate();
            }
        });
    }

    private void initView(){
        edit_name = findViewById(R.id.editText4);
        edit_password = findViewById(R.id.editText5);
        button_login = findViewById(R.id.button);
        edit_name.setText("dev@xiaozhu.com");
        edit_password.setText("**&&HNN*(");
    }


    private void toastMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
