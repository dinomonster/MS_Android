package com.hr.ms.ms_android.mvp.resources.officialeventmanager.create.flowinfo.add;

import com.better.appbase.mvp.BasePresenter;
import com.google.gson.Gson;
import com.hr.ms.ms_android.bean.BaseResponseReturnValue;
import com.hr.ms.ms_android.data.repository.ServiceRepository;
import com.hr.ms.ms_android.mvp.resources.officialeventmanager.create.flowinfo.OrderManager;
import com.hr.ms.ms_android.network.ApiQueryMapUtils;
import com.hr.ms.ms_android.network.ResponseCode;
import com.hr.ms.ms_android.utils.CodeStringUtils;
import com.hr.ms.ms_android.utils.PresenterUtils;
import com.socks.library.KLog;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Dino on 2018/4/3.
 */

public class AddFlowPresenter extends BasePresenter implements AddFlowContract.Presenter {
    private final AddFlowContract.View mView;
    ServiceRepository repository;

    public AddFlowPresenter(AddFlowContract.View view,
                            ServiceRepository repository) {
        mView = view;
        this.repository = repository;
    }

    @Override
    public void addEventFlowPoints(String officialEventInfoId, Integer flowPointType, String flowOrderNum, Integer extra1, String extra2, Long flowBeginDate, Long flowEndDate) {
        String msg;
        if (flowPointType != null && flowPointType == CodeStringUtils.INSTANCE.getNodeTypeCode()[1]) {
            msg = PresenterUtils.INSTANCE.checkParamasNotNull(
                    true,
                    new String[]{"流程顺序", "节点类型", "分享嘉宾", "分享主题", "开始时间", "结束时间"},
                    flowOrderNum, flowPointType, extra1, extra2, flowBeginDate, flowEndDate);
        } else {
            msg = PresenterUtils.INSTANCE.checkParamasNotNull(
                    true,
                    new String[]{"流程顺序", "节点类型", "开始时间", "结束时间"},
                    flowOrderNum, flowPointType, flowBeginDate, flowEndDate);
        }
        if (msg != null) {
            mView.showToast(msg);
            return;
        }
        if (OrderManager.INSTANCE.isAlreadyExist(flowOrderNum)) {
            mView.showToast("该流程顺序已经存在");
            return;
        }
        Map<String, Object> map = new HashMap();
        map.put("officialEventInfoId", officialEventInfoId);
        map.put("flowPointType", flowPointType);
        map.put("flowOrderNum", flowOrderNum);
        ApiQueryMapUtils.addQueryMap("extra1", extra1, map);
        ApiQueryMapUtils.addQueryMap("extra2", extra2, map);
        map.put("flowBeginDate", flowBeginDate);
        map.put("flowEndDate", flowEndDate);
        mView.showProgressDialog("保存活动流程节点...");
        Disposable disposable = repository.addEventFlowPoints(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BaseResponseReturnValue>() {
                    @Override
                    public void onNext(BaseResponseReturnValue value) {
                        KLog.e(new Gson().toJson(value));
                        mView.dismissProgressDialog();
                        switch (value.getCode()) {
                            case ResponseCode.SUCCESS_CODE:
                                mView.showToast("活动流程节点保存成功");
                                mView.success("" + value.getData());
                                break;

                            default:
                                mView.showToast("活动流程节点保存失败");
                                break;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.dismissProgressDialog();
                        mView.showToast("活动流程节点保存失败");
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        disposables.add(disposable);
    }

    @Override
    public void editEventFlowPoints(String flowInfoId, Integer flowPointType, String flowOrderNum, Integer extra1, String extra2, Long flowBeginDate, Long flowEndDate) {
        String msg;
        if (flowPointType != null && flowPointType == CodeStringUtils.INSTANCE.getNodeTypeCode()[1]) {
            msg = PresenterUtils.INSTANCE.checkParamasNotNull(
                    true,
                    new String[]{"流程顺序", "节点类型", "分享嘉宾", "分享主题", "开始时间", "结束时间"},
                    flowOrderNum, flowPointType, extra1, extra2, flowBeginDate, flowEndDate);
        } else {
            msg = PresenterUtils.INSTANCE.checkParamasNotNull(
                    true,
                    new String[]{"流程顺序", "节点类型", "开始时间", "结束时间"},
                    flowOrderNum, flowPointType, flowBeginDate, flowEndDate);
        }
        if (msg != null) {
            mView.showToast(msg);
            return;
        }
//        if (OrderManager.INSTANCE.isAlreadyExist(flowOrderNum)) {
//            mView.showToast("该流程顺序已经存在");
//            return;
//        }
        Map<String, Object> map = new HashMap();
        map.put("flowInfoId", flowInfoId);
        map.put("flowOrderNum", flowOrderNum);
        ApiQueryMapUtils.addQueryMap("extra1", extra1, map);
        ApiQueryMapUtils.addQueryMap("extra2", extra2, map);
        map.put("flowBeginDate", flowBeginDate);
        map.put("flowEndDate", flowEndDate);
        mView.showProgressDialog("保存活动流程节点...");
        Disposable disposable = repository.editEventFlowPoints(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BaseResponseReturnValue>() {
                    @Override
                    public void onNext(BaseResponseReturnValue value) {
                        KLog.e(new Gson().toJson(value));
                        mView.dismissProgressDialog();
                        switch (value.getCode()) {
                            case ResponseCode.SUCCESS_CODE:
                                mView.showToast("活动流程节点保存成功");
                                mView.success("" + value.getData());
                                break;

                            default:
                                mView.showToast("活动流程节点保存失败");
                                break;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.dismissProgressDialog();
                        mView.showToast("活动流程节点保存失败");
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        disposables.add(disposable);
    }
}
