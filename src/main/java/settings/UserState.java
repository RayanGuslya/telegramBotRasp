package settings;

import java.util.HashMap;
import java.util.Map;
//1.представляет состояние диалога пользователя
public class UserState {
//    Map<Long, UserState> adminStates = new HashMap<>();
    private boolean isWaitingPassword;
    public void setWaitingPassword(boolean isWaitingPassword){
        this.isWaitingPassword = isWaitingPassword;
    }
    public boolean getWaitingPassword(){
        return this.isWaitingPassword;
    }
}
