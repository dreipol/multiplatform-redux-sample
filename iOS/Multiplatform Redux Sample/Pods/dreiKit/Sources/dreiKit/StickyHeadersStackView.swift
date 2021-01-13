//
//  StickyHeadersStackView.swift
//  dreiKit
//
//  Created by Nils Becker on 14.09.20.
//

import UIKit

public class StickyHeadersStackView: UIScrollView {
    let stackView = UIStackView.autoLayout(axis: .vertical)

    private var tagCounter = 0x4eade9
    private var headerTags = Set<Int>()

    public init() {
        super.init(frame: .zero)
        translatesAutoresizingMaskIntoConstraints = false

        delegate = self
        addSubview(stackView)
        stackView.fitVerticalScrollView()
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    public func addArrangedSubview(_ view: UIView) {
        stackView.addArrangedSubview(view)
    }

    public func addHeader(_ header: UIView) {
        header.tag = tagCounter
        headerTags.insert(tagCounter)
        tagCounter += 1

        stackView.addArrangedSubview(header)
    }

    public func addSpace(_ height: CGFloat) {
        stackView.addSpace(height)
    }

    public override func layoutSubviews() {
        super.layoutSubviews()

        contentSize = stackView.frame.size
    }

    public override func scrollRectToVisible(_ rect: CGRect, animated: Bool) {
        let headerOffset = stackView.arrangedSubviews.prefix(while: { $0.frame.minY <= rect.minY })
            .last(where: { headerTags.contains($0.tag) })?.frame.height ?? 0
        super.scrollRectToVisible(rect.inset(by: UIEdgeInsets(top: -headerOffset, left: 0, bottom: 0, right: 0)), animated: animated)
    }
}

extension StickyHeadersStackView: UIScrollViewDelegate {
    public func scrollViewDidScroll(_ scrollView: UIScrollView) {
        var lastHeader: UIView?
        var pushingHeader: UIView?
        var height: CGFloat = 0

        for subview in stackView.arrangedSubviews {
            if headerTags.contains(subview.tag) {
                if height <= scrollView.contentOffset.y || scrollView.contentOffset.y < 0 {
                    lastHeader = subview
                } else {
                    pushingHeader = subview
                }
            }

            height += subview.frame.size.height
            if height > scrollView.contentOffset.y + (lastHeader?.frame.size.height ?? 0) {
                break
            }
        }

        if let lastHeader = lastHeader {
            stackView.bringSubviewToFront(lastHeader)
            if let pushingHeader = pushingHeader, lastHeader != pushingHeader {
                lastHeader.frame.origin.y = pushingHeader.frame.origin.y - lastHeader.frame.size.height
            } else {
                lastHeader.frame.origin.y = max(scrollView.contentOffset.y, 0)
            }
        }
    }
}
