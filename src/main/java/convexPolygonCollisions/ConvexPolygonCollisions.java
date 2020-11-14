package convexPolygonCollisions;

import engine.vectors.points2d.Vec2df;

/**
 * This class contains the four methods explained
 * on the YouTube video of OneLoneCoder channel about
 * convex polygon collisions
 * The different algorithms are encapsulated inside
 * static methods
 *
 * @class ConvexPolygonCollisionsAlgorithms
 * @author Sergio Martí Torregrosa
 * @date 14/11/2020
 */
public class ConvexPolygonCollisions {

    /**
     * This method contains the algorithm "SAT"
     * @param r1 the polygon 1
     * @param r2 the polygon 2
     * @return if there is a overlap between the two polygons
     */
    public static boolean shapeOverlapSAT(Polygon r1, Polygon r2) {
        Polygon poly1 = new Polygon(r1.getP(), r1.getO(), r1.getPosition(), r1.getAngle(), r1.isOverlap());
        Polygon poly2 = new Polygon(r2.getP(), r2.getO(), r2.getPosition(), r2.getAngle(), r2.isOverlap());

        for ( int shape = 0; shape < 2; shape++ ) {

            if ( shape == 1 ) {
                poly1 = new Polygon(r2.getP(), r2.getO(), r2.getPosition(), r2.getAngle(), r2.isOverlap());
                poly2 = new Polygon(r1.getP(), r1.getO(), r1.getPosition(), r1.getAngle(), r1.isOverlap());
            }

            for ( int a = 0; a < poly1.getP().length; a++ ) {
                int b = (a + 1) % poly1.getP().length;
                Vec2df axisProjection = new Vec2df(
                        -(poly1.getP()[b].getY() - poly1.getP()[a].getY()),
                        (poly1.getP()[b].getX() - poly1.getP()[a].getX())
                );
                float d = axisProjection.mag();
                axisProjection.multiply(1 / d);

                float minR1 = Float.MAX_VALUE;
                float maxR1 = -Float.MAX_VALUE;

                for ( int p = 0; p < poly1.getP().length; p++ ) {
                    float q = (poly1.getP()[p].getX() * axisProjection.getX() + poly1.getP()[p].getY() * axisProjection.getY());
                    minR1 = Float.min(minR1, q);
                    maxR1 = Float.max(maxR1, q);
                }

                float minR2 = Float.MAX_VALUE;
                float maxR2 = -Float.MAX_VALUE;

                for ( int p = 0; p < poly2.getP().length; p++ ) {
                    float q = (poly2.getP()[p].getX() * axisProjection.getX() + poly2.getP()[p].getY() * axisProjection.getY());
                    minR2 = Float.min(minR2, q);
                    maxR2 = Float.max(maxR2, q);
                }

                if ( !(maxR2 >= minR1 && maxR1 >= minR2) ) {
                    return false;
                }

            }

        }
        return true;
    }

    /**
     * This method contains the algorithm "SAT"
     * This method does a displacement over r1 polygon
     * @param r1 the polygon 1
     * @param r2 the polygon 2
     * @return if there is a overlap between the two polygons
     */
    public static boolean shapeOverlapStaticSAT(Polygon r1, Polygon r2) {
        Polygon poly1 = new Polygon(r1.getP(), r1.getO(), r1.getPosition(), r1.getAngle(), r1.isOverlap());
        Polygon poly2 = new Polygon(r2.getP(), r2.getO(), r2.getPosition(), r2.getAngle(), r2.isOverlap());

        float overlap = Float.MAX_VALUE;

        for ( int shape = 0; shape < 2; shape++ ) {

            if ( shape == 1 ) {
                poly1 = new Polygon(r2.getP(), r2.getO(), r2.getPosition(), r2.getAngle(), r2.isOverlap());
                poly2 = new Polygon(r1.getP(), r1.getO(), r1.getPosition(), r1.getAngle(), r1.isOverlap());
            }

            for ( int a = 0; a < poly1.getP().length; a++ ) {
                int b = (a + 1) % poly1.getP().length;
                Vec2df axisProjection = new Vec2df(
                        -(poly1.getP()[b].getY() - poly1.getP()[a].getY()),
                        (poly1.getP()[b].getX() - poly1.getP()[a].getX())
                );
                float d = axisProjection.mag();
                axisProjection.multiply(1 / d);

                float minR1 = Float.MAX_VALUE;
                float maxR1 = -Float.MAX_VALUE;

                for ( int p = 0; p < poly1.getP().length; p++ ) {
                    float q = (poly1.getP()[p].getX() * axisProjection.getX() + poly1.getP()[p].getY() * axisProjection.getY());
                    minR1 = Float.min(minR1, q);
                    maxR1 = Float.max(maxR1, q);
                }

                float minR2 = Float.MAX_VALUE;
                float maxR2 = -Float.MAX_VALUE;

                for ( int p = 0; p < poly2.getP().length; p++ ) {
                    float q = (poly2.getP()[p].getX() * axisProjection.getX() + poly2.getP()[p].getY() * axisProjection.getY());
                    minR2 = Float.min(minR2, q);
                    maxR2 = Float.max(maxR2, q);
                }


                overlap = Float.min(Float.min(maxR1, maxR2) - Float.max(minR1, minR2), overlap);

                if ( !(maxR2 >= minR1 && maxR1 >= minR2) ) {
                    return false;
                }

            }

        }

        Vec2df d = new Vec2df(r2.getPosition().getX() - r1.getPosition().getX(), r2.getPosition().getY() - r1.getPosition().getY());
        float s = d.mag();
        r1.getPosition().addToX(- overlap * d.getX() / s);
        r1.getPosition().addToY(- overlap * d.getY() / s);

        return false;
    }

}
