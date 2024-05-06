package settings;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;


public class sendRasp {
    //database database = new database();
    //private List<String> YandexRasp = new ArrayList<>();
    private String list[] = {"https://disk.yandex.lt/i/HHj5RLmnGQOOMA",
            "https://disk.yandex.lt/i/bSLjW3QU_NAb4A",
            "https://disk.yandex.lt/i/s8p4dAxCyWlXEA",
            "https://disk.yandex.lt/i/ey6CUDOLaKYQOg",
            "https://disk.yandex.lt/i/BNDD0-bhNX26VA"};
    /*
    private String IKS = "https://disk.yandex.lt/i/HHj5RLmnGQOOMA";
    private String MSS = "https://disk.yandex.lt/i/bSLjW3QU_NAb4A";
    private String FC = "https://disk.yandex.lt/i/s8p4dAxCyWlXEA";
    private String GNS = "https://disk.yandex.lt/i/ey6CUDOLaKYQOg";
    private String CALL = "https://disk.yandex.lt/i/BNDD0-bhNX26VA";

     */
    /*
    public void addRasp(){
        YandexRasp.add("https://disk.yandex.lt/i/HHj5RLmnGQOOMA");
        YandexRasp.add("https://disk.yandex.lt/i/bSLjW3QU_NAb4A");
        YandexRasp.add("https://disk.yandex.lt/i/s8p4dAxCyWlXEA");
        YandexRasp.add("https://disk.yandex.lt/i/ey6CUDOLaKYQOg");
        YandexRasp.add("https://disk.yandex.lt/i/BNDD0-bhNX26VA");
    }
     */
    public String getIKS(){
        return list[0];
    }
    public String getMSS(){
        return list[1];
    }
    public String getFC(){
        return list[2];
    }
    public String getGNS(){
        return list[3];
    }
    public String getCALL(){
        return list[4];
    }
    //SET
    public void setIKS(String IKS){
        list[0-1] = IKS;
    }
    public void setMSS(String MSS){
        list[1-1] = MSS;
    }
    public void setFC(String FC){
        list[2-1] = FC;
    }
    public void setGNS(String GNS){
        list[3-1] = GNS;
    }
    public void setCALL(String CALL){
        list[4-1] = CALL;
    }
}
