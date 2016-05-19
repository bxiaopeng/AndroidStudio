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

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

/**
 * 一个用户信息设置页面,用户可以输入名字、出生日期和email地址
 * 点击保存后,个人信息被保存到SharedPreferences中.
 */
public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";

    // 负责管理SharedPreferences写入的帮助类
    private SharedPreferencesHelper mSharedPreferencesHelper;

    private EditText mNameText;    // 名字输入框

    private DatePicker mDobPicker;     // 选择出生日期

    private EditText mEmailText;     // email输入框

    private EmailValidator mEmailValidator;     // 验证email输入的字段


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNameText = (EditText) findViewById(R.id.userNameInput);
        mDobPicker = (DatePicker) findViewById(R.id.dateOfBirthInput);
        mEmailText = (EditText) findViewById(R.id.emailInput);

        // 字段验证
        mEmailValidator = new EmailValidator();
        mEmailText.addTextChangedListener(mEmailValidator);

        // 实例化SharedPreferencesHelper.
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mSharedPreferencesHelper = new SharedPreferencesHelper(sharedPreferences);

        populateUi();
    }

    /**
     * 初始化所有要保存到SharedPreferences中的个人信息字段
     */
    private void populateUi() {
        SharedPreferenceEntry sharedPreferenceEntry;
        sharedPreferenceEntry = mSharedPreferencesHelper.getPersonalInfo();

        //名字
        mNameText.setText(sharedPreferenceEntry.getName());

        //出生日期
        Calendar dateOfBirth = sharedPreferenceEntry.getDateOfBirth();
        mDobPicker.init(dateOfBirth.get(Calendar.YEAR), dateOfBirth.get(Calendar.MONTH),
                dateOfBirth.get(Calendar.DAY_OF_MONTH), null);
        //email
        mEmailText.setText(sharedPreferenceEntry.getEmail());
    }


    /**
     * 点击【保存】按钮时调用
     */
    public void onSaveClick(View view) {
        // 如果字段没有验证,则不保存
        if (!mEmailValidator.isValid()) {
            mEmailText.setError("无效的email");
            Log.w(TAG, "没有保存个人信息: 无效的email");
            return;
        }

        // 从输入的字段获取文本
        String name = mNameText.getText().toString();
        Calendar dateOfBirth = Calendar.getInstance();
        dateOfBirth.set(mDobPicker.getYear(), mDobPicker.getMonth(), mDobPicker.getDayOfMonth());
        String email = mEmailText.getText().toString();

        // Create a Setting model class to persist.
        // 创建一个设置模型的类来存留
        SharedPreferenceEntry sharedPreferenceEntry =
                new SharedPreferenceEntry(name, dateOfBirth, email);

        // Persist the personal information.
        // 存留个人信息
        boolean isSuccess = mSharedPreferencesHelper.savePersonalInfo(sharedPreferenceEntry);
        if (isSuccess) {
            Toast.makeText(this, "个人信息已保存", Toast.LENGTH_LONG).show();
            Log.i(TAG, "个人信息已保存");
        } else {
            Log.e(TAG, "个人信息写入SharedPreferences失败");
        }
    }

    /**
     * 点击【恢复】按钮时调用
     */
    public void onRevertClick(View view) {
        populateUi();
        Toast.makeText(this, "个人信息已恢复", Toast.LENGTH_LONG).show();
        Log.i(TAG, "个人信息已恢复");
    }
}
