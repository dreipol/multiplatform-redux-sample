//
//  MainViewController.swift
//  Multiplatform Redux Sample
//
//  Created by Samuel Bichsel on 30.10.20.
//

import UIKit.UITabBar
import ReduxSampleShared

class MainViewController: UITabBarController {
    private func setupViewControllers() {
        let vcs: [TabBarCompatible] =  [
            CalendarViewController(),
            InfoViewController(),
            SettingsViewController()
        ]
        tabBar.unselectedItemTintColor = UIColor.testAppBlue
        tabBar.tintColor = UIColor.testAppGreen
        tabBar.barTintColor = UIColor.white
        vcs.enumerated().forEach { i, vc in
            vc.setTabBarItem(tag: i)
        }
        viewControllers = vcs
    }

    init() {
        super.init(nibName: nil, bundle: nil)
        self.delegate = self
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    override func viewDidLoad() {
        super.viewDidLoad()
        setupViewControllers()
        view.backgroundColor = .testAppGreenLight
    }
}

extension MainViewController: UITabBarControllerDelegate {
    func tabBarController(_ tabBarController: UITabBarController, didSelect viewController: UIViewController) {
        switch tabBarController.selectedIndex {
        case 0:
            _ = dispatch(NavigationAction.calendar)
        case 1:
            _ = dispatch(NavigationAction.info)
        case 2:
            _ = dispatch(NavigationAction.settings)
        default:
            print("Unknown tabbar selected")
        }
    }
}
