//
//  MDCChipView+Brand.swift
//  Multiplatform Redux Sample
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
        chipView.accessoryView = UIImageView(image: UIImage(named: "ic_remove"))
        chipView.accessoryPadding = UIEdgeInsets(top: kUnit1, left: 0, bottom: kUnit1, right: kUnit1)
        chipView.accessoryView?.isHidden = true
        return chipView
    }
}
