//
//  MainViewController.swift
//  Multiplatform Redux Sample
//
//  Created by Samuel Bichsel on 30.10.20.
//

import UIKit.UITabBar
import ReduxSampleShared

class MainViewController: UITabBarController {
    private static let tabBarItemInset = UIEdgeInsets(top: 6.0, left: 0, bottom: -6.0, right: 0)
    init() {
        super.init(nibName: nil, bundle: nil)
        self.delegate = self
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    private func setupViewControllers() {
        let vcs: [TabBarCompatible] =  [
            CalendarViewController(),
            InfoViewController(),
            SettingsViewController()
        ]
        setupTabBarAppearance()

        vcs.enumerated().forEach { i, vc in
            vc.setTabBarItem(tag: i)
            vc.tabBarItem.imageInsets = Self.tabBarItemInset
        }

        viewControllers = vcs
    }

    private static func setItemAppearance(_ itemAppearance: UITabBarItemAppearance) {
        itemAppearance.normal.iconColor = .testAppBlue
        itemAppearance.selected.iconColor = .testAppGreen

        itemAppearance.normal.titleTextAttributes = [.foregroundColor: UIColor.clear]
        itemAppearance.selected.titleTextAttributes = [.foregroundColor: UIColor.clear]
    }

    private func setupTabBarAppearance() {
        let appearance = UITabBarAppearance()
        Self.setItemAppearance(appearance.stackedLayoutAppearance)
        Self.setItemAppearance(appearance.inlineLayoutAppearance)
        Self.setItemAppearance(appearance.compactInlineLayoutAppearance)

        appearance.backgroundColor = .white
        tabBar.standardAppearance = appearance
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
