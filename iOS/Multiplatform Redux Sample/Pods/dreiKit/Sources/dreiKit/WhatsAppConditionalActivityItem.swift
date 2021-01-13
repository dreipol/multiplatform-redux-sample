//
//  WhatsAppConditionalActivityItem.swift
//  dreiKit
//
//  Created by Nils Becker on 06.01.21.
//

import UIKit

// https://stackoverflow.com/questions/43519969/uiactivityviewcontroller-chose-different-text-for-whatsapp-or-facebook/51029640
private let imageOnlyActivityTypes: Set<String?> = ["net.whatsapp.WhatsApp.ShareExtension",
                                                    "com.facebook.Facebook.ShareExtension",
                                                    "com.facebook.Messenger.ShareExtension",
                                                    "com.burbn.instagram.shareextension"]

public class WhatsAppConditonalActivityItem<T>: NSObject, UIActivityItemSource {
    public let item: T

    public init(item: T) {
        self.item = item
        super.init()
    }

    public func activityViewControllerPlaceholderItem(_ activityViewController: UIActivityViewController) -> Any {
        return item
    }

    public func activityViewController(_ activityViewController: UIActivityViewController,
                                       itemForActivityType activityType: UIActivity.ActivityType?) -> Any? {
        if imageOnlyActivityTypes.contains(activityType?.rawValue) {
            return nil
        } else {
            return item
        }
    }
}
