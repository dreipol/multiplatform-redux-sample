//
//  TabBarCompatible.swift
//  Multiplatform Redux Sample
//
//  Copyright (c) 2021 dreipol GmbH
//
//  Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
//  No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
//
//  Created by Samuel Bichsel on 30.10.20.
//

import Foundation
import UIKit.UIImage

protocol TabBarCompatible: UIViewController {
    var tabBarImageName: String { get }
}

extension TabBarCompatible {
    private var tabBarImage: UIImage? {
        return UIImage(named: tabBarImageName)
    }

    func setTabBarItem(tag: Int) {
        self.tabBarItem = UITabBarItem(title: nil, image: self.tabBarImage, tag: tag)
    }
}
