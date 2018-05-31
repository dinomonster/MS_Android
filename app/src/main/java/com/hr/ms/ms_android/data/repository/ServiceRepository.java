package com.hr.ms.ms_android.data.repository;


import com.hr.ms.ms_android.bean.AddStageResponseBean;
import com.hr.ms.ms_android.bean.AgentOrScholarBean;
import com.hr.ms.ms_android.bean.AliOSSBean;
import com.hr.ms.ms_android.bean.BaseResponseReturnValue;
import com.hr.ms.ms_android.bean.CollegeRoomDetailBean;
import com.hr.ms.ms_android.bean.CollegeRoomListBean;
import com.hr.ms.ms_android.bean.CourseListBean;
import com.hr.ms.ms_android.bean.IdentityBeanList;
import com.hr.ms.ms_android.bean.LoginUserBean;
import com.hr.ms.ms_android.bean.SeniorListBean;
import com.hr.ms.ms_android.bean.StageDetailBean;
import com.hr.ms.ms_android.bean.StageListBean;
import com.hr.ms.ms_android.bean.StageSelectListBean;
import com.hr.ms.ms_android.bean.SubjectDetailBean;
import com.hr.ms.ms_android.bean.SubjectListBean;
import com.hr.ms.ms_android.bean.TagListBean;
import com.hr.ms.ms_android.bean.UserDetailBean;
import com.hr.ms.ms_android.bean.UserListBean;
import com.hr.ms.ms_android.bean.UserSelectBean;
import com.hr.ms.ms_android.bean.WithdrawApplyBean;
import com.hr.ms.ms_android.bean.WithdrawCashBean;
import com.hr.ms.ms_android.bean.WithdrawCashCheckBean;
import com.hr.ms.ms_android.data.local.ServiceLocalDataSource;
import com.hr.ms.ms_android.data.remote.ServiceRemoteDataSource;
import com.hr.ms.ms_android.network.api.ServiceDataApi;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 * Created by Liang on 2017/4/15.
 * 数据管理仓库，控制选择使用remote数据还是local数据（SP、数据库、缓存）
 */

public class ServiceRepository implements ServiceDataApi {
    private static ServiceRepository INSTANCE = null;

    private final ServiceDataApi mRemoteDataSource;

    private final ServiceDataApi mLocalDataSource;

    public ServiceRepository(ServiceLocalDataSource mLocalDataSource, ServiceRemoteDataSource mRemoteDataSource) {
        this.mRemoteDataSource = mRemoteDataSource;
        this.mLocalDataSource = mLocalDataSource;
    }


    private ServiceRepository(ServiceDataApi remoteDataSource,
                              ServiceDataApi localDataSource) {
        mRemoteDataSource = remoteDataSource;
        mLocalDataSource = localDataSource;
    }

    public static ServiceRepository getInstance(ServiceDataApi remoteDataSource,
                                                ServiceDataApi localDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new ServiceRepository(remoteDataSource, localDataSource);
        }
        return INSTANCE;
    }


    @Override
    public Observable<BaseResponseReturnValue<UserListBean>> getAccount(Map map) {
        return mRemoteDataSource.getAccount(map);
    }

    @Override
    public Observable<BaseResponseReturnValue<LoginUserBean>> login(Map map) {
        return mRemoteDataSource.login(map);
    }

    @Override
    public Observable<BaseResponseReturnValue<AliOSSBean>> getUploadAuth(Map<String, Object> map) {
        return mRemoteDataSource.getUploadAuth(map);
    }

    @Override
    public Observable<BaseResponseReturnValue<TagListBean>> getTagList(Map<String, Object> map) {
        return mRemoteDataSource.getTagList(map);
    }

    @Override
    public Observable<BaseResponseReturnValue<UserDetailBean>> getAccountVoInfo(Map<String, Object> map) {
        return mRemoteDataSource.getAccountVoInfo(map);
    }

    @Override
    public Observable<BaseResponseReturnValue> editAccount(Map<String, Object> map) {
        return mRemoteDataSource.editAccount(map);
    }

    @Override
    public Observable<BaseResponseReturnValue<IdentityBeanList>> getIdentityManageList(Map<String, Object> map) {
        return mRemoteDataSource.getIdentityManageList(map);
    }

    @Override
    public Observable<BaseResponseReturnValue> setAgentOrScholar(Map<String, Object> map) {
        return mRemoteDataSource.setAgentOrScholar(map);
    }

    @Override
    public Observable<BaseResponseReturnValue> cancelAgentOrScholar(Map<String, Object> map) {
        return mRemoteDataSource.cancelAgentOrScholar(map);
    }

    @Override
    public Observable<BaseResponseReturnValue> editAccountStage(Map<String, Object> map) {
        return mRemoteDataSource.editAccountStage(map);
    }

    @Override
    public Observable<BaseResponseReturnValue<StageSelectListBean>> getPersonalCityStageList(Map<String, Object> map) {
        return mRemoteDataSource.getPersonalCityStageList(map);
    }

    @Override
    public Observable<BaseResponseReturnValue<List<WithdrawCashBean>>> getWithdrawCashList(Map<String, Object> map) {
        return mRemoteDataSource.getWithdrawCashList(map);
    }

    @Override
    public Observable<BaseResponseReturnValue<WithdrawApplyBean>> getWithdrawCashApplyDetails(Map<String, Object> map) {
        return mRemoteDataSource.getWithdrawCashApplyDetails(map);
    }

    @Override
    public Observable<BaseResponseReturnValue<WithdrawCashCheckBean>> getWithdrawCashCheckDetails(Map<String, Object> map) {
        return mRemoteDataSource.getWithdrawCashCheckDetails(map);
    }

    @Override
    public Observable<BaseResponseReturnValue> checkWithdrawCashApply(Map<String, Object> map) {
        return mRemoteDataSource.checkWithdrawCashApply(map);
    }

    @Override
    public Observable<BaseResponseReturnValue<SubjectListBean>> getTopicList(Map<String, Object> map) {
        return mRemoteDataSource.getTopicList(map);
    }

    @Override
    public Observable<BaseResponseReturnValue<SubjectDetailBean>> getTopicDetail(Map<String, Object> map) {
        return mRemoteDataSource.getTopicDetail(map);
    }

    @Override
    public Observable<BaseResponseReturnValue> addTopic(Map<String, Object> map) {
        return mRemoteDataSource.addTopic(map);
    }

    @Override
    public Observable<BaseResponseReturnValue> editTopic(Map<String, Object> map) {
        return mRemoteDataSource.editTopic(map);
    }

    @Override
    public Observable<BaseResponseReturnValue<CourseListBean>> getLessonList(Map<String, Object> map) {
        return mRemoteDataSource.getLessonList(map);
    }

    @Override
    public Observable<BaseResponseReturnValue> addLesson(Map<String, Object> map) {
        return mRemoteDataSource.addLesson(map);
    }

    @Override
    public Observable<BaseResponseReturnValue> auditLesson(Map<String, Object> map) {
        return mRemoteDataSource.auditLesson(map);
    }

    @Override
    public Observable<BaseResponseReturnValue> editLesson(Map<String, Object> map) {
        return mRemoteDataSource.editLesson(map);
    }

    @Override
    public Observable<BaseResponseReturnValue<UserSelectBean>> getUserList(Map<String, Object> map) {
        return mRemoteDataSource.getUserList(map);
    }

    @Override
    public Observable<BaseResponseReturnValue<CollegeRoomListBean>> getCollegeroomList(Map<String, Object> map) {
        return mRemoteDataSource.getCollegeroomList(map);
    }

    @Override
    public Observable<BaseResponseReturnValue<CollegeRoomDetailBean>> getCollegeroomDetail(Map<String, Object> map) {
        return mRemoteDataSource.getCollegeroomDetail(map);
    }

    @Override
    public Observable<BaseResponseReturnValue> addCollegeroom(Map<String, Object> map) {
        return mRemoteDataSource.addCollegeroom(map);
    }

    @Override
    public Observable<BaseResponseReturnValue> updateCollegeroom(Map<String, Object> map) {
        return mRemoteDataSource.updateCollegeroom(map);
    }

    @Override
    public Observable<BaseResponseReturnValue<StageListBean>> getStageIndexList(Map<String, Object> map) {
        return mRemoteDataSource.getStageIndexList(map);
    }

    @Override
    public Observable<BaseResponseReturnValue<StageDetailBean>> getStageDetail(Map<String, Object> map) {
        return mRemoteDataSource.getStageDetail(map);
    }

    @Override
    public Observable<BaseResponseReturnValue<AgentOrScholarBean>> getAgentOrScholarList(Map<String, Object> map) {
        return mRemoteDataSource.getAgentOrScholarList(map);
    }

    @Override
    public Observable<BaseResponseReturnValue> editStage(Map<String, Object> map) {
        return mRemoteDataSource.editStage(map);
    }

    @Override
    public Observable<BaseResponseReturnValue> createECard(Map<String, Object> map) {
        return mRemoteDataSource.createECard(map);
    }

    @Override
    public Observable<BaseResponseReturnValue<AddStageResponseBean>> addStage(Map<String, Object> map) {
        return mRemoteDataSource.addStage(map);
    }

    @Override
    public Observable<BaseResponseReturnValue<SeniorListBean>> getSeniorList(Map<String, Object> map) {
        return mRemoteDataSource.getSeniorList(map);
    }

    @Override
    public Observable<BaseResponseReturnValue> setSenior(Map<String, Object> map) {
        return mRemoteDataSource.setSenior(map);
    }
}
