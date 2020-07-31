package hu.bme.aut.android.mobilszoft.ui;

import android.content.Context;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import hu.bme.aut.android.mobilszoft.di.Network;
import hu.bme.aut.android.mobilszoft.ui.conversations.ConversationsPresenter;
import hu.bme.aut.android.mobilszoft.ui.login.LoginPresenter;
import hu.bme.aut.android.mobilszoft.ui.messages.MessagesPresenter;

@Module
public class UIModule {
	private Context context;

	public UIModule(Context context) {
		this.context = context;
	}

	@Provides
	public Context provideContext() {
		return context;
	}

	@Provides
	@Singleton
	public LoginPresenter provideLoginPresenter() {
		return new LoginPresenter();
	}

	@Provides
	@Singleton
	public ConversationsPresenter provideConversationsPresenter() {
		return new ConversationsPresenter();
	}

	@Provides
	@Singleton
	public MessagesPresenter provideMessagesPresenter() {
		return new MessagesPresenter();
	}

	@Provides
	@Singleton
	@Network
	public Executor provideNetworkExecutor() {
		return Executors.newFixedThreadPool(1);
	}
}
