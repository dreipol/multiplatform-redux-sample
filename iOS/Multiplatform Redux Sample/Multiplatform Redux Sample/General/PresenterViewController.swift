//
//  PresenterViewController.swift
//  Multiplatform Redux Sample
//
//  Created by Julia Strasser on 30.10.20.
//

import Foundation
import UIKit.UIViewController
import ReduxSampleShared

typealias Presenter<V: View> = (View, CoroutineScope) -> (Store) -> () -> KotlinUnit

class PresenterViewController<V: View>: UIViewController, View {
    private let scrollView = UIScrollView.autoLayout()
    let vStack = UIStackView.autoLayout(axis: .vertical)

    init() {
        super.init(nibName: nil, bundle: nil)
        scrollView.showsVerticalScrollIndicator = false
        scrollView.addSubview(vStack)
        vStack.fitVerticalScrollView()
        view.addSubview(scrollView)
        scrollView.fillSuperviewMargins(edgeInsets: NSDirectionalEdgeInsets(top: kUnit2, leading: kUnit1, bottom: 0, trailing: kUnit1))
        vStack.alignment = .fill
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    var viewPresenter: Presenter<V> {
            fatalError("This must me implemented in subclasses")
    }

    override func viewDidDisappear(_ animated: Bool) {
        super.viewDidDisappear(animated)
        PresenterInjectorKt.detachView(view: self)
    }

    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)
        PresenterInjectorKt.attachView(view: self)
    }

    @objc func presenter() -> (View, CoroutineScope) -> (Store) -> () -> KotlinUnit {
        return viewPresenter
    }
}
