//
//  UIButton+Brand.swift
//  Multiplatform Redux Sample
//
//  Created by Samuel Bichsel on 09.03.21.
//

import UIKit.UIButton

extension UIButton {
    class func createLink() -> UIButton {
        let button = UIButton(type: .custom).autolayout()
        button.titleLabel?.font = .link()
        button.contentHorizontalAlignment = .left
        button.setTitleColor(.accentDarkAccent, for: .normal)
        button.setTitleColor(.accentAccent, for: .highlighted)

        return button
    }
}
