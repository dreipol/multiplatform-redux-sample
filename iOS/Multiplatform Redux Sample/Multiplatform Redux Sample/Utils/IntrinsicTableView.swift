//
//  IntrinsicTableView.swift
//  Multiplatform Redux Sample
//
//  Created by Julia Strasser on 23.11.20.
//

import UIKit

// Code from: https://stackoverflow.com/a/39497229

final class IntrinsicTableView: UITableView {

    override var contentSize: CGSize {
        didSet {
            invalidateIntrinsicContentSize()
        }
    }

    override var intrinsicContentSize: CGSize {
        layoutIfNeeded()
        return CGSize(width: UIView.noIntrinsicMetric, height: contentSize.height)
    }

}
