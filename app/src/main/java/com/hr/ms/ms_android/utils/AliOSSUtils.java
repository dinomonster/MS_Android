package com.hr.ms.ms_android.utils;

import android.content.Context;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSStsTokenCredentialProvider;
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.hr.ms.ms_android.bean.AliOSSBean;
import com.hr.ms.ms_android.data.AccountHelper;
import com.socks.library.KLog;

import java.net.URI;
import java.net.URISyntaxException;


public class AliOSSUtils implements OSSCompletedCallback<PutObjectRequest, PutObjectResult>, OSSProgressCallback<PutObjectRequest> {

    private static final String END_POINT = "http://oss-cn-shenzhen.aliyuncs.com";
    private String mBucketName = "sxzx-image";
    private OSSClient mOSSClient = null;


    public AliOSSUtils(Context context) {
        AliOSSBean bean = AccountHelper.getAliOSS();
        if (bean == null){
            return;
        }
        OSSCredentialProvider credentialProvider = new OSSStsTokenCredentialProvider(bean.getAccessKeyId(), bean.getAccessKeySecret(), bean.getSecurityToken());
        ClientConfiguration conf = new ClientConfiguration();
        //连接超时，默认15秒
        conf.setConnectionTimeout(60 * 1000);
        //socket超时，默认15秒
        conf.setSocketTimeout(5 * 60 * 1000);
        //最大并发请求书，默认5个
        conf.setMaxConcurrentRequest(5);
        //失败后最大重试次数，默认2次
        conf.setMaxErrorRetry(2);
        mOSSClient = new OSSClient(context, END_POINT, credentialProvider, conf);
    }

    public void upLoadImageViewCallBack(String objectKey, String uploadFilePath,OSSCompletedCallback<PutObjectRequest, PutObjectResult> completedCallback) {
        String filePath = null;
        try {
            filePath = new URI(uploadFilePath).getPath();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        PutObjectRequest put = new PutObjectRequest(mBucketName, objectKey, filePath);
        // 异步上传时可以设置进度回调
        put.setProgressCallback(this);
        OSSAsyncTask task = mOSSClient.asyncPutObject(put, completedCallback);
    }

    @Override
    public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {
        KLog.d("PutObject currentSize: " + currentSize + " totalSize: " + totalSize);
    }

    @Override
    public void onSuccess(PutObjectRequest request, PutObjectResult result) {
        KLog.d("PutObject" + "UploadSuccess");
        KLog.d("ETag = " + result.getETag());
        KLog.d("RequestId = " + result.getRequestId());
    }

    @Override
    public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {

        //请求异常
        if (clientExcepion != null) {
            //本地异常如网络异常等
            KLog.e(clientExcepion.getMessage(), clientExcepion);
            clientExcepion.printStackTrace();
            return;
        }
        if (serviceException != null) {
            //服务异常
            Log.e("lyz","hh=="+serviceException.getMessage());
            KLog.d("ErrorCode", serviceException.getErrorCode());
            KLog.d("RequestId", serviceException.getRequestId());
            KLog.d("HostId", serviceException.getHostId());
            KLog.d("RawMessage", serviceException.getRawMessage());
            return;
        }
    }

}
