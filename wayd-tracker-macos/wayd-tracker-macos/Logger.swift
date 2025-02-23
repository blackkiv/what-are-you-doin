//
//  Logger.swift
//  wayd-tracker-macos
//

import Foundation

class Logger {
    private var logs: Set<TrackLog> = []
    private let queue = DispatchQueue(label: "logsList", attributes: .concurrent)
    
    func recordLog() {
        if let log = WindowFetcher.getLog() {
            queue.sync{
                self.logs.insert(log)
                ConsoleLogging.shared.printLog("Log: \(log)")
            }
        } else {
            ConsoleLogging.shared.printLog("Error during app tracking")
        }
    }
    
    func syncLogs() {
        queue.sync {
            let tempLogsCopy = Array(self.logs)
            syncLogsToServer(logs: tempLogsCopy)
        }
    }
    
    private func syncLogsToServer(logs: [TrackLog]) {
        Requester.sendLogs(logs) { result in
            switch result {
            case .success(_):
                ConsoleLogging.shared.printLog("Logs synced")
                self.logs.removeAll()
            case .failure(let error):
                ConsoleLogging.shared.printLog("Error occurred: \(error)")
            }
        }
    }
}
