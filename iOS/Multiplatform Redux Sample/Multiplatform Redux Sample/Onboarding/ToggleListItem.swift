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
    private let lineView = UIView.autoLayout()
    let disposalType: DisposalType?
    let remindType: RemindTime?

    override var isEnabled: Bool {
        didSet {
            if isEnabled {
                label.textColor = UIColor.white
                imageView.tintColor = UIColor.testAppGreen
                lineView.backgroundColor = UIColor.testAppGreen
            } else {
                label.textColor = UIColor.testAppBlueDark
                imageView.tintColor = UIColor.testAppBlueDark
                lineView.backgroundColor = UIColor.testAppBlueDark
            }
        }
    }

    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    init(isLast: Bool) {
        disposalType = nil
        remindType = nil
        super.init(frame: .zero)
        initializeStackView(isLightTheme: false)
        initializeViews(labelText: "onboarding_pushes", isLast: isLast)
    }

    init(type: DisposalType, isLightTheme: Bool, isLast: Bool) {
        disposalType = type
        remindType = nil
        super.init(frame: .zero)
        initializeStackView(isLightTheme: false)
        addImage(type.iconId, stackView)
        initializeViews(labelText: type.translationKey, isLightTheme: isLightTheme, isLast: isLast)
    }

    init(notificationType: RemindTime, isLightTheme: Bool = false, isLast: Bool) {
        disposalType = nil
        remindType = notificationType
        super.init(frame: .zero)
        initializeViews(labelText: remindType?.descriptionKey, isLightTheme: isLightTheme, isLast: isLast)
    }

    func setToggle(enabled: Bool) {
        if remindType != nil {
            imageView.isHidden = !enabled
        } else {
            toggleSwitch.setOn(enabled, animated: true)
            if disposalType == nil {
                lineView.backgroundColor = enabled ? UIColor.testAppGreen : UIColor.testAppBlueDark
            }
        }
    }

    fileprivate func initializeStackView(isLightTheme: Bool) {
        translatesAutoresizingMaskIntoConstraints = false
        isUserInteractionEnabled = true

        stackView.distribution = .fill
        stackView.isUserInteractionEnabled = false
        stackView.axis = .horizontal
        stackView.alignment = .center
        stackView.spacing = 12
        addSubview(stackView)
        if isLightTheme {
            stackView.fillSuperview(edgeInsets: NSDirectionalEdgeInsets(top: kUnit2, leading: kUnit2, bottom: kUnit2, trailing: kUnit2))
        } else {
            stackView.fillSuperview(edgeInsets: NSDirectionalEdgeInsets(top: kUnit2, leading: kUnit5, bottom: kUnit2, trailing: kUnit5))
        }
    }

    fileprivate func initializeViews(labelText: String?, isLightTheme: Bool = false, isLast: Bool) {
        initializeStackView(isLightTheme: isLightTheme)
        addLabel(labelText)
        if isLightTheme {
            label.textColor = .testAppBlue
        }
        addSwitch()
        if !isLast {
            addLineView()
        }
    }

    fileprivate func addLineView() {
        lineView.backgroundColor = UIColor.testAppGreen
        addSubview(lineView)
        lineView.heightAnchor.constraint(equalToConstant: 1).isActive = true
        lineView.bottomAnchor.constraint(equalTo: bottomAnchor).isActive = true
        lineView.trailingAnchor.constraint(equalTo: trailingAnchor, constant: -kUnit2).isActive = true
        lineView.leadingAnchor.constraint(equalTo: leadingAnchor, constant: kUnit2).isActive = true
    }

    fileprivate func addSwitch() {
        if remindType != nil {
            imageView.image = UIImage(named: "ic_36_check")?.withRenderingMode(.alwaysTemplate)
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
