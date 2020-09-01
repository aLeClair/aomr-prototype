package pac.interpretationscoord;


import pac.knowledgecoordinator.KnowledgeCoordinator;
import java.util.ArrayList;

public class InterpretationsCoordinator {

    private static InterpretationsCoordinator instance = null;

    private KnowledgeCoordinator kc = null;

    private ArchRepository ar = null;
    private DSRepository dsr = null;

    private InterpretationsCoordinator() {
        ar = new ArchRepository();
        dsr = new DSRepository();
    }


    //interpretations coord is responsible for creating archetypes, and storing them
    public void createArch(String name, String[] attributes){
        Archetype a = new Archetype(name);
        for(String i : attributes){
            a.addAttr(i);
        }
        ar.saveArch(a);
    }

    public Archetype getArch(String name){
        return ar.getArchByName(name);
    }


    //it is also responsible for creating and storing the domainspecs
    public void createDomainSpec(String name, Template template){
        DomainSpec ds = new DomainSpec(name, template);
        dsr.saveDS(ds);
    }

    public DomainSpec getDS(String name){
        return dsr.getDSByName(name);
    }


    public void interpretDS(String ds, String attr, String type){
        DomainSpec _ds = dsr.getDSByName(ds);
        _ds.modifyAttrType(attr, type);
    }

    public boolean isDomainSpec(String attribute){
        for(DomainSpec ds : dsr.getAll()){
            if(ds.getName().equals(attribute))
                return true;
        }
        return false;
    }

    //Interpretations Coordinator is a singleton class
    public static InterpretationsCoordinator getInstance(){
        if (instance == null)
            instance = new InterpretationsCoordinator();
        return instance;
    }

    public ArrayList<DomainSpec> getAllDS(){
        return dsr.getAll();
    }

    public void registerKnowledgeCoord(KnowledgeCoordinator kc){
        this.kc = kc;
    }

}
