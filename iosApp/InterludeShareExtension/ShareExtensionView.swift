//
//  ShareExtensionView.swift
//  iosApp
//
//  Created by Lennard Stubbe on 26.07.25.
//


import SwiftUI
import ComposeApp

struct SResponse: Decodable {
    let results: [SConvertedLink]
}

struct SConvertedLink: Decodable {
    let provider: String
    let type: String
    let displayName: String
    let url: String
    let artwork: String?
}

struct ShareExtensionView: View {
    @State private var text: String
    @State private var convertedLinks: [SConvertedLink] = []
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
                        LazyVStack(spacing: 8) {
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
    
    func callConvertAPI(link: String) async throws -> [SConvertedLink] {
        let url = URL(string: "https://interlude.api.leshift.de/convert?link=\(link.addingPercentEncoding(withAllowedCharacters: .urlQueryAllowed) ?? "")")!

        var request = URLRequest(url: url)
        
        let apiToken = ApiHelperKt.getBase64EncodedApiToken()
        
        request.setValue("Bearer \(apiToken)", forHTTPHeaderField: "Authorization")
        request.setValue("application/json", forHTTPHeaderField: "Accept")
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")

        let (data, _) = try await URLSession.shared.data(for: request)
        let response = try JSONDecoder().decode(SResponse.self, from: data)
        return response.results.filter { $0.displayName.isEmpty == false }
    }
}

struct ConvertedLinkRow: View {
    let link: SConvertedLink
    
    var body: some View {
        Link(
            destination: URL(string: link.url)!,
            label: {
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
                            .foregroundStyle(.white)
                            .multilineTextAlignment(.leading)
                            .lineLimit(2)
                        
                        Text(link.provider)
                            .font(.caption)
                            .foregroundColor(.secondary)
                    }
                    
                    Spacer()
                    
                    Button {
                        ShareSong_iosKt.shareSong(link: ConvertedLink.init(
                            provider: Provider(name: link.provider, url: "", logoUrl: "", iconUrl: ""),
                            type: ConvertedLinkType.entries.filter { $0.name.lowercased() == link.type.lowercased() }.first!,
                            displayName: link.displayName,
                            url: link.url,
                            artwork: link.artwork ?? ""
                        ), context: nil)
                    } label: {
                        Image(systemName: "square.and.arrow.up")
                            .foregroundColor(.blue)
                    }
                }
                .padding()
                .background(Color(.systemBackground))
                .cornerRadius(12)
                .shadow(color: .black.opacity(0.1), radius: 2, x: 0, y: 1)
                .onLongPressGesture {
                    UIPasteboard.general.string = link.url
                }
            }
        )
    }
}

