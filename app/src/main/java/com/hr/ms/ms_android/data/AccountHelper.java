package com.hr.ms.ms_android.data;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import com.better.appbase.utils.SPUtils;
import com.better.appbase.utils.ToastTools;
import com.google.gson.Gson;
import com.hr.ms.ms_android.R;
import com.hr.ms.ms_android.bean.AliOSSBean;
import com.hr.ms.ms_android.bean.LoginUserBean;
import com.hr.ms.ms_android.bean.Menu;
import com.hr.ms.ms_android.bean.MenuAuthBean;
import com.hr.ms.ms_android.bean.SysPermission;
import com.hr.ms.ms_android.mvp.login.LoginActivity;
import com.socks.library.KLog;

import java.util.List;


/**
 * by：梁惠涌
 * 账户辅助类，
 * 用于更新用户信息和保存当前账户等操作
 */
public final class AccountHelper {
    private static String SP_ALI_OSS = "SP_ALI_OSS";
    private static String SP_ACCOUNT_KEY = "SP_ACCOUNT_KEY";
    private static String SP_PASSWORD_KEY = "SP_PASSWORD_KEY";

    private static String SP_USER_KEY = "SP_USER_KEY";
    private static String SP_TOKEN_KEY = "SP_TOKEN_KEY";
    private static String SP_MENUAUTH_KEY = "SP_MENUAUTH_KEY";
    private String TOKEN;
    private LoginUserBean user;
    private MenuAuthBean menuAuth;
    private AliOSSBean aliOSSBean;

    @SuppressLint("StaticFieldLeak")
    private static AccountHelper instances;

    private AccountHelper() {

    }

    public static void init() {
        instances = new AccountHelper();
    }

    /*
     * 判断是否登录
     */
    public static boolean isLogin() {
        return !TextUtils.isEmpty(getToken());
    }


    public static void setToken(String token) {
        // 保存User缓存
        instances.TOKEN = token;
        SPUtils.putString(SP_TOKEN_KEY, token);
    }

    /**
     * 获取token
     */
    public synchronized static String getToken() {
        if (instances == null) {
            KLog.e("AccountHelper instances is null, you need call init() method.");
            return "";
        }
        if (instances.TOKEN == null) {
            instances.TOKEN = SPUtils.getString(SP_TOKEN_KEY);
        }

        return instances.TOKEN;
    }

    /**
     * 获取用户
     */
    public synchronized static LoginUserBean getUser() {
        if (instances == null) {
            KLog.e("AccountHelper instances is null, you need call init() method.");
            return new LoginUserBean();
        }
        if (instances.user == null) {
            String userStr = SPUtils.getString(SP_USER_KEY);
            instances.user = new Gson().fromJson(userStr, LoginUserBean.class);
        }
        if (instances.user == null)
            instances.user = new LoginUserBean();

        //KLog.e(new Gson().toJson(instances.user));
        return instances.user;
    }

    public synchronized static MenuAuthBean getMenuAuth() {
        if (instances == null) {
            KLog.e("AccountHelper instances is null, you need call init() method.");
            return new MenuAuthBean();
        }
        if (instances.menuAuth == null) {
            String userStr = SPUtils.getString(SP_MENUAUTH_KEY);
            instances.menuAuth = new Gson().fromJson(userStr, MenuAuthBean.class);
        }
        if (instances.menuAuth == null)
            instances.menuAuth = new MenuAuthBean();

        //KLog.e(new Gson().toJson(instances.user));
        return instances.menuAuth;
    }

    /**
     * 更新用户信息
     */
    private static void updateUserCache(LoginUserBean user) {
        if (user == null)
            return;

        instances.user = user;
        SPUtils.putString(SP_USER_KEY, new Gson().toJson(user));
        //更新token
        instances.TOKEN = user.getToken();
        SPUtils.putString(SP_TOKEN_KEY, instances.TOKEN);
        instances.menuAuth = new MenuAuthBean();
        for (Menu menu : user.getMenus()) {
            switch (menu.getId()) {
                case 1:

                    break;
                case 31:
                    instances.menuAuth.setResourceManagerEnable(true);
                    break;
                case 84:
                    instances.menuAuth.setOperationManagerEnable(true);
                    break;
            }
            setPermission(menu.getSysPermissions());
        }
        SPUtils.putString(SP_MENUAUTH_KEY, new Gson().toJson(instances.menuAuth));
    }

    private static void setPermission(List<SysPermission> sysPermissions) {
        for (SysPermission sysPermission : sysPermissions) {
            switch (sysPermission.getId()) {
                case 3:
                    instances.menuAuth.setPermissionManagerEnable(true);
                    break;
                case 4:
                    instances.menuAuth.setUserManagerEnable(true);
                    break;
                case 12:
                    instances.menuAuth.setRoleManagerEnable(true);
                    break;
                case 13:
                    instances.menuAuth.setMenuManagerEnable(true);
                    break;
                case 101:
                    instances.menuAuth.setOverViewEnable(true);
                    break;
                case 97:
                    instances.menuAuth.setTeacherManagerEnable(true);
                    break;
                case 110:
                    instances.menuAuth.setScheduleManagerEnable(true);
                    break;
                case 105:
                    instances.menuAuth.setWithDrawManagerEnable(true);
                    break;
                case 86:
                    instances.menuAuth.setOPUserManagerEnable(true);
                    break;
                case 111:
                    instances.menuAuth.setOPUserListEnable(true);
                    break;
                case 127:
                    instances.menuAuth.setIdentifyManagerEnable(true);
                    break;
                case 113:
                    instances.menuAuth.setSubjectManagerEnable(true);
                    break;
                case 114:
                    instances.menuAuth.setInterviewManagerEnable(true);
                    break;
                case 115:
                    instances.menuAuth.setReportManagerEnable(true);
                    break;
                case 116:
                    instances.menuAuth.setPrivateManagerEnable(true);
                    break;
                case 117:
                    instances.menuAuth.setCollegeManagerEnable(true);
                    break;
                case 92:
                    instances.menuAuth.setHopManagerEnable(true);
                    break;
                case 118:
                    instances.menuAuth.setCityHopEnable(true);
                    break;
                case 166:
                    instances.menuAuth.setPersonHopEnable(true);
                    break;
                case 167:
                    instances.menuAuth.setSchoolHopEnable(true);
                    break;
                case 120:
                    instances.menuAuth.setActivityManagerEnable(true);
                    break;
                case 123:
                    instances.menuAuth.setLiveManagerEnable(true);
                    break;
                case 122:
                    instances.menuAuth.setConfigManagerEnable(true);
                    break;
                case 124:
                    instances.menuAuth.setConfigResourceManagerEnable(true);
                    break;
                case 125:
                    instances.menuAuth.setConfigTerraceEnable(true);
                    break;
                case 126:
                    instances.menuAuth.setConfigBannerEnable(true);
                    break;
                case 163:
                    instances.menuAuth.setTimeTaskManagerEnable(true);
                    break;
                case 164:
                    instances.menuAuth.setCommentManagerEnable(true);
                    break;
                case 165:
                    instances.menuAuth.setCommonCommentEnable(true);
                    break;
                case 168:
                    instances.menuAuth.setLiveCommentEnable(true);
                    break;
                case 169:
                    instances.menuAuth.setOtherCommentEnable(true);
                    break;
            }
            setPermission(sysPermission.getSysPermissions());
        }
    }

    public static void gotoLogin(Activity activity) {
        ToastTools.showToast("请先登录!");
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    // 退出登陆
    public static void logout() {
        // 清除用户缓存
        instances.user = null;
        SPUtils.remove(SP_USER_KEY);

        //清空token
        instances.TOKEN = null;
        SPUtils.remove(SP_TOKEN_KEY);
    }

    /**
     * 用户登录
     */
    public static void login(LoginUserBean user) {
        // 保存User缓存
        updateUserCache(user);
    }

    public static void setAccountAndPwd(String account, String pwd) {
        SPUtils.putString(SP_ACCOUNT_KEY, account);
        SPUtils.putString(SP_PASSWORD_KEY, pwd);
    }

    public static String getAccount() {
        return SPUtils.getString(SP_ACCOUNT_KEY,"");
    }

    public static String getPwd() {
        return SPUtils.getString(SP_PASSWORD_KEY,"");
    }

    /**
     * 设置阿里云参数
     * @param aliOSSBean
     */
    public static void setAliOSS(AliOSSBean aliOSSBean) {
        instances.aliOSSBean = aliOSSBean;
        SPUtils.putString(SP_ALI_OSS, new Gson().toJson(aliOSSBean));
    }

    /**
     * 获取阿里云参数
     */
    public static AliOSSBean getAliOSS() {
        if (instances == null) {
            KLog.e("AccountHelper instances is null, you need call init() method.");
            return null;
        }

        if (instances.aliOSSBean == null) {
            String aliOSSStr = SPUtils.getString(SP_ALI_OSS);
            instances.aliOSSBean = new Gson().fromJson(aliOSSStr, AliOSSBean.class);
        }

        if (instances.aliOSSBean == null)
            instances.aliOSSBean = new AliOSSBean();

        return instances.aliOSSBean;
    }
}
