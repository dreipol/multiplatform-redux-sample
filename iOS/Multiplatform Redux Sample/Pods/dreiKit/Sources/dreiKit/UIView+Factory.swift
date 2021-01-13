//
// Created by Samuel Bichsel on 07.11.19.
// Copyright (c) 2019 dreipol. All rights reserved.
//

import UIKit.UIView

public protocol BlockTransformable {
}

extension UIView: BlockTransformable {
}

public extension BlockTransformable where Self: UIView {
    func then(_ action: (Self) -> Void) -> Self {
        action(self)
        return self
    }
}
