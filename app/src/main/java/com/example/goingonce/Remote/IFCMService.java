package com.example.goingonce.Remote;

import com.example.goingonce.models.FCMResponse;
import com.example.goingonce.models.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface IFCMService {
    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAAj8bcEzg:APA91bGaaIXtOD93KWFDAbm4ozO5tI3gwhoEHksWCXa1ST4-n2QESkyZkscW1hnLTfxp3y7KhYr5muEGgbV3pQrA_Vp5hGdwPcqhr7v0--nYt92Nvj02cYoi3MXpOwmv7beF5dkYbGMw"
    })
    @POST("fcm/send")
    Call<FCMResponse> sendMessage(@Body Sender body);
}
