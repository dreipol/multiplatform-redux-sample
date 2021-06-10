//
//  BasePresenterViewController.swift
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

protocol Poppable: AnyObject {
    var navigateBackThroughAction: Bool { get set }
}

typealias Presenter<V: View> = (View, CoroutineScope) -> (Store) -> () -> KotlinUnit

class BasePresenterViewController<V: View>: UIViewController, View, UIGestureRecognizerDelegate, Poppable {
    let scrollView = UIScrollView.autoLayout()

    var navigateBackThroughAction = false
    private var inNavigationController = false

    init() {
        super.init(nibName: nil, bundle: nil)
        view.layoutMargins = .zero
        scrollView.showsVerticalScrollIndicator = false
        view.addSubview(scrollView)
        scrollView.fitSuperview()
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

        if !navigateBackThroughAction, inNavigationController, isBeingDismissed || isMovingFromParent {
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
