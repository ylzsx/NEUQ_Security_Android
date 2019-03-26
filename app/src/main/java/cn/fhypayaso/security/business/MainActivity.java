package cn.fhypayaso.security.business;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.fhypayaso.security.R;
import cn.fhypayaso.security.base.activity.BaseNoBarActivity;
import cn.fhypayaso.security.business.model.request.CarRequestModel;
import cn.fhypayaso.security.business.model.response.CarResponseModel;
import cn.fhypayaso.security.common.CacheKey;
import cn.fhypayaso.security.common.Config;
import cn.fhypayaso.security.network.NetworkFactory;
import cn.fhypayaso.security.network.response.ApiResponse;
import cn.fhypayaso.security.utils.CacheUtil;
import cn.fhypayaso.security.utils.CheckPermissionUtil;
import cn.fhypayaso.security.utils.DialogUtil;
import cn.fhypayaso.security.utils.TimeUtil;
import cn.fhypayaso.security.utils.ToastUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author FanHongyu.
 * @since 18/5/31 20:01.
 * email fanhongyu@hrsoft.net.
 */
public class MainActivity extends BaseNoBarActivity {


    @BindView(R.id.btn_scan_two_bars)
    ImageButton mBtnScanTwoBars;
    @BindView(R.id.ibtn_clearedt)
    ImageButton mIbtnClearedt;
    @BindView(R.id.tv_search)
    TextView mTvSearch;
    @BindView(R.id.edt_search)
    EditText mEdtSearch;
    @BindView(R.id.tv_logout)
    TextView mTvLogout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ZXingLibrary.initDisplayOpinion(this);

        /**
         * 设置清空按钮
         */
        mEdtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() > 0){
                    mIbtnClearedt.setVisibility(View.VISIBLE);
                }else {
                    mIbtnClearedt.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        initPermission();
    }

    @Override
    protected void initView() {
        /**
         * token过期返回登录界面
         */
        if(!TimeUtil.isTokenValid()) {
            CacheUtil.put(CacheKey.TOKEN, "");
            ToastUtil.showToast("登录状态已过期，请重新登录");
            startActivity(LoginActivity.class);
            finish();
        }
    }

    @OnClick({R.id.btn_scan_two_bars,R.id.ibtn_clearedt,R.id.tv_search,R.id.tv_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_scan_two_bars:
                initPermission();
                if (CheckPermissionUtil.isPermissionAllow()) {
                    scanQRcode();
                }
                break;
            case R.id.ibtn_clearedt:
                mEdtSearch.setText("");
                break;
            case R.id.tv_search:
                String carNumber = mEdtSearch.getText().toString();
                showProgressDialog();
                searchByCarNumber(carNumber);
                break;
            case R.id.tv_logout:
                new DialogUtil.QuickDialog(this).setClickListener(new DialogUtil.QuickDialog.DialogPositiveButtonListener() {
                    @Override
                    public void onPositiveButtonClick() {
                        startActivity(LoginActivity.class);
                        CacheUtil.put(CacheKey.TOKEN, "");
                        finish();
                    }
                }).showDialog("确认退出登录");
                break;
        }
    }

    /**
     * 根据车牌号搜索的请求
     * @param carNumber
     */
    private void searchByCarNumber(String carNumber) {
        NetworkFactory.getService().searchByCarNumber(new CarRequestModel(carNumber)).enqueue(new Callback<ApiResponse<List<CarResponseModel>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<CarResponseModel>>> call, Response<ApiResponse<List<CarResponseModel>>> response) {
                CarResponseModel car = null;
                if (response.body().getData().size() > 0) {
                    car = response.body().getData().get(0);
                }
                onSearchSuccess(car);
            }

            @Override
            public void onFailure(Call<ApiResponse<List<CarResponseModel>>> call, Throwable t) {
                onSearchFail();
            }
        });
    }

    /**
     * 车信息查找成功
     * @param car 返回的车信息
     */
    private void onSearchSuccess(CarResponseModel car) {
        dismissProgressDialog();
        if (car != null) {
            Intent intent = new Intent(MainActivity.this,SearchResultActivity.class);
            intent.putExtra("car_id",car.getId())
                    .putExtra("car_name",car.getName())
                    .putExtra("car_department",car.getDepartment())
                    .putExtra("car_number",car.getCar_number())
                    .putExtra("car_phone",car.getPhone());
            startActivity(intent);
        }

    }

    /**
     * 车信息查找失败
     */
    private void onSearchFail() {
        dismissProgressDialog();
        ToastUtil.showToast("查找失败");
    }

    /**
     * 初始化权限事件
     */
    private void initPermission() {
        //检查权限
        String[] permissions = CheckPermissionUtil.checkPermission(this);
        if (permissions.length == 0) {
            CheckPermissionUtil.setPermissionAllow(true);
        } else {
            //申请权限
            ActivityCompat.requestPermissions(this, permissions, 100);
        }
    }

    /**
     * 扫描二维码
     */
    private void scanQRcode() {
        Intent intent = new Intent(MainActivity.this, CustomScannActivity.class);
        startActivityForResult(intent, Config.REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /**
         * 处理二维码扫描结果
         */
        if (requestCode == Config.REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    ToastUtil.showToast("解析二维码成功");
                    searchByCarNumber(result);
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    ToastUtil.showToast("解析二维码失败");
                }
            }
        }
    }
}
