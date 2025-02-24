//
//  ConsoleLogging.swift
//  wayd-tracker-macos
//

class ConsoleLogging {
    static let shared = ConsoleLogging()
    private let logging: Bool
    
    init() {
        logging = Config.shared.loadConfig()?.consoleLogging ?? false
    }
    
    func printLog(_ message: String) {
        if (logging) {
            print(message)
        }
    }
}
