import java.util.HashMap;
import java.util.Map;

public class DictionaryDecompress {
    Map<Integer, String> dic = new HashMap<>();
    public void add(Pair p){
        dic.put(p.getNumber(), p.getStr());
    }
    public String get(Integer code){
        return dic.get(code);
    }
    public void clear(){
        dic.clear();
    }
}
