package com.bigtechsolutions.toniclifefenix.viewmodel.interfaces;

import com.bigtechsolutions.toniclifefenix.api.responses.models.ContentMobileResponse;

import java.util.List;

public interface OnContentResponse {
    void OnSuccess(List<ContentMobileResponse> contentMobileResponse);
    void OnError(String title, String message);
}
