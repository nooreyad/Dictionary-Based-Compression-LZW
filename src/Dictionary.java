import java.security.KeyPair;
import java.util.HashMap;
import java.util.Map;

public class Dictionary {
    Map<String, Integer> dic = new HashMap<>();

    public void initializeDictionary(){
        for (int i = 0; i < 128; i++){
            String s = "";
            s += (char)i;
            dic.put(s, i);
        }
    }
    public Integer get(String s){
        return dic.get(s);
    }
    public void add(Pair p){
        dic.put(p.getStr(), p.getNumber());
    }
    public void clear(){
        dic.clear();
    }
}
