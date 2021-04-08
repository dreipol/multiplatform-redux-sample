// Copyright 2020-present the Material Components for iOS authors. All Rights Reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

#import <UIKit/UIKit.h>

@class MDCLegacyInkLayer;

/**
 Delegate protocol for the MDCLegacyInkLayer. Clients may implement this protocol to receive updates
 when ink layer animations start and end.
 */
@protocol MDCLegacyInkLayerDelegate <NSObject>

@optional

/**
 Called when the ink ripple animation begins.

 @param inkLayer The MDCLegacyInkLayer that starts animating.
 */
- (void)legacyInkLayerAnimationDidStart:(nonnull MDCLegacyInkLayer *)inkLayer;

/**
 Called when the ink ripple animation ends.

 @param inkLayer The MDCLegacyInkLayer that ends animating.
 */
- (void)legacyInkLayerAnimationDidEnd:(nonnull MDCLegacyInkLayer *)inkLayer;

@end
