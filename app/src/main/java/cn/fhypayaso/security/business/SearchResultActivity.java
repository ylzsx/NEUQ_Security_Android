package cn.fhypayaso.security.business;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.fhypayaso.security.R;
import cn.fhypayaso.security.base.activity.BaseNoBarActivity;

public class SearchResultActivity extends BaseNoBarActivity {


    @BindView(R.id.ibtn_back)
    ImageButton mIbtnBack;
    @BindView(R.id.tv_number_of_car)
    TextView mTvNumberOfCar;
    @BindView(R.id.tv_name_of_owner)
    TextView mTvNameOfOwner;
    @BindView(R.id.tv_phone_of_owner)
    TextView mTvPhoneOfOwner;
    @BindView(R.id.tv_sector)
    TextView mTvSector;
    @BindView(R.id.ibtn_ring)
    ImageButton mIbtnRing;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_search_result;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        Intent intent = getIntent();
        mTvNumberOfCar.setText(intent.getStringExtra("car_number"));
        mTvNameOfOwner.setText(intent.getStringExtra("car_name"));
        mTvPhoneOfOwner.setText(intent.getStringExtra("car_phone"));
        mTvSector.setText(intent.getStringExtra("car_department"));
    }

    @OnClick({R.id.ibtn_back,R.id.ibtn_ring})
    public void onViewClicked(View view){
        switch (view.getId()) {
            case R.id.ibtn_back:
                finish();
                break;
            case R.id.ibtn_ring:
                String phone = mTvPhoneOfOwner.getText().toString();
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+phone));
                startActivity(intent);
                break;
        }
    }
}
