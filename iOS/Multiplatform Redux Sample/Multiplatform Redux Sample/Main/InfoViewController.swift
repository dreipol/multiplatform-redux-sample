//
//  InfoViewController.swift
//  Multiplatform Redux Sample
//
//  Created by Samuel Bichsel on 30.10.20.
//

import UIKit
import ReduxSampleShared

class CollectionPointMapViewController: PresenterViewController<CollectionPointMapView>, CollectionPointMapView {
    override var viewPresenter: Presenter<CollectionPointMapView> { CollectionPointMapViewKt.collectionPointMapPresenter }
    private let titleLabel = UILabel.h2()

    override init() {
        super.init()
        titleLabel.text = "map_work_in_progress".localized
        view.addSubview(titleLabel)
        NSLayoutConstraint.activate([
            titleLabel.centerYAnchor.constraint(equalTo: view.centerYAnchor),
            titleLabel.centerXAnchor.constraint(equalTo: view.centerXAnchor),
        ])
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    func render(collectionPointMapViewState: CollectionPointMapViewState) {

    }

}

extension CollectionPointMapViewController: TabBarCompatible {
    var tabBarImageName: String { "ic_32_location" }
}
