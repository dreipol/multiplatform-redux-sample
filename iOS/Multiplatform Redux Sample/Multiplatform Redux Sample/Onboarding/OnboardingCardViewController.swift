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
    var pages = [UIViewController]()
    let pageControl = UIPageControl()
    let closeButton = UIButton.autoLayout()

    override func viewDidLoad() {
        super.viewDidLoad()

        self.delegate = self
        let initialPage = 0

        addCloseButton()

        // add the individual viewControllers to the pageViewController
        pages.append(EnterZipViewController())
        pages.append(SelectDisposalTypesViewController())
        pages.append(AddNotificationViewController())
        pages.append(OnboardingFinishViewController())
        setViewControllers([pages[initialPage]], direction: .forward, animated: true, completion: nil)

        addPageIndication(initialPage)
    }

    func render(onboardingViewState: OnboardingViewState) {
        print("OnboardingCardViewController")
        closeButton.isHidden = !onboardingViewState.closeEnabled
        //disable vertical page scrolling if the button is not enabled
        dataSource = onboardingViewState.closeEnabled ? self : nil
    }

    func setCurrentPage(screen: Screen) {
        print("setCurrentPage: \(screen)")
        if let onboardingScreen = screen as? OnboardingScreen {
            let newIndex = Int(onboardingScreen.step) - 1

            setViewControllers([pages[Int(onboardingScreen.step) - 1]],
                               direction: newIndex > pageControl.currentPage ? .forward : .reverse,
                               animated: true,
                               completion: nil)
            self.pageControl.currentPage = newIndex
        }
    }

    @objc
    func closeTapped() {
        _ = dispatch(NavigationAction.onboardingEnd)
    }

    fileprivate func addCloseButton() {
        closeButton.addTarget(self, action: #selector(closeTapped), for: .touchUpInside)
        closeButton.setImage(UIImage(named: "iconLightCloseButton"), for: .normal)
        closeButton.isHidden = true
        view.addSubview(closeButton)
        closeButton.topAnchor.constraint(equalTo: view.topAnchor, constant: kUnit3).isActive = true
        closeButton.widthAnchor.constraint(equalToConstant: kUnit5).isActive = true
        closeButton.heightAnchor.constraint(equalToConstant: kUnit5).isActive = true
        closeButton.trailingAnchor.constraint(equalTo: view.trailingAnchor, constant: -kUnit2).isActive = true
    }

    fileprivate func addPageIndication(_ initialPage: Int) {
        pageControl.frame = CGRect()
        pageControl.currentPageIndicatorTintColor = UIColor.testAppGreen
        pageControl.pageIndicatorTintColor = UIColor.testAppGreenLight
        pageControl.numberOfPages = pages.count
        pageControl.currentPage = initialPage
        view.addSubview(pageControl)

        pageControl.translatesAutoresizingMaskIntoConstraints = false
        pageControl.topAnchor.constraint(equalTo: view.topAnchor, constant: 24).isActive = true
        pageControl.widthAnchor.constraint(equalTo: view.widthAnchor, constant: -20).isActive = true
        pageControl.heightAnchor.constraint(equalToConstant: 20).isActive = true
        pageControl.centerXAnchor.constraint(equalTo: view.centerXAnchor).isActive = true
    }
}

extension OnboardingCardViewController: UIPageViewControllerDataSource, UIPageViewControllerDelegate {

    func pageViewController(_ pageViewController: UIPageViewController, willTransitionTo pendingViewControllers: [UIViewController]) {
        // the page view controller is about to transition to a new page, so take note
        // of the index of the page it will display.  (We can't update our currentIndex
        // yet, because the transition might not be completed - we will check in didFinishAnimating:)
        if let itemController = pendingViewControllers[0] as? BaseOnboardingViewController {
            nextIndex = itemController.getIndex()
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
