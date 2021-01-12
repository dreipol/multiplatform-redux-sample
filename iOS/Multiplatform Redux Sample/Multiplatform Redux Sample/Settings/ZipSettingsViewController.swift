//
//  ZipSettingsViewController.swift
//  Multiplatform Redux Sample
//
//  Created by Julia Strasser on 17.12.20.
//

import UIKit
import ReduxSampleShared

class ZipSettingsViewController: PresenterViewController<ZipSettingsView>, ZipSettingsView {

    override var viewPresenter: Presenter<ZipSettingsView> { ZipSettingsViewKt.zipSettingsPresenter }
    private let headerView = HeaderView()
    private let zipEnterControl = ZipEnterControl()
    private var zipViewState: ZipSettingsViewState?

    override init() {
        super.init()
        view.backgroundColor = .testAppGreenLight
        vStack.addArrangedSubview(headerView)
        vStack.addSpace(kUnit3)
        vStack.addArrangedSubview(zipEnterControl)
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    func render(zipSettingsViewState: ZipSettingsViewState) {
        zipViewState = zipSettingsViewState
        headerView.titleLabel.text = zipSettingsViewState.headerViewState.title.localized
        zipEnterControl.updateControl(
            title: zipSettingsViewState.enterZipViewState.enterZipLabel.localized.uppercased(),
            enterText: zipSettingsViewState.enterZipViewState.selectedZip?.stringValue,
            isHiddenCollection: zipSettingsViewState.enterZipViewState.filteredZips.count < 2,
            dataSource: zipSettingsViewState.enterZipViewState.filteredZips
        )
    }

}
