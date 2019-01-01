package com.tommyputranto.myapplication.api;

import com.tommyputranto.myapplication.api.core.CoreApi;

/**
 * Created by Tommy Dwi Putranto on 11/04/18.
 */

public class BaseRequest {
    protected CoreApi coreApi;

    public BaseRequest(CoreApi coreApi) {
        this.coreApi = coreApi;
    }
}

