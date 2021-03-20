package project;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Form {
	Rectangle a;
	Rectangle b;
	Rectangle c;
	Rectangle d; 
	Color color;
	private String colorName;
	private int form = 1;
	private static final int ALL_FORMS = 4;
	
	public static final String GREY = "j";
	public static final String GOLDEN = "l";
	public static final String RED = "o";
	public static final String GREEN = "s";
	public static final String BLUE = "t";
	public static final String PINK = "z";
	public static final String BROWN = "i";
	
	public Form(Rectangle a, Rectangle b, Rectangle c, Rectangle d, String colorName) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		this.colorName = colorName;
	
		switch(colorName) {
		case GREY:
			color = Color.SLATEGREY;
			break;
		case GOLDEN:
			color = Color.DARKGOLDENROD;
			break;
		case RED:
			color = Color.INDIANRED;
			break;
		case GREEN:
			color = Color.FORESTGREEN;
			break;
		case BLUE:
			color = Color.CADETBLUE;
			break;
		case PINK:
			color = Color.HOTPINK;
			break;
		case BROWN:
			color = Color.SANDYBROWN;
			break;
		}
		this.a.setFill(color);
		this.b.setFill(color);
		this.c.setFill(color);
		this.d.setFill(color);
	}
	
	
	public String getColorName() {
		return this.colorName;
	}
	
	
	public int getForm() {
		return form;
	}
	
	
	public void changeForm() {
		if (form != ALL_FORMS) {
			form++;
		} else {
			form = 1;
		}
	}
}
