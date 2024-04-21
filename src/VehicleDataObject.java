import java.io.Serializable;

public class VehicleDataObject implements Serializable {

    private int playerNumber;
    private int locationX;
    private int locationY;
    private int kartDirection;

    private boolean connectionStatus;

    private boolean win;
    private boolean crashedWithAnotherKart;
    public VehicleDataObject(int playerNumber,int x, int y, int _direction, boolean connectionStatus, boolean win, boolean crashedWithAnotherVehicle) {
        this.playerNumber = playerNumber;
        this.locationX = x;
        this.locationY = y;
        this.kartDirection = _direction;
        this.connectionStatus = connectionStatus;
        this.win = win;

        this.crashedWithAnotherKart = crashedWithAnotherVehicle;
    }



    public int GetDirection() {
        return kartDirection;
    }

    public int GetLocationY() {
        return locationY;
    }

    public int GetLocationX() {
        return locationX;
    }


    @Override
    public String toString() {
        return "VehicleDataObject{" +
                "playerNumber='" + playerNumber +
                ", locationX=" + locationX +
                ", locationY=" + locationY +
                ", vehicleDirection=" + kartDirection +
                ", crashedWithAnotherVehicle=" + crashedWithAnotherKart +
                '}';
    }

    public boolean isConnectionStatus() {
        return connectionStatus;
    }

    public boolean isWin() {
        return win;
    }

    public boolean crashedWithAnotherVehicle() {
        return crashedWithAnotherKart;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }
}
