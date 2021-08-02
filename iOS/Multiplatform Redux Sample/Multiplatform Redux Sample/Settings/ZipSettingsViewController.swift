//
//  ZipSettingsViewController.swift
//  Multiplatform Redux Sample
//
//  Copyright (c) 2021 dreipol GmbH
//
//  Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
//  No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
//
//  Created by Julia Strasser on 17.12.20.
//

import UIKit
import ReduxSampleShared

class ZipSettingsViewController: StackPresenterViewController<ZipSettingsView>, ZipSettingsView {

    override var viewPresenter: Presenter<ZipSettingsView> { ZipSettingsViewKt.zipSettingsPresenter }
    private let headerView = HeaderView()
    private let zipEnterControl = ZipEnterControl(isLightTheme: true)
    private var zipViewState: ZipSettingsViewState?

    override init() {
        super.init()
        view.backgroundColor = .primaryLight
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
        headerView.canGoBack = zipSettingsViewState.enterZipViewState.canGoBack
        zipEnterControl.updateControl(
            title: zipSettingsViewState.enterZipViewState.enterZipLabel.localized,
            enterText: zipSettingsViewState.enterZipViewState.selectedZip?.stringValue,
            isHiddenCollection: zipSettingsViewState.enterZipViewState.filteredZips.count < 2,
            dataSource: zipSettingsViewState.enterZipViewState.filteredZips
        )
    }

}
