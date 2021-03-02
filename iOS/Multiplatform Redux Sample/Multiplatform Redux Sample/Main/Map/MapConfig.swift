// From https://github.com/openmobilemaps/maps-core/tree/main/ios

import MapCore
import MapCoreSharedModule

class TiledLayerConfig: MCTiled2dMapLayerConfig {
    // Defines both an additional scale factor for the tiles, as well as how many
         // layers above the ideal one should be loaded an displayed as well.
    func getZoomInfo() -> MCTiled2dMapZoomInfo {
      MCTiled2dMapZoomInfo(zoomLevelScaleFactor: 1.2,
                           numDrawPreviousLayers: 1)
    }

    // Defines the bounds of the layer
    func getBounds() -> MCRectCoord {
      let identifer = MCCoordinateSystemIdentifiers.epsg3857()
      let topLeft = MCCoord(systemIdentifier: identifer,
                            x: -20037508.34,
                            y: 20037508.34, z: 0.0)
      let bottomRight = MCCoord(systemIdentifier: identifer,
                                x: 20037508.34,
                                y: -20037508.34, z: 0.0)
      return MCRectCoord(
        topLeft: topLeft,
        bottomRight: bottomRight)
    }

    // Defines the url-pattern to load tiles. Enter a valid OSM tile server here
    func getTileUrl(_ x: Int32, y: Int32, zoom: Int32) -> String {
      return "https://example.com/tiles/\(zoom)/\(x)/\(y).png"
    }

    // Pattern to create a tile identifier used internally
    func getTileIdentifier(_ x: Int32, y: Int32, zoom: Int32) -> String {
        "ExampleTile<\(zoom)-\(x)-\(y)>"
    }

    // List of valid zoom-levels and their target zoom-value, the tile size in
    // the layers coordinate system, the number of tiles on that level and the
    // zoom identifier used for the tile-url (see getTileUrl above)
    func getZoomLevelInfos() -> [MCTiled2dMapZoomLevelInfo] {
        [
            .init(zoom: 500_000_000, tileWidthLayerSystemUnits: 40_075_016, numTilesX: 1, numTilesY: 1, zoomLevelIdentifier: 0),
            .init(zoom: 250_000_000, tileWidthLayerSystemUnits: 20_037_508, numTilesX: 2, numTilesY: 2, zoomLevelIdentifier: 1),
            .init(zoom: 150_000_000, tileWidthLayerSystemUnits: 10_018_754, numTilesX: 4, numTilesY: 4, zoomLevelIdentifier: 2),
            .init(zoom: 70_000_000, tileWidthLayerSystemUnits: 5_009_377.1, numTilesX: 8, numTilesY: 8, zoomLevelIdentifier: 3),
            .init(zoom: 35_000_000, tileWidthLayerSystemUnits: 2_504_688.5, numTilesX: 16, numTilesY: 16, zoomLevelIdentifier: 4),
            .init(zoom: 15_000_000, tileWidthLayerSystemUnits: 1_252_344.3, numTilesX: 32, numTilesY: 32, zoomLevelIdentifier: 5),
            .init(zoom: 10_000_000, tileWidthLayerSystemUnits: 626_172.1, numTilesX: 64, numTilesY: 64, zoomLevelIdentifier: 6),
            .init(zoom: 4_000_000, tileWidthLayerSystemUnits: 313_086.1, numTilesX: 128, numTilesY: 128, zoomLevelIdentifier: 7),
            .init(zoom: 2_000_000, tileWidthLayerSystemUnits: 156_543, numTilesX: 256, numTilesY: 256, zoomLevelIdentifier: 8),
            .init(zoom: 1_000_000, tileWidthLayerSystemUnits: 78271.5, numTilesX: 512, numTilesY: 512, zoomLevelIdentifier: 9),
            .init(zoom: 500_000, tileWidthLayerSystemUnits: 39135.8, numTilesX: 1024, numTilesY: 1024, zoomLevelIdentifier: 10),
            .init(zoom: 250_000, tileWidthLayerSystemUnits: 19567.9, numTilesX: 2048, numTilesY: 2048, zoomLevelIdentifier: 11),
            .init(zoom: 150_000, tileWidthLayerSystemUnits: 9783.94, numTilesX: 4096, numTilesY: 4096, zoomLevelIdentifier: 12),
            .init(zoom: 70000, tileWidthLayerSystemUnits: 4891.97, numTilesX: 8192, numTilesY: 8192, zoomLevelIdentifier: 13),
            .init(zoom: 35000, tileWidthLayerSystemUnits: 2445.98, numTilesX: 16384, numTilesY: 16384, zoomLevelIdentifier: 14),
            .init(zoom: 15000, tileWidthLayerSystemUnits: 1222.99, numTilesX: 32768, numTilesY: 32768, zoomLevelIdentifier: 15),
            .init(zoom: 8000, tileWidthLayerSystemUnits: 611.496, numTilesX: 65536, numTilesY: 65536, zoomLevelIdentifier: 16),
            .init(zoom: 4000, tileWidthLayerSystemUnits: 305.748, numTilesX: 131_072, numTilesY: 131_072, zoomLevelIdentifier: 17),
            .init(zoom: 2000, tileWidthLayerSystemUnits: 152.874, numTilesX: 262_144, numTilesY: 262_144, zoomLevelIdentifier: 18),
            .init(zoom: 1000, tileWidthLayerSystemUnits: 76.437, numTilesX: 524_288, numTilesY: 524_288, zoomLevelIdentifier: 19),
        ]
    }
}
