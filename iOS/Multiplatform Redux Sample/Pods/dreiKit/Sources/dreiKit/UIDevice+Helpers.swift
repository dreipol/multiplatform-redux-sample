//
// Created by Samuel Bichsel on 05.09.19.
// Copyright (c) 2019 dreipol. All rights reserved.
//

import UIKit.UIDevice

public extension UIDevice {
    var isSimulator: Bool {
        #if targetEnvironment(simulator)
            return true
        #else
            return false
        #endif
    }

    static var bottomInset: CGFloat {
        return safeAreaInsets.bottom
    }

    static var topInset: CGFloat {
        return safeAreaInsets.top
    }

    private static var safeAreaInsets: UIEdgeInsets {
        return UIApplication.shared.delegate?.window??.safeAreaInsets ?? .zero
    }
}
