package convexPolygonCollisions;

import engine.vectors.points2d.Vec2df;

/**
 * This class contains all static methods
 * for build different Polygons.
 * Triangles, rectangles, and any regular polygon
 *
 * @class PolygonFactory
 * @author Sergio Martí Torregrosa
 * @date 14/11/2020
 */
public class PolygonFactory {

    /**
     * This method builds a regular polygon
     * @param numVertices the number of vertices of the polygon
     * @param position the position of the shape
     * @param size the size of the shape
     * @param angle the angle of the shape
     * @return a new regular polygon
     */
    public static Polygon makeRegularPolygon(int numVertices, Vec2df position, float size, float angle) {
        Vec2df[] o = new Vec2df[numVertices];
        Vec2df[] p = new Vec2df[numVertices];
        double theta = Math.PI * 2.0 / (double)numVertices;
        for ( int i = 0; i < numVertices; i++ ) {
            o[i] = new Vec2df(size * (float)Math.cos(theta * i), size * (float)Math.sin(theta * i));
            p[i] = new Vec2df(size * (float)Math.cos(theta * i), size * (float)Math.sin(theta * i));
        }
        return new Polygon(p, o, position, angle, false);
    }

    /**
     * This method builds a regular triangle
     * @param position the position of the triangle
     * @param size the size of the triangle
     * @param angle the angle of the triangle
     * @return a triangle
     */
    public static Polygon makeTriangle(Vec2df position, float size, float angle) {
        return makeRegularPolygon(3, position, size, angle);
    }

    /**
     * This method builds a regular quadrilateral
     * @param position the position of the quad
     * @param size the size of the quad
     * @param angle the angle of the quad
     * @return a polygon which is a quad
     */
    public static Polygon makeQuad(Vec2df position, float size, float angle) {
        return makeRegularPolygon(4, position, size, angle);
    }

}
