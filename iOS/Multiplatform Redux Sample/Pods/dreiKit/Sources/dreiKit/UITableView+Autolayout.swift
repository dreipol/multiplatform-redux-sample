//
//  UITableView+Tools.swift
//  dreiKit
//
//  Created by Samuel Bichsel on 21.11.19.
//  Copyright © 2019 dreipol. All rights reserved.
//

import Foundation
import UIKit.UITableView

public extension UITableView {
    class func autoLayout(style: UITableView.Style = .plain) -> Self {
        let view = Self(frame: .zero, style: style)
        view.translatesAutoresizingMaskIntoConstraints = false
        return view
    }
}
