import convexPolygonCollisions.ConvexPolygonCollisions;
import convexPolygonCollisions.Polygon;
import convexPolygonCollisions.PolygonFactory;
import engine.AbstractGame;
import engine.GameContainer;
import engine.gfx.HexColors;
import engine.gfx.Renderer;
import engine.vectors.points2d.Vec2df;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * This class is made to test the working of the
 * polygons class and the different collisions
 * techniques
 *
 * @class TestPolygons
 * @author Sergio Martí Torregrosa
 * @date 14/11/2020
 */
public class TestPolygons extends AbstractGame {

    /**
     * The polygons
     */
    private ArrayList<Polygon> polygons;

    /**
     * The mode of the program, which determines
     * the collision algorithm
     */
    private int mode = 0;

    private TestPolygons(String title) {
        super(title);
    }

    @Override
    public void initialize(GameContainer gameContainer) {
        polygons = new ArrayList<>();
        polygons.add(PolygonFactory.makeRegularPolygon(5, new Vec2df(100, 100), 30, 0));
        polygons.add(PolygonFactory.makeTriangle(new Vec2df(200, 150), 20, 0));
        polygons.add(PolygonFactory.makeQuad(new Vec2df(50, 200), 30, (float) Math.PI / 4));
        updatePolygons();
    }

    /**
     * This method manages the user input for change the mode of
     * collision between polygons
     * @param gc the game container object with the input object
     */
    private void updateUserInputMode(GameContainer gc) {
        if ( gc.getInput().isKeyDown(KeyEvent.VK_F1) ) {
            mode = 0;
        }
        if ( gc.getInput().isKeyDown(KeyEvent.VK_F2) ) {
            mode = 1;
        }
        if ( gc.getInput().isKeyDown(KeyEvent.VK_F3) ) {
            mode = 2;
        }
        if ( gc.getInput().isKeyDown(KeyEvent.VK_F4) ) {
            mode = 3;
        }
    }

    /**
     * This method manages the user input for the pentagon
     * @param gc the game container object with the input object
     * @param elapsedTime the elapsed time between the frames
     */
    private void updateUserInputPentagon(GameContainer gc, float elapsedTime) {
        if ( gc.getInput().isKeyHeld(KeyEvent.VK_LEFT) ) {
            polygons.get(0).setAngle(polygons.get(0).getAngle() - 2.0f * elapsedTime);
        }
        if ( gc.getInput().isKeyHeld(KeyEvent.VK_RIGHT) ) {
            polygons.get(0).setAngle(polygons.get(0).getAngle() + 2.0f * elapsedTime);
        }
        if ( gc.getInput().isKeyHeld(KeyEvent.VK_UP) ) {
            polygons.get(0).getPosition().addToX((float)Math.cos(polygons.get(0).getAngle()) * 60.0f * elapsedTime);
            polygons.get(0).getPosition().addToY((float)Math.sin(polygons.get(0).getAngle()) * 60.0f * elapsedTime);
        }
        if ( gc.getInput().isKeyHeld(KeyEvent.VK_DOWN) ) {
            polygons.get(0).getPosition().addToX(-(float)Math.cos(polygons.get(0).getAngle()) * 60.0f * elapsedTime);
            polygons.get(0).getPosition().addToY(-(float)Math.sin(polygons.get(0).getAngle()) * 60.0f * elapsedTime);
        }
    }

    /**
     * This method manages the user input for the triangle
     * @param gc the game container object with the input object
     * @param elapsedTime the elapsed time between the frames
     */
    private void updateUserInputTriangle(GameContainer gc, float elapsedTime) {
        if ( gc.getInput().isKeyHeld(KeyEvent.VK_A) ) {
            polygons.get(1).setAngle(polygons.get(1).getAngle() - 2.0f * elapsedTime);
        }
        if ( gc.getInput().isKeyHeld(KeyEvent.VK_D) ) {
            polygons.get(1).setAngle(polygons.get(1).getAngle() + 2.0f * elapsedTime);
        }
        if ( gc.getInput().isKeyHeld(KeyEvent.VK_W) ) {
            polygons.get(1).getPosition().addToX((float)Math.cos(polygons.get(1).getAngle()) * 60.0f * elapsedTime);
            polygons.get(1).getPosition().addToY((float)Math.sin(polygons.get(1).getAngle()) * 60.0f * elapsedTime);
        }
        if ( gc.getInput().isKeyHeld(KeyEvent.VK_S) ) {
            polygons.get(1).getPosition().addToX(-(float)Math.cos(polygons.get(1).getAngle()) * 60.0f * elapsedTime);
            polygons.get(1).getPosition().addToY(-(float)Math.sin(polygons.get(1).getAngle()) * 60.0f * elapsedTime);
        }
    }

    /**
     * This method updates all the user inputs
     * @param gc the game container object with the input object
     * @param elapsedTime the elapsed time between the frames
     */
    private void updateUserInput(GameContainer gc, float elapsedTime) {
        updateUserInputMode(gc);
        updateUserInputPentagon(gc, elapsedTime);
        updateUserInputTriangle(gc, elapsedTime);
    }

    /**
     * This method updates all the polygons
     */
    private void updatePolygons() {
        for ( Polygon p : polygons ) {
            p.update();
        }
    }

    /**
     * This method sets all the polygons
     * overlap flag to false
     */
    private void resetPolygonsFlags() {
        for ( Polygon p : polygons ) {
            p.setOverlap(false);
        }
    }

    /**
     * This method manages the collision between two polygons
     * It depends on the mode of collision of the program
     * @param polygon1 the first polygon
     * @param polygon2 the second polygon
     */
    private void managePolygonCollision(Polygon polygon1, Polygon polygon2) {
        switch ( mode ) {
            case 0: default:
                polygon1.setOverlap(ConvexPolygonCollisions.shapeOverlapSAT(polygon1, polygon2));
                break;
            case 1:
                polygon1.setOverlap(ConvexPolygonCollisions.shapeOverlapStaticSAT(polygon1, polygon2));
                break;
        }
    }

    /**
     * This method manages all the collisions between the polygons,
     * but it does it with a certain order, so the first polygon can moves
     * the rest of polygons but the last one can't move none
     */
    private void managePolygonsCollisions() {
        for ( int m = 0; m < polygons.size(); m++ ) {
            for ( int n = m + 1; n < polygons.size(); n++ ) {
                managePolygonCollision(polygons.get(m), polygons.get(n));
            }
        }
    }

    @Override
    public void update(GameContainer gameContainer, float v) {
        updateUserInput(gameContainer, v);
        updatePolygons();
        resetPolygonsFlags();
        managePolygonsCollisions();
    }

    /**
     * This method draws all the polygons
     * @param r the renderer object with all drawing methods
     */
    private void drawPolygons(Renderer r) {
        for ( Polygon p : polygons ) {
            p.drawYourSelf(r);
        }
    }

    /**
     * This method draws the texts on screen
     * which displays the modes of collisions
     * @param r the renderer object with all drawing methods
     */
    private void drawModesHub(Renderer r) {
        r.drawText("F1: SAT", 8, 10, (mode == 0 ? HexColors.RED : HexColors.YELLOW));
        r.drawText("F2: SAT/STATIC", 8, 40, (mode == 1 ? HexColors.RED : HexColors.YELLOW));
        r.drawText("F3: DIAG", 8, 70, (mode == 2 ? HexColors.RED : HexColors.YELLOW));
        r.drawText("F4: DIAG/STATIC", 8, 100, (mode == 3 ? HexColors.RED : HexColors.YELLOW));
    }

    @Override
    public void render(GameContainer gameContainer, Renderer renderer) {
        renderer.clear(HexColors.ROYAL_BLUE);
        drawPolygons(renderer);
        drawModesHub(renderer);
    }

    public static void main(String[] args) {
        GameContainer gc = new GameContainer(new TestPolygons("Testing polygons"));
        gc.start();
    }

}
