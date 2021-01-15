//
//  CALayer+Gradient.swift
//  GTToS
//
//  Created by Fabian Tinsz on 30.08.19.
//  Copyright © 2019 dreipol. All rights reserved.
//

import QuartzCore.CALayer
import UIKit.UIScreen

public extension CALayer {
    func addGradient(size: CGSize, colors: [CGColor], locations: [NSNumber]? = nil, shouldRasterize: Bool = false) -> CALayer {
        let gradientLayer = CALayer.createGradient(size: size, colors: colors, locations: locations, shouldRasterize: shouldRasterize)
        addSublayer(gradientLayer)
        return gradientLayer
    }

    static func createGradient(size: CGSize,
                               colors: [CGColor],
                               locations: [NSNumber]? = [0, 1],
                               shouldRasterize: Bool = false) -> CAGradientLayer {
        let gradientLayer = CAGradientLayer()
        gradientLayer.frame = CGRect(x: .zero, y: .zero, width: size.width, height: size.height)
        gradientLayer.colors = colors
        gradientLayer.locations = locations
        gradientLayer.startPoint = .zero
        gradientLayer.endPoint = CGPoint(x: .zero, y: 1.0)

        if shouldRasterize {
            gradientLayer.shouldRasterize = true
            gradientLayer.rasterizationScale = UIScreen.main.scale
        }
        return gradientLayer
    }
}

public extension UIView {
    func addGradientLayer(colors: [UIColor], locations: [NSNumber]? = nil, shouldRasterize: Bool = false) -> CALayer {
        let cgColors = colors.map { $0.cgColor }
        return self.layer.addGradient(size: self.bounds.size, colors: cgColors, locations: locations, shouldRasterize: shouldRasterize)
    }
}
