//
//  NavigationCoordinator.swift
//
//  Created by Samuel Bichsel on 23.07.20.
//  Copyright © 2020 dreipol GmbH. All rights reserved.
//

import Foundation
import ReduxSampleShared
import UIKit.UIApplication

class NavigationCoordinator: Navigator, Coordinator {

    func getNavigationState() -> NavigationState {
        return store.appState.navigationState
    }

    let store: Store

    lazy var onboardingCoordinator: OnboardingCoordinator = {
        OnboardingCoordinator(root: self)
    }()
    lazy var mainCoordinator: MainCoordinator = {
        MainCoordinator(root: self)
    }()

    var state: NavigationState {
        return getNavigationState()
    }

    var window: UIWindow?
    var windowStrong: UIWindow {
            guard let window = window else {
                fatalError("Window is nil")
            }
            return window
    }
    var rootViewController: UIViewController? {
        get { windowStrong.rootViewController }

        set {
            windowStrong.rootViewController = newValue
            windowStrong.makeKey()
        }
    }

    init(store: Store) {
        self.store = store
    }

    func setup(window: UIWindow?) {
        self.window = window
        NavigatorKt.subscribeNavigationState(self)
        updateNavigationState(navigationState: state)
    }

    func updateNavigationState(navigationState: NavigationState) {
        print(navigationState)
        switch navigationState.screens.last {
        case is OnboardingScreen:
            onboardingCoordinator.updateNavigationState(navigationState: navigationState)
        case is MainScreen:
            mainCoordinator.updateNavigationState(navigationState: navigationState)
        default:
            fatalError("Implement")
        }
    }
}
