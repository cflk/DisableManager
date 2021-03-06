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

package com.nagopy.android.disablemanager.util;

/**
 * アプリの情報を保持するクラス
 */
public class AppStatus {

	/**
	 * ラベル名
	 */
	private String label;

	/**
	 * パッケージ名
	 */
	private String packageName;

	/**
	 * 有効かどうか
	 */
	private boolean enabled;

	/**
	 * システムアプリかどうか
	 */
	private boolean system;

	/**
	 * 無効化が可能かどうか
	 */
	private boolean canDisable;

	/**
	 * 実行中のプロセスの情報
	 */
	private String process;

	/**
	 * コンストラクタ
	 * @param label
	 *           ラベル名
	 * @param packageName
	 *           パッケージ名
	 * @param enabled
	 *           有効かどうか
	 * @param system
	 *           システムアプリかどうか
	 * @param canDisable
	 *           無効化できるかどうか
	 */
	public AppStatus(String label, String packageName, boolean enabled, boolean system, boolean canDisable) {
		this.label = label;
		this.packageName = packageName;
		this.enabled = enabled;
		this.system = system;
		this.canDisable = canDisable;
	}

	/**
	 * @return ラベル名
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @return パッケージ名
	 */
	public String getPackageName() {
		return packageName;
	}

	/**
	 * @return 有効ならtrue、そうでなければfalse
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * 有効・無効のステータスを設定する
	 * @param enabled
	 *           有効かどうか
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return システムアプリならtrue、そうでなければfalse
	 */
	public boolean isSystem() {
		return system;
	}

	/**
	 * @return 無効化できるアプリならtrue、そうでなければfalse
	 */
	public boolean canDisable() {
		return canDisable;
	}

	@Override
	public String toString() {
		return getLabel() + ":" + getPackageName() + ", enabled:" + isEnabled() + ", system:" + isSystem()
				+ ", canDisable:" + canDisable();
	}

	/**
	 * 実行中のプロセスの情報を整形済みの文字列で取得する
	 * @return 実行中のプロセスの情報
	 */
	public String getProcessStrings() {
		return process;
	}

	/**
	 * 実行中のプロセスの情報を整形済みの文字列で設定
	 * @param processNames
	 *           実行中のプロセスの情報
	 */
	public void setProcessStrings(String processNames) {
		this.process = processNames;
	}
}
