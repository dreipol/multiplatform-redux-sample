// From https://github.com/openmobilemaps/maps-core/tree/main/ios
import MapCore
import MapCoreSharedModule
import UIKit

private let zurichLat = 47.3744489
private let zurichLon = 8.5410422

class MapViewController: UIViewController {
  var mapConfig = MCMapConfig(mapCoordinateSystem: MCCoordinateSystemFactory.getEpsg3857System())
  lazy var mapView = MCMapView(mapConfig: mapConfig)
  lazy var loader = MCTextureLoader()
  lazy var rasterLayer = MCTiled2dMapRasterLayerInterface.create(TiledLayerConfig(),
                                                    textureLoader: loader)

  override func viewDidLoad() {
    super.viewDidLoad()
    view.addSubview(mapView)
    mapView.fitSuperview()

    mapView.add(layer: rasterLayer?.asLayerInterface())
    mapView.camera.move(toCenterPosition: .init(systemIdentifier: MCCoordinateSystemIdentifiers.epsg4326(),
                                                    x: zurichLon,
                                                    y: zurichLat,
                                                    z: 0), animated: true)
    mapView.frame = CGRect(x: 0, y: 0, width: 300, height: 300)
  }
}
