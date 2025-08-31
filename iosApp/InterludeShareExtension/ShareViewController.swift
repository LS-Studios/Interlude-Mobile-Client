//
//  ShareViewController.swift
//  InterludeShareExtension
//
//  Created by Lennard Stubbe on 25.07.25.
//

import UIKit
import SwiftUI
import UniformTypeIdentifiers

class ShareViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        
        extractSharedText { text in
            guard let text else {
                self.close()
                return
            }
            
            DispatchQueue.main.async {
                let contentView = UIHostingController(rootView: ShareExtensionView(text: text))
                self.addChild(contentView)
                self.view.addSubview(contentView.view)
                contentView.didMove(toParent: self)
                
                contentView.view.translatesAutoresizingMaskIntoConstraints = false
                NSLayoutConstraint.activate([
                    contentView.view.topAnchor.constraint(equalTo: self.view.topAnchor),
                    contentView.view.bottomAnchor.constraint(equalTo: self.view.bottomAnchor),
                    contentView.view.leadingAnchor.constraint(equalTo: self.view.leadingAnchor),
                    contentView.view.trailingAnchor.constraint(equalTo: self.view.trailingAnchor)
                ])
            }
        }
        
        NotificationCenter.default.addObserver(forName: NSNotification.Name("close"), object: nil, queue: nil) { _ in
            DispatchQueue.main.async {
                self.close()
            }
        }
    }
    
    private func extractSharedText(completion: @escaping (String?) -> Void) {
        guard
            let item = extensionContext?.inputItems.first as? NSExtensionItem,
            let provider = item.attachments?.first
        else {
            completion(nil)
            return
        }
        
        let supportedTypes = [
            UTType.plainText.identifier,
            UTType.text.identifier,
            UTType.url.identifier
        ]
        
        for type in supportedTypes {
            if provider.hasItemConformingToTypeIdentifier(type) {
                provider.loadItem(forTypeIdentifier: type, options: nil) { item, error in
                    if let error {
                        print("Error loading item: \(error.localizedDescription)")
                        completion(nil)
                        return
                    }
                    
                    if let str = item as? String {
                        completion(str)
                    } else if let url = item as? URL {
                        completion(url.absoluteString)
                    } else {
                        completion(nil)
                    }
                }
                return
            }
        }
        
        completion(nil)
    }

    func close() {
        self.extensionContext?.completeRequest(returningItems: [], completionHandler: nil)
    }
    
}
