package hu.bme.aut.android.mobilszoft;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.orm.SugarContext;

import hu.bme.aut.android.mobilszoft.ui.UIModule;
import io.fabric.sdk.android.Fabric;

public class MobilSzoftApplication extends Application {

	public static MobilSzoftApplicationComponent injector;

	private Tracker mTracker;

	/**
	 * Gets the default {@link Tracker} for this {@link Application}.
	 * @return tracker
	 */
	synchronized public Tracker getDefaultTracker() {
		if (mTracker == null) {
			GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
			// To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
			mTracker = analytics.newTracker(R.xml.global_tracker);
		}
		return mTracker;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Fabric.with(this, new Crashlytics());

		injector =
				DaggerMobilSzoftApplicationComponent.builder().
						uIModule(
								new UIModule(this)
						).build();

		SugarContext.init(this);
	}

	@Override
	public void onTerminate(){
        super.onTerminate();
        SugarContext.terminate();
	}
}
