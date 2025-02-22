//
//  WindowFetcher.swift
//  wayd-tracker-macos
//

import AppKit
import ApplicationServices

public struct TrackLog: Codable {
    var appName: String
    var timestamp: Int
}

class WindowFetcher {
    
    public static func getLog() -> TrackLog? {
        if let name = WindowFetcher.getFocusAppName() {
            return TrackLog(appName: name, timestamp: getTimestamp())
        } else {
            return nil
        }
    }
    
    private static func getFocusAppName() -> String? {
        return NSWorkspace.shared.frontmostApplication?.localizedName
    }
    
    private static func getTimestamp() -> Int {
        return Int(Date().timeIntervalSince1970)
    }
}
