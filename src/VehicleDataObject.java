import java.io.Serializable;

public class VehicleDataObject implements Serializable {

    private int playerNumber;
    private String playerName;
    private int locationX;
    private int locationY;
    private int vehicleDirection;

    private boolean connectionStatus;

    private boolean win;
    private boolean crashedWithAnotherVehicle;
    public VehicleDataObject(int playerNumber, String _playerName, int x, int y, int _direction, boolean connectionStatus, boolean win, boolean crashedWithAnotherVehicle) {
        this.playerNumber = playerNumber;
        this.playerName = _playerName;
        this.locationX = x;
        this.locationY = y;
        this.vehicleDirection = _direction;
        this.connectionStatus = connectionStatus;
        this.win = win;

        this.crashedWithAnotherVehicle = crashedWithAnotherVehicle;
    }



    public int GetDirection() {
        return vehicleDirection;
    }

    public int GetLocationY() {
        return locationY;
    }

    public int GetLocationX() {
        return locationX;
    }

    public String getPlayerName() {
        return playerName;
    }

    @Override
    public String toString() {
        return "VehicleDataObject{" +
                "playerName='" + playerName + '\'' +
                ", locationX=" + locationX +
                ", locationY=" + locationY +
                ", vehicleDirection=" + vehicleDirection +
                ", connectionStatus=" + connectionStatus +
                ", win=" + win +
                ", crashedWithAnotherVehicle=" + crashedWithAnotherVehicle +
                '}';
    }

    public boolean isConnectionStatus() {
        return connectionStatus;
    }

    public boolean isWin() {
        return win;
    }

    public boolean isCrashedWithAnotherVehicle() {
        return crashedWithAnotherVehicle;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }
}
