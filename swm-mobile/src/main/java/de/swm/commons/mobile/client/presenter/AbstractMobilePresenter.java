/*
 * Copyright 2011 SWM Services GmbH.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package de.swm.commons.mobile.client.presenter;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HasWidgets;
import de.swm.commons.mobile.client.page.SimplePage;
import de.swm.commons.mobile.client.page.Transition;
import de.swm.commons.mobile.client.utils.AsyncCallbackSuccess;
import de.swm.commons.mobile.client.widgets.LoadingIndicatorPopup;
import de.swm.commons.mobile.client.widgets.itf.ISpinnerStarted;
import de.swm.commons.mobile.client.widgets.page.IMainView;
import de.swm.commons.mobile.client.widgets.page.IPageWithHeader;
import de.swm.commons.mobile.client.widgets.page.IPageWithoutHeader;
import de.swm.gwt.client.eventbus.IDispatcher;
import de.swm.gwt.client.eventbus.IEvent;
import de.swm.gwt.client.eventbus.MobileEvent;
import de.swm.gwt.client.mobile.Direction;
import de.swm.gwt.client.mobile.IPage;
import de.swm.gwt.client.mobile.ITransitionCompletedCallback;

import java.util.logging.Logger;

/**
 * Abstract presenter class.
 */
public abstract class AbstractMobilePresenter implements IMobilePresenter {

	private static final Logger LOGGER = Logger.getLogger(AbstractMobilePresenter.class.getName());

	private static final LoadingIndicatorPopup spinner = new LoadingIndicatorPopup();

	/**
	 * The global content area is null or contains a fixed toolbar on the button of the page. *
	 */
	private static HasWidgets globalContentArea;

	private static Direction nextTransitionDirection = Direction.RIGHT;

	private static IPageWithoutHeader lastDisplayedPage = null;

	/**
	 * True if every page transition should have an spinner page.
	 */
	private static boolean showSpinnerBetweenTransitions = false;

	private static boolean isSpinnerStarted = false;

	/**
	 * do not allow a transition if another one is already running. *
	 */
	private static boolean transitionIsRunning = false;

	private static boolean allTransitionsDisabled = false;

	private final IDispatcher dispatcher;

	private final EventBus eventBus;

	private boolean transitionsFromMainViewDisabled = false;

	private boolean isVisible = false;

	private IEvent leaveToEvent;

	/**
	 * Default constructor.
	 *
	 * @param disp der dispatcher.
	 */
	public AbstractMobilePresenter(IDispatcher disp) {
		this.eventBus = null;
		this.dispatcher = disp;
	}

	/**
	 * Dispatcher mit evnent Bus.
	 *
	 * @param eventBus der event bus
	 */
	public AbstractMobilePresenter(EventBus eventBus) {
		this.dispatcher = null;
		this.eventBus = eventBus;
	}

	/**
	 * Liefert die richtung der naechsten Transition. Dieses ist normalerweise recht. Falls ein Back-Navigatiosnevent
	 * vorgemerkt ist ist es ausnahmsweise rechts.
	 *
	 * @return die Richtung des naechsten ueberganges.
	 */
	public static Direction getNextTransitionDirection() {
		if(!nextTransitionDirection.equals(Direction.RIGHT)) {
			Direction toReturn = nextTransitionDirection;
			nextTransitionDirection = Direction.RIGHT;
			return toReturn;
		}
		return nextTransitionDirection;
	}

	/**
	 * Enabled or disabled the spinner between transitions.
	 *
	 * @param showSpinnerBetweenTransitions true if the spinner should be shown between transitions.
	 */
	public static void setShowSpinnerBetweenTransitions(boolean showSpinnerBetweenTransitions) {
		AbstractMobilePresenter.showSpinnerBetweenTransitions = showSpinnerBetweenTransitions;
	}

	/**
	 * Return value of transitionsFromMainViewDisabled (see {@link #setAllTransitionsDisabled(boolean)} ).
	 *
	 * @return value
	 */
	public static boolean isAllTransitionsDisabled() {
		return allTransitionsDisabled;
	}

	/**
	 * If set to {code}true{code}, no transition effect is performed when page switch originates from ANY view
	 * (i.e., in {@link #gotoPage(IPage, IPageWithoutHeader)
	 *
	 * @param allTransitionsDisabled setting of allTransitionsDisabled
	 */
	public static void setAllTransitionsDisabled(boolean allTransitionsDisabled) {
		AbstractMobilePresenter.allTransitionsDisabled = allTransitionsDisabled;
	}

	@Override
	public IDispatcher getDispatcher() {
		return dispatcher;
	}

	@Override
	public LeavePageResponse leavePageRequest() {
		return LeavePageResponse.YES_LEAVE_PAGE;
	}

	/**
	 * Liefert rue wenn der vom Presenter kontollierte View sichtbar ist.
	 *
	 * @return true wenn sichtbar.
	 */
	@Override
	public boolean isOwningViewVisible() {
		return isVisible;
	}

	/**
	 * Generic method to switch to another page. Shortcut for {code}gotoPage(originator, targetView, true, null, null){code}.
	 *
	 * @param originator the view which the event to switch the page originated from
	 * @param targetView view to switch to (usually the view of the current presenter)
	 */
	@Override
	public void gotoPage(final IPage originator, final IPageWithoutHeader targetView) {
		if(transitionsFromMainViewDisabled) {
			gotoPage(originator, targetView, !(originator instanceof IMainView), null, null);
		} else {
			gotoPage(originator, targetView, true, null, null);
		}
	}

	/**
	 * Generic method to switch to another page.
	 *
	 * @param originator          the view which the event to switch the page originated from
	 * @param targetView          view to switch to (usually the view of the current presenter)
	 * @param automaticTransition whether the transition effect should be determined automatically
	 *                            (parameters transition, transitionDirection are ignored)
	 * @param transition          transition effect ({code}null{code} for no transition effect)
	 * @param transitionDirection transition direction
	 */
	@Override
	public void gotoPage(final IPage originator, final IPageWithoutHeader targetView, boolean automaticTransition,
						 Transition transition, Direction transitionDirection) {
		isVisible = true;
		PresenterController.get().setActivePresenter(this, targetView);
		//if a page is shown but next transisition event is used with wrong originator
		if(lastDisplayedPage != null && lastDisplayedPage.getView() != originator) {
			lastDisplayedPage.getView().beforeLeaving();
		}
		lastDisplayedPage = targetView;
		setRootContentArea(originator, targetView);
		if(globalContentArea != null) {
			targetView.getView().setParent(globalContentArea);
			originator.setParent(globalContentArea);
		}

		// keine transition wenn Main-Wiew
		if(!transitionIsRunning && originator != null) {
			transitionIsRunning = true;
			//Callback for transition end
			final ITransitionCompletedCallback transitionCompletedCallback = new ITransitionCompletedCallback() {
				@Override
				public void isCompleted() {
					LOGGER.info("Transition to Page (" + targetView.getView().getName() + ") completed");
					stopSpinnerIfRunning();
					transitionIsRunning = false;
				}
			};

			if(allTransitionsDisabled) {
				if(originator instanceof SimplePage) {
					((SimplePage) originator).goTo(targetView.getView(), null, null, transitionCompletedCallback);
				} else {
					//Fallback if originator is not simple page
					originator.goTo(targetView.getView(), null, transitionCompletedCallback);
				}
			} else if(automaticTransition) {
				originator.goTo(targetView.getView(), getNextTransitionDirection(), transitionCompletedCallback);
			} else if(originator instanceof SimplePage) {
				((SimplePage) originator).goTo(targetView.getView(), transition, transitionDirection, transitionCompletedCallback);
			} else {
				originator.goTo(targetView.getView(), transitionDirection, transitionCompletedCallback);
			}
		} else if(!transitionIsRunning) {
			// es gibt keinen originator
			Scheduler.get().scheduleDeferred(new ScheduledCommand() {
				@Override
				public void execute() {
					SimplePage.load(targetView.getView());
					stopSpinnerIfRunning();
					transitionIsRunning = false;
				}
			});
			transitionIsRunning = true;
		} else {
			LOGGER.info("Cannot perform transition to page (" + targetView.getView().getName() + "), because other transition is still running.");
		}

	}

	/**
	 * Generische trasitionsmethode fuer den Seitenwechsel.
	 *
	 * @param targetView die view des aktuellen Presenters (Ziel) - als Content area des Ziels wird Root gesetzt.
	 */
	@Override
	public void gotoPageWithRootAsContentArea(final IPageWithoutHeader targetView) {
		PresenterController.get().setActivePresenter(this, targetView);
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				SimplePage.load(targetView.getView());
				transitionIsRunning = false;
			}
		});
		transitionIsRunning = true;

	}

	/**
	 * Generische trasitionsmethode fuer den Seitenwechsel.
	 *
	 * @param newRootContentArea new root content area. All new sceens will be places as chidlern of this area.
	 * @param startupContent     view which should be deplayed as content of the new root panel
	 */
	public void setRootContentArea(final IPage newRootContentArea, final IPageWithoutHeader startupContent) {
		// falls das Ursprungs-Panel das Main-View ist die content area wechseln
		if(globalContentArea == null && newRootContentArea != null && newRootContentArea instanceof IMainView) {
			globalContentArea = ((IMainView) newRootContentArea).toolbarPannel().getContentArea();
			globalContentArea.add(startupContent.getView().asComposite());
		}
	}

	public void fireMobileEvent(MobileEvent eventToFire, final IPage originatorPage) {
		if(eventBus != null) {
			eventToFire.setOriginatorPage(originatorPage);
			eventBus.fireEvent(eventToFire);
		}
	}

	/**
	 * Will create a click hanlder, which will automatically crerate a loading indicator when clicked (if loadimng indicator is globally enabled)
	 *
	 * @param toWrap the callback to wrap
	 * @return the wrapped click handler.
	 */
	public ClickHandler createLoadingIndicatorClickHandler(final ClickHandler toWrap) {
		final ClickHandler result = new ClickHandler() {
			@Override
			public void onClick(final ClickEvent event) {
				if(!isSpinnerStarted) {
					startPopup(new ISpinnerStarted() {
						@Override
						public void spinnerStarted() {
							toWrap.onClick(event);
							stopSpinnerIfRunning();
						}
					});
				}

			}
		};

		return result;
	}

	/**
	 * Returns true if this screen is active.
	 *
	 * @return true if this screen is active.
	 */
	public boolean isScreenActive() {
		if(PresenterController.get().getActivePresenter() != null) {
			return PresenterController.get().getActivePresenter().equals(this);
		}
		return false;
	}

	/**
	 * Return value of transitionsFromMainViewDisabled (see {@link #setTransitionsFromMainViewDisabled(boolean)).
	 *
	 * @return value
	 */
	public boolean isTransitionsFromMainViewDisabled() {
		return transitionsFromMainViewDisabled;
	}

	/**
	 * If set to {code}true{code}, no transition effect is performed when page switch originates from main view (i.e., in {@link #gotoPage(IPage, IPageWithoutHeader) the
	 * originator is an instance of {@link IMainView}). I.E., if the main view is a tab panel, direct navigation via the tab header does not
	 * lead to a transition effect.
	 *
	 * @param transitionsFromMainViewDisabled setting of transitionsFromMainViewDisabled
	 */
	public void setTransitionsFromMainViewDisabled(
			boolean transitionsFromMainViewDisabled) {
		this.transitionsFromMainViewDisabled = transitionsFromMainViewDisabled;
	}

	public IEvent getLeaveToEvent() {
		return leaveToEvent;
	}

	public void setLeaveToEvent(IEvent leaveToEvent) {
		this.leaveToEvent = leaveToEvent;
	}

	/**
	 * Created and adds a click handler for the back button. When the back button will be clicked the passed event will
	 * be fired (which should display the new page). ClickHandler does not show a spinner.
	 *
	 * @param myView      currently displayed view.
	 * @param eventToFire the event to fire after the back button will be clicked.
	 * @return the created internal click handler (e.g. to fire the event manually)
	 */
	protected ClickHandler addBackButtonNavigationHandlers(final IPageWithHeader myView, final IEvent eventToFire) {
		return addBackButtonNavigationHandlers(myView, eventToFire, false);
	}

	/**
	 * Created and adds a click handler for the back button. When the back button will be clicked the passed event will
	 * be fired (which should display the new page). Optional showing of a spinner.
	 *
	 * @param myView      currently displayed view.
	 * @param eventToFire the event to fire after the back button will be clicked.
	 * @param showSpinner Flag if spinner should be shown
	 * @return the created internal click handler (e.g. to fire the event manually)
	 */
	protected ClickHandler addBackButtonNavigationHandlers(final IPageWithHeader myView, final IEvent eventToFire, boolean showSpinner) {
		ClickHandler backClickHandler = null;
		if(myView.getHeader() != null) {
			if(showSpinner) {
				backClickHandler = new ClickHandler() {
					@Override
					public void onClick(ClickEvent clickEvent) {
						leaveToEvent = eventToFire;
						if(!isSpinnerStarted) {
							startPopup(new ISpinnerStarted() {
								@Override
								public void spinnerStarted() {
									PresenterController.get().beforePageLeave(new AsyncCallbackSuccess<Boolean>() {

										@Override
										public void onSuccess(Boolean result) {
											if(result) {
												if(!transitionIsRunning) {
													beforeBack();
													LOGGER.info("History Back Event >" + eventToFire.eventName());
													fireBackEvent(eventToFire, myView);
												} else {
													LOGGER.info("History Back Event > NO Action because a transition is in Progress");
												}
											}
										}
									});
									stopSpinnerIfRunning();
								}
							});
						}
					}
				};
			} else {
				backClickHandler = new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						leaveToEvent = eventToFire;
						PresenterController.get().beforePageLeave(new AsyncCallbackSuccess<Boolean>() {

							@Override
							public void onSuccess(Boolean result) {
								if(result) {
									if(!transitionIsRunning) {
										beforeBack();
										LOGGER.info("History Back Event >" + eventToFire.eventName());
										fireBackEvent(eventToFire, myView);
									} else {
										LOGGER.info("History Back Event > NO Action because a transition is in Progress");
									}
								}
							}
						});

					}
				};
			}
			myView.getHeader().setLeftButtonClickHandler(backClickHandler);
		}
		return backClickHandler;
	}

	/**
	 * Created and adds a click handler for the back button. When the back button will be clicked the passed callback
	 * will be executed.
	 *
	 * @param myView   currently displayed view.
	 * @param callback the callback executed after the back button will be clicked.
	 * @return the created internal click handler (e.g. to fire the event manually)
	 */
	protected ClickHandler addBackButtonNavigationHandlers(final IPageWithHeader myView,
														   final IBackCallback callback) {
		if(myView.getHeader() != null) {
			ClickHandler backClickHandler = new ClickHandler() {

				@Override
				public void onClick(final ClickEvent event) {
					leaveToEvent = null;
					PresenterController.get().beforePageLeave(new AsyncCallbackSuccess<Boolean>() {
						@Override
						public void onSuccess(Boolean result) {
							if(result) {
								if(!transitionIsRunning) {
									beforeBack();
									if(showSpinnerBetweenTransitions) {
										startPopup(new ISpinnerStarted() {
											@Override
											public void spinnerStarted() {
												callback.onBack(event);
											}
										});
									} else {
										callback.onBack(event);
									}
								} else {
									LOGGER.info("History Back Event > NO Action because a transition is in Progress");
								}
							}
						}
					});

				}
			};

			myView.getHeader().setLeftButtonClickHandler(backClickHandler);
			return backClickHandler;
		}

		return null;

	}

	/**
	 * Will be called, before back action is executed.
	 */
	protected void beforeBack() {
		isVisible = false;
		nextTransitionDirection = Direction.LEFT;
	}

	/**
	 * Fires the back event, defined for the back button
	 *
	 * @param eventToFire the event to fire
	 * @param myView      my view.
	 */
	private void fireBackEvent(final IEvent eventToFire, final IPageWithHeader myView) {
		if(showSpinnerBetweenTransitions) {
			startPopup(new ISpinnerStarted() {
				@Override
				public void spinnerStarted() {
					fireEvent(eventToFire, myView.getView());
				}
			});
		} else {
			fireEvent(eventToFire, myView.getView());
		}
	}

	private void fireEvent(final IEvent eventToFire, final IPage page) {
		if(this.dispatcher != null) {
			dispatcher.fireMobileEvent(eventToFire, page);
		} else if(eventBus != null && eventToFire instanceof MobileEvent) {
			fireMobileEvent((MobileEvent)eventToFire, page);
		}
	}

	/**
	 * Starts a propgress bar.
	 *
	 * @param spinnerStarted spinner is started
	 * @return the loading indicator.
	 */
	private LoadingIndicatorPopup startPopup(final ISpinnerStarted spinnerStarted) {
		spinner.showCentered(true);
		final Timer timer = new Timer() {

			@Override
			public void run() {
				isSpinnerStarted = true;
				spinnerStarted.spinnerStarted();
			}
		};
		timer.schedule(50);
		return spinner;
	}

	/**
	 * Will hide a loading indicator popup.
	 */
	private void stopSpinnerIfRunning() {
		if(isSpinnerStarted) {
			isSpinnerStarted = false;
			spinner.setVisible(false);
		}
	}

	/**
	 * Returns {code}true{code} if a transition is currently running (in this case, goToPage() has no effect).
	 *
	 * @return {code}true{code} if a transition is currently running
	 */
	protected static boolean getTransitionIsRunning() {
		return transitionIsRunning;
	}

	/**
	 * Callback, when the back button is clicked.
	 */
	public static interface IBackCallback {
		void onBack(ClickEvent clickEvent);
	}
}
