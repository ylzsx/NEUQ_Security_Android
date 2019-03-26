package cn.fhypayaso.security.network;


import java.util.List;

import cn.fhypayaso.security.business.model.request.CarRequestModel;
import cn.fhypayaso.security.business.model.request.LoginRequestModel;
import cn.fhypayaso.security.business.model.response.CarResponseModel;
import cn.fhypayaso.security.business.model.response.LoginResponseModel;
import cn.fhypayaso.security.network.response.ApiResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * @author fhyPayaso
 * @since 2018/4/24 on 上午12:18
 * fhyPayaso@qq.com
 */
public interface ApiService {

    @POST("user/loginapp")
    Call<ApiResponse<LoginResponseModel>> login(@Body LoginRequestModel loginRM);

    @POST("pass/search")
    Call<ApiResponse<List<CarResponseModel>>> searchByCarNumber(@Body CarRequestModel carRM);
}
