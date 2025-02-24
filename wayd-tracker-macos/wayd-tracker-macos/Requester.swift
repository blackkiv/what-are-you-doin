//
//  Requester.swift
//  wayd-tracker-macos
//

import Foundation

struct LogWrapper: Codable {
    var logs: [TrackLog]
}

class Requester {
    
    static func sendLogs(_ logs: [TrackLog],completion: @escaping (Result<(), Error>) -> Void) {
        let config = Config.shared.loadConfig()!
        var request = URLRequest(url: URL(string: config.url)!)
        request.httpMethod = "POST"
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        request.setValue(config.token, forHTTPHeaderField: "User-Token")
        
        do {
            let encoder = JSONEncoder()
            let data = try encoder.encode(LogWrapper(logs: logs))
            request.httpBody = data
        } catch {
            completion(.failure(error))
            return
        }
        
        // Perform the request
        let task = URLSession.shared.dataTask(with: request) { data, response, error in
            if let error = error {
                completion(.failure(error))
                return
            }
            
            // Check for valid HTTP response
            if let httpResponse = response as? HTTPURLResponse, !(200..<300).contains(httpResponse.statusCode) {
                let error = NSError(domain: "APIClient",
                                    code: httpResponse.statusCode,
                                    userInfo: [NSLocalizedDescriptionKey: httpResponse.description])
                completion(.failure(error))
                return
            }
            
            completion(.success(()))
        }
        
        task.resume()
    }
}
