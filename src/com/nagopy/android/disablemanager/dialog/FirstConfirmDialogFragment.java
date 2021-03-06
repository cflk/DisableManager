/*
 * Copyright (C) 2013 75py
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

package com.nagopy.android.disablemanager.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

/**
 * 初回起動の確認ダイアログ<br>
 * バックキーによるキャンセルとダイアログ外側タップによるキャンセル無効化済み
 */
public class FirstConfirmDialogFragment extends ConfirmDialogFragment {

	/**
	 * 初回起動かを保存するためのキー
	 */
	private static final String KEY_IS_FIRST = "com.nagopy.android.disablemanager.dialog.FirstConfirmDialogFragment.KEY_IS_FIRST";

	/**
	 * @param context
	 *           コンテキスト
	 * @return 初回起動ならtrueを返す
	 */
	public static boolean isFirst(Context context) {
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
		return sp.getBoolean(KEY_IS_FIRST, true);
	}

	/**
	 * 次から表示しないようにする
	 * @param context
	 *           コンテキスト
	 * @return 保存に成功すればtrueを返す
	 */
	public static boolean setFlagOff(Context context) {
		return PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(KEY_IS_FIRST, false)
				.commit();
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final Dialog dialog = super.onCreateDialog(savedInstanceState);
		dialog.setCanceledOnTouchOutside(false);

		// バックキー無効化
		// これはダメだよ
		// dialog.setCancelable(false);
		setCancelable(false);

		return dialog;
	}
}
