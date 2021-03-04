//
//  CollectionPoint+Icon.swift
//  Multiplatform Redux Sample
//
//  Created by Samuel Bichsel on 04.03.21.
//

import Foundation
import MapCore
import MapCoreSharedModule
import ReduxSampleShared
import UIKit

extension UIImage {
    func withTintByMultiply(with color: UIColor) -> UIImage {
        defer { UIGraphicsEndImageContext() }

        UIGraphicsBeginImageContextWithOptions(size, false, UIScreen.main.scale)
        guard let context = UIGraphicsGetCurrentContext() else {
            return self
        }

        // flip the image
        context.scaleBy(x: 1.0, y: -1.0)
        context.translateBy(x: 0.0, y: -size.height)

        // multiply blend mode
        context.setBlendMode(.multiply)

        let rect = CGRect(x: 0, y: 0, width: size.width, height: size.height)
        context.clip(to: rect, mask: cgImage!)
        color.setFill()
        context.fill(rect)

        // create UIImage
        guard let newImage = UIGraphicsGetImageFromCurrentImageContext() else {
            return self
        }

        return newImage
    }
}

extension CollectionPoint {
    private static let unselectedPin: CGImage! = UIImage(named: "ic_32_location")?.withTintByMultiply(with: .accentAccent).cgImage!
    private static let selectedPin: CGImage! = UIImage(named: "selected")?.cgImage!

    var coordinate: MCCoord {
        return MCCoord(systemIdentifier: MCCoordinateSystemIdentifiers.epsg4326(), x: lon, y: lat, z: 0)
    }

    private func createIcon(pin: CGImage, scale: MCIconType = .FIXED) -> MCIconInfoInterface {
        let iconTexture = try! TextureHolder(pin)
        let vector = MCVec2F(x: Float(iconTexture.getImageWidth()), y: Float(iconTexture.getImageHeight()))

        return MCIconFactory.createIcon(id, coordinate: coordinate, texture: iconTexture, iconSize: vector, scale: .FIXED)!
    }

    var unselectedIcon: MCIconInfoInterface {
        return createIcon(pin: Self.unselectedPin)
    }

    var selectedIcon: MCIconInfoInterface {
        return createIcon(pin: Self.selectedPin, scale: .SCALE_INVARIANT)
    }
}
