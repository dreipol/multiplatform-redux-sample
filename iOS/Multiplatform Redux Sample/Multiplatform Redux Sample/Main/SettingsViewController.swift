//
//  SettingsViewController.swift
//  Multiplatform Redux Sample
//
//  Created by Samuel Bichsel on 30.10.20.
//

import UIKit

class SettingsViewController: UIViewController {
    override func viewDidLoad() {
        super.viewDidLoad()
    }
}

extension SettingsViewController: TabBarCompatible {
    var tabBarImageName: String { "iconIc40Settings" }
}
