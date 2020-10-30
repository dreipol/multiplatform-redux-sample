//
// Created by Samuel Bichsel on 30.10.20.
//

import Foundation
import ReduxSampleShared

class SubCoordinator: NSObject {
    weak var rootCoordinator: NavigationCoordinator!
    
    init(root: NavigationCoordinator) {
        rootCoordinator = root
    }
}
