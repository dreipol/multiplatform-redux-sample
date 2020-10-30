//
//  TabBarCompatible.swift
//  Multiplatform Redux Sample
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
