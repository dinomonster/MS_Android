package com.hr.ms.ms_android.data.local;


import com.hr.ms.ms_android.bean.AddStageResponseBean;
import com.hr.ms.ms_android.bean.AgentOrScholarBean;
import com.hr.ms.ms_android.bean.AliOSSBean;
import com.hr.ms.ms_android.bean.BaseResponseReturnValue;
import com.hr.ms.ms_android.bean.CardActiveDetailBean;
import com.hr.ms.ms_android.bean.CollegeRoomDetailBean;
import com.hr.ms.ms_android.bean.CollegeRoomListBean;
import com.hr.ms.ms_android.bean.CourseListBean;
import com.hr.ms.ms_android.bean.IdentityBeanList;
import com.hr.ms.ms_android.bean.LoginUserBean;
import com.hr.ms.ms_android.bean.OfficialEventDetailBean;
import com.hr.ms.ms_android.bean.OfficialEventListBean;
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
import com.hr.ms.ms_android.network.api.ServiceDataApi;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;

/**
 *
 * 数据local实现方式
 */
public class ServiceLocalDataSource implements ServiceDataApi {

    private static ServiceLocalDataSource INSTANCE;


    public static ServiceLocalDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ServiceLocalDataSource();
        }
        return INSTANCE;
    }


    @Override
    public Observable<BaseResponseReturnValue<UserListBean>> getAccount(Map map) {
        return null;
    }

    @Override
    public Observable<BaseResponseReturnValue<LoginUserBean>> login(Map map) {
        return null;
    }

    @Override
    public Observable<BaseResponseReturnValue<AliOSSBean>> getUploadAuth(Map<String, Object> map) {
        return null;
    }

    @Override
    public Observable<BaseResponseReturnValue<TagListBean>> getTagList(Map<String, Object> map) {
        return null;
    }

    @Override
    public Observable<BaseResponseReturnValue> addTag(Map<String, Object> map) {
        return null;
    }

    @Override
    public Observable<BaseResponseReturnValue<UserDetailBean>> getAccountVoInfo(Map<String, Object> map) {
        return null;
    }

    @Override
    public Observable<BaseResponseReturnValue> editAccount(Map<String, Object> map) {
        return null;
    }

    @Override
    public Observable<BaseResponseReturnValue<IdentityBeanList>> getIdentityManageList(Map<String, Object> map) {
        return null;
    }

    @Override
    public Observable<BaseResponseReturnValue> setAgentOrScholar(Map<String, Object> map) {
        return null;
    }

    @Override
    public Observable<BaseResponseReturnValue> cancelAgentOrScholar(Map<String, Object> map) {
        return null;
    }

    @Override
    public Observable<BaseResponseReturnValue> editAccountStage(Map<String, Object> map) {
        return null;
    }

    @Override
    public Observable<BaseResponseReturnValue<StageSelectListBean>> getPersonalCityStageList(Map<String, Object> map) {
        return null;
    }

    @Override
    public Observable<BaseResponseReturnValue<List<WithdrawCashBean>>> getWithdrawCashList(Map<String, Object> map) {
        return null;
    }

    @Override
    public Observable<BaseResponseReturnValue<WithdrawApplyBean>> getWithdrawCashApplyDetails(Map<String, Object> map) {
        return null;
    }

    @Override
    public Observable<BaseResponseReturnValue<WithdrawCashCheckBean>> getWithdrawCashCheckDetails(Map<String, Object> map) {
        return null;
    }

    @Override
    public Observable<BaseResponseReturnValue> checkWithdrawCashApply(Map<String, Object> map) {
        return null;
    }

    @Override
    public Observable<BaseResponseReturnValue<SubjectListBean>> getTopicList(Map<String, Object> map) {
        return null;
    }

    @Override
    public Observable<BaseResponseReturnValue<SubjectDetailBean>> getTopicDetail(Map<String, Object> map) {
        return null;
    }

    @Override
    public Observable<BaseResponseReturnValue> addTopic(Map<String, Object> map) {
        return null;
    }

    @Override
    public Observable<BaseResponseReturnValue> editTopic(Map<String, Object> map) {
        return null;
    }

    @Override
    public Observable<BaseResponseReturnValue<CourseListBean>> getLessonList(Map<String, Object> map) {
        return null;
    }

    @Override
    public Observable<BaseResponseReturnValue> addLesson(Map<String, Object> map) {
        return null;
    }

    @Override
    public Observable<BaseResponseReturnValue> auditLesson(Map<String, Object> map) {
        return null;
    }

    @Override
    public Observable<BaseResponseReturnValue> editLesson(Map<String, Object> map) {
        return null;
    }

    @Override
    public Observable<BaseResponseReturnValue<UserSelectBean>> getUserList(Map<String, Object> map) {
        return null;
    }

    @Override
    public Observable<BaseResponseReturnValue<CollegeRoomListBean>> getCollegeroomList(Map<String, Object> map) {
        return null;
    }

    @Override
    public Observable<BaseResponseReturnValue<CollegeRoomDetailBean>> getCollegeroomDetail(Map<String, Object> map) {
        return null;
    }

    @Override
    public Observable<BaseResponseReturnValue> addCollegeroom(Map<String, Object> map) {
        return null;
    }

    @Override
    public Observable<BaseResponseReturnValue> updateCollegeroom(Map<String, Object> map) {
        return null;
    }

    @Override
    public Observable<BaseResponseReturnValue<StageListBean>> getStageIndexList(Map<String, Object> map) {
        return null;
    }

    @Override
    public Observable<BaseResponseReturnValue<StageDetailBean>> getStageDetail(Map<String, Object> map) {
        return null;
    }

    @Override
    public Observable<BaseResponseReturnValue<AgentOrScholarBean>> getAgentOrScholarList(Map<String, Object> map) {
        return null;
    }

    @Override
    public Observable<BaseResponseReturnValue> editStage(Map<String, Object> map) {
        return null;
    }

    @Override
    public Observable<BaseResponseReturnValue> createECard(Map<String, Object> map) {
        return null;
    }

    @Override
    public Observable<BaseResponseReturnValue<AddStageResponseBean>> addStage(Map<String, Object> map) {
        return null;
    }

    @Override
    public Observable<BaseResponseReturnValue<CardActiveDetailBean>> getCardActiveDetail(Map<String, Object> map) {
        return null;
    }

    @Override
    public Observable<BaseResponseReturnValue<OfficialEventListBean>> getOfficialEventList(Map<String, Object> map) {
        return null;
    }

    @Override
    public Observable<BaseResponseReturnValue<OfficialEventDetailBean>> getOfficialEventDetail(Map<String, Object> map) {
        return null;
    }

    @Override
    public Observable<BaseResponseReturnValue> addOfficialEvent(Map<String, Object> map) {
        return null;
    }

    @Override
    public Observable<BaseResponseReturnValue> addEventSponor(Map<String, Object> map) {
        return null;
    }

    @Override
    public Observable<BaseResponseReturnValue> addEventOfflineInfo(Map<String, Object> map) {
        return null;
    }

    @Override
    public Observable<BaseResponseReturnValue> addEventLiveInfo(Map<String, Object> map) {
        return null;
    }

    @Override
    public Observable<BaseResponseReturnValue> editEventLiveInfo(Map<String, Object> map) {
        return null;
    }

    @Override
    public Observable<BaseResponseReturnValue> addEventImManager(Map<String, Object> map) {
        return null;
    }

    @Override
    public Observable<BaseResponseReturnValue> editEventImManager(Map<String, Object> map) {
        return null;
    }

    @Override
    public Observable<BaseResponseReturnValue> addEventFlowPoints(Map<String, Object> map) {
        return null;
    }

    @Override
    public Observable<BaseResponseReturnValue> addEventCoorganizer(Map<String, Object> map) {
        return null;
    }

    @Override
    public Observable<BaseResponseReturnValue> editEventOfflineInfo(Map<String, Object> map) {
        return null;
    }

    @Override
    public Observable<BaseResponseReturnValue> editOfficialEvent(Map<String, Object> map) {
        return null;
    }

    @Override
    public Observable<BaseResponseReturnValue> deleteImManagerInfo(Map<String, Object> map) {
        return null;
    }

    @Override
    public Observable<BaseResponseReturnValue> deleteFlowPoints(Map<String, Object> map) {
        return null;
    }

    @Override
    public Observable<BaseResponseReturnValue> editEventFlowPoints(Map<String, Object> map) {
        return null;
    }

    @Override
    public Observable<BaseResponseReturnValue> deleteEventSponorInfo(Map<String, Object> map) {
        return null;
    }

    @Override
    public Observable<BaseResponseReturnValue> editEventSponorInfo(Map<String, Object> map) {
        return null;
    }

    @Override
    public Observable<BaseResponseReturnValue> deleteEventCoorganizer(Map<String, Object> map) {
        return null;
    }

    @Override
    public Observable<BaseResponseReturnValue> editEventCoorganizer(Map<String, Object> map) {
        return null;
    }

    @Override
    public Observable<BaseResponseReturnValue<SeniorListBean>> getSeniorList(Map<String, Object> map) {
        return null;
    }

    @Override
    public Observable<BaseResponseReturnValue> setSenior(Map<String, Object> map) {
        return null;
    }
}
