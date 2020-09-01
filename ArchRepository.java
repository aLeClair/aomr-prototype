package pac.interpretationscoord;

import java.util.HashMap;

public class ArchRepository {
    HashMap<String, Archetype> archetypes;

    public ArchRepository(){
        archetypes = new HashMap<String, Archetype>();
    }

    public Archetype getArchByName(String name){
        return archetypes.get(name);
    }

    public void saveArch(Archetype a) {
        if(archetypes.containsValue(a))
            archetypes.replace(a.getName(), a);
        else
            archetypes.put(a.getName(), a);
    }


    public void deleteArch(Archetype a) {
        if (archetypes.containsValue(a)) {
            archetypes.remove(a);
        }
    }

}
