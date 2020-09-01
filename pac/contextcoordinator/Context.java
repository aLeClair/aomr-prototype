package pac.contextcoordinator;

import pac.interpretationscoord.DomainSpec;

import java.util.HashMap;


public class Context {
    private String name;
    //A map is a domain specification to an attribute/table attribute pair
    private HashMap<DomainSpec, HashMap<String,String>> maps;

    public Context() { maps = new HashMap<DomainSpec, HashMap<String,String>>();}

    private void addDStoContext(DomainSpec domainSpec){
        maps.put(domainSpec, new HashMap<String,String>());
    }

    public void mapDStoTable(DomainSpec domainSpec, String domainSpecAttr, String table, String tableAttribute){
        if(!maps.containsKey(domainSpec)){
            addDStoContext(domainSpec);
        }

        maps.get(domainSpec).put(domainSpecAttr, table+"."+tableAttribute);
    }

    public HashMap<DomainSpec, HashMap<String,String>> getMaps(){
        return maps;
    }

    public HashMap<String,String> getDSMappings(DomainSpec DS){
        return maps.get(DS);
    }

    public void saveContext(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
