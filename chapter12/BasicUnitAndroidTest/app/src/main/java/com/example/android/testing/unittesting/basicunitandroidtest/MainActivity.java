/*
 * Copyright 2015 Google Inc. All rights reserved.
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

package com.example.android.testing.unittesting.basicunitandroidtest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 允许用户添加多行日志,并显示时间戳
 */
public class MainActivity extends Activity {

    private static final String KEY_HISTORY_DATA = "KEY_HISTORY_DATA";

    private LogHistory mLogHistory;
    private boolean mIsHistoryEmpty = true;
    private TextView mHistoryTextView;
    private DateFormat mSimpleDateFormatter;

    /**
     * 当用户追加历史条目时被调用
     */
    public void updateHistory(View view) {
        EditText editText = (EditText) view.getRootView().findViewById(R.id.editText);
        // 获取文本
        CharSequence textToAdd = editText.getText();
        // 获取时间戳
        long timestamp = System.currentTimeMillis();

        // 添加记录
        appendEntryToView(textToAdd.toString(), timestamp);

        // 更新history
        mLogHistory.addEntry(textToAdd.toString(), timestamp);

        // 输入框重置为空
        editText.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLogHistory = new LogHistory();

        mHistoryTextView = ((TextView) findViewById(R.id.history));
        mSimpleDateFormatter = new SimpleDateFormat("HH:mm:ss:SSS", Locale.getDefault());

        if (savedInstanceState != null) {
            // 我们有过去的状态,将其应用到UI
            mLogHistory = savedInstanceState.getParcelable(KEY_HISTORY_DATA);
            for (Pair<String, Long> entry : mLogHistory.getData()) {
                appendEntryToView(entry.first, entry.second);
            }
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_HISTORY_DATA, mLogHistory);
    }

    /**
     * 添加记录
     * @param text 文本
     * @param timestamp 时间戳
     */
    private void appendEntryToView(String text, long timestamp) {
        Date date = new Date(timestamp);
        // 添加一个新的换行符或清空文本
        if (!mIsHistoryEmpty) {
            mHistoryTextView.append("\n");
        } else {
            mHistoryTextView.setText("");
        }
        mHistoryTextView.append(String.format("%s [%s]", text, mSimpleDateFormatter.format(date)));

        mIsHistoryEmpty = false;
    }
}
