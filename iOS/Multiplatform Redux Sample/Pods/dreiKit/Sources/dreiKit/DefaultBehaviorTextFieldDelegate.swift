//
//  DefaultBehaviorTextFieldDelegate.swift
//  dreiKit
//
//  Created by Nils Becker on 10.09.20.
//

import UIKit

open class DefaultBehaviorTextFieldDelegate: NSObject, UITextFieldDelegate {
    weak open var forwardingDelegate: UITextFieldDelegate?

    open func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        if let returnHandler = forwardingDelegate?.textFieldShouldReturn {
            return returnHandler(textField)
        } else {
            textField.resignFirstResponder()
            return true
        }
    }

    open func textFieldDidEndEditing(_ textField: UITextField) {
        forwardingDelegate?.textFieldDidEndEditing?(textField)
    }

    open func textFieldDidBeginEditing(_ textField: UITextField) {
        forwardingDelegate?.textFieldDidBeginEditing?(textField)
    }

    @available(iOS 13, *)
    open func textFieldDidChangeSelection(_ textField: UITextField) {
        forwardingDelegate?.textFieldDidChangeSelection?(textField)
    }

    open func textFieldShouldClear(_ textField: UITextField) -> Bool {
        return forwardingDelegate?.textFieldShouldClear?(textField) ?? true
    }

    open func textFieldShouldEndEditing(_ textField: UITextField) -> Bool {
        return forwardingDelegate?.textFieldShouldEndEditing?(textField) ?? true
    }

    open func textFieldShouldBeginEditing(_ textField: UITextField) -> Bool {
        return forwardingDelegate?.textFieldShouldBeginEditing?(textField) ?? true
    }

    open func textFieldDidEndEditing(_ textField: UITextField, reason: UITextField.DidEndEditingReason) {
        forwardingDelegate?.textFieldDidEndEditing?(textField, reason: reason)
    }

    public func textField(_ textField: UITextField, shouldChangeCharactersIn range: NSRange, replacementString string: String) -> Bool {
        guard let text = textField.text,
              let range = Range(range, in: text) else {
            return isValid(text: string, in: textField)
        }

        let afterEdit = text.replacingCharacters(in: range, with: string)
        return isValid(text: afterEdit, in: textField)
    }

    open func isValid(text: String, in textField: UITextField) -> Bool {
        return true
    }
}

public final class KeyboardValidationTextFieldDelegate: DefaultBehaviorTextFieldDelegate {
    public override func isValid(text: String, in textField: UITextField) -> Bool {
        var validationString = text
        switch textField.keyboardType {
        case .decimalPad:
            if validationString.filter({ $0 == "." || $0 == "," }).count > 1 {
                return false
            }
            validationString = validationString.filter({ $0 != "." && $0 != "," })
            fallthrough
        case .numberPad:
            return validationString.allSatisfy({ $0.isNumber })
        default:
            return true
        }
    }
}

public final class CurrencyValidationTextFieldDelegate: DefaultBehaviorTextFieldDelegate {
    public override func isValid(text: String, in textField: UITextField) -> Bool {
        var subUnits = text.drop(while: { $0.isNumber })
        if let separator = subUnits.first, separator == "." || separator == "," {
            subUnits = subUnits.dropFirst()
        }
        return subUnits.allSatisfy({ $0.isNumber }) && subUnits.count <= 2
    }
}
