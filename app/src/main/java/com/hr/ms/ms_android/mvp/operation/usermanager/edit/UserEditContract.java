package com.hr.ms.ms_android.mvp.operation.usermanager.edit;

import android.app.Activity;

import com.better.appbase.mvp.MvpPresenter;
import com.better.appbase.mvp.MvpView;
import com.hr.ms.ms_android.bean.UserDetailBean;

/**
 * Created by Dino on 2018/4/3.
 */

public interface UserEditContract {
    interface View extends MvpView {
        void showData(UserDetailBean bean);
        void uploadImgSucess(String url);
        void editSucess();
        void showSetPermissionDialog(String s);
        void startSelectPic();
    }

    interface Presenter extends MvpPresenter {
        void getAccountVoInfo(int accId);
        void editAccount(int role,int status,String memberName,String imageUri,String email,String introduction,int sex,String birth,String motto,int identityId,int fieldId,String company,String title) ;
        void checkExternalStoragePermission(Activity activity);
        void uploadImg(String path);
    }
}
