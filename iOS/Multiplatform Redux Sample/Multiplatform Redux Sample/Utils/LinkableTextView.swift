//
//  LinkableTextView.swift
//  Multiplatform Redux Sample
//
//  Created by Julia Strasser on 14.12.20.
//

import UIKit

class LinkableTextView: UITextView {
    override init(frame: CGRect, textContainer: NSTextContainer?) {
        super.init(frame: frame, textContainer: textContainer)
        self.isEditable = false
        self.isScrollEnabled = false
        self.sizeToFit()
        self.isSelectable = true
        self.textContainerInset = .zero
        self.textContainer.lineFragmentPadding = .zero
        self.backgroundColor = .primaryLight
    }

    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}
