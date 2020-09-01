package pac.interpretationscoord;

import java.util.Vector;

public class Archetype implements Template {

    private String name;
    private Vector<String> attributes = new Vector<String>();

    public Archetype(String name){
        this.name = name;
    }

    public void addAttr(String attribute){
        this.attributes.add(attribute);
    }

    public String getName(){
        return name;
    }

    public Boolean remAttr(String attribute){
        if(attributes.contains(attribute)) {
            attributes.remove(attribute);
            return true;
        }
        else
            return false;

    }
    
    public String[] getAttrs(){
        String[] result = new String[attributes.size()];
        attributes.copyInto(result);
        return result;
    }

    public Boolean modifyAttr(String attr1, String attr2){
        if(attributes.contains(attr1)) {
            attributes.set(attributes.indexOf(attr1), attr2);
            return true;
    }
        else
                return false;
    }

}
