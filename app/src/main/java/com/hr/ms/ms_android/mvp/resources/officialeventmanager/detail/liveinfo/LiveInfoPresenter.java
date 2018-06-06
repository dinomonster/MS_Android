package com.hr.ms.ms_android.mvp.resources.officialeventmanager.detail.liveinfo;

import com.better.appbase.mvp.BasePresenter;
import com.google.gson.Gson;
import com.hr.ms.ms_android.bean.BaseResponseReturnValue;
import com.hr.ms.ms_android.bean.OfficialEventDetailBean;
import com.hr.ms.ms_android.data.repository.ServiceRepository;
import com.hr.ms.ms_android.network.ApiQueryMapUtils;
import com.hr.ms.ms_android.network.ResponseCode;
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

public class LiveInfoPresenter extends BasePresenter implements LiveInfoContract.Presenter {
    private final LiveInfoContract.View mView;
    ServiceRepository repository;

    public LiveInfoPresenter(LiveInfoContract.View view,
                             ServiceRepository repository) {
        mView = view;
        this.repository = repository;
    }

    @Override
    public void editEventLiveInfo(String liveInfoId, String chatroomMsg, String liveBookNum, String liveWatchNum) {
        String msg = PresenterUtils.INSTANCE.checkParamasNotNull(
                true,
                new String[]{"预约人数基数", "观看人数基数"},
                liveBookNum, liveWatchNum);
        if (msg != null) {
            mView.showToast(msg);
            return;
        }
        Map<String, Object> map = new HashMap();
        map.put("liveInfoId", liveInfoId);
        map.put("liveBookNum", liveBookNum);
        map.put("liveWatchNum", liveWatchNum);
        ApiQueryMapUtils.addQueryMap("chatroomMsg", chatroomMsg, map);
        mView.showProgressDialog("保存直播信息...");
        Disposable disposable = repository.editEventLiveInfo(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BaseResponseReturnValue>() {
                    @Override
                    public void onNext(BaseResponseReturnValue value) {
                        KLog.e(new Gson().toJson(value));
                        mView.dismissProgressDialog();
                        switch (value.getCode()) {
                            case ResponseCode.SUCCESS_CODE:
                                mView.showToast("直播信息保存成功");
                                mView.saveSuccess();
                                break;

                            default:
                                mView.showToast("直播信息保存失败");
                                break;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.dismissProgressDialog();
                        mView.showToast("直播信息保存失败");
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        disposables.add(disposable);
    }

    @Override
    public void addEventLiveInfo(String officialEventInfoId, String chatroomMsg, String liveBookNum, String liveWatchNum) {
        String msg = PresenterUtils.INSTANCE.checkParamasNotNull(
                true,
                new String[]{"预约人数基数", "观看人数基数"},
                liveBookNum, liveWatchNum);
        if (msg != null) {
            mView.showToast(msg);
            return;
        }
        Map<String, Object> map = new HashMap();
        map.put("officialEventInfoId", officialEventInfoId);
        map.put("liveBookNum", liveBookNum);
        map.put("liveWatchNum", liveWatchNum);
        ApiQueryMapUtils.addQueryMap("chatroomMsg", chatroomMsg, map);
        mView.showProgressDialog("保存直播信息...");
        Disposable disposable = repository.addEventLiveInfo(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BaseResponseReturnValue>() {
                    @Override
                    public void onNext(BaseResponseReturnValue value) {
                        KLog.e(new Gson().toJson(value));
                        mView.dismissProgressDialog();
                        switch (value.getCode()) {
                            case ResponseCode.SUCCESS_CODE:
                                mView.showToast("直播信息保存成功");
                                mView.saveSuccess();
                                break;

                            default:
                                mView.showToast("直播信息保存失败");
                                break;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.dismissProgressDialog();
                        mView.showToast("直播信息保存失败");
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        disposables.add(disposable);
    }

    @Override
    public void getOfficialEventDetail(String officialEventInfoId) {
        Map<String, Object> map = new HashMap();
        map.put("officialEventInfoId", officialEventInfoId);
        mView.showProgressDialog("获取详情...");
        Disposable disposable = repository.getOfficialEventDetail(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BaseResponseReturnValue<OfficialEventDetailBean>>() {
                    @Override
                    public void onNext(BaseResponseReturnValue<OfficialEventDetailBean> value) {
                        KLog.e(new Gson().toJson(value));
                        mView.dismissProgressDialog();
                        switch (value.getCode()) {
                            case ResponseCode.SUCCESS_CODE:
                                mView.showDetail(value.getData());
                                break;

                            default:
                                mView.showToast("获取详情失败");
                                break;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.dismissProgressDialog();
                        mView.showToast("获取详情失败");
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        disposables.add(disposable);
    }

    @Override
    public void deleteImManagerInfo(String imManagerInfoId) {
        Map<String, Object> map = new HashMap();
        map.put("imManagerInfoId", imManagerInfoId);
        mView.showProgressDialog("删除辅导员...");
        Disposable disposable = repository.deleteImManagerInfo(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BaseResponseReturnValue>() {
                    @Override
                    public void onNext(BaseResponseReturnValue value) {
                        KLog.e(new Gson().toJson(value));
                        mView.dismissProgressDialog();
                        switch (value.getCode()) {
                            case ResponseCode.SUCCESS_CODE:
                                mView.deleteSuccess();
                                break;

                            default:
                                mView.showToast("删除辅导员失败");
                                break;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.dismissProgressDialog();
                        mView.showToast("删除辅导员失败");
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        disposables.add(disposable);
    }
}
