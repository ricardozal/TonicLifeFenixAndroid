package com.bigtechsolutions.toniclifefenix.viewmodel.interfaces;

import com.bigtechsolutions.toniclifefenix.api.responses.share_points.SharePointsResponse;

public interface OnSharePointsResponse {
    void OnSuccess(SharePointsResponse sharePointsResponse);
    void OnError(String title, String message);
}
