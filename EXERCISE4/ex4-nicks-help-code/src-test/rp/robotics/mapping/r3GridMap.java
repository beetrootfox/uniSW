package rp.robotics.mapping;
import lejos.geom.Line;
import lejos.geom.Point;
import lejos.robotics.navigation.Pose;
import rp.robotics.mapping.IGridMap;
import rp.robotics.mapping.RPLineMap;


public class r3GridMap implements IGridMap {
	
	private RPLineMap lineMap;
	private int gridSizeX;
	private int gridSizeY;
	private float xInset;
	private float yInset;
	private float cellSize;
	
	public r3GridMap(int gridSizeX, int gridSizeY, float xInset,
			float yInset, float cellSize, RPLineMap lineMap){
		this.lineMap = lineMap;
		this.gridSizeX = gridSizeX;
		this.gridSizeY = gridSizeY;
		this.xInset = xInset;
		this.yInset = yInset;
		this.cellSize = cellSize;
	}

	@Override
	public int getXSize() {
		// TODO Auto-generated method stub
		return gridSizeX;
	}

	@Override
	public int getYSize() {
		// TODO Auto-generated method stub
		return gridSizeY;
	}

	@Override
	public boolean isValidGridPosition(int _x, int _y) {
		// TODO Auto-generated method stub
		return _x < gridSizeX && _y < gridSizeY;
	}

	@Override
	public boolean isObstructed(int _x, int _y) {
		// TODO Auto-generated method stub
		return !lineMap.inside(getCoordinatesOfGridPosition(_x, _y)); 
	}

	@Override
	public Point getCoordinatesOfGridPosition(int _x, int _y) {
		// TODO Auto-generated method stub
		float xCoord = _x * cellSize + xInset;
		float yCoord = _y * cellSize + yInset;
		return new Point(xCoord, yCoord);
	}

	@Override
	public boolean isValidTransition(int _x1, int _y1, int _x2, int _y2) {
		// TODO Auto-generated method stub
		if(isObstructed(_x1, _y1) || isObstructed(_x2, _y2)){
			return false;
		}else{
			Line[] lines = lineMap.getLines();
			Point point1 = getCoordinatesOfGridPosition(_x1, _y1);
			Point point2 = getCoordinatesOfGridPosition(_x2, _y2);
			Line transition = new Line(point1.x, point1.y, point2.x, point2.y);
			for(int i = 0; i< lines.length; i++){
				if(lineMap.intersectsAt(transition, lines[i]) != null){
					return false;
				}
			}
			return true;
		}
	}

	@Override
	public float rangeToObstacleFromGridPosition(int _x, int _y, float _heading) {
		// TODO Auto-generated method stub
		Point location = getCoordinatesOfGridPosition(_x, _y);
		return lineMap.range(new Pose(location.x, location.y, _heading));
	}

}
