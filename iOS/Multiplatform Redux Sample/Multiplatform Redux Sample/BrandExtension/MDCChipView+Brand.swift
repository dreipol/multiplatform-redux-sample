//
//  MDCChipView+Brand.swift
//  Multiplatform Redux Sample
//
//  Copyright (c) 2021 dreipol GmbH
//
//  Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
//  No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
//
//  Created by Nils Becker on 08.04.21.
//

import ReduxSampleShared
import MaterialComponents.MaterialChips

extension MDCChipView {
    static func filterChip(for filter: CollectionPointType) -> MDCChipView {
        let chipView = MDCChipView.autoLayout()
        chipView.titleLabel.text = filter.translationKey.localized
        chipView.setTitleColor(.primaryDark, for: .normal)
        chipView.setBackgroundColor(.primaryLight, for: .normal)
        chipView.setTitleColor(.white, for: .selected)
        chipView.setBackgroundColor(.primaryDark, for: .selected)
        chipView.accessoryView = UIImageView(image: UIImage(named: "ic_check"))
        chipView.accessoryPadding = UIEdgeInsets(top: kUnit1, left: 0, bottom: kUnit1, right: kUnit1)
        chipView.accessoryView?.isHidden = true
        return chipView
    }
}
