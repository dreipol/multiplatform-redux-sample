//
//  NextDisposalCell.swift
//  Multiplatform Redux Sample
//
//  Created by Julia Strasser on 23.11.20.
//

import UIKit

class NextDisposalCell: UITableViewCell {

    static let reuseIdentifier = "NextDisposalCell"

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    override init(style: UITableViewCell.CellStyle, reuseIdentifier: String?) {
        super.init(style: UITableViewCell.CellStyle.subtitle, reuseIdentifier: reuseIdentifier)
        setupCell()
    }

    func setupCell() {
        backgroundColor = .testAppBlue
        selectionStyle = .none

        textLabel?.textColor = .white
        textLabel?.font = .h2()

    }

}
