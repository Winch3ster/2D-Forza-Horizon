package DataObjects;

public class Status {

    private boolean connectionEstablished;

    public Status(boolean _status){
        this.connectionEstablished = _status;
    }

    public boolean GetConnectionEstablishedStatus() {
        return connectionEstablished;
    }
}
