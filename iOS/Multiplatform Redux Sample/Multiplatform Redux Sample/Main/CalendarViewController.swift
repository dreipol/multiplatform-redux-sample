//
//  CalendarViewController.swift
//  Multiplatform Redux Sample
//
//  Created by Samuel Bichsel on 30.10.20.
//

import UIKit

class CalendarViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
    }
}

extension CalendarViewController: TabBarCompatible {
    var tabBarImageName: String { "iconIc40Calendar" }
}
