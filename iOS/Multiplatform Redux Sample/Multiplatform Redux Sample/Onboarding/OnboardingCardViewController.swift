//
//  OnboardingCardViewController.swift
//  Multiplatform Redux Sample
//
//  Created by Julia Strasser on 30.10.20.
//

import Foundation
import UIKit
import ReduxSampleShared

class OnboardingCardViewController: PagePresenterViewController<OnboardingView>, OnboardingView {
    override var viewPresenter: Presenter<OnboardingView> { OnboardingViewKt.onboardingPresenter }
    var currentIndex = 0
    var nextIndex = 0

    let pages: [UIViewController] = [
        EnterZipViewController(),
        SelectDisposalTypesViewController(),
        AddNotificationViewController(),
        OnboardingFinishViewController(),
    ]
    let pageControl = UIPageControl()
    let closeButton = UIButton.autoLayout()
    let backButton = UIButton.autoLayout()

    override func viewDidLoad() {
        super.viewDidLoad()

        self.delegate = self
        view.backgroundColor = .primaryDark

        addCloseButton()
        addBackButton()

        let initialPage = 0
        setViewControllers([pages[initialPage]], direction: .forward, animated: true, completion: nil)
        addPageIndication(initialPage)
    }

    func render(onboardingViewState: OnboardingViewState) {
        closeButton.isHidden = !onboardingViewState.closeEnabled
        backButton.isHidden = !onboardingViewState.canGoBack
        //disable vertical page scrolling if the button is not enabled
        dataSource = onboardingViewState.closeEnabled ? self : nil
    }

    func setCurrentPage(screen: Screen) {
        if let onboardingScreen = screen as? OnboardingScreen {
            let newIndex = Int(onboardingScreen.step) - 1

            setViewControllers([pages[newIndex]],
                               direction: newIndex > pageControl.currentPage ? .forward : .reverse,
                               animated: true,
                               completion: nil)
            updatePageControl(newIndex)
        }
    }

    @objc
    func closeTapped() {
        _ = dispatch(NavigationAction.onboardingEnd)
    }

    @objc
    func backTapped() {
        _ = dispatch(NavigationAction.back)
    }

    private func updatePageControl(_ newIndex: Int) {
        pageControl.currentPage = newIndex
        if #available(iOS 14.0, *) {
            for i in pages.indices {
                pageControl.setIndicatorImage(UIImage(systemName: "circle"), forPage: i)
            }
            pageControl.setIndicatorImage(UIImage(systemName: "circle.fill"), forPage: newIndex)
        } else {
            //for earlier version there will be circles with two different colors
            pageControl.pageIndicatorTintColor = .secondarySecondary
            pageControl.currentPageIndicatorTintColor = .white
        }
    }

    private func addCloseButton() {
        closeButton.addTarget(self, action: #selector(closeTapped), for: .touchUpInside)
        closeButton.setImage(UIImage(named: "ic_40_close_button"), for: .normal)
        closeButton.isHidden = true
        view.addSubview(closeButton)
        NSLayoutConstraint.activate([
            closeButton.topAnchor.constraint(equalTo: view.safeAreaLayoutGuide.topAnchor, constant: kUnit2),
            closeButton.widthAnchor.constraint(equalToConstant: kUnit5),
            closeButton.heightAnchor.constraint(equalToConstant: kUnit5),
            closeButton.trailingAnchor.constraint(equalTo: view.trailingAnchor, constant: -kUnit2),
        ])
    }

    private func addBackButton() {
        backButton.addTarget(self, action: #selector(backTapped), for: .touchUpInside)
        backButton.setImage(UIImage(named: "ic_36_chevron_left")?.withTintColor(.primaryPrimary), for: .normal)
        backButton.isHidden = true
        view.addSubview(backButton)
        NSLayoutConstraint.activate([
            backButton.topAnchor.constraint(equalTo: view.safeAreaLayoutGuide.topAnchor, constant: kUnit2),
            backButton.widthAnchor.constraint(equalToConstant: kUnit5),
            backButton.heightAnchor.constraint(equalToConstant: kUnit5),
            backButton.leadingAnchor.constraint(equalTo: view.leadingAnchor, constant: kUnit2),
        ])
    }

    private func addPageIndication(_ initialPage: Int) {
        pageControl.frame = CGRect()
        pageControl.numberOfPages = pages.count
        //Note: the colors are not as specified in the design
        //but the colors were wrong after updating the current page
        pageControl.pageIndicatorTintColor = .secondarySecondary
        pageControl.currentPageIndicatorTintColor = .secondarySecondary
        pageControl.isUserInteractionEnabled = false
        view.addSubview(pageControl)
        pageControl.translatesAutoresizingMaskIntoConstraints = false
        NSLayoutConstraint.activate([
            pageControl.centerYAnchor.constraint(equalTo: closeButton.centerYAnchor),
            pageControl.widthAnchor.constraint(equalTo: view.widthAnchor, constant: -20),
            pageControl.heightAnchor.constraint(equalToConstant: 20),
            pageControl.centerXAnchor.constraint(equalTo: view.centerXAnchor),
        ])

        updatePageControl(initialPage)
    }
}

extension OnboardingCardViewController: UIPageViewControllerDataSource, UIPageViewControllerDelegate {

    func pageViewController(_ pageViewController: UIPageViewController, willTransitionTo pendingViewControllers: [UIViewController]) {
        // the page view controller is about to transition to a new page, so take note
        // of the index of the page it will display.  (We can't update our currentIndex
        // yet, because the transition might not be completed - we will check in didFinishAnimating:)
        if let itemController = pendingViewControllers[0] as? BaseOnboardingViewController {
            nextIndex = itemController.cardIndex
        }
    }

    func pageViewController(_ pageViewController: UIPageViewController,
                            viewControllerBefore viewController: UIViewController) -> UIViewController? {
        if let viewControllerIndex = self.pages.firstIndex(of: viewController) {
            if viewControllerIndex == 0 {
                // beginning of array
                return nil
            } else {
                // go to previous page in array
                return self.pages[viewControllerIndex - 1]
            }
        }
        return nil
    }

    func pageViewController(_ pageViewController: UIPageViewController,
                            viewControllerAfter viewController: UIViewController) -> UIViewController? {
        if let viewControllerIndex = self.pages.firstIndex(of: viewController) {
            if viewControllerIndex < self.pages.count - 1 {
                // go to next page in array
                return self.pages[viewControllerIndex + 1]
            } else {
                // end of array
                return nil
            }
        }
        return nil
    }

    func pageViewController(_ pageViewController: UIPageViewController,
                            didFinishAnimating finished: Bool,
                            previousViewControllers: [UIViewController],
                            transitionCompleted completed: Bool) {
        if completed {
            // check if there was a forward or backward swipe
            if nextIndex > currentIndex {
                _ = dispatch(NavigationAction.onboardingNext)
            } else {
                _ = dispatch(NavigationAction.back)
            }
            //update the indices for further checks
            currentIndex = nextIndex

        }
    }
}
