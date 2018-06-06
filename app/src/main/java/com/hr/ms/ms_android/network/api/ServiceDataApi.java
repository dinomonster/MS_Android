package com.hr.ms.ms_android.network.api;


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

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;


public interface ServiceDataApi {

    /**
     * 登陆
     *
     * @param map
     * @return
     */
    @POST("api/login/n/apiLogin")
    Observable<BaseResponseReturnValue<LoginUserBean>> login(@QueryMap Map<String, Object> map);

    /**
     * 获取阿里云上传接口秘钥
     *
     * @param map
     * @return
     */
    @POST("api/tools/aliyun/appToken")
    Observable<BaseResponseReturnValue<AliOSSBean>> getUploadAuth(@QueryMap Map<String, Object> map);

    /**
     * 标签列表
     *
     * @param map
     * @return
     */
    @POST("api/tag/getTagList")
    Observable<BaseResponseReturnValue<TagListBean>> getTagList(@QueryMap Map<String, Object> map);

    /**
     * 新增标签
     *
     * @param map
     * @return
     */
    @POST("api/tag/addTag")
    Observable<BaseResponseReturnValue> addTag(@QueryMap Map<String, Object> map);

    /**
     * 运营管理-用户列表
     *
     * @param map
     * @return
     */
    @POST("api/account/getAccountList")
    Observable<BaseResponseReturnValue<UserListBean>> getAccount(@QueryMap Map<String, Object> map);

    /**
     * 运营管理-用户列表-根据用户ID获取用户基本信息
     *
     * @param map
     * @return
     */
    @POST("api/account/getAccountVoInfo")
    Observable<BaseResponseReturnValue<UserDetailBean>> getAccountVoInfo(@QueryMap Map<String, Object> map);

    /**
     * 运营管理-用户列表-编辑用户基本信息
     *
     * @param map
     * @return
     */
    @POST("api/account/editAccount")
    Observable<BaseResponseReturnValue> editAccount(@QueryMap Map<String, Object> map);

    /**
     * 运营管理-身份管理-身份管理列表
     *
     * @param map
     * @return
     */
    @POST("api/account/getIdentityManageList")
    Observable<BaseResponseReturnValue<IdentityBeanList>> getIdentityManageList(@QueryMap Map<String, Object> map);

    /**
     * 运营管理-身份管理-身份管理列表-开通代言人/学霸
     *
     * @param map
     * @return
     */
    @POST("api/account/setAgentOrScholar")
    Observable<BaseResponseReturnValue> setAgentOrScholar(@QueryMap Map<String, Object> map);

    /**
     * 运营管理-身份管理-身份管理列表-取消代言人/学霸
     *
     * @param map
     * @return
     */
    @POST("api/account/cancelAgentOrScholar")
    Observable<BaseResponseReturnValue> cancelAgentOrScholar(@QueryMap Map<String, Object> map);

    /**
     * 运营管理-身份管理-身份管理列表-开通个人驿站
     *
     * @param map
     * @return
     */
    @POST("api/account/editAccountStage")
    Observable<BaseResponseReturnValue> editAccountStage(@QueryMap Map<String, Object> map);

    /**
     * 运营管理-身份管理-身份管理列表-个人及城市驿站列表(驿站查询列表)
     *
     * @param map
     * @return
     */
    @POST("api/stage/getPersonalCityStageList")
    Observable<BaseResponseReturnValue<StageSelectListBean>> getPersonalCityStageList(@QueryMap Map<String, Object> map);

    /**
     * 运营管理-师兄管理列表
     *
     * @param map
     * @return
     */
    @POST("api/account/getSeniorList")
    Observable<BaseResponseReturnValue<SeniorListBean>> getSeniorList(@QueryMap Map<String, Object> map);

    /**
     * 运营管理-师兄管理列表-开通导师、客座嘉宾
     *
     * @param map
     * @return
     */
    @POST("api/account/setSenior")
    Observable<BaseResponseReturnValue> setSenior(@QueryMap Map<String, Object> map);

    /**
     * 运营管理-提现列表
     *
     * @param map
     * @return
     */
    @POST("api/cash/getWithdrawCashList")
    Observable<BaseResponseReturnValue<List<WithdrawCashBean>>> getWithdrawCashList(@QueryMap Map<String, Object> map);

    /**
     * 提现列表-提现申请详情
     *
     * @param map
     * @return
     */
    @POST("api/cash/getWithdrawCashApplyDetails")
    Observable<BaseResponseReturnValue<WithdrawApplyBean>> getWithdrawCashApplyDetails(@QueryMap Map<String, Object> map);

    /**
     * 提现列表-审核信息
     *
     * @param map
     * @return
     */
    @POST("api/cash/getWithdrawCashCheckDetails")
    Observable<BaseResponseReturnValue<WithdrawCashCheckBean>> getWithdrawCashCheckDetails(@QueryMap Map<String, Object> map);


    /**
     * 提现详情-提现审核
     *
     * @param map
     * @return
     */
    @POST("api/cash/checkWithdrawCashApply")
    Observable<BaseResponseReturnValue> checkWithdrawCashApply(@QueryMap Map<String, Object> map);


    /**
     * 运营管理-课题列表
     *
     * @param map
     * @return
     */
    @POST("api/topic/getTopicList")
    Observable<BaseResponseReturnValue<SubjectListBean>> getTopicList(@QueryMap Map<String, Object> map);


    /**
     * 课题列表-课题详情
     *
     * @param map
     * @return
     */
    @POST("api/topic/getTopicDetail")
    Observable<BaseResponseReturnValue<SubjectDetailBean>> getTopicDetail(@QueryMap Map<String, Object> map);

    /**
     * 课题列表-新增课题
     *
     * @param map
     * @return
     */
    @POST("api/topic/addTopic")
    Observable<BaseResponseReturnValue> addTopic(@QueryMap Map<String, Object> map);

    /**
     * 课题列表-课题编辑
     *
     * @param map
     * @return
     */
    @POST("api/topic/editTopic")
    Observable<BaseResponseReturnValue> editTopic(@QueryMap Map<String, Object> map);

    /**
     * 运营管理-课程列表
     *
     * @param map
     * @return
     */
    @POST("api/lesson/getLessonList")
    Observable<BaseResponseReturnValue<CourseListBean>> getLessonList(@QueryMap Map<String, Object> map);

    /**
     * 课程列表-新增课程
     *
     * @param map
     * @return
     */
    @POST("api/lesson/addLesson")
    Observable<BaseResponseReturnValue> addLesson(@QueryMap Map<String, Object> map);

    /**
     * 课程列表-发布课程
     *
     * @param map
     * @return
     */
    @POST("api/lesson/auditLesson")
    Observable<BaseResponseReturnValue> auditLesson(@QueryMap Map<String, Object> map);

    /**
     * 课程列表-编辑课程
     *
     * @param map
     * @return
     */
    @POST("api/lesson/editLesson")
    Observable<BaseResponseReturnValue> editLesson(@QueryMap Map<String, Object> map);

    /**
     * 用户查询列表
     *
     * @param map
     * @return
     */
    @POST("api/account/getUserList")
    Observable<BaseResponseReturnValue<UserSelectBean>> getUserList(@QueryMap Map<String, Object> map);

    /**
     * 私塾列表
     *
     * @param map
     * @return
     */
    @POST("api/collegeroom/getCollegeroomList")
    Observable<BaseResponseReturnValue<CollegeRoomListBean>> getCollegeroomList(@QueryMap Map<String, Object> map);

    /**
     * 获取私塾详情
     *
     * @param map
     * @return
     */
    @POST("api/collegeroom/getCollegeroomDetail")
    Observable<BaseResponseReturnValue<CollegeRoomDetailBean>> getCollegeroomDetail(@QueryMap Map<String, Object> map);

    /**
     * 新增私塾
     *
     * @param map
     * @return
     */
    @POST("api/collegeroom/addCollegeroom")
    Observable<BaseResponseReturnValue> addCollegeroom(@QueryMap Map<String, Object> map);

    /**
     * 编辑私塾
     *
     * @param map
     * @return
     */
    @POST("/api/collegeroom/editCollegeroom")
    Observable<BaseResponseReturnValue> updateCollegeroom(@QueryMap Map<String, Object> map);

    /**
     * 运营管理-驿站管理-驿站列表
     *
     * @param map
     * @return
     */
    @POST("api/stage/getStageIndexList")
    Observable<BaseResponseReturnValue<StageListBean>> getStageIndexList(@QueryMap Map<String, Object> map);

    /**
     * 运营管理-驿站管理-驿站详情
     *
     * @param map
     * @return
     */
    @POST("api/stage/getStageDetail")
    Observable<BaseResponseReturnValue<StageDetailBean>> getStageDetail(@QueryMap Map<String, Object> map);

    /**
     * 运营管理-驿站管理-驿站列表-获取代言人、学霸列表
     *
     * @param map
     * @return
     */
    @POST("api/stage/getAgentOrScholarList")
    Observable<BaseResponseReturnValue<AgentOrScholarBean>> getAgentOrScholarList(@QueryMap Map<String, Object> map);

    /**
     * 运营管理-驿站管理-编辑驿站基本信息
     *
     * @param map
     * @return
     */
    @POST("api/stage/editStage")
    Observable<BaseResponseReturnValue> editStage(@QueryMap Map<String, Object> map);

    /**
     * 运营管理-驿站管理-派发电子卡
     *
     * @param map
     * @return
     */
    @POST("api/stage/createECard")
    Observable<BaseResponseReturnValue> createECard(@QueryMap Map<String, Object> map);

    /**
     * 运营管理-驿站管理-开设驿站
     *
     * @param map
     * @return
     */
    @POST("api/stage/addStage")
    Observable<BaseResponseReturnValue<AddStageResponseBean>> addStage(@QueryMap Map<String, Object> map);

    /**
     * 运营管理-驿站管理-已激活卡片详情
     *
     * @param map
     * @return
     */
    @POST("api/stage/getCardActiveDetail")
    Observable<BaseResponseReturnValue<CardActiveDetailBean>> getCardActiveDetail(@QueryMap Map<String, Object> map);

    /**
     * 资源管理-官方活动管理-官方活动列表
     *
     * @param map
     * @return
     */
    @POST("api/officialEvent/getOfficialEventList")
    Observable<BaseResponseReturnValue<OfficialEventListBean>> getOfficialEventList(@QueryMap Map<String, Object> map);

    /**
     * 资源管理-官方活动管理-官方活动详情
     *
     * @param map
     * @return
     */
    @POST("api/officialEvent/getOfficialEventDetail")
    Observable<BaseResponseReturnValue<OfficialEventDetailBean>> getOfficialEventDetail(@QueryMap Map<String, Object> map);

    /**
     * 资源管理-官方活动管理-创建活动基本信息
     *
     * @param map
     * @return
     */
    @POST("api/officialEvent/addOfficialEvent")
    Observable<BaseResponseReturnValue> addOfficialEvent(@QueryMap Map<String, Object> map);

    /**
     * 资源管理-官方活动管理-添加官方活动赞助商
     *
     * @param map
     * @return
     */
    @POST("api/officialEvent/addEventSponor")
    Observable<BaseResponseReturnValue> addEventSponor(@QueryMap Map<String, Object> map);

    /**
     * 资源管理-官方活动管理-创建官方活动线下报名信息
     *
     * @param map
     * @return
     */
    @POST("api/officialEvent/addEventOfflineInfo")
    Observable<BaseResponseReturnValue> addEventOfflineInfo(@QueryMap Map<String, Object> map);

    /**
     * 资源管理-官方活动管理-创建官方直播信息
     *
     * @param map
     * @return
     */
    @POST("api/officialEvent/addEventLiveInfo")
    Observable<BaseResponseReturnValue> addEventLiveInfo(@QueryMap Map<String, Object> map);

    /**
     * 资源管理-官方活动管理-直播信息编辑
     *
     * @param map
     * @return
     */
    @POST("api/officialEvent/editEventLiveInfo")
    Observable<BaseResponseReturnValue> editEventLiveInfo(@QueryMap Map<String, Object> map);

    /**
     * 资源管理-官方活动管理-添加官方活动直播聊天室管理员
     *
     * @param map
     * @return
     */
    @POST("api/officialEvent/addEventImManager")
    Observable<BaseResponseReturnValue> addEventImManager(@QueryMap Map<String, Object> map);

    /**
     * 资源管理-官方活动管理-聊天室管理员信息编辑
     *
     * @param map
     * @return
     */
    @POST("api/officialEvent/editEventImManager")
    Observable<BaseResponseReturnValue> editEventImManager(@QueryMap Map<String, Object> map);

    /**
     * 资源管理-官方活动管理-创建活动流程节点
     *
     * @param map
     * @return
     */
    @POST("api/officialEvent/addEventFlowPoints")
    Observable<BaseResponseReturnValue> addEventFlowPoints(@QueryMap Map<String, Object> map);

    /**
     * 资源管理-官方活动管理-添加官方活动承办方
     *
     * @param map
     * @return
     */
    @POST("api/officialEvent/addEventCoorganizer")
    Observable<BaseResponseReturnValue> addEventCoorganizer(@QueryMap Map<String, Object> map);

    /**
     * 资源管理-官方活动管理-编辑线下报名信息
     *
     * @param map
     * @return
     */
    @POST("api/officialEvent/editEventOfflineInfo")
    Observable<BaseResponseReturnValue> editEventOfflineInfo(@QueryMap Map<String, Object> map);

    /**
     * 资源管理-官方活动管理-编辑活动基本信息
     *
     * @param map
     * @return
     */
    @POST("api/officialEvent/editOfficialEvent")
    Observable<BaseResponseReturnValue> editOfficialEvent(@QueryMap Map<String, Object> map);

    /**
     * 资源管理-官方活动管理-删除聊天室管理员
     *
     * @param map
     * @return
     */
    @POST("api/officialEvent/deleteImManagerInfo")
    Observable<BaseResponseReturnValue> deleteImManagerInfo(@QueryMap Map<String, Object> map);

    /**
     * 资源管理-官方活动管理-删除活动流程节点
     *
     * @param map
     * @return
     */
    @POST("api/officialEvent/deleteFlowPoints")
    Observable<BaseResponseReturnValue> deleteFlowPoints(@QueryMap Map<String, Object> map);

    /**
     * 资源管理-官方活动管理-删除活动流程节点
     *
     * @param map
     * @return
     */
    @POST("api/officialEvent/editEventFlowPoints")
    Observable<BaseResponseReturnValue> editEventFlowPoints(@QueryMap Map<String, Object> map);

    /**
     * 资源管理-官方活动管理-删除赞助商信息
     *
     * @param map
     * @return
     */
    @POST("api/officialEvent/deleteEventSponorInfo")
    Observable<BaseResponseReturnValue> deleteEventSponorInfo(@QueryMap Map<String, Object> map);

    /**
     * 资源管理-官方活动管理-添加官方活动赞助商
     *
     * @param map
     * @return
     */
    @POST("api/officialEvent/editEventSponorInfo")
    Observable<BaseResponseReturnValue> editEventSponorInfo(@QueryMap Map<String, Object> map);

    /**
     * 资源管理-官方活动管理-删除承办方
     *
     * @param map
     * @return
     */
    @POST("api/officialEvent/deleteEventCoorganizer")
    Observable<BaseResponseReturnValue> deleteEventCoorganizer(@QueryMap Map<String, Object> map);

    /**
     * 资源管理-官方活动管理-删除承办方
     *
     * @param map
     * @return
     */
    @POST("api/officialEvent/editEventCoorganizer")
    Observable<BaseResponseReturnValue> editEventCoorganizer(@QueryMap Map<String, Object> map);

}
