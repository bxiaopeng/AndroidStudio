/*
 * Copyright 2015, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.testing.unittesting.BasicSample;

import android.content.SharedPreferences;

import java.util.Calendar;

/**
 * 管理SharedPreferences访问的工具类
 */
public class SharedPreferencesHelper {

    // 保存到SharedPreferences中的key
    static final String KEY_NAME = "key_name"; //姓名
    static final String KEY_DOB = "key_dob_millis"; //出生日期
    static final String KEY_EMAIL = "key_email"; //邮箱

    // 插入SharedPreferences实现使用持久化
    private final SharedPreferences mSharedPreferences;

    /**
     * 构造函数使用依赖注入
     *
     * @param sharedPreferences The {@link SharedPreferences} that will be used in this DAO.
     */
    public SharedPreferencesHelper(SharedPreferences sharedPreferences) {
        mSharedPreferences = sharedPreferences;
    }

    /**
     * 保存个人信息到SharedPreferences
     *
     * @param sharedPreferenceEntry 保存的数据
     * @return 保存成功返回true, 失败返回false
     */
    public boolean savePersonalInfo(SharedPreferenceEntry sharedPreferenceEntry) {
        // 开始一个SharedPreferences事务
        SharedPreferences.Editor editor = mSharedPreferences.edit();

        editor.putString(KEY_NAME, sharedPreferenceEntry.getName());
        editor.putLong(KEY_DOB, sharedPreferenceEntry.getDateOfBirth().getTimeInMillis());
        editor.putString(KEY_EMAIL, sharedPreferenceEntry.getEmail());

        // 向SharedPreferences提交变更
        return editor.commit();
    }

    /**
     * 从SharedPreferences中获取个人信息
     *
     * @return 检索到的个人信息
     */
    public SharedPreferenceEntry getPersonalInfo() {
        // 从SharedPreferences中获取数据
        String name = mSharedPreferences.getString(KEY_NAME, "");
        Long dobMillis = mSharedPreferences.getLong(KEY_DOB, Calendar.getInstance().getTimeInMillis());
        Calendar dateOfBirth = Calendar.getInstance();
        dateOfBirth.setTimeInMillis(dobMillis);
        String email = mSharedPreferences.getString(KEY_EMAIL, "");

        // 创建一个SharedPreferenceEntry对象
        return new SharedPreferenceEntry(name, dateOfBirth, email);
    }
}
