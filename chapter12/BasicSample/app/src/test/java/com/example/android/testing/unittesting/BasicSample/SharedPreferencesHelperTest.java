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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import android.content.SharedPreferences;
import android.test.suitebuilder.annotation.SmallTest;

import java.util.Calendar;


/**
 * 模拟SharedPreferences对SharedPreferencesHelper进行单元测试
 */
@SmallTest
@RunWith(MockitoJUnitRunner.class)
public class SharedPreferencesHelperTest {

    private static final String TEST_NAME = "Test name"; //姓名

    private static final String TEST_EMAIL = "test@email.com"; //邮箱

    private static final Calendar TEST_DATE_OF_BIRTH = Calendar.getInstance(); //出生日期

    static {
        TEST_DATE_OF_BIRTH.set(1986, 9, 17);
    }

    private SharedPreferenceEntry mSharedPreferenceEntry; //个人信息模型

    private SharedPreferencesHelper mMockSharedPreferencesHelper; //模拟正确的SharedPreferencesHelper

    private SharedPreferencesHelper mMockBrokenSharedPreferencesHelper; //模拟损坏的SharedPreferencesHelper

    @Mock
    SharedPreferences mMockSharedPreferences; //模拟正常的SharedPreferences

    @Mock
    SharedPreferences mMockBrokenSharedPreferences; //模拟损坏的SharedPreferences

    @Mock
    SharedPreferences.Editor mMockEditor; //模拟正常的Editor

    @Mock
    SharedPreferences.Editor mMockBrokenEditor;//模拟损坏的Editor

    /**
     * 初始化mock
     */
    @Before
    public void initMocks() {
        // 个人信息
        mSharedPreferenceEntry = new SharedPreferenceEntry(TEST_NAME, TEST_DATE_OF_BIRTH, TEST_EMAIL);

        // 模拟正常的SharedPreferences
        mMockSharedPreferencesHelper = createMockSharedPreference();

        // 模拟保存数据失败的SharedPreferences
        mMockBrokenSharedPreferencesHelper = createBrokenMockSharedPreference();
    }

    /**
     * 测试sharedPreferencesHelper能够正常保存和读取个人信息
     */
    @Test
    public void sharedPreferencesHelper_SaveAndReadPersonalInformation() {

        // 个人信息保存到SharedPreferences
        boolean success = mMockSharedPreferencesHelper.savePersonalInfo(mSharedPreferenceEntry);
        assertThat("校验SharedPreferenceEntry.save... 返回true", success, is(true));

        // 从SharedPreferences读取个人信息
        SharedPreferenceEntry savedSharedPreferenceEntry = mMockSharedPreferencesHelper.getPersonalInfo();

        // 确认写入和读取的个人信息相同
        assertThat("校验SharedPreferenceEntry.name 写入和读取的名字相同",
                mSharedPreferenceEntry.getName(),
                is(equalTo(savedSharedPreferenceEntry.getName())));

        assertThat("校验SharedPreferenceEntry.dateOfBirth 写入和读取的出生日期相同",
                mSharedPreferenceEntry.getDateOfBirth(),
                is(equalTo(savedSharedPreferenceEntry.getDateOfBirth())));

        assertThat("校验SharedPreferenceEntry.email写入和读取的email相同",
                mSharedPreferenceEntry.getEmail(),
                is(equalTo(savedSharedPreferenceEntry.getEmail())));
    }

    /**
     * 测试sharedPreferencesHelper保存个人信息失败时返回false
     */
    @Test
    public void sharedPreferencesHelper_SavePersonalInformationFailed_ReturnsFalse() {
        // 从一个损坏的SharedPreferencesHelper中读取个人信息
        boolean success =
                mMockBrokenSharedPreferencesHelper.savePersonalInfo(mSharedPreferenceEntry);

        assertThat("确认写入一个损坏的SharedPreferencesHelper返回false", success, is(false));
    }

    /**
     * 创建模拟的SharedPreferences
     */
    private SharedPreferencesHelper createMockSharedPreference() {

        when(mMockSharedPreferences.getString(eq(SharedPreferencesHelper.KEY_NAME), anyString()))
                .thenReturn(mSharedPreferenceEntry.getName());

        when(mMockSharedPreferences.getString(eq(SharedPreferencesHelper.KEY_EMAIL), anyString()))
                .thenReturn(mSharedPreferenceEntry.getEmail());

        when(mMockSharedPreferences.getLong(eq(SharedPreferencesHelper.KEY_DOB), anyLong()))
                .thenReturn(mSharedPreferenceEntry.getDateOfBirth().getTimeInMillis());

        // 模拟写入时返回true
        when(mMockEditor.commit()).thenReturn(true);

        // 模拟的Editor
        when(mMockSharedPreferences.edit()).thenReturn(mMockEditor);

        return new SharedPreferencesHelper(mMockSharedPreferences);
    }

    /**
     * 创建模拟写入失败的SharedPreferences
     */
    private SharedPreferencesHelper createBrokenMockSharedPreference() {
        // 模拟写入时返回false
        when(mMockBrokenEditor.commit()).thenReturn(false);

        // 模拟的Editor
        when(mMockBrokenSharedPreferences.edit()).thenReturn(mMockBrokenEditor);

        return new SharedPreferencesHelper(mMockBrokenSharedPreferences);
    }
}
