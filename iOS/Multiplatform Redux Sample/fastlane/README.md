fastlane documentation
================
# Installation

Make sure you have the latest version of the Xcode command line tools installed:

```
xcode-select --install
```

Install _fastlane_ using
```
[sudo] gem install fastlane -NV
```
or alternatively using `brew install fastlane`

# Available Actions
## iOS
### ios test
```
fastlane ios test
```
Runs all the tests
### ios setup_signing
```
fastlane ios setup_signing
```

### ios lint
```
fastlane ios lint
```
Run SwiftLint
### ios lint_autocorrect
```
fastlane ios lint_autocorrect
```
Run SwiftLint autocorrect
### ios stage
```
fastlane ios stage
```
Submit a new Beta Build to Apple TestFlight
### ios production
```
fastlane ios production
```
Submit a new Build (Live-Target) to Apple TestFlight
### ios register_new_device
```
fastlane ios register_new_device
```
Register new device

----

This README.md is auto-generated and will be re-generated every time [fastlane](https://fastlane.tools) is run.
More information about fastlane can be found on [fastlane.tools](https://fastlane.tools).
The documentation of fastlane can be found on [docs.fastlane.tools](https://docs.fastlane.tools).
