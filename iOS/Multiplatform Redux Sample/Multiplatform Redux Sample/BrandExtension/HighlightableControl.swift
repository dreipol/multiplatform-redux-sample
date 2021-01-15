//
//  HighlightableControl.swift
//  Multiplatform Redux Sample
//
//  Created by Julia Strasser on 14.01.21.
//

import UIKit

class HighlightableControl: UIControl {

    override var isHighlighted: Bool {
        didSet {
            alpha = isHighlighted ? kHighlightAlphaValue : 1
        }
    }

}
