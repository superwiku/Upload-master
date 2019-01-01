package com.tommyputranto.myapplication.main;

import android.util.Log;

import com.tommyputranto.myapplication.CoreApp;
import com.tommyputranto.myapplication.api.BaseRequest;
import com.tommyputranto.myapplication.api.core.CoreApi;
import com.tommyputranto.myapplication.api.dao.SuccessDao;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by Tommy Dwi Putranto on 11/04/18.
 */

public class MainRequestImpl extends BaseRequest implements MainRequest {

    public MainRequestImpl(CoreApi coreApi) {
        super(coreApi);
    }

    @Override
    public Observable<SuccessResponse> requestUpload(String id, String nama, String alamat, String email, String jenkel, String image, String sheet) {
        return CoreApp.getAPiCore().getApiService().postUpload(id, nama, alamat, email, jenkel, image, sheet)
                .map(new Func1<SuccessDao, SuccessResponse>() {
                    @Override
                    public SuccessResponse call(SuccessDao successDao) {
                        return new SuccessResponse(successDao.getStatus());
                    }
                });
    }
}
