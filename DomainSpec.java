package pac.interpretationscoord;

import java.util.Vector;
import java.util.HashMap;
import javafx.util.Pair;

public class DomainSpec implements Template{

    private String name;
    private Template template;
    HashMap<String,Pair<String,String>> attributes;

    public DomainSpec(String name, Template template){
        this.name = name;
        this.template = template;
        attributes = new HashMap<String,Pair<String,String>>();
        for (String i : template.getAttrs()) {
            attributes.put(i, new Pair<>("NULL","NULL"));
        }
    }

    @Deprecated
    public void addAttr(String s){
        attributes.put(s, new Pair<>("NULL","NULL"));
    }

    public void addAttr(String attribute, String type){
        attributes.put(attribute, new Pair<>(type,"NULL"));
    }

    public String getName(){
        return name;
    }

    public Boolean remAttr(String attribute){
        if(attributes.containsKey(attribute)) {
            attributes.remove(attribute);
            return true;
        }
        else
            return false;

    }

    public String[] getAttrs(){
        String[] result = new String[attributes.size()];
        attributes.keySet().toArray(result);
        return result;
    }

    public String getType(String attribute){
        return attributes.get(attribute).getKey();
    }

    public int numAttrs(){
        return attributes.size();
    }

    public String[][] getAttrsNTypes(){
        String[][] result = new String[attributes.size()][2];
        int i = 0;
        for(String s : attributes.keySet()){
            result[i][0] = s;
            result[i][1] = attributes.get(s).getKey();
            i++;
        }
        return result;
    }

    public String[][] getAttrsNTypesNValues(){
        String[][] result = new String[attributes.size()][3];
        int i = 0;
        for(String s : attributes.keySet()){
            result[i][0] = s;
            result[i][1] = attributes.get(s).getKey();
            result[i][2] = attributes.get(s).getValue();
            i++;
        }
        return result;
    }

    public String getAttrValue(String attribute){
        return attributes.get(attribute).getValue();
    }

    public void setAttrValue(String attribute, String value){
        if(attributes.containsKey(attribute)){
            String name = attributes.get(attribute).getKey();
            attributes.replace(attribute, new Pair<>(name, value));
        }
    }

    public boolean hasAttr(String attribute){
        return(attributes.containsKey(attribute));
    }

    public Boolean modifyAttr(String oldName, String newName){
        if(attributes.containsKey(oldName)) {
            Pair type = attributes.get(oldName);
            attributes.remove(oldName, type);
            attributes.put(newName, type);
            return true;
        }
        else
            return false;
    }

    public Boolean modifyAttrType(String name, String newType){
        if(attributes.containsKey(name)){
            String value = attributes.get(name).getValue();
            attributes.replace(name, new Pair<>(newType, value));
            return true;
        }
        else
            return false;
    }
}
