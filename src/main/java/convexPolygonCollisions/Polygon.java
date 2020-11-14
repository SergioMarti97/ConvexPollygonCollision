package convexPolygonCollisions;

import engine.gfx.HexColors;
import engine.gfx.Renderer;
import engine.vectors.points2d.Vec2df;

/**
 * This class represents a Polygon
 * a polygon is conformed by points and stretch lines
 * which connect the points
 *
 * @class Polygon
 * @author Sergio Martí Torregrosa
 * @date 14/11/2020
 */
public class Polygon {

    /**
     * The transformed points of the polygon
     */
    private Vec2df[] p;

    /**
     * The "model" of the shape
     */
    private Vec2df[] o;

    /**
     * The position is space of the shape
     */
    private Vec2df position;

    /**
     * The direction of the shape
     */
    private float angle;

    /**
     * The flag to indicate overlap has occurred
     */
    private boolean overlap;

    /**
     * Full parameter constructor
     * @param p the transformed points
     * @param o  the "model" of the shape
     * @param position the position of the shape
     * @param angle the direction of the shape
     * @param overlap flag to indicate overlap has occurred
     */
    public Polygon(Vec2df[] p, Vec2df[] o, Vec2df position, float angle, boolean overlap) {
        this.p = p;
        this.o = o;
        this.position = position;
        this.angle = angle;
        this.overlap = overlap;
    }

    /**
     * Full parameter constructor,
     * but this constructor makes new instances for the objects
     * @param position the position of the shape
     * @param p the transformed points
     * @param o  the "model" of the shape
     * @param angle the direction of the shape
     * @param overlap flag to indicate overlap has occurred
     */
    public Polygon(Vec2df position, Vec2df[] p, Vec2df[] o, float angle, boolean overlap) {
        this.position = new Vec2df(position);
        this.p = copyVec2dfArray(p);
        this.o = copyVec2dfArray(o);
        this.angle = angle;
        this.overlap = overlap;
    }

    /**
     * Normal constructor
     * @param position position of the shape
     * @param p points of the shape
     * @param o model of the polygon
     * @param angle the angle of the polygon
     */
    public Polygon(Vec2df position, Vec2df[] p, Vec2df[] o, float angle) {
        this(position, p, o, angle, false);
    }

    /**
     * Copy constructor
     * @param polygon the instance of the Polygon class to copy the values
     */
    public Polygon(Polygon polygon) {
        this(polygon.getPosition(), polygon.getP(), polygon.getO(), polygon.getAngle(), polygon.isOverlap());
    }

    /**
     * This method copies making a new instance with
     * the same values of an Vec2f array.
     * @param vec2dfs the Vec2df array
     * @return a new Vec2df array with new instances but the same values
     */
    private Vec2df[] copyVec2dfArray(Vec2df[] vec2dfs) {
        Vec2df[] p2 = new Vec2df[vec2dfs.length];
        for ( int i = 0; i < vec2dfs.length; i++ ) {
            p2[i] = new Vec2df(vec2dfs[i]);
        }
        return p2;
    }

    /**
     * This method updates the points of the polygon
     * by the position and the angle.
     * This method makes the 2D rotation and the translation
     */
    public void update() {
        for ( int i = 0; i < o.length; i++ ) {
            p[i].setX((float)(o[i].getX() * Math.cos(angle) - o[i].getY() * Math.sin(angle)) + position.getX());
            p[i].setY((float)(o[i].getX() * Math.sin(angle) + o[i].getY() * Math.cos(angle)) + position.getY());
        }
    }

    /**
     * This method draws the polygon on screen
     * Only draws a wire polygon and a line from the center
     * to the first point
     * @param r the renderer class with all drawing methods
     */
    public void drawYourSelf(Renderer r) {
        for (int i = 0; i < p.length; i++) {
            r.drawLine(
                    (int)p[i].getX(),
                    (int)p[i].getY(),
                    (int)p[(i + 1) % p.length].getX(),
                    (int)p[(i + 1) % p.length].getY(),
                    (overlap ? HexColors.RED : HexColors.WHITE)
            );
        }
        r.drawLine(
                (int)p[0].getX(),
                (int)p[0].getY(),
                (int)position.getX(),
                (int)position.getY(),
                (overlap ? HexColors.RED : HexColors.WHITE)
        );
    }

    @Override
    public String toString() {
        StringBuilder strPoints = new StringBuilder();
        for ( Vec2df p : this.p ) {
            strPoints.append(' ').append(p.toString()).append(" |");
        }
        StringBuilder strModel = new StringBuilder();
        for ( Vec2df o : this.o ) {
            strModel.append(' ').append(o.toString()).append(" |");
        }
        return "points (" + p.length + "): " + strPoints.toString() + " model (" + o.length + "): " + strModel.toString() +
                "position: " + position + " angle: " + angle + " is overlap? " + ( overlap ? "true" : "false");
    }

    ////////////////////////////////////////////////////////////////

    public Vec2df[] getP() {
        return p;
    }

    public Vec2df[] getO() {
        return o;
    }

    public Vec2df getPosition() {
        return position;
    }

    public float getAngle() {
        return angle;
    }

    public boolean isOverlap() {
        return overlap;
    }

    public void setP(Vec2df[] p) {
        this.p = p;
    }

    public void setO(Vec2df[] o) {
        this.o = o;
    }

    public void setPosition(Vec2df position) {
        this.position = position;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public void setOverlap(boolean overlap) {
        this.overlap = overlap;
    }
}
