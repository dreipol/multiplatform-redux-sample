// Copyright 2016-present the Material Components for iOS authors. All Rights Reserved.
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

#import <CoreGraphics/CoreGraphics.h>
#import <UIKit/UIKit.h>

#import "MaterialElevation.h"
#import "MaterialInk.h"
#import "MaterialRipple.h"
#import "MaterialShadowElevations.h"
#import "MaterialShapes.h"

/**
 A Material flat, raised or floating button.

 All buttons display animated ink splashes when the user interacts with the button.

 The title color of the button set to have an accessible contrast ratio with the button's
 background color. To ensure this works for flat buttons (with transparent background), the caller
 is responsible for setting (and updating, if necessary) the button's underlyingColor property.

 All buttons set the exclusiveTouch property to YES by default, which prevents users from
 simultaneously interacting with a button and other UI elements.

 @see https://material.io/go/design-buttons
 */
@interface MDCButton : UIButton <MDCElevatable, MDCElevationOverriding>

/** The ripple style of the button. */
@property(nonatomic, assign) MDCRippleStyle rippleStyle;

/**
 The color of the ripple.

 @note Defaults to a transparent black.
 */
@property(nonatomic, strong, null_resettable) UIColor *rippleColor;

/**
 The maximum radius the ripple can expand to.

 @note This property is ignored if @c rippleStyle is set to @c MDCRippleStyleBounded.

 @note Defaults to 0.
 */
@property(nonatomic, assign) CGFloat rippleMaximumRadius;

/**
 This property determines if an @c MDCButton should use the @c MDCInkView behavior or not.

 By setting this property to @c YES, @c MDCStatefulRippleView is used to provide the user visual
 touch feedback, instead of the legacy @c MDCInkView.

 @note Defaults to @c NO.
 */
@property(nonatomic, assign) BOOL enableRippleBehavior;

/**
 The alpha value that will be applied when the button is disabled. Most clients can leave this as
 the default value to get a semi-transparent button automatically.
 */
@property(nonatomic) CGFloat disabledAlpha;

/**
 If true, converts the button title to uppercase. Changing this property to NO will update the
 current title string.

 Default is YES.
 */
@property(nonatomic, getter=isUppercaseTitle) BOOL uppercaseTitle UI_APPEARANCE_SELECTOR;

/**
 A Boolean value that determines whether the visible area is centered in the bounds of the view.

 If set to YES, the visible area is centered in the bounds of the view, which is often used to
 configure invisible tappable area. If set to NO, the visible area fills its bounds. This property
 doesn't affect the result of @c sizeThatFits:.

 The default value is @c NO.
*/
@property(nonatomic, assign) BOOL centerVisibleArea;

/**
 The edges of this guide are constrained to equal the edges of the visible area
 when @c centerVisibleArea is @c YES.

 @note If centerVisibleArea is @c NO then visibleAreaLayoutGuide is nil.
*/
@property(nonatomic, readonly, strong, nullable) UILayoutGuide *visibleAreaLayoutGuide;

/**
 The default content edge insets of the button. They are set at initialization time.
 */
@property(nonatomic, readonly) UIEdgeInsets defaultContentEdgeInsets;

/**
 The offset (in points) of the button's inkView or rippleView (depending on which is being used -
 see @c enableRippleBehavior)

 Default is CGSizeZero.
 */
@property(nonatomic) CGSize inkViewOffset;

/**
 The inset or outset margins for the rectangle surrounding the button’s ripple.
 */
@property(nonatomic, assign) UIEdgeInsets rippleEdgeInsets;

/**
 The minimum size of the button’s alignment rect. If either the height or width are non-positive
 (negative or zero), they will be ignored and that axis will adjust to its content size.

 Defaults to CGSizeZero.
 */
@property(nonatomic, assign) CGSize minimumSize UI_APPEARANCE_SELECTOR;

/**
 The maximum size of the button’s alignment rect. If either the height or width are non-positive
 (negative or zero), they will be ignored and that axis will adjust to its content size. Setting a
 maximum size may result in image clipping or text truncation.

 Defaults to CGSizeZero.
 */
@property(nonatomic, assign) CGSize maximumSize UI_APPEARANCE_SELECTOR;

/**
 Setting this property to @c YES when the button's @c titleLabel is multi-line (i.e. when @c
 numberOfLines is not equal to 1) will result in the button inferring what its size should be and
 then setting both the @c minimumSize and @c maximumSize to that value. Setting this property back
 to @c NO will result in @c maximumSize and @c minimumSize being reset to @c CGSizeZero.

 In both Manual Layout and Auto Layout environments the inferred height is a function of the width.
 In an Auto Layout environment the width will depend on the constraints placed on the view. In a
 Manual Layout environment the current width will be assumed to be the preferred width, so it is
 important to make sure the button's width is set to an appropriate value before turning this flag
 on. In an Auto Layout environment, the view will likely resize itself as needed when this flag is
 turned on. In a Manual Layout environment, you will likely have to call @c -sizeToFit after turning
 this flag on.

 Defaults to NO.
 */
@property(nonatomic, assign) BOOL inferMinimumAndMaximumSizeWhenMultiline;

/**
 The apparent background color as seen by the user, i.e. the color of the view behind the button.

 The underlying color hint is used by buttons to calculate accessible title text colors when in
 states with transparent background colors. The hint is used whenever the button changes state such
 that the background color changes, for example, setting the background color or disabling the
 button.

 For flat buttons, this is the color of both the surrounding area and the button's background.
 For raised and floating buttons, this is the color of view underneath the button.

 The default is nil.  If left unset, buttons will likely have an incorrect appearance when
 disabled. Additionally, flat buttons might have text colors with low accessibility.
 */
@property(nonatomic, strong, nullable) UIColor *underlyingColorHint;

/*
 Indicates whether the button should automatically update its font when the device’s
 UIContentSizeCategory is changed.

 This property is modeled after the adjustsFontForContentSizeCategory property in the
 UIContentSizeCategoryAdjusting protocol added by Apple in iOS 10.0.

 If set to YES, this button will base its text font on MDCFontTextStyleButton.

 Defaults value is NO.
 */
@property(nonatomic, readwrite, setter=mdc_setAdjustsFontForContentSizeCategory:)
    BOOL mdc_adjustsFontForContentSizeCategory UI_APPEARANCE_SELECTOR;

/**
 Affects the fallback behavior for when a scaled font is not provided.

 If @c YES, the font size will adjust even if a scaled font has not been provided for
 a given @c UIFont property on this component.

 If @c NO, the font size will only be adjusted if a scaled font has been provided.

 Default value is @c YES.
 */
@property(nonatomic, assign) BOOL adjustsFontForContentSizeCategoryWhenScaledFontIsUnavailable;

/**
 The shape generator used to define the button's shape.

 When the shapeGenerator is nil, MDCButton will use the default underlying layer with
 its default settings.

 @note If a layer property is explicitly set after the shapeGenerator has been set,
       it can lead to unexpected behavior.

 @note When @c centerVisibleArea is set to YES, this property can no longer be set.

 Default value for shapeGenerator is nil.
 */
@property(nullable, nonatomic, strong) id<MDCShapeGenerating> shapeGenerator;

/**
 A block that is invoked when the MDCButton receives a call to @c
 traitCollectionDidChange:. The block is called after the call to the superclass.
 */
@property(nonatomic, copy, nullable) void (^traitCollectionDidChangeBlock)
    (MDCButton *_Nonnull button, UITraitCollection *_Nullable previousTraitCollection);

/**
 A color used as the button's @c backgroundColor for @c state.

 @param state The state.
 @return The background color.
 */
- (nullable UIColor *)backgroundColorForState:(UIControlState)state;

/**
 A color used as the button's @c backgroundColor.

 If left unset or reset to nil for a given state, then a default blue color is used.

 @param backgroundColor The background color.
 @param state The state.
 */
- (void)setBackgroundColor:(nullable UIColor *)backgroundColor
                  forState:(UIControlState)state UI_APPEARANCE_SELECTOR;

/* Convenience for `setBackgroundColor:backgroundColor forState:UIControlStateNormal`. */
- (void)setBackgroundColor:(nullable UIColor *)backgroundColor;

/** Sets the enabled state with optional animation. */
- (void)setEnabled:(BOOL)enabled animated:(BOOL)animated;

/**
 Returns the elevation for a particular control state.

 The default values depend on the kind of button, for example, flat buttons in the
 UIControlStateNormal state have zero elevation.

 @param state The control state to retrieve the elevation.
 @return The elevation for the requested state.
 */
- (MDCShadowElevation)elevationForState:(UIControlState)state;

/**
 Sets the elevation for a particular control state.

 @param elevation The elevation to set.
 @param state The state to set.
 */
- (void)setElevation:(MDCShadowElevation)elevation forState:(UIControlState)state;

/**
 A color used as the button's @c borderColor for @c state.

 @param state The state.
 @return The border color.
 */
- (nullable UIColor *)borderColorForState:(UIControlState)state;

/**
 Sets the border color for a particular control state. Sets the @c borderColor of the layer.

 @param borderColor The border color to set.
 @param state The state to set.
 */
- (void)setBorderColor:(nullable UIColor *)borderColor
              forState:(UIControlState)state UI_APPEARANCE_SELECTOR;

/**
 A color used as the button's imageView tint color @c imageTintColor for @c state.

 If no image tint color has been set for a given state, the returned value will fall back to the
 value set for UIControlStateNormal.

 @param state The state.
 @return The tint color.
 */
- (nullable UIColor *)imageTintColorForState:(UIControlState)state;

/**
 Sets the image view tint color for a particular control state.

 If left unset or reset to nil for a given state, it falls back to UIControlStateNormal setting.

 @param imageTintColor The imageView tint color to set.
 @param state The state to set.
 */
- (void)setImageTintColor:(nullable UIColor *)imageTintColor forState:(UIControlState)state;

/**
 The value set for the button's @c borderWidth for @c state.

 @param state The state.
 @return The border width.
 */
- (CGFloat)borderWidthForState:(UIControlState)state;

/**
 Sets the border width for a particular control state. Sets the @c borderWidth of the layer.

 @param borderWidth The border width to set.
 @param state The state to set.
 */
- (void)setBorderWidth:(CGFloat)borderWidth forState:(UIControlState)state UI_APPEARANCE_SELECTOR;

/**
 Sets this button's layer's shadowColor for the specified control state.

 During initialization, the value for @c UIControlStateNormal is set to the value of this button's
 layer's @c shadowColor. Providing a @c nil value for @c shadowColor will remove the shadow color
 mapping for the specified state.

 If the color is not set for a specified state, the default behavior is to use the color associated
 with @c UIControlStateNormal. If the color for @c UIControlStateNormal is not set, then @c nil will
 be used.

 @param shadowColor The shadow color to use for the specified state.
 @param state       The state that uses the specified color. The possible values are described in
                    @c UIControlState.
 */
- (void)setShadowColor:(nullable UIColor *)shadowColor
              forState:(UIControlState)state UI_APPEARANCE_SELECTOR;

/**
 The color used as this button's layer's @c shadowColor for the specified control state.

 During initialization, the value for @c UIControlStateNormal is set to the value of this view's
 layer's @c shadowColor.

 If the color is not set for a specified state, the default behavior is to use the color associated
 with @c UIControlStateNormal. If the color for @c UIControlStateNormal has not been set, then
 @c nil is returned.

 @param state The state that uses the shadow color. The possible values are described in
              @c UIControlState.
 @return      The shadow color for the specified state. If no shadow color has been set for the
              specific state, this method returns the shadow color associated with the
              @c UIControlStateNormal state.

 @return The shadow color.
 */
- (nullable UIColor *)shadowColorForState:(UIControlState)state;

#pragma mark - UIButton changes

/**
 From UIButton's documentation: "If you subclass UIButton, this method does not return an instance
 of your subclass. If you want to create an instance of a specific subclass, you must alloc/init
 the button directly."
 */
+ (nonnull instancetype)buttonWithType:(UIButtonType)buttonType NS_UNAVAILABLE;

@end

@interface MDCButton (ToBeDeprecated)

/**
 Enables the state-based font behavior of the receiver.

 If @c NO, then @c titleFont:forState: and @c setTitleFont:forState: have no effect.  Defaults to
 @c YES.

 @note This API will eventually be deprecated and removed.
 */
@property(nonatomic, assign) BOOL enableTitleFontForState;

/**
 The inset margins for the rectangle surrounding all of the button’s visual representation.
 Use this property when you wish to have the touch target (frame) be larger than the
 visible content.

 A positive value shrinks the visible area of the button. A negative value expands the visible area
 of the button.

 The button uses this property to determine intrinsicContentSize and sizeThatFits:.

 @note This API will be deprecated and removed. Consider using @c centerVisibleArea.

 Default is UIEdgeInsetsZero.
*/
@property(nonatomic, assign) UIEdgeInsets visibleAreaInsets;

/**
 The font used by the button's @c title.

 If left unset or reset to nil for a given state, then a default font is used.

 @param font The font.
 @param state The state.

 @note This API will eventually be deprecated and removed.
 */
- (void)setTitleFont:(nullable UIFont *)font forState:(UIControlState)state UI_APPEARANCE_SELECTOR;

/**
 The font used by the button's @c title for @c state.

 @param state The state.
 @return The font.

 @note This API will eventually be deprecated and removed.
 */
- (nullable UIFont *)titleFontForState:(UIControlState)state;

/**
 If @c true, @c accessiblityTraits will always include @c UIAccessibilityTraitButton.
 If @c false, @c accessibilityTraits will inherit its behavior from @c UIButton.

 @note Defaults to true.
 @note This API is intended as a migration flag to restore @c UIButton behavior to @c MDCButton. In
       a future version, this API will eventually be deprecated and then deleted.
 */
@property(nonatomic, assign) BOOL accessibilityTraitsIncludesButton;

/** The ink style of the button. */
@property(nonatomic, assign) MDCInkStyle inkStyle UI_APPEARANCE_SELECTOR;

/** The ink color of the button. */
@property(nonatomic, strong, null_resettable) UIColor *inkColor UI_APPEARANCE_SELECTOR;

/*
 Maximum radius of the button's ink. If the radius <= 0 then half the length of the diagonal of
 self.bounds is used. This value is ignored if button's @c inkStyle is set to |MDCInkStyleBounded|.
 */
@property(nonatomic, assign) CGFloat inkMaxRippleRadius UI_APPEARANCE_SELECTOR;

@end

@interface MDCButton (Deprecated)

/**
 Insets to apply to the button’s hit area.

 Allows the button to detect touches outside of its bounds. A negative value indicates an
 extension past the bounds.

 Default is UIEdgeInsetsZero.
 */
@property(nonatomic) UIEdgeInsets hitAreaInsets __deprecated_msg("Use centerVisibleArea instead.");

@end
