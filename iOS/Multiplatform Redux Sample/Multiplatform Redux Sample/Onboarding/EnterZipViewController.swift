//
//  EnterZipViewController.swift
//  Multiplatform Redux Sample
//
//  Created by Julia Strasser on 30.10.20.
//

import Foundation
import UIKit
import ReduxSampleShared

let kZipCellIdentifier = "possibleZipCell"

class EnterZipViewController: BaseOnboardingViewController {

    private let zipLabel = UILabel.label()
    private let enterView = UITextField.autoLayout()
    private var zipViewState: EnterZipOnboardingState?
    private let zipCollectionView = UICollectionView(frame: .zero, collectionViewLayout: UICollectionViewFlowLayout())

    override func render(onboardingSubState: BaseOnboardingSubState) {
        guard let enterZipState = onboardingSubState as? EnterZipOnboardingState else {
            return
        }
        super.render(onboardingSubState: onboardingSubState)
        zipViewState = enterZipState
        addZipLabel(enterZipState)
        addZipInputView()

        addPossibleZipView()
    }

    override func getIndex() -> Int {
        return 0
    }

    @objc
    func zipValueChanged() {
        if let newValue = Int(enterView.text ?? "") {
            _ = dispatch(ZipUpdatedAction(zip: KotlinInt(integerLiteral: newValue)))
        }
    }

    fileprivate func addZipLabel(_ enterZipState: EnterZipOnboardingState) {
        zipLabel.text = enterZipState.enterZipViewState.enterZipLabel.localized.uppercased()
        zipLabel.textColor = UIColor.testAppGreen
        view.addSubview(zipLabel)
        zipLabel.centerXAnchor.constraint(equalTo: view.centerXAnchor).isActive = true
        zipLabel.topAnchor.constraint(equalTo: titleLabel.bottomAnchor, constant: kUnit5).isActive = true
    }

    fileprivate func addZipInputView() {
        enterView.backgroundColor = .white
        enterView.layer.cornerRadius = kButtonCornerRadius
        enterView.font = UIFont.inputLabel()
        enterView.textColor = UIColor.testAppBlack
        enterView.textAlignment = .center

        view.addSubview(enterView)
        enterView.centerXAnchor.constraint(equalTo: view.centerXAnchor).isActive = true
        enterView.widthAnchor.constraint(equalToConstant: kButtonWidth).isActive = true
        enterView.heightAnchor.constraint(equalToConstant: kUnit6).isActive = true
        enterView.topAnchor.constraint(equalTo: zipLabel.bottomAnchor, constant: kUnit3).isActive = true
        enterView.keyboardType = .numberPad
        enterView.addTarget(self, action: #selector(zipValueChanged), for: .editingChanged)
    }

    fileprivate func addPossibleZipView() {
        let layout: UICollectionViewFlowLayout = UICollectionViewFlowLayout()
        layout.sectionInset = UIEdgeInsets(top: 20, left: 0, bottom: 10, right: 0)
        layout.itemSize = CGSize(width: kButtonWidth, height: kUnit6)
        layout.sectionInset = UIEdgeInsets(top: 0.0, left: 0.0, bottom: 0.0, right: 0.0)
        layout.minimumInteritemSpacing = 0
        layout.minimumLineSpacing = 0
        zipCollectionView.collectionViewLayout = layout
        zipCollectionView.translatesAutoresizingMaskIntoConstraints = false
        zipCollectionView.backgroundColor = UIColor.testAppWhite

        view.addSubview(zipCollectionView)
        zipCollectionView.widthAnchor.constraint(equalToConstant: kButtonWidth).isActive = true
        zipCollectionView.heightAnchor.constraint(equalToConstant: kPossibleZipContainerHeight).isActive = true
        zipCollectionView.centerXAnchor.constraint(equalTo: view.centerXAnchor).isActive = true
        zipCollectionView.topAnchor.constraint(equalTo: enterView.bottomAnchor, constant: 1).isActive = true
        zipCollectionView.register(ZipCollectionViewCell.self, forCellWithReuseIdentifier: kZipCellIdentifier)
        zipCollectionView.dataSource = self
        zipCollectionView.layer.cornerRadius = kButtonCornerRadius
    }
}

extension EnterZipViewController: UICollectionViewDataSource {

    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        // swiftlint:disable force_cast
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: kZipCellIdentifier,
                                                      for: indexPath as IndexPath) as! ZipCollectionViewCell
        if let zip = zipViewState?.enterZipViewState.possibleZips[indexPath.row] {
            cell.label.text = zip.stringValue
        }
        return cell
    }

    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return zipViewState?.enterZipViewState.possibleZips.count ?? 0
    }
}
