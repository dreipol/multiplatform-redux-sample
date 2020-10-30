//
//  PagePresenterViewController.swift
//  Multiplatform Redux Sample
//
//  Created by Julia Strasser on 30.10.20.
//

import Foundation
import UIKit.UIViewController
import ReduxSampleShared

class PagePresenterViewController<V: View>: UIPageViewController, View {
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
