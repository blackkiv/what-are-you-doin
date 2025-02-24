//
//  main.swift
//  wayd-tracker-macos
//

import Foundation

let logger = Logger()

func fetchAppData() {
    logger.recordLog()
}

func syncLogs() {
    logger.syncLogs()
}

// TODO: look on possibility to subscribe to change of active window event
Timer.scheduledTimer(withTimeInterval: 5, repeats: true) { _ in
    fetchAppData()
}

Timer.scheduledTimer(withTimeInterval: 30, repeats: true) { _ in
    syncLogs()
}

RunLoop.main.run()
