//
//  ShareExtensionView.swift
//  iosApp
//
//  Created by Lennard Stubbe on 26.07.25.
//


import SwiftUI

struct ShareExtensionView: View {
    @State private var text: String
    
    init(text: String) {
        self.text = text
    }
    
    var body: some View {
        NavigationStack{
            VStack(spacing: 20){
                TextField("Link to convert", text: $text, axis: .vertical)
                    .lineLimit(3...6)
                    .textFieldStyle(.roundedBorder)
                
                Button {
                    convertLink(link: text)
                    close()
                } label: {
                    Text("Convert")
                        .frame(maxWidth: .infinity)
                }
                .buttonStyle(.borderedProminent)
                
                Spacer()
            }
            .padding()
            .navigationTitle("Interlude")
            .toolbar {
                Button("Cancel") {
                    close()
                }
            }
        }
    }
    
    func close() {
        NotificationCenter.default.post(name: NSNotification.Name("close"), object: nil)
    }
    
    func convertLink(link: String) {
        NotificationCenter.default.post(name: NSNotification.Name("openApp"), object: nil, userInfo: ["link": link])
        close()
    }
}

