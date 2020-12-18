//
//  ZipEnterControl.swift
//  Multiplatform Redux Sample
//
//  Created by Julia Strasser on 17.12.20.
//

import UIKit
import ReduxSampleShared

let kZipCellIdentifier = "possibleZipCell"

class ZipEnterControl: UIView {
    private let zipLabel = UILabel.label()
    private let enterView = UITextField.autoLayout()
    private let zipCollectionView = UICollectionView(frame: .zero, collectionViewLayout: UICollectionViewFlowLayout())
    private var possibleZips: [KotlinInt] = []

    init() {
        super.init(frame: .zero)
        translatesAutoresizingMaskIntoConstraints = false
        addZipLabel()
        layoutZipInputView()
        layoutZipCollectionView()
        zipCollectionView.dataSource = self
        zipCollectionView.delegate = self
        heightAnchor.constraint(equalToConstant: kUnit11 + kUnit4 + kPossibleZipContainerHeight).isActive = true
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    func updateControl(title: String, enterText: String?, isHiddenCollection: Bool, dataSource: [KotlinInt]) {
        zipLabel.text = title
        enterView.text = enterText
        zipCollectionView.isHidden = isHiddenCollection
        possibleZips = dataSource
        zipCollectionView.reloadData()
    }

    fileprivate func addZipLabel() {
        zipLabel.textColor = UIColor.testAppGreen
        addSubview(zipLabel)
        zipLabel.centerXAnchor.constraint(equalTo: centerXAnchor).isActive = true
        zipLabel.topAnchor.constraint(equalTo: topAnchor, constant: kUnit5).isActive = true
    }

    fileprivate func layoutZipInputView() {
        enterView.backgroundColor = .white
        enterView.layer.cornerRadius = kButtonCornerRadius
        enterView.font = UIFont.inputLabel()
        enterView.textColor = UIColor.testAppBlack
        enterView.textAlignment = .center
        enterView.tintColor = UIColor.testAppGreenDark
        enterView.becomeFirstResponder()

        enterView.widthAnchor.constraint(equalToConstant: kButtonWidth).isActive = true
        enterView.heightAnchor.constraint(equalToConstant: kUnit6).isActive = true

        addSubview(enterView)
        enterView.centerXAnchor.constraint(equalTo: centerXAnchor).isActive = true
        enterView.topAnchor.constraint(equalTo: zipLabel.bottomAnchor, constant: kUnit3).isActive = true
        enterView.keyboardType = .numberPad
        enterView.addTarget(self, action: #selector(zipValueChanged), for: .editingChanged)
    }

    fileprivate func layoutZipCollectionView() {
        let layout: UICollectionViewFlowLayout = UICollectionViewFlowLayout()
        layout.sectionInset = UIEdgeInsets(top: 20, left: 0, bottom: 10, right: 0)
        layout.itemSize = CGSize(width: kButtonWidth, height: kUnit6)
        layout.sectionInset = UIEdgeInsets(top: 0.0, left: 0.0, bottom: 0.0, right: 0.0)
        layout.minimumInteritemSpacing = 0
        layout.minimumLineSpacing = 0
        zipCollectionView.collectionViewLayout = layout
        zipCollectionView.translatesAutoresizingMaskIntoConstraints = false
        zipCollectionView.backgroundColor = UIColor.testAppWhite
        zipCollectionView.layer.cornerRadius = kButtonCornerRadius
        zipCollectionView.register(ZipCollectionViewCell.self, forCellWithReuseIdentifier: kZipCellIdentifier)

        zipCollectionView.heightAnchor.constraint(equalToConstant: kPossibleZipContainerHeight).isActive = true
        zipCollectionView.widthAnchor.constraint(equalToConstant: kButtonWidth).isActive = true

        addSubview(zipCollectionView)
        zipCollectionView.centerXAnchor.constraint(equalTo: centerXAnchor).isActive = true
        zipCollectionView.topAnchor.constraint(equalTo: enterView.bottomAnchor, constant: 1).isActive = true
    }

    @objc
    func zipValueChanged() {
        dispatchNewValue(value: enterView.text)
    }

    func dispatchNewValue(value: String?) {
        if let newValue = Int(value ?? "") {
            _ = dispatch(ZipUpdatedAction(zip: KotlinInt(integerLiteral: newValue)))
        }
    }
}

extension ZipEnterControl: UICollectionViewDataSource {

    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        // swiftlint:disable force_cast
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: kZipCellIdentifier,
                                                      for: indexPath as IndexPath) as! ZipCollectionViewCell
        cell.label.text = possibleZips[indexPath.row].stringValue
        return cell
    }

    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return possibleZips.count
    }
}

extension ZipEnterControl: UICollectionViewDelegate {
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        if let selectedCell = collectionView.cellForItem(at: indexPath) as? ZipCollectionViewCell {
            if let newValue = Int(selectedCell.label.text ?? "") {
                _ = dispatch(ZipUpdatedAction(zip: KotlinInt(integerLiteral: newValue)))
            }
        }
    }
}
