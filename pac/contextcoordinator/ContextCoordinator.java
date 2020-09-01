package pac.contextcoordinator;


import java.sql.ResultSet;
import java.sql.Connection;
import pac.interpretationscoord.DomainSpec;
import pac.knowledgecoordinator.KnowledgeCoordinator;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Set;

public class ContextCoordinator {
    private static ContextCoordinator instance = null;

    DataComponent dc = null;
    KnowledgeCoordinator kc = null;

    Context workingContext;
    //A context is: <Context, <Domain Spec, <Attr, Table Attr>>>
    //Each context has many domain specifications in it, each domain specification will have several attributes linked to table attributes
    HashMap<String, Context> contexts;


    private ContextCoordinator() {
        dc = DataComponent.getInstance();
        contexts = new HashMap<String, Context>();
    }

    public void map(DomainSpec domainSpec, String attribute, String table, String column){
        workingContext.mapDStoTable(domainSpec, attribute, table, column);
    }

     public void createContext(){
        workingContext =  new Context();
    }

    public void saveContext(String name){
        contexts.put(name, workingContext);
    }

    public Context getContext(String name){
        return contexts.get(name);
    }

    public Set<String> getContexts(){return contexts.keySet();}

    public Connection getDBConnection() throws SQLException {
        return dc.getConnection();
    }

    public ResultSet getData(DomainSpec domainSpec){
        try(Connection conn = getDBConnection()) {
            return dc.getData(domainSpec.getName());
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static ContextCoordinator getInstance(){
        if (instance == null)
            instance = new ContextCoordinator();
        return instance;
    }

    public void registerKnowledgeCoord(KnowledgeCoordinator kc){
        this.kc = kc;
    }

    public void closeConnection(){dc.closeConnection();}
}
