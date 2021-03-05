//
//  CollectionPoint+Icon.swift
//  Multiplatform Redux Sample
//
//  Created by Samuel Bichsel on 04.03.21.
//

import dreiKit
import Foundation
import MapCore
import MapCoreSharedModule
import ReduxSampleShared

extension CollectionPoint {
    private static let unselectedPin: CGImage! = UIImage(named: "ic_32_location")?.withTintByMultiply(with: .accentAccent).cgImage
    private static let selectedPin: CGImage! = UIImage(named: "selected")?.cgImage

    var coordinate: MCCoord {
        return MCCoord(systemIdentifier: MCCoordinateSystemIdentifiers.epsg4326(), x: lon, y: lat, z: 0)
    }

    private func createIcon(pin: CGImage, scale: MCIconType = .FIXED) -> MCIconInfoInterface? {
        guard let iconTexture = try? TextureHolder(pin) else {
            return nil
        }
        let vector = MCVec2F(x: Float(iconTexture.getImageWidth()), y: Float(iconTexture.getImageHeight()))

        return MCIconFactory.createIcon(id, coordinate: coordinate, texture: iconTexture, iconSize: vector, scale: scale)
    }

    var unselectedIcon: MCIconInfoInterface? {
        return createIcon(pin: Self.unselectedPin, scale: .FIXED)
    }

    var selectedIcon: MCIconInfoInterface? {
        return createIcon(pin: Self.selectedPin, scale: .FIXED)
    }
}
