//
//  Logger.swift
//  wayd-tracker-macos
//

import Foundation

class Logger {
    private var requester: Requester
    private var logs: [TrackLog] = []
    private let queue = DispatchQueue(label: "logsList", attributes: .concurrent)
    
    init() {
        self.requester = Requester(url: URL(string: "aboba")!)
    }
    
    func recordLog() {
        if let log = WindowFetcher.getLog() {
            queue.async(flags: .barrier) { [weak self] in
                self?.logs.append(log)
                print("Log: \(log)")
            }
        } else {
            print("Error during app tracking")
        }
    }
    
    func syncLogs() {
        queue.sync {
            let tempLogsCopy = self.logs
            syncLogsToServer(logs: tempLogsCopy)
        }
    }
    
    private func syncLogsToServer(logs: [TrackLog]) {
        requester.sendLogs(logs) { result in
            switch result {
            case .success(_):
                print("Logs synced")
                self.logs.removeAll()
            case .failure(let error):
                print("Error occurred: \(error)")
            }
        }
    }
}
