//
//  KDog.swift
//  Multiplatform Redux Sample
//
//  Copyright (c) 2021 dreipol GmbH
//
//  Licensed under a Creative Commons Attribution-NonCommercial 4.0 International License. A copy of the license is provided with this code.
//  No warranties are given. The license may not give you all of the permissions necessary for your intended use. For example, other rights such as publicity, privacy, or moral rights may limit how you use the material.
//
//  Created by Samuel Bichsel on 11.02.21.
//

import Foundation
import os.log
import ReduxSampleShared

// Copied from:https://github.com/touchlab/Kermit/blob/master/KermitSample/KermitSampleIOS/KermitSampleIOS/OSLogLogger.swift
class OSLogLogger: ReduxSampleShared.Logger {
    private func getSeverity(severity: Severity) -> OSLogType {
        switch severity {
        case Severity.verbose: return OSLogType.info
        case Severity.debug: return OSLogType.debug
        case Severity.info: return OSLogType.info
        case Severity.warn: return OSLogType.debug
        case Severity.error: return OSLogType.error
        case Severity.assert: return OSLogType.fault
        default: return OSLogType.default
        }
    }

    override func isLoggable(severity: Severity) -> Bool {
        OSLog.default.isEnabled(type: getSeverity(severity: severity))
    }

    override func log(severity: Severity, message: String, tag: String?, throwable: KotlinThrowable?) {
        os_log("%@", log: OSLog(subsystem: tag ?? "default", category: tag ?? "default"), type: getSeverity(severity: severity), message)
        if let realThrowable = throwable {
            os_log("%@",
                   log: OSLog(subsystem: tag ?? "default", category: tag ?? "default"),
                   type: getSeverity(severity: severity),
                   realThrowable.message ?? realThrowable.description)
        }
    }
}

func kermit() -> Kermit {
    return Kermit(logger: OSLogLogger())
}

extension Kermit {
    func d(_ message: Any) {
        d { "\(message)" }
    }
}
