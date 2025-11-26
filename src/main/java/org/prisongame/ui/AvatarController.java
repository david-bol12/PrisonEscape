package org.prisongame.ui;

import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;
import org.prisongame.map.GameMapState;
import org.prisongame.map.Location;

import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Map;

public class AvatarController {

    final HashMap<Location, Point2D.Double> locationPoints = new HashMap<>(Map.of(
            // Points are given in ratio of image size
            Location.SHOWERS, new Point2D.Double(0.355, 0.86),
            Location.CELL_BLOCK, new Point2D.Double(0.112, 0.55),
            Location.GUARDS_QUARTERS, new Point2D.Double(0.375, 0.26),
            Location.HALLWAY, new Point2D.Double(0.495, 0.53),
            Location.STAIRS, new Point2D.Double(0.7, 0.28),
            Location.CANTEEN, new Point2D.Double(0.615, 0.86),
            Location.YARD, new Point2D.Double(0.875, 0.55)
    ));

    private final ImageView avatar;
    private Point2D.Double pos;
    private Location location;

    public AvatarController(ImageView avatar, Location location, Bounds mapBounds) {
        for (Location cell : GameMapState.getCells()) {
            locationPoints.put(cell, new Point2D.Double(0.112, 0.55));
        }
        this.avatar = avatar;
        setLocation(location, mapBounds);
    }

    public void setPos(Point2D.Double pos) {
        this.pos = pos;
        avatar.setX(pos.x);
        avatar.setY(pos.y);
    }

    public Point2D.Double getPos() {
        return pos;
    }

    public void setLocation(Location location, Bounds mapBounds) {
        this.location = location;
        double x = locationPoints.get(location).x * mapBounds.getWidth();
        double y = locationPoints.get(location).y * mapBounds.getHeight();
        setPos(new Point2D.Double(x, y));
    }

    public Location getLocation() {
        return location;
    }
}
