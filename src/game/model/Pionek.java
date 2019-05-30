package game.model;

import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public class Pionek extends Pane {
	
	private double scale;
	private Color color;
	private Circle circle;
	private Polygon triangle;
	private Rectangle rectangle;
	private CubicCurve curve;
	private int stageX;
	private int stageY;
	
	public Pionek() {
		this(Color.LIME, 1);
	}
	
	public Pionek(Color color, double scale) {
		super();
		this.scale = scale;
		this.color = color;circle = new Circle(15 * scale);
		circle.setFill(color);
		circle.setLayoutX(20 * scale);
		circle.setLayoutY(20 * scale);
		
		triangle = new Polygon();
        triangle.getPoints().addAll(new Double[]{
            20.0 * scale, 10.0 * scale,
            00.0 * scale, 75.0 * scale,
            40.0 * scale, 75.0 * scale });
        triangle.setFill(color);
        
        rectangle = new Rectangle();
        rectangle.setWidth(40 * scale);
        rectangle.setHeight(10 * scale);
        rectangle.setFill(color);
        rectangle.setLayoutX(0 * scale);
        rectangle.setLayoutY(75 * scale);
        
        curve = new CubicCurve();
        curve.setFill(color);
        curve.setStartX(0 * scale);
        curve.setStartY(85 * scale);
        curve.setEndX(40 * scale);
        curve.setEndY(85 * scale);
        curve.setControlX1(10 * scale);
        curve.setControlY1(95 * scale);
        curve.setControlX2(30 * scale);
        curve.setControlY2(95 * scale);
        
        stageX =(int) (20 * scale);
        stageY =(int) (80 * scale);
        
        
        circle.getStyleClass().add("counter_circle");
        Group group = new Group();
        group.getChildren().addAll(triangle, rectangle, curve);
        group.getStyleClass().add("counter_triangle");
		
		this.getChildren().addAll(group, circle);
	}
	
	public void build() {
		
	}

	public int getStageX() {
		return stageX;
	}

	public int getStageY() {
		return stageY;
	}
	
	public void setPosition(int x, int y) {
		setLayoutX(x - stageX);
		setLayoutY(y - stageY);
	}
	
	
	
}
