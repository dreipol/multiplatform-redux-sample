//
//  PinChangeSet.swift
//  Multiplatform Redux Sample
//
//  Created by Samuel Bichsel on 05.03.21.
//

import Foundation
import ReduxSampleShared
import MapCoreSharedModule

enum PinKind {
    case unselected, selected
}

struct PinChangeSet {
    let kind: PinKind
    let layer: MCIconLayerInterface
    let newPoints: [CollectionPoint]

    lazy var existingIds: Set<String> = Set(layer.getIcons().map { $0.getIdentifier() })
    lazy var newIds: Set<String> = Set(newPoints.map { $0.id })

    private var addIds: Set<String> { mutating get { newIds.subtracting(existingIds) } }
    private var removeIds: Set<String> { mutating get { existingIds.subtracting(newIds) } }

    mutating func updateLayer() {
        layer.getIcons().filter { removeIds.contains($0.getIdentifier()) }.forEach { layer.remove($0) }

        newPoints.filter { addIds.contains($0.id) }.forEach { addToLayer(point: $0) }
    }

    private func addToLayer(point: CollectionPoint) {
        switch kind {
        case .unselected:
            layer.add(point.unselectedIcon)
        case .selected:
            layer.add(point.selectedIcon)
        }
    }
}
