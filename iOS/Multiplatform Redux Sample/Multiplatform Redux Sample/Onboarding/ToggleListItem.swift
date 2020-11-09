//
//  ToggleListItem.swift
//  Multiplatform Redux Sample
//
//  Created by Julia Strasser on 06.11.20.
//

import UIKit
import ReduxSampleShared

class ToggleListItem: UIControl {
    //Note: there are three different types of toggles:
    //a) DisposalType: Icon, Label, Switch -> use init with DisposalType
    //b) RemindType: Label, Check-Image -> use init with RemindType
    //c) PushEnabled: Label, Switch -> use default init
    private let stackView = UIStackView.autoLayout()
    private let imageView: UIImageView = UIImageView.autoLayout()
    private let label = UILabel.h3()
    private let toggleSwitch = UISwitch.autoLayout()
    private let selectedImage = UIImageView.autoLayout()
    private let lineView = UIView.autoLayout()
    let disposalType: DisposalType?
    let remindType: RemindTime?

    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    init() {
        disposalType = nil
        remindType = nil
        super.init(frame: .zero)
        initializeStackView()
        initializeViews(labelText: "onboarding_pushes")
    }

    init(type: DisposalType) {
        disposalType = type
        remindType = nil
        super.init(frame: .zero)
        initializeStackView()
        addImage(type.iconId, stackView)
        initializeViews(labelText: type.translationKey)
    }

    init(notificationType: RemindTime) {
        disposalType = nil
        remindType = notificationType
        super.init(frame: .zero)
        initializeViews(labelText: remindType?.descriptionKey)
    }

    func setToggle(enabled: Bool) {
        if remindType != nil {
            imageView.isHidden = !enabled
        } else {
            toggleSwitch.setOn(enabled, animated: true)
        }
    }

    fileprivate func initializeStackView() {
        translatesAutoresizingMaskIntoConstraints = false
        isUserInteractionEnabled = true

        stackView.distribution = .fill
        stackView.isUserInteractionEnabled = false
        stackView.axis = .horizontal
        stackView.alignment = .center
        stackView.spacing = 12
        addSubview(stackView)
        stackView.fillSuperview(edgeInsets: NSDirectionalEdgeInsets(top: kUnit2, leading: kUnit5, bottom: kUnit2, trailing: kUnit5))
    }

    fileprivate func initializeViews(labelText: String?) {
        initializeStackView()
        addLabel(labelText)
        addSwitch()
        addLineView()
    }

    fileprivate func addLineView() {
        lineView.backgroundColor = UIColor.testAppGreen
        addSubview(lineView)
        lineView.heightAnchor.constraint(equalToConstant: 1).isActive = true
        lineView.bottomAnchor.constraint(equalTo: bottomAnchor).isActive = true
        lineView.trailingAnchor.constraint(equalTo: trailingAnchor, constant: -kUnit5).isActive = true
        lineView.leadingAnchor.constraint(equalTo: leadingAnchor, constant: kUnit5).isActive = true
    }

    fileprivate func addSwitch() {
        if remindType != nil {
            imageView.image = UIImage(named: "ic_36_check")
            imageView.isHidden = true
            stackView.addArrangedSubview(imageView)
            imageView.heightAnchor.constraint(equalToConstant: 25).isActive = true
            imageView.widthAnchor.constraint(equalToConstant: 25).isActive = true
        } else {
            toggleSwitch.isEnabled = true
            toggleSwitch.isOn = true
            toggleSwitch.tintColor = UIColor.testAppGreenDark
            toggleSwitch.setContentHuggingPriority(.required, for: .horizontal)
            stackView.addArrangedSubview(toggleSwitch)
        }

        let tap = UITapGestureRecognizer(target: self, action: #selector(didTapInside))
        addGestureRecognizer(tap)
    }

    fileprivate func addLabel(_ text: String?) {
        label.text = text?.localized
        label.textAlignment = .left
        label.textColor = UIColor.white
        stackView.addArrangedSubview(label)
    }

    fileprivate func addImage(_ image: String?, _ stackView: UIStackView) {
        if let imageName = image {
            imageView.image = UIImage(named: imageName)
            imageView.setContentHuggingPriority(.required, for: .horizontal)
            stackView.addArrangedSubview(imageView)
        }
    }

    @objc func didTapInside() {
        if let type = disposalType {
            _ = dispatch(UpdateShowDisposalType(disposalType: type, show: !toggleSwitch.isOn))
        } else if let time = remindType {
            _ = dispatch(UpdateRemindTime(remindTime: time))
        } else {
            _ = dispatch(UpdateAddNotification(addNotification: !toggleSwitch.isOn))
        }
    }
}
