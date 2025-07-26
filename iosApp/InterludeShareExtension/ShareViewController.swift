//
//  ShareViewController.swift
//  InterludeShareExtension
//
//  Created by Lennard Stubbe on 25.07.25.
//

import UIKit
import Social

class ShareViewController: SLComposeServiceViewController {

    override func isContentValid() -> Bool {
        return true
    }

    override func didSelectPost() {
        guard let item = extensionContext?.inputItems.first as? NSExtensionItem,
              let attachments = item.attachments else {
            extensionContext?.completeRequest(returningItems: nil)
            return
        }

        for provider in attachments {
            if provider.hasItemConformingToTypeIdentifier("public.plain-text") {
                provider.loadItem(forTypeIdentifier: "public.plain-text", options: nil) { (data, error) in
                    if let text = data as? String {
                        let sharedDefaults = UserDefaults(suiteName: "group.de.stubbe.interlude")
                        sharedDefaults?.set(text, forKey: "shared_link")
                    }

                    DispatchQueue.main.async {
                        self.extensionContext?.completeRequest(returningItems: nil)
                    }
                }
                break
            }
        }
    }

    override func configurationItems() -> [Any]! {
        return []
    }
}
