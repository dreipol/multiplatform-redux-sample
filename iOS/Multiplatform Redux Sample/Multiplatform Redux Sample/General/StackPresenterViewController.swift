//
//  StackPresenterViewController.swift
//  Multiplatform Redux Sample
//
//  Copyright (c) 2021 dreipol GmbH
//
//  Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
//  No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
//
//  Created by Julia Strasser on 30.10.20.
//

import Foundation
import UIKit.UIViewController
import ReduxSampleShared

class StackPresenterViewController<V: View>: BasePresenterViewController<V> {
    let vStack = UIStackView.autoLayout(axis: .vertical)

    override init() {
        super.init()

        scrollView.addSubview(vStack)
        vStack.fitVerticalScrollView()
        vStack.layoutMargins = UIEdgeInsets(top: kUnit2, left: kUnit3, bottom: 0, right: kUnit3)
        vStack.isLayoutMarginsRelativeArrangement = true
        vStack.alignment = .fill
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}
