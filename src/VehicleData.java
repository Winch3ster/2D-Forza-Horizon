public class VehicleData {
    private int x;
    private int y;
    private int direction;

    private boolean won;

    public VehicleData(int x, int y, int d, boolean w){
        this.x = x;
        this.y = y;
        this.direction = d;
        this.won = w;
    }


    public int getX() {
        return x;
    }

    public int getDirection() {
        return direction;
    }

    public int getY() {
        return y;
    }

    public boolean Won() {
        return won;
    }
}
