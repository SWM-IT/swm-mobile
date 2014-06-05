package de.swm.commons.mobile.client.presenter;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import de.swm.commons.mobile.client.constants.I18NConstants;
import de.swm.commons.mobile.client.utils.ISwmPopupUtil;
import de.swm.commons.mobile.client.utils.SwmPopupUtilImpl;
import de.swm.commons.mobile.client.widgets.page.IPageWithoutHeader;
import de.swm.gwt.client.mobile.IPage;

import java.util.HashSet;
import java.util.Set;

/**
 * Controlls the currently active presenter. Will handle all page leave events.
 *
 * @author wiese.daniel
 *         <br>
 *         copyright (C) 2012, Stadtwerke München GmbH
 */
public class PresenterController {

	private final static PresenterController INSTANCE = new PresenterController();
	private final Set<IPageChangeListener> pageChangeListener = new HashSet<IPageChangeListener>();
	private IMobilePresenter activePresenter;
	private IPageWithoutHeader activeView;
	private ISwmPopupUtil swmPopupUtil = new SwmPopupUtilImpl();
	private final I18NConstants appConstants = GWT.create(I18NConstants.class);

	/**
	 * Private constructor - singelton
	 */
	private PresenterController() {
		//Utility constructor.
	}


	/**
	 * Will set the currently active presenter.
	 *
	 * @param activePresenter the persenter which is currenty active.
	 * @param activeView
	 */
	public void setActivePresenter(IMobilePresenter activePresenter, IPageWithoutHeader activeView) {
		if (this.activePresenter != null && !this.activePresenter.equals(activePresenter)) {
			for (IPageChangeListener iPageChangeListener : pageChangeListener) {
				iPageChangeListener.onPageChange(activePresenter, activeView);
			}
		}
		this.activePresenter = activePresenter;
		this.activeView = activeView;
	}

	/**
	 * Will add a page change listener.
	 *
	 * @param toAdd the page change listener to add.
	 */
	public void addPageChangeListener(IPageChangeListener toAdd) {
		if (toAdd != null) {
			pageChangeListener.add(toAdd);
		}
	}

	/**
	 * Will remove a page change listener.
	 *
	 * @param toRemove the page change listener to add.
	 */
	public void removePageChangeListener(IPageChangeListener toRemove) {
		if (toRemove != null) {
			pageChangeListener.remove(toRemove);
		}
	}

	/**
	 * Call this method if you want to leave a Page form another action then the back Action (e.g. Menu bar)
	 *
	 * @param result true if the controller of the current page allows the page leave action.
	 */
	public void beforePageLeave(AsyncCallback<Boolean> result) {
		if (activePresenter != null) {
			final LeavePageResponse leavePageResponse = activePresenter.leavePageRequest();
			switch (leavePageResponse) {
				case YES_LEAVE_PAGE:
					result.onSuccess(true);
					break;
				case NO_STAY_ON_CURRENT_PAGE:
					result.onSuccess(false);
					break;
				case DISPLAY_YES_NO_DIALOG:
					displayYesNoDialog(result);
			}
		} else {
			result.onSuccess(true);
		}
	}

	private void displayYesNoDialog(final AsyncCallback<Boolean> result) {
		ClickHandler okClickHanlder = new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (event != null) {
					event.stopPropagation();
				}
				result.onSuccess(true);
			}
		};
		ClickHandler cancelClickHanlder = new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (event != null) {
					event.stopPropagation();
				}
				result.onSuccess(false);
			}
		};
		swmPopupUtil.showPopupYesNo(true, appConstants.leavePageQuestionHeader(), appConstants.leavePageQuestionText(), okClickHanlder, cancelClickHanlder);
	}


	public static PresenterController get() {
		return INSTANCE;
	}


	/**
	 * Returns the active View.
	 *
	 * @return thhe active view
	 */
	public IPage getActiveView() {
		if (activeView != null) {
			return activeView.getView();
		}

		return null;
	}

	/**
	 * Returns the active Page.
	 *
	 * @return thhe active Page
	 */
	public IPageWithoutHeader getActivePage() {
		return activeView;
	}

	/**
	 * Returns the active presenter.
	 *
	 * @return thhe active presenter
	 */
	public IMobilePresenter getActivePresenter() {
		return activePresenter;
	}


	/**
	 * Lister which allows a applictionlisten to Page change events.
	 */
	public static interface IPageChangeListener {

		/**
		 * Will be fired on a page change event.
		 *
		 * @param activePresenter the  presenter which will become active
		 * @param activeView      the page which will become active
		 */
		void onPageChange(IMobilePresenter activePresenter, IPageWithoutHeader activeView);
	}
}
