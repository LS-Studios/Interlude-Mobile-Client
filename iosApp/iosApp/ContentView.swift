import UIKit
import SwiftUI
import ComposeApp

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    var body: some View {
        ComposeView()
                .ignoresSafeArea(edges: .all)
                .ignoresSafeArea(.keyboard)
                .onOpenURL { incomingURL in
                    handleIncomingURL(incomingURL)
                }
    }
    
    private func handleIncomingURL(_ url: URL) {
        guard url.scheme == "interlude" else {
            return
        }
        guard let components = URLComponents(url: url, resolvingAgainstBaseURL: true) else {
            print("Invalid URL")
            return
        }

        guard let action = components.host, action == "share" else {
            print("Unknown URL, we can't handle this one!")
            return
        }

        guard let link = components.queryItems?.first(where: { $0.name == "link" })?.value else {
            print("Link was not found")
            return
        }

        ExternalLinkHandler.shared.onNewLink(uri: link)
    }
}



