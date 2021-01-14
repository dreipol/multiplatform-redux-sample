//
//  ControlDelegate.swift
//  dreiKit
//
//  Created by Samuel Bichsel on 21.07.20.
//

import Foundation
/// The ControlActionDelegate is used to pass a delegate and an action to an (UI)control
/// In the control must hold this as instance variable and call the tapped method
///             class Control<Delegate: AnyObject>: UIControl {
///              var buttonAction: ControlActionDelegate<Delegate>?
///
///           @objc private func buttonTapped() {
///                  buttonAction?.tapped()
///            }
///
public struct ControlActionDelegate<D: AnyObject> {
    weak var delegate: D?
    let action: Action<D>

    public init(delegate: D, action: @escaping Action<D>) {
        self.delegate = delegate
        self.action = action
    }

    public func tapped() {
        guard let delegate = delegate else {
            return
        }
        action(delegate)
    }
}

public typealias Action<Delegate> = (_ delegate: Delegate) -> Void
