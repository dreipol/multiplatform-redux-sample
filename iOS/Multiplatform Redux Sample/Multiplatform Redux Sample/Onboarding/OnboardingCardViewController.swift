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

    var pages = [UIViewController]()
    let pageControl = UIPageControl()

    override func viewDidLoad() {
        super.viewDidLoad()

        self.dataSource = self
        self.delegate = self
        let initialPage = 0

        // add the individual viewControllers to the pageViewController
        pages.append(EnterZipViewController())
        pages.append(SelectDisposalTypesViewController())
        pages.append(AddNotificationViewController())
        pages.append(OnboardingFinishViewController())
        setViewControllers([pages[initialPage]], direction: .forward, animated: true, completion: nil)

        // pageControl
        pageControl.frame = CGRect()
        pageControl.currentPageIndicatorTintColor = UIColor.tetstAppGruen
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

    func render(onboardingViewState: OnboardingViewState) {
        print("OnboardingCardViewController")
    }
}

extension OnboardingCardViewController: UIPageViewControllerDataSource, UIPageViewControllerDelegate {

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
        // set the pageControl.currentPage to the index of the current viewController in pages
        if let viewControllers = pageViewController.viewControllers {
            if let viewControllerIndex = self.pages.firstIndex(of: viewControllers[0]) {
                self.pageControl.currentPage = viewControllerIndex
            }
        }
    }
}
