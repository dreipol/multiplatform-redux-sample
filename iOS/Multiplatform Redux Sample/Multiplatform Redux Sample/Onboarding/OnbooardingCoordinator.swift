//
//  OnbooardingCoordinator.swift
//  Cassie
//
//  Created by Samuel Bichsel on 24.07.20.
//  Copyright © 2020 dreipol GmbH. All rights reserved.
//

import Foundation
import UIKit.UIPageViewController
import rezhycleShared

class OnboardingCoordinator: SubCoordinator, Coordinator {

    let viewController = OnboardingCardViewController(transitionStyle: .scroll,
                                                           navigationOrientation: .horizontal,
                                                           options: nil)

    func updateNavigationState(navigationState: NavigationState) {
        if !(rootCoordinator.rootViewController is OnboardingCardViewController) {
            rootCoordinator.rootViewController = viewController
        } else {
            viewController.setCurrentPage(screen: navigationState.currentScreen)
        }
    }
}
