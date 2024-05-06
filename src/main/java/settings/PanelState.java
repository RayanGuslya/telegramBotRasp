package settings;

public class PanelState {
    private boolean isWaitingPanel;
    public void setWaiting(boolean isWaitingPanel){
        this.isWaitingPanel = isWaitingPanel;
    }
    public boolean getWaiting(){
        return this.isWaitingPanel;
    }
}
