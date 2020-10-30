//
//  DashboardViewController.swift
//  Multiplatform Redux Sample
//
//  Created by Samuel Bichsel on 30.10.20.
//

import UIKit.UITabBar

class DashboardViewController: UITabBarController {
    private func setupViewControllers() {
        let vcs: [TabBarCompatible] =  [
            CalendarViewController(),
            InfoViewController(),
            SettingsViewController()
        ]
        vcs.enumerated().forEach { i, vc in
            vc.setTabBarItem(tag: i)
        }
        viewControllers = vcs
    }

    init() {
        super.init(nibName: nil, bundle: nil)
        setupViewControllers()
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    }
}
