//
//  String+HtmlBrand.swift
//  Multiplatform Redux Sample
//
//  Created by Julia Strasser on 07.12.20.
//

import UIKit

extension String {
    private static func createAttributedString(from html: String) -> NSAttributedString? {
        guard let data = html.data(using: .utf8),
              let attributedString = try? NSAttributedString(
                  data: data,
                  options: [.documentType: NSAttributedString.DocumentType.html],
                  documentAttributes: nil)
        else {
            return nil
        }

        return attributedString
    }

    func htmlAttributedString(size: CGFloat, color: UIColor) -> NSAttributedString? {
        guard let colorString = color.hexString else {
            return nil
        }
        let htmlTemplate = """
        <!doctype html>
        <html>
          <head>
            <meta charset="UTF-8">
            <style>
              body {
                color: \(colorString);
                font-family: 'OpenSans-Regular';
                src: url('BrandExtension/Fonts/OpenSans-Regular.ttf') format('truetype');
                font-size: \(size)px;
              }
            </style>
          </head>
          <body>
            \(self)
          </body>
        </html>
        """

        return Self.createAttributedString(from: htmlTemplate)
    }
}

extension UIColor {
    var hexString: String? {
        if let components = self.cgColor.components {
            let r = components[0]
            let g = components[1]
            let b = components[2]
            return String(format: "#%02x%02x%02x", (Int)(r * 255), (Int)(g * 255), (Int)(b * 255))
        }
        return nil
    }
}
