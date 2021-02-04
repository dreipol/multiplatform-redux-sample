//
//  PresenterViewController.swift
//  Multiplatform Redux Sample
//
//  Created by Julia Strasser on 30.10.20.
//

import Foundation
import UIKit.UIViewController
import ReduxSampleShared

protocol Poppable: class {
    var poppingViaAction: Bool { get set }
}

typealias Presenter<V: View> = (View, CoroutineScope) -> (Store) -> () -> KotlinUnit

class PresenterViewController<V: View>: UIViewController, View, UIGestureRecognizerDelegate, Poppable {
    private let scrollView = UIScrollView.autoLayout()
    let vStack = UIStackView.autoLayout(axis: .vertical)

    var poppingViaAction = false
    private var inNavigationController = false

    init() {
        super.init(nibName: nil, bundle: nil)
        view.layoutMargins = .zero
        scrollView.showsVerticalScrollIndicator = false
        scrollView.addSubview(vStack)
        vStack.fitVerticalScrollView()
        vStack.layoutMargins = UIEdgeInsets(top: kUnit2, left: kUnit3, bottom: 0, right: kUnit3)
        vStack.isLayoutMarginsRelativeArrangement = true

        view.addSubview(scrollView)
        scrollView.fitSuperview()
        vStack.alignment = .fill
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    override var preferredStatusBarStyle: UIStatusBarStyle {
        return .darkContent
    }

    var viewPresenter: Presenter<V> {
            fatalError("This must me implemented in subclasses")
    }

    override func viewDidDisappear(_ animated: Bool) {
        super.viewDidDisappear(animated)
        PresenterInjectorKt.detachView(view: self)

        if !poppingViaAction, inNavigationController, isBeingDismissed || isMovingFromParent {
            _ = dispatch(NavigationAction.back)
        }
    }

    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        PresenterInjectorKt.attachView(view: self)

        inNavigationController = navigationController != nil
        navigationController?.interactivePopGestureRecognizer?.delegate = self
    }

    @objc func presenter() -> (View, CoroutineScope) -> (Store) -> () -> KotlinUnit {
        return viewPresenter
    }

    func gestureRecognizerShouldBegin(_ gestureRecognizer: UIGestureRecognizer) -> Bool {
        return V.self != SettingsView.self
    }
}
