//
//  Store+ApplicationState.swift
//  Multiplatform Redux Sample
//
//  Created by Samuel Bichsel on 30.10.20.
//

import Combine
import Foundation
import ReduxSampleShared

extension Store {
    func appStatePublisher() -> StatePublisher<AppState> {
        return StatePublisher(store: self, map: { $0.appState })
    }

    func settingsStatePublisher() -> StatePublisher<SettingsState> {
        return StatePublisher(store: self, map: { $0.appState.settingsState.state })
    }
}

typealias MappingFunc<State: Equatable> = (Store) -> State?

struct StatePublisher<State: Equatable>: Publisher {
    typealias Output = State
    typealias Failure = Never

    let store: Store
    let mappingFunc: MappingFunc<State>

    init(store: Store, map: @escaping MappingFunc<State>) {
        self.store = store
        self.mappingFunc = map
    }

    func receive<S>(subscriber: S) where S: Subscriber, Self.Failure == S.Failure, Self.Output == S.Input {
        let subscription = Subscription<S>(store: store, target: subscriber, mappingFunc: mappingFunc)
        subscriber.receive(subscription: subscription)
    }
}

private extension StatePublisher {
    class Subscription<Target: Subscriber>: Combine.Subscription where Target.Input == Output {
        private var target: Target?
        private var storeSubscription: (() -> KotlinUnit)?
        var last: Output?

        init(store: Store, target: Target, mappingFunc: @escaping MappingFunc<Output>) {
            storeSubscription = store.subscribe {
                if let output = mappingFunc(store), output != self.last {
                    self.last = output
                    _ = target.receive(output)
                }
                return KotlinUnit()
            }
            self.target = target
        }

        func request(_ demand: Subscribers.Demand) {}

        func cancel() {
            target = nil
            _ = storeSubscription?()
            storeSubscription = nil
        }
    }
}
