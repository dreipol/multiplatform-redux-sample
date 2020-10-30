//
//  OnbooardingCoordinator.swift
//  Cassie
//
//  Created by Samuel Bichsel on 24.07.20.
//  Copyright © 2020 dreipol GmbH. All rights reserved.
//

import Foundation
import UIKit.UINavigationController
import ReduxSampleShared

class OnboardingCoordinator: NSObject, Coordinator {
    weak var rootCoordinator: NavigationCoordinator?

    init(root: NavigationCoordinator) {
        rootCoordinator = root
    }

    func updateNavigationState(navigationState: NavigationState) {
        guard navigationState.navigationDirection == NavigationDirection.push else {
            return
        }

//        guard let root = rootCoordinator else {
//            fatalError("rootCoordinator is not set")
//        }

//        let rootVC = root.rootViewController
//        let navigationController: OnboardingNavigationController
//
//        if rootVC is OnboardingNavigationController {
//            // swiftlint:disable:next force_cast
//            navigationController = rootVC as! OnboardingNavigationController
//        } else {
//            navigationController = OnboardingNavigationController()
//            navigationController.delegate = self
//            root.rootViewController = navigationController
//        }
//
//        navigationController.pushViewController(OnboardingCardViewController(), animated: true)
    }
}

extension OnboardingCoordinator: UINavigationControllerDelegate {
    func navigationController(_ navigationController: UINavigationController,
                              animationControllerFor operation: UINavigationController.Operation,
                              from fromVC: UIViewController,
                              to toVC: UIViewController) -> UIViewControllerAnimatedTransitioning? {
        if operation == .pop {
            _ = dispatch(NavigationAction.back)
        }
        return nil
    }
}
