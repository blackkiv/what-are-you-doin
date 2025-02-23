//
//  Config.swift
//  wayd-tracker-macos
//

import Foundation

public struct Configuration: Codable {
    var url: String
    var token: String
    var consoleLogging: Bool
}

class Config {
    static let shared = Config()
        
    private let fileManager = FileManager.default
    private var configFolderURL: URL? {
        fileManager.urls(for: .applicationSupportDirectory, in: .userDomainMask).first?
            .appendingPathComponent("wayd", isDirectory: true)
    }
    
    private var configFileURL: URL? {
        configFolderURL?.appendingPathComponent("config.json")
    }
    
    init() {
        createConfigIfNeeded()
    }
    
    private func createConfigIfNeeded() {
        guard let configFolderURL, let configFileURL else { return }
        
        do {
            if !fileManager.fileExists(atPath: configFolderURL.path) {
                try fileManager.createDirectory(at: configFolderURL, withIntermediateDirectories: true, attributes: nil)
                ConsoleLogging.shared.printLog("Created wayd folder at \(configFolderURL.path)")
            }
            
            if !fileManager.fileExists(atPath: configFileURL.path) {
                let defaultConfig = Configuration(url: "server_url", token: "user_token", consoleLogging: false)
                let jsonData = try JSONEncoder().encode(defaultConfig)
                try jsonData.write(to: configFileURL)
                ConsoleLogging.shared.printLog("Created config.json in wayd folder")
            }
        } catch {
            ConsoleLogging.shared.printLog("Failed to create config.json: \(error)")
        }
    }
    
    func loadConfig() -> Configuration? {
        guard let configFileURL,
              let data = try? Data(contentsOf: configFileURL),
              let config = try? JSONDecoder().decode(Configuration.self, from: data) else {
            ConsoleLogging.shared.printLog("Failed to load config.json")
            return nil
        }
        return config
    }
    
}
