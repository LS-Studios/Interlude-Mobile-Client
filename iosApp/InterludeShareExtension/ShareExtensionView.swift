//
//  ShareExtensionView.swift
//  iosApp
//
//  Created by Lennard Stubbe on 26.07.25.
//


import SwiftUI
import ComposeApp

struct ConvertedLink {
    let provider: String
    let type: String
    let displayName: String
    let url: String
    let artwork: String?
}

struct ShareExtensionView: View {
    @State private var text: String
    @State private var convertedLinks: [ConvertedLink] = []
    @State private var isLoading = false
    @State private var errorMessage: String?
    @State private var debugInfo: String?
    
    init(text: String) {
        self.text = text
    }
    
    var body: some View {
        NavigationStack {
            VStack(spacing: 16) {
                if isLoading {
                    ProgressView("Converting...")
                        .frame(maxWidth: .infinity, maxHeight: .infinity)
                } else if let error = errorMessage {
                    ScrollView {
                        VStack(spacing: 16) {
                            Image(systemName: "exclamationmark.triangle")
                                .font(.largeTitle)
                                .foregroundColor(.red)
                            Text("Error")
                                .font(.headline)
                            Text(error)
                                .multilineTextAlignment(.center)
                                .foregroundColor(.secondary)
                            
                            if let debug = debugInfo {
                                VStack(alignment: .leading, spacing: 8) {
                                    Text("Debug Info:")
                                        .font(.headline)
                                    Text(debug)
                                        .font(.caption)
                                        .foregroundColor(.secondary)
                                        .textSelection(.enabled)
                                }
                                .padding()
                                .background(Color(.systemGray6))
                                .cornerRadius(8)
                            }
                        }
                        .padding()
                    }
                    .frame(maxWidth: .infinity, maxHeight: .infinity)
                } else if convertedLinks.isEmpty {
                    VStack {
                        Text("No converted links available")
                            .font(.headline)
                        Text("Try again with a different link")
                            .foregroundColor(.secondary)
                    }
                    .frame(maxWidth: .infinity, maxHeight: .infinity)
                } else {
                    ScrollView {
                        LazyVStack(spacing: 12) {
                            ForEach(convertedLinks.indices, id: \.self) { index in
                                ConvertedLinkRow(link: convertedLinks[index])
                            }
                        }
                        .padding()
                    }
                }
            }
            .navigationTitle("Converted Links")
            .navigationBarTitleDisplayMode(.inline)
            .toolbar {
                Button("Close") {
                    close()
                }
            }
            .onAppear {
                convertLink(link: text)
            }
        }
    }
    
    func close() {
        NotificationCenter.default.post(name: NSNotification.Name("close"), object: nil)
    }
    
    func convertLink(link: String) {
        isLoading = true
        errorMessage = nil
        
        // API call to convert link
        Task {
            do {
                let result = try await callConvertAPI(link: link)
                await MainActor.run {
                    self.convertedLinks = result
                    self.isLoading = false
                }
            } catch {
                await MainActor.run {
                    self.errorMessage = error.localizedDescription
                    self.isLoading = false
                    // debugInfo is already set in callConvertAPI for HTTP errors
                }
            }
        }
    }
    
    func callConvertAPI(link: String) async throws -> [ConvertedLink] {
        let url = URL(string: "https://interlude.api.leshift.de/convert?link=\(link.addingPercentEncoding(withAllowedCharacters: .urlQueryAllowed) ?? "")")!

        var request = URLRequest(url: url)
        
        let apiToken = ApiHelperKt.getBase64EncodedApiToken()
        
        request.setValue("Bearer \(apiToken)", forHTTPHeaderField: "Authorization")
        request.setValue("application/json", forHTTPHeaderField: "Accept")
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")

        let (data, response) = try await URLSession.shared.data(for: request)
        
        guard let httpResponse = response as? HTTPURLResponse else {
            throw NSError(
                domain: "MyApp",
                code: 1,
                userInfo: [NSLocalizedDescriptionKey: "Keine HTTP Response erhalten"]
            )
        }
        
        let statusCode = httpResponse.statusCode
        let headers = httpResponse.allHeaderFields
        let dataString = String(data: data, encoding: .utf8) ?? "Keine Daten (\(data.count) bytes)"
        
        let debugString = """
        Status Code: \(statusCode)
        Headers: \(headers)
        Response: \(dataString)
        """
        
        guard httpResponse.statusCode == 200 else {
            await MainActor.run {
                self.debugInfo = debugString
            }
            
            throw NSError(
                domain: "MyApp", 
                code: httpResponse.statusCode,
                userInfo: [
                    NSLocalizedDescriptionKey: "HTTP Fehler \(httpResponse.statusCode)"
                ]
            )
        }
        
        // Store debug info for successful requests too (optional)
        await MainActor.run {
            self.debugInfo = debugString
        }

        // Parse JSON response - expecting array of arrays: [[provider, type, displayName, url, artwork], []]
        guard let jsonArray = try JSONSerialization.jsonObject(with: data) as? [[String?]] else {
            throw URLError(.cannotParseResponse)
        }

        return jsonArray.compactMap { linkArray in
            guard linkArray.count >= 4,
                  let provider = linkArray[0],
                  let type = linkArray[1],
                  let displayName = linkArray[2],
                  let url = linkArray[3] else {
                return nil
            }

            let artwork = linkArray.count > 4 ? linkArray[4] : nil

            return ConvertedLink(
                provider: provider,
                type: type,
                displayName: displayName,
                url: url,
                artwork: artwork
            )
        }
    }
}

struct ConvertedLinkRow: View {
    let link: ConvertedLink
    
    var body: some View {
        HStack(spacing: 12) {
            AsyncImage(url: URL(string: link.artwork ?? "")) { image in
                image
                    .resizable()
                    .aspectRatio(contentMode: .fill)
            } placeholder: {
                RoundedRectangle(cornerRadius: 8)
                    .fill(Color.gray.opacity(0.3))
                    .overlay(
                        Image(systemName: "music.note")
                            .foregroundColor(.gray)
                    )
            }
            .frame(width: 50, height: 50)
            .clipShape(RoundedRectangle(cornerRadius: 8))
            
            VStack(alignment: .leading, spacing: 4) {
                Text(link.displayName)
                    .font(.headline)
                    .lineLimit(2)
                
                Text(link.provider)
                    .font(.caption)
                    .foregroundColor(.secondary)
            }
            
            Spacer()
            
            Button {
                UIPasteboard.general.string = link.url
            } label: {
                Image(systemName: "doc.on.doc")
                    .foregroundColor(.blue)
            }
        }
        .padding()
        .background(Color(.systemBackground))
        .cornerRadius(12)
        .shadow(color: .black.opacity(0.1), radius: 2, x: 0, y: 1)
        .onTapGesture {
            // Copy URL to clipboard as fallback
            UIPasteboard.general.string = link.url
        } 
        .onLongPressGesture {
            UIPasteboard.general.string = link.url
        }
    }
}

