//
//  UIScrollView+ScrollTo.swift
//  dreiKit
//
//  Created by Samuel Bichsel on 10.12.19.
//  Copyright © 2019 dreipol. All rights reserved.
//

// import DogSwift
import Foundation
import UIKit.UIScrollView

public extension UIScrollView {
    // Scroll to a specific view so that it's top is at the top our scrollview
    func scrollToView(view: UIView, animated: Bool) {
        if let origin = view.superview {
            // Get the Y position of your child view
            DispatchQueue.main.asyncAfter(deadline: .now() + 0.1) { [unowned self] in
                let childStartPoint = origin.convert(view.frame.origin, to: self)
                let topInset = self.adjustedContentInset.top
                let offsetY = self.frame.height + self.safeAreaInsets.bottom - self.adjustedContentInset.bottom
                let normalizedOffset = CGPoint(x: 0, y: max((childStartPoint.y + view.frame.height + 4) - offsetY, -topInset))
//                Log.info("Will scroll to: \(normalizedOffset.y)", tag: Tag.ui)
                self.setContentOffset(normalizedOffset, animated: animated)
            }
        }
    }

    // Bonus: Scroll to top
    func scrollToTop(animated: Bool) {
        let topOffset = CGPoint(x: 0, y: -contentInset.top)
        setContentOffset(topOffset, animated: animated)
    }

    // Bonus: Scroll to bottom
    func scrollToBottom() {
        let bottomOffset = CGPoint(x: 0, y: contentSize.height - bounds.size.height + contentInset.bottom)
        if bottomOffset.y > 0 {
            setContentOffset(bottomOffset, animated: true)
        }
    }
}
