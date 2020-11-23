//
//  DisposalCell.swift
//  Multiplatform Redux Sample
//
//  Created by Julia Strasser on 23.11.20.
//

import UIKit

class DisposalCell: UITableViewCell {

    static let reuseIdentifier = "DisposalCell"

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    override init(style: UITableViewCell.CellStyle, reuseIdentifier: String?) {
        super.init(style: UITableViewCell.CellStyle.subtitle, reuseIdentifier: reuseIdentifier)
        setupCell()
    }

    func setupCell() {
        tintColor = .testAppBlue
        selectionStyle = .none

        textLabel?.textColor = .testAppGreen
        textLabel?.font = .h2()

    }
}
