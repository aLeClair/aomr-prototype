package pac.interpretationscoord;

import java.util.HashMap;
import java.util.ArrayList;

public class DSRepository {
    HashMap<String, DomainSpec> specs;

    public DSRepository(){
        specs = new HashMap<String, DomainSpec>();
    }

    public DomainSpec getDSByName(String name){
        return specs.get(name);
    }

    public void saveDS(DomainSpec d) {
        if(specs.containsValue(d))
            specs.replace(d.getName(), d);
        else
            specs.put(d.getName(), d);
    }

    public ArrayList<DomainSpec> getAll(){
        ArrayList<DomainSpec> tempArray = new ArrayList<DomainSpec>();
        for(String s : specs.keySet()){
            tempArray.add(specs.get(s));
        }
        return tempArray;
    }

    public void deleteSpec(DomainSpec d) {
        if (specs.containsValue(d)) {
            specs.remove(d);
        }
    }

}
