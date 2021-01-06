//
//  LanguageSelectionControl.swift
//  Multiplatform Redux Sample
//
//  Created by Julia Strasser on 06.01.21.
//

import UIKit
import ReduxSampleShared

class LanguageSelectionControl: UIStackView, ToggleListItemTapDelegate {
    private var allToggles = [ToggleListItem]()

    init() {
        super.init(frame: .zero)
        translatesAutoresizingMaskIntoConstraints = false
        axis = .vertical

        let backgroundView = UIView.autoLayout()
        backgroundView.backgroundColor = .white
        backgroundView.layer.cornerRadius = kCardCornerRadius
        addSubview(backgroundView)
        backgroundView.fitSuperview()

//        let totalCount = allNotificationOpions.count
//        for (index, option) in allNotificationOpions.enumerated() {
//            let toggle = ToggleListItem(notificationType: option,
//                                        isLightTheme: isLightTheme,
//                                        hideBottomLine: isLightTheme && index == (totalCount-1))
//            toggle.tapDelegate = self
//            addArrangedSubview(toggle)
//            allToggles.append(toggle)
//        }
    }

    required init(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    func update(availableLanguages: [AppLanguage], selectedLangauge: AppLanguage) {
        for toggle in allToggles {
            toggle.removeFromSuperview()
        }

        let totalCount = availableLanguages.count
        for (index, option) in availableLanguages.enumerated() {
            let toggle = ToggleListItem(text: option.descriptionKey.localized, hideBottomLine: index == (totalCount-1))
            toggle.tapDelegate = self
            toggle.setToggle(enabled: option.name.elementsEqual(selectedLangauge.name))
            addArrangedSubview(toggle)
            allToggles.append(toggle)
        }
    }

    // MARK: ToggleListItemTapDelegate
    func didTapToggle(isOn: Bool, disposalType: DisposalType?, remindType: RemindTime?) {
    }

    func didTapLanguage(languageName: String) {
        let newLanguage: AppLanguage
        switch languageName {
        case AppLanguage.english.descriptionKey.localized:
            newLanguage = AppLanguage.english
        case AppLanguage.german.descriptionKey.localized:
            newLanguage = AppLanguage.german
        default:
            fatalError("Language switch needs to be implemented")
        }
        _ = dispatch(ThunkAction(thunk: ThunksKt.setNewAppLanguageThunk(appLanguage: newLanguage, platformSpecificAction: {
            //TODO how to change localization here?
            print("after switch")
        })))
    }
}
