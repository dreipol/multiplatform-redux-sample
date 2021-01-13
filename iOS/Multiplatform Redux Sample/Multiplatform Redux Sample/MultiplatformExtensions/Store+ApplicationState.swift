//
//  Store+ApplicationState.swift
//  Multiplatform Redux Sample
//
//  Created by Samuel Bichsel on 30.10.20.
//

import Foundation
import rezhycleShared

extension Store {
    var appState: AppState {
            guard let s  = state as? AppState else {
                fatalError("app State has a wrong type")
            }
            return s
    }
}
