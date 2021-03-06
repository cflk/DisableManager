package com.nagopy.android.disablemanager.util;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.test.AndroidTestCase;

import com.nagopy.android.disablemanager.R;

public class AppsLoaderTest extends AndroidTestCase {

	private AppsLoader mAppsLoader;
	private ArrayList<AppStatus> runningAppsList, allAppsList;
	private HashMap<String, Drawable> runningAppsIcon, allAppsIcon;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		mAppsLoader = new AppsLoader(getContext());

		loadAllApps();

		loadRunningApps();

	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	@SuppressWarnings("unchecked")
	private void loadAllApps() throws Exception {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
		sharedPreferences.edit()
				.putBoolean(getContext().getString(R.string.pref_key_general_show_only_running_packages), false)
				.commit();
		allAppsIcon = mAppsLoader.load();
		assertNotNull("アイコンキャッシュがnullでない", allAppsIcon);
		assertTrue("アイコンキャッシュがひとつ以上ある", !allAppsIcon.isEmpty());

		allAppsList = (ArrayList<AppStatus>) mAppsLoader.getAppsList().clone();
		assertNotNull("アプリ一覧がnullでない", allAppsList);
		assertTrue("アプリ一覧が空ではない", !allAppsList.isEmpty());
	}

	private void loadRunningApps() throws Exception {
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
		sharedPreferences.edit()
				.putBoolean(getContext().getString(R.string.pref_key_general_show_only_running_packages), true)
				.commit();
		runningAppsIcon = mAppsLoader.load();
		assertNotNull("アイコンキャッシュがnullでない", runningAppsIcon);
		assertTrue("アイコンキャッシュがひとつ以上ある", !runningAppsIcon.isEmpty());

		runningAppsList = mAppsLoader.getAppsList();
		assertNotNull("アプリ一覧がnullでない", runningAppsList);
		assertTrue("アプリ一覧が空ではない", !runningAppsList.isEmpty());
	}

	public void test_loadRunningApps_Foreground() throws Exception {
		AppStatus appStatusThisApp = null;
		for (AppStatus appStatus : runningAppsList) {
			if (appStatus.getPackageName().equals(getContext().getPackageName())) {
				appStatusThisApp = appStatus;
				break;
			}
		}
		assertNotNull("実行中一覧にこのアプリが含まれているか", appStatusThisApp);
		assertEquals("このアプリのステータスがフォアグラウンドになっているか", "[Foreground]", appStatusThisApp.getProcessStrings());
		assertTrue("このアプリのアイコンがキャッシュされている", runningAppsIcon.containsKey(getContext().getPackageName()));
	}

	public void test_loadRunningApps_Perceptible() throws Exception {
		AppStatus appStatusBC = null;
		String pkgName = "com.nagopy.bashfulclock";
		for (AppStatus appStatus : runningAppsList) {
			if (appStatus.getPackageName().equals(pkgName)) {
				appStatusBC = appStatus;
				break;
			}
		}
		assertNotNull("Bashful Clockが実行中に含まれているか", appStatusBC);
		assertEquals("Bashful Clockのステータスがperceptibleか", pkgName + " [Perceptible]",
				appStatusBC.getProcessStrings());
		assertTrue("このアプリのアイコンがキャッシュされている", runningAppsIcon.containsKey(pkgName));
	}

	public void test_loadRunningApps_Visible() throws Exception {
		AppStatus appStatusGSF = null;
		String pkgName = "com.google.process.gapps";
		for (AppStatus appStatus : runningAppsList) {
			if (appStatus.getPackageName().equals(pkgName)) {
				appStatusGSF = appStatus;
				break;
			}
		}
		assertNotNull("Googleサービスフレームワークが含まれているか", appStatusGSF);
		assertEquals("GoogleサービスフレームワークがVisibleになっているか", pkgName + " [Visible]",
				appStatusGSF.getProcessStrings());
		assertTrue("このアプリのアイコンがキャッシュされている", runningAppsIcon.containsKey(pkgName));
	}

	public void test_loadRunningApps_Service() throws Exception {
		String pkgName = "android.process.media";
		AppStatus appStatusMedia = null;
		for (AppStatus appStatus : runningAppsList) {
			if (appStatus.getPackageName().equals(pkgName)) {
				appStatusMedia = appStatus;
				break;
			}
		}
		assertNotNull("メディアストレージが含まれているか", appStatusMedia);
		assertEquals("メディアストレージがserviceになっているか", pkgName + " [Service]", appStatusMedia.getProcessStrings());
		assertTrue("このアプリのアイコンがキャッシュされている", runningAppsIcon.containsKey(pkgName));
	}

	public void test_loadRunningApps_Background() throws Exception {
		AppStatus appStatusGooglePlay = null;
		String pkgName = "com.android.settings";
		for (AppStatus appStatus : runningAppsList) {
			if (appStatus.getPackageName().equals(pkgName)) {
				appStatusGooglePlay = appStatus;
				break;
			}
		}
		assertNotNull("Google Playが含まれているか", appStatusGooglePlay);
		assertEquals("Google PlayがBackgroundになっているか", pkgName + " [Background]",
				appStatusGooglePlay.getProcessStrings());
		assertTrue("このアプリのアイコンがキャッシュされている", runningAppsIcon.containsKey(pkgName));
	}

	public void testアイコンキャッシュの確認_all() throws Exception {
		ArrayList<String> errors = new ArrayList<String>();
		for (AppStatus appStatus : allAppsList) {
			if (allAppsIcon.get(appStatus.getPackageName()) == null) {
				errors.add(appStatus.getPackageName());
			}
		}
		assertTrue(errors.toString(), errors.isEmpty());
	}

	public void testアイコンキャッシュの確認_running() throws Exception {
		ArrayList<String> errors = new ArrayList<String>();
		for (AppStatus appStatus : runningAppsList) {
			if (runningAppsIcon.get(appStatus.getPackageName()) == null) {
				errors.add(appStatus.getPackageName());
			}
		}
		assertTrue(errors.toString(), errors.isEmpty());
	}

	public void test数の確認() throws Exception {
		assertTrue("すべてのアプリ：" + allAppsList.size() + " 実行中：" + runningAppsList.size(),
				allAppsList.size() > runningAppsList.size());
		assertTrue("すべてのアプリのアイコン：" + allAppsIcon.size() + " 実行中：" + runningAppsIcon.size(),
				allAppsIcon.size() > runningAppsIcon.size());
	}

	// 良いテストが思いつかん
	// public void testUpdateStatus() throws Exception {
	// }
}
