//
//  TapListener.swift
//  Multiplatform Redux Sample
//
//  Created by Samuel Bichsel on 05.03.21.
//

import Foundation
import MapCoreSharedModule
import ReduxSampleShared

class PinTapListener: NSObject {
    let kind: PinKind

    init(kind: PinKind) {
        self.kind = kind
    }
}

extension PinTapListener: MCIconLayerCallbackInterface {
    func onClickConfirmed(_ icons: [MCIconInfoInterface]) -> Bool {
        guard let icon = icons.first else {
            return false
        }

        DispatchQueue.main.async {
            let action: Action
            switch self.kind {
            case .unselected:
                action = SelectCollectionPointAction(collectionPointId: icon.getIdentifier())
            case .selected:
                action = DeselectCollectionPointAction()
            }
            _ = dispatch(action)
        }
        return true
    }
}

class BaseLayerTapListener: MCTiled2dMapRasterLayerCallbackInterface {
    func onClickConfirmed(_ coord: MCCoord) -> Bool {
        DispatchQueue.main.async {
            _ = dispatch(DeselectCollectionPointAction())
        }
        return true
    }

    func onLongPress(_ coord: MCCoord) -> Bool {
        return false
    }
}
