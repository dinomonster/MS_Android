package com.hr.ms.ms_android.mvp.operation.stagemanager;

import com.better.appbase.mvp.BasePresenter;
import com.google.gson.Gson;
import com.hr.ms.ms_android.bean.BaseResponseReturnValue;
import com.hr.ms.ms_android.bean.StageListBean;
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

import static com.hr.ms.ms_android.constants.CommonConstants.PAGESIZE;

/**
 * Created by Dino on 2018/4/3.
 */

public class StagePresenter extends BasePresenter implements StageContract.Presenter {
    private final StageContract.View mView;

    ServiceRepository repository;

    public StagePresenter(StageContract.View view,
                          ServiceRepository repository) {
        mView = view;
        this.repository = repository;
    }


    @Override
    public void getStageIndexList(int pageNo,Integer stageType, String stageName, Long createTimeStart, Long createTimeEnd,Integer orderRule) {
        Map<String, Object> map = new HashMap();
        ApiQueryMapUtils.addQueryMap("stageType", stageType, map);
        ApiQueryMapUtils.addQueryMap("stageName", stageName, map);
        ApiQueryMapUtils.addQueryMap("createTimeStart", createTimeStart, map);
        ApiQueryMapUtils.addQueryMap("createTimeEnd", createTimeEnd, map);
        ApiQueryMapUtils.addQueryMap("orderRule", orderRule, map);
        map.put("size", PAGESIZE);
        map.put("page", pageNo);
        Disposable disposable = repository.getStageIndexList(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BaseResponseReturnValue<StageListBean>>() {
                    @Override
                    public void onNext(BaseResponseReturnValue<StageListBean> value) {
                        KLog.e(new Gson().toJson(value));
                        switch (value.getCode()) {
                            case ResponseCode.SUCCESS_CODE:
                                mView.showList(value.getData());
                                break;

                            default:
                                mView.showNetWorkError();
//                                mView.showToast(value.getStatus());
                                break;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        mView.showNetWorkError();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        disposables.add(disposable);
    }

    @Override
    public void createECard(Integer stageId, String familyNum) {
        String msg = PresenterUtils.INSTANCE.checkParamasNotNull(
                true,
                new String[]{"驿站ID", "数量"},
                stageId, familyNum);
        if (msg != null) {
            mView.showToast(msg);
            return;
        }
        Map<String, Object> map = new HashMap();
        map.put("stageId", stageId);
        map.put("familyNum", familyNum);
        mView.showProgressDialog("分配电子卡...");
        Disposable disposable = repository.createECard(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<BaseResponseReturnValue>() {
                    @Override
                    public void onNext(BaseResponseReturnValue value) {
                        KLog.e(new Gson().toJson(value));
                        mView.dismissProgressDialog();
                        switch (value.getCode()) {
                            case ResponseCode.SUCCESS_CODE:
                                mView.showToast("分配电子卡成功");
                                mView.createECardSucess();
                                break;

                            default:
                                mView.showToast("分配电子卡失败");
                                break;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.dismissProgressDialog();
                        mView.showToast("网络错误");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        disposables.add(disposable);
    }

}
