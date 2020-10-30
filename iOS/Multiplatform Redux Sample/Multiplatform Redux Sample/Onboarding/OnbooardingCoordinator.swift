//
//  OnbooardingCoordinator.swift
//  Cassie
//
//  Created by Samuel Bichsel on 24.07.20.
//  Copyright © 2020 dreipol GmbH. All rights reserved.
//

import Foundation
import UIKit.UIPageViewController
import ReduxSampleShared

class OnboardingCoordinator: SubCoordinator, Coordinator {
    func updateNavigationState(navigationState: NavigationState) {
        guard navigationState.navigationDirection == NavigationDirection.push else {
            return
        }

        if !(rootCoordinator.rootViewController is OnboardingCardViewController) {
            let viewController = OnboardingCardViewController(transitionStyle: .scroll,
                                                                   navigationOrientation: .horizontal,
                                                                   options: nil)
            viewController.delegate = self
            rootCoordinator.rootViewController = viewController
        }
    }
}

extension OnboardingCoordinator: UIPageViewControllerDelegate {
//    TODO: Update state
}
