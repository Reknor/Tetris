package project;

import javafx.scene.shape.Rectangle;

public class Controller {
	//getting numbers and GRID from Tetris class
	private final int MOVE;
	private final int SIZE;
	private final int XMAX;
	private final int YMAX;
	private int [][] GRID;
	private final int figuresCount = 7;
	private final Tetris GAME;
	
	public Controller(int move, int size, int xMax, int yMax, int[][] grid, Tetris game) {
		MOVE = move;
		SIZE = size;
		XMAX = xMax;
		YMAX = yMax;
		GRID = grid;
		GAME = game;
	}
	
	
	// Move figure
	public void MoveDown(Form form) {
		// Stop moving if there is block beneath or fall at bottom
		if (form.a.getY() == YMAX - SIZE || form.b.getY() == YMAX - SIZE || form.c.getY() == YMAX - SIZE
				|| form.d.getY() == YMAX - SIZE || blockBelow(form)) {
			GRID[((int) form.a.getX() / SIZE)][((int) form.a.getY() / SIZE)] = 1;
			GRID[((int) form.b.getX() / SIZE)][((int) form.b.getY() / SIZE)] = 1;
			GRID[((int) form.c.getX() / SIZE)][((int) form.c.getY() / SIZE)] = 1;
			GRID[((int) form.d.getX() / SIZE)][((int) form.d.getY() / SIZE)] = 1;

			GAME.RemoveRows();
			GAME.addNextBlock();
		}

		// Move one block down if possible
		if (form.a.getY() + MOVE < YMAX && form.b.getY() + MOVE < YMAX && form.c.getY() + MOVE < YMAX
				&& form.d.getY() + MOVE < YMAX) {
			int movea = GRID[((int) form.a.getX() / SIZE)][((int) form.a.getY() / SIZE) + 1];
			int moveb = GRID[((int) form.b.getX() / SIZE)][((int) form.b.getY() / SIZE) + 1];
			int movec = GRID[((int) form.c.getX() / SIZE)][((int) form.c.getY() / SIZE) + 1];
			int moved = GRID[((int) form.d.getX() / SIZE)][((int) form.d.getY() / SIZE) + 1];

			if (movea == 0 && moveb == 0 && movec == 0 && moved == 0) {
				form.a.setY(form.a.getY() + MOVE);
				form.b.setY(form.b.getY() + MOVE);
				form.c.setY(form.c.getY() + MOVE);
				form.d.setY(form.d.getY() + MOVE);
			}
		}
	}

	
	// Returns true if there is any block directly below
	private boolean blockBelow(Form form) {
		return (GRID[(int) form.a.getX() / SIZE][((int) form.a.getY() / SIZE) + 1] == 1) ||
				(GRID[(int) form.b.getX() / SIZE][((int) form.b.getY() / SIZE) + 1] == 1) ||
				(GRID[(int) form.c.getX() / SIZE][((int) form.c.getY() / SIZE) + 1] == 1) ||
				(GRID[(int) form.d.getX() / SIZE][((int) form.d.getY() / SIZE) + 1] == 1);
	}

	
	
	public void MoveRight(Form form) {
		if (form.a.getX() + MOVE <= XMAX - SIZE && form.b.getX() + MOVE <= XMAX - SIZE
			&& form.c.getX() + MOVE <= XMAX - SIZE && form.d.getX() + MOVE <= XMAX - SIZE) {
			int movea = GRID[((int)form.a.getX() / SIZE) + 1][((int)form.a.getY() / SIZE)];
			int moveb = GRID[((int)form.b.getX() / SIZE) + 1][((int)form.b.getY() / SIZE)];
			int movec = GRID[((int)form.c.getX() / SIZE) + 1][((int)form.c.getY() / SIZE)];
			int moved = GRID[((int)form.d.getX() / SIZE) + 1][((int)form.d.getY() / SIZE)];
		if (movea == 0 && moveb == 0 && movec == 0 && moved == 0) {
			form.a.setX(form.a.getX() + MOVE);
			form.b.setX(form.b.getX() + MOVE);
			form.c.setX(form.c.getX() + MOVE);
			form.d.setX(form.d.getX() + MOVE);
		}
		
		}
	}
	
	
	public void MoveLeft(Form form) {
		if (form.a.getX() - MOVE >= 0  && form.b.getX() - MOVE >= 0
			&& form.c.getX() - MOVE >= 0 && form.d.getX() - MOVE >= 0) {
			int movea = GRID[((int)form.a.getX() / SIZE) - 1][((int)form.a.getY() / SIZE)];
			int moveb = GRID[((int)form.b.getX() / SIZE) - 1][((int)form.b.getY() / SIZE)];
			int movec = GRID[((int)form.c.getX() / SIZE) - 1][((int)form.c.getY() / SIZE)];
			int moved = GRID[((int)form.d.getX() / SIZE) - 1][((int)form.d.getY() / SIZE)];
		if (movea == 0 && moveb == 0 && movec == 0 && moved == 0) {
			form.a.setX(form.a.getX() - MOVE);
			form.b.setX(form.b.getX() - MOVE);
			form.c.setX(form.c.getX() - MOVE);
			form.d.setX(form.d.getX() - MOVE);
		}
		
		}
	}
	
	
	// Create figure
	public Form makeRect() {
		int block = (int)(Math.random()*figuresCount);
		String colorName;
		
		Rectangle a = new Rectangle(SIZE-1, SIZE-1),
				b = new Rectangle(SIZE-1, SIZE-1),
				c = new Rectangle(SIZE-1, SIZE-1),
				d = new Rectangle(SIZE-1, SIZE-1);
		
		if (block == 0) {
			a.setX(XMAX / 2 - SIZE);
			b.setX(XMAX / 2 - SIZE);
			b.setY(SIZE);
			c.setX(XMAX / 2);
			c.setY(SIZE);
			d.setX(XMAX / 2 + SIZE);
			d.setY(SIZE);
			colorName = Form.GREY;
			
		} else if (block == 1) {
			a.setX(XMAX / 2 + SIZE);
			b.setX(XMAX / 2 - SIZE);
			b.setY(SIZE);
			c.setX(XMAX / 2);
			c.setY(SIZE);
			d.setX(XMAX / 2 + SIZE);
			d.setY(SIZE);
			colorName = Form.GOLDEN;
			
		} else if (block == 2) {
			a.setX(XMAX / 2 - SIZE);
			b.setX(XMAX / 2);
			c.setX(XMAX / 2 - SIZE);
			c.setY(SIZE);
			d.setX(XMAX / 2);
			d.setY(SIZE);
			colorName = Form.RED;
			
		} else if (block == 3) {
			a.setX(XMAX / 2 + SIZE);
			b.setX(XMAX / 2);
			c.setX(XMAX / 2);
			c.setY(SIZE);
			d.setX(XMAX / 2 - SIZE);
			d.setY(SIZE);
			colorName = Form.GREEN;
			
		} else if (block == 4) {
			a.setX(XMAX / 2 - SIZE);
			b.setX(XMAX / 2);
			c.setX(XMAX / 2);
			c.setY(SIZE);
			d.setX(XMAX / 2 + SIZE);
			colorName = Form.BLUE;
			
		} else if (block == 5) {
			a.setX(XMAX / 2 + SIZE);
			b.setX(XMAX / 2);
			c.setX(XMAX / 2 + SIZE);
			c.setY(SIZE);
			d.setX(XMAX / 2 + SIZE + SIZE);
			d.setY(SIZE);
			colorName = Form.PINK;
			
		} else {
			a.setX(XMAX / 2 - SIZE - SIZE);
			b.setX(XMAX / 2 - SIZE);
			c.setX(XMAX / 2);
			d.setX(XMAX / 2 + SIZE);
			colorName = Form.BROWN;
			
		}
		
		return new Form(a, b, c, d, colorName);
	}
	
	//Rotate
	public void rotateFigure(Form form) {
		int f = form.getForm();
		Rectangle a = form.a;
		Rectangle b = form.b;
		Rectangle c = form.c;
		Rectangle d = form.d;
		switch (form.getColorName()) {
		case Form.GREY:
			if (f == 1 && cB(a, 1, -1) && cB(c, -1, -1) && cB(d, -2, -2)) {
				MoveRight(form.a);
				MoveDown(form.a);
				MoveDown(form.c);
				MoveLeft(form.c);
				MoveDown(form.d);
				MoveDown(form.d);
				MoveLeft(form.d);
				MoveLeft(form.d);
				form.changeForm();
				break;
			}
			if (f == 2 && cB(a, -1, -1) && cB(c, -1, 1) && cB(d, -2, 2)) {
				MoveDown(form.a);
				MoveLeft(form.a);
				MoveLeft(form.c);
				MoveUp(form.c);
				MoveLeft(form.d);
				MoveLeft(form.d);
				MoveUp(form.d);
				MoveUp(form.d);
				form.changeForm();
				break;
			}
			if (f == 3 && cB(a, -1, 1) && cB(c, 1, 1) && cB(d, 2, 2)) {
				MoveLeft(form.a);
				MoveUp(form.a);
				MoveUp(form.c);
				MoveRight(form.c);
				MoveUp(form.d);
				MoveUp(form.d);
				MoveRight(form.d);
				MoveRight(form.d);
				form.changeForm();
				break;
			}
			if (f == 4 && cB(a, 1, 1) && cB(c, 1, -1) && cB(d, 2, -2)) {
				MoveUp(form.a);
				MoveRight(form.a);
				MoveRight(form.c);
				MoveDown(form.c);
				MoveRight(form.d);
				MoveRight(form.d);
				MoveDown(form.d);
				MoveDown(form.d);
				form.changeForm();
				break;
			}
			break;
		case Form.GOLDEN:
			if (f == 1 && cB(a, 1, -1) && cB(c, 1, 1) && cB(b, 2, 2)) {
				MoveRight(form.a);
				MoveDown(form.a);
				MoveUp(form.c);
				MoveRight(form.c);
				MoveUp(form.b);
				MoveUp(form.b);
				MoveRight(form.b);
				MoveRight(form.b);
				form.changeForm();
				break;
			}
			if (f == 2 && cB(a, -1, -1) && cB(b, 2, -2) && cB(c, 1, -1)) {
				MoveDown(form.a);
				MoveLeft(form.a);
				MoveRight(form.b);
				MoveRight(form.b);
				MoveDown(form.b);
				MoveDown(form.b);
				MoveRight(form.c);
				MoveDown(form.c);
				form.changeForm();
				break;
			}
			if (f == 3 && cB(a, -1, 1) && cB(c, -1, -1) && cB(b, -2, -2)) {
				MoveLeft(form.a);
				MoveUp(form.a);
				MoveDown(form.c);
				MoveLeft(form.c);
				MoveDown(form.b);
				MoveDown(form.b);
				MoveLeft(form.b);
				MoveLeft(form.b);
				form.changeForm();
				break;
			}
			if (f == 4 && cB(a, 1, 1) && cB(b, -2, 2) && cB(c, -1, 1)) {
				MoveUp(form.a);
				MoveRight(form.a);
				MoveLeft(form.b);
				MoveLeft(form.b);
				MoveUp(form.b);
				MoveUp(form.b);
				MoveLeft(form.c);
				MoveUp(form.c);
				form.changeForm();
				break;
			}
			break;
		case Form.RED:
			break;
		case Form.GREEN:
			if (f == 1 && cB(a, -1, -1) && cB(c, -1, 1) && cB(d, 0, 2)) {
				MoveDown(form.a);
				MoveLeft(form.a);
				MoveLeft(form.c);
				MoveUp(form.c);
				MoveUp(form.d);
				MoveUp(form.d);
				form.changeForm();
				break;
			}
			if (f == 2 && cB(a, 1, 1) && cB(c, 1, -1) && cB(d, 0, -2)) {
				MoveUp(form.a);
				MoveRight(form.a);
				MoveRight(form.c);
				MoveDown(form.c);
				MoveDown(form.d);
				MoveDown(form.d);
				form.changeForm();
				break;
			}
			if (f == 3 && cB(a, -1, -1) && cB(c, -1, 1) && cB(d, 0, 2)) {
				MoveDown(form.a);
				MoveLeft(form.a);
				MoveLeft(form.c);
				MoveUp(form.c);
				MoveUp(form.d);
				MoveUp(form.d);
				form.changeForm();
				break;
			}
			if (f == 4 && cB(a, 1, 1) && cB(c, 1, -1) && cB(d, 0, -2)) {
				MoveUp(form.a);
				MoveRight(form.a);
				MoveRight(form.c);
				MoveDown(form.c);
				MoveDown(form.d);
				MoveDown(form.d);
				form.changeForm();
				break;
			}
			break;
		case Form.BLUE:
			if (f == 1 && cB(a, 1, 1) && cB(d, -1, -1) && cB(c, -1, 1)) {
				MoveUp(form.a);
				MoveRight(form.a);
				MoveDown(form.d);
				MoveLeft(form.d);
				MoveLeft(form.c);
				MoveUp(form.c);
				form.changeForm();
				break;
			}
			if (f == 2 && cB(a, 1, -1) && cB(d, -1, 1) && cB(c, 1, 1)) {
				MoveRight(form.a);
				MoveDown(form.a);
				MoveLeft(form.d);
				MoveUp(form.d);
				MoveUp(form.c);
				MoveRight(form.c);
				form.changeForm();
				break;
			}
			if (f == 3 && cB(a, -1, -1) && cB(d, 1, 1) && cB(c, 1, -1)) {
				MoveDown(form.a);
				MoveLeft(form.a);
				MoveUp(form.d);
				MoveRight(form.d);
				MoveRight(form.c);
				MoveDown(form.c);
				form.changeForm();
				break;
			}
			if (f == 4 && cB(a, -1, 1) && cB(d, 1, -1) && cB(c, -1, -1)) {
				MoveLeft(form.a);
				MoveUp(form.a);
				MoveRight(form.d);
				MoveDown(form.d);
				MoveDown(form.c);
				MoveLeft(form.c);
				form.changeForm();
				break;
			}
			break;
		case Form.PINK:
			if (f == 1 && cB(b, 1, 1) && cB(c, -1, 1) && cB(d, -2, 0)) {
				MoveUp(form.b);
				MoveRight(form.b);
				MoveLeft(form.c);
				MoveUp(form.c);
				MoveLeft(form.d);
				MoveLeft(form.d);
				form.changeForm();
				break;
			}
			if (f == 2 && cB(b, -1, -1) && cB(c, 1, -1) && cB(d, 2, 0)) {
				MoveDown(form.b);
				MoveLeft(form.b);
				MoveRight(form.c);
				MoveDown(form.c);
				MoveRight(form.d);
				MoveRight(form.d);
				form.changeForm();
				break;
			}
			if (f == 3 && cB(b, 1, 1) && cB(c, -1, 1) && cB(d, -2, 0)) {
				MoveUp(form.b);
				MoveRight(form.b);
				MoveLeft(form.c);
				MoveUp(form.c);
				MoveLeft(form.d);
				MoveLeft(form.d);
				form.changeForm();
				break;
			}
			if (f == 4 && cB(b, -1, -1) && cB(c, 1, -1) && cB(d, 2, 0)) {
				MoveDown(form.b);
				MoveLeft(form.b);
				MoveRight(form.c);
				MoveDown(form.c);
				MoveRight(form.d);
				MoveRight(form.d);
				form.changeForm();
				break;
			}
			break;
		case Form.BROWN:
			if (f == 1 && cB(a, 2, 2) && cB(b, 1, 1) && cB(d, -1, -1)) {
				MoveUp(form.a);
				MoveUp(form.a);
				MoveRight(form.a);
				MoveRight(form.a);
				MoveUp(form.b);
				MoveRight(form.b);
				MoveDown(form.d);
				MoveLeft(form.d);
				form.changeForm();
				break;
			}
			if (f == 2 && cB(a, -2, -2) && cB(b, -1, -1) && cB(d, 1, 1)) {
				MoveDown(form.a);
				MoveDown(form.a);
				MoveLeft(form.a);
				MoveLeft(form.a);
				MoveDown(form.b);
				MoveLeft(form.b);
				MoveUp(form.d);
				MoveRight(form.d);
				form.changeForm();
				break;
			}
			if (f == 3 && cB(a, 2, 2) && cB(b, 1, 1) && cB(d, -1, -1)) {
				MoveUp(form.a);
				MoveUp(form.a);
				MoveRight(form.a);
				MoveRight(form.a);
				MoveUp(form.b);
				MoveRight(form.b);
				MoveDown(form.d);
				MoveLeft(form.d);
				form.changeForm();
				break;
			}
			if (f == 4 && cB(a, -2, -2) && cB(b, -1, -1) && cB(d, 1, 1)) {
				MoveDown(form.a);
				MoveDown(form.a);
				MoveLeft(form.a);
				MoveLeft(form.a);
				MoveDown(form.b);
				MoveLeft(form.b);
				MoveUp(form.d);
				MoveRight(form.d);
				form.changeForm();
				break;
			}
			break;
		}
	}

	
	// Check boundries
	private boolean cB(Rectangle rect, int x, int y) {
		boolean xb = false;
		boolean yb = false;
		if (x >= 0)
			xb = rect.getX() + x * MOVE <= XMAX - SIZE;
		if (x < 0)
			xb = rect.getX() + x * MOVE >= 0;
		if (y >= 0)
			yb = rect.getY() - y * MOVE > 0;
		if (y < 0)
			yb = rect.getY() + y * MOVE < YMAX;
		return xb && yb && GRID[((int) rect.getX() / SIZE) + x][((int) rect.getY() / SIZE) - y] == 0;
	}
	
	
	private void MoveDown(Rectangle rect) {
		if (rect.getY() + MOVE < YMAX) {
			rect.setY(rect.getY() + MOVE);
		}
	}

	
	private void MoveRight(Rectangle rect) {
		if (rect.getX() + MOVE <= XMAX - SIZE) {
			rect.setX(rect.getX() + MOVE);
		}
	}

	
	private void MoveLeft(Rectangle rect) {
		if (rect.getX() - MOVE >= 0) {
			rect.setX(rect.getX() - MOVE);
		}
	}
	
	
	private void MoveUp(Rectangle rect) {
		if (rect.getY() - MOVE > 0) {
			rect.setY(rect.getY() - MOVE);
		}
	}

}
