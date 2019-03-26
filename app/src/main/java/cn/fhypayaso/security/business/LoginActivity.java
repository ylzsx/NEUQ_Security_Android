package cn.fhypayaso.security.business;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.OnClick;
import cn.fhypayaso.security.R;
import cn.fhypayaso.security.base.activity.BaseNoBarActivity;
import cn.fhypayaso.security.business.model.request.LoginRequestModel;
import cn.fhypayaso.security.business.model.response.LoginResponseModel;
import cn.fhypayaso.security.common.CacheKey;
import cn.fhypayaso.security.network.NetworkFactory;
import cn.fhypayaso.security.network.ResponseCallBack;
import cn.fhypayaso.security.network.response.ApiResponse;
import cn.fhypayaso.security.utils.CacheUtil;
import cn.fhypayaso.security.utils.TimeUtil;
import cn.fhypayaso.security.utils.ToastUtil;
import retrofit2.Call;
import retrofit2.Response;

public class LoginActivity extends BaseNoBarActivity {


    @BindView(R.id.edt_account)
    EditText mEdtAccount;
    @BindView(R.id.edt_pwd)
    EditText mEdtPwd;
    @BindView(R.id.btn_login)
    Button mBtnLogin;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
    }

    @Override
    protected void initView() {
        //判断是否有登录记录
        String token = CacheUtil.getSP().getString(CacheKey.TOKEN, "");
        if (!"".equals(token)) {
            startActivity(MainActivity.class);
            finish();
        }
    }

    @OnClick({R.id.btn_login})
    public void toLogin(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                showProgressDialog();
                String account = mEdtAccount.getText().toString();
                String pwd = mEdtPwd.getText().toString();
                login(account, pwd);
                break;
        }
    }

    /**
     * 登录请求函数
     *
     * @param account 账号
     * @param pwd     密码
     */
    public void login(String account, String pwd) {
        //onLoginSuccess();
        NetworkFactory.getService().login(new LoginRequestModel(account, pwd)).enqueue(new ResponseCallBack<ApiResponse<LoginResponseModel>>() {
            @Override
            public void onDataResponse(Call<ApiResponse<LoginResponseModel>> call, Response<ApiResponse<LoginResponseModel>> response) {
                CacheUtil.put(CacheKey.TOKEN, response.body().getData().getTokenStr());
                CacheUtil.put(CacheKey.LOGIN_TIME,TimeUtil.getCurrentTime());
                onLoginSuccess();
            }

            @Override
            public void onDataFailure(Call<ApiResponse<LoginResponseModel>> call, Throwable t) {
                onLoginFailure();
            }
        });
    }

    public void onLoginSuccess() {
        dismissProgressDialog();
        startActivity(MainActivity.class);
        finish();
        ToastUtil.showToast("登录成功");
    }

    public void onLoginFailure() {
        dismissProgressDialog();
        ToastUtil.showToast("登录失败");
    }


}
