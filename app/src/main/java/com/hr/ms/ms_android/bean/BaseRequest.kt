package com.hr.ms.ms_android.bean


import com.hr.ms.ms_android.data.AccountHelper

/**
 * Created by Dino on 1/18 0018.
 */

open class BaseRequest {
    //private final String TOKEN = "AF93A7C3-76FE-4B69-A27C-91026B96393F";
    private val token = AccountHelper.getToken()

}
