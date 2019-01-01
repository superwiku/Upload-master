package com.tommyputranto.myapplication.main;

import rx.Observable;

/**
 * Created by Tommy Dwi Putranto on 11/04/18.
 */

public interface MainRequest {
    Observable<SuccessResponse> requestUpload(String id, String name, String alamat, String email, String jenkel, String image, String sheet);


    public class SuccessResponse {

        private String status;

        public SuccessResponse(String username) {
            this.status = username;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String username) {
            this.status = username;
        }
    }
}
