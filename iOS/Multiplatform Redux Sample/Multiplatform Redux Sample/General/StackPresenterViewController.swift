//
//  StackPresenterViewController.swift
//  Multiplatform Redux Sample
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
