package pac.knowledgecoordinator;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import pac.contextcoordinator.Context;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import pac.contextcoordinator.ContextCoordinator;
import pac.interpretationscoord.DomainSpec;
import pac.interpretationscoord.InterpretationsCoordinator;

import javax.swing.*;
import java.sql.*;


import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Set;


//The knowledge coordinator is tasked with coordinating the entire system, so it is aware of the two lower level coordinators, as well is able to retrieve the necessary data from them
public class KnowledgeCoordinator {
    private static KnowledgeCoordinator instance = null;

    private ContextCoordinator cc = null;
    private InterpretationsCoordinator ic = null;
    private ReasoningInterface ri = new ReasoningInterface();
    OWLTranslator translator = new OWLTranslator();


    ListMultimap<String, String> contextAxiom;

    private KnowledgeCoordinator() {
        cc = ContextCoordinator.getInstance();
        ic = InterpretationsCoordinator.getInstance();

        cc.registerKnowledgeCoord(this);
        ic.registerKnowledgeCoord(this);

        contextAxiom = ArrayListMultimap.create();
    }

    public DomainSpec getDomainSpec(String name){
        return ic.getDS(name);
    }


    private Context getContext(String context){
        return cc.getContext(context);
    }

    public Set<String> getContexts(){
        return cc.getContexts();
    }

    public static KnowledgeCoordinator getInstance(){
        if (instance == null)
            instance = new KnowledgeCoordinator();
        return instance;
    }

    public void reason(String context, int query){
        translateOntology(context);
        doReason(context,query);
    }

    public void createInterface(){
        ri.createInterface(this);
    }


    //This function is unique for translating to OWL, and also done with heuristics. If desired, a new function can be made which would translate to FOL or another language
    private void translateOntology(String context)  {
        translator.translateOntology();

        Context retContext =  getContext(context);

        //Each DS in the context needs to be translated to a concept
        for(DomainSpec domainSpec : retContext.getMaps().keySet()) {
            translator.translateDS(domainSpec);


            HashMap<String,String> mappings = retContext.getMaps().get(domainSpec);



            //Get the data directly. This is only implemented this way for prototype reasons ... should contact Context Coordinator.
            try (Connection conn = cc.getDBConnection()){
                //For prototype purposes, hardcode each Domain Spec to have its own table. Ideally, can be generated
                String query = null;
                //Maintablepark requires its own if statement because it is uniquely named as Park rather than as maintainablepark
                if(domainSpec.getName().equals("Park")){
                    query = "SELECT * FROM maintainablepark";
                }
                else {
                    query = "SELECT * FROM " + domainSpec.getName();
                }
                Statement statement = conn.createStatement();
                ResultSet queryResult = statement.executeQuery(query);

                //Go through every row of the results
                while (queryResult.next()) {
                    //Put each row into the domain specification with the values related via dataproperty axioms

                    //This needs to not be hard-coded for each domain specification.
                    String conceptName = "";
                    switch(domainSpec.getName()){
                        case "MaintainableStreet":
                            conceptName = queryResult.getString("CNN");
                            break;
                        case "MaintainablePark":
                        case "Park":
                            conceptName = queryResult.getString("Map_Label");
                            break;
                        case "RealEstateInventory":
                            conceptName = queryResult.getString("APP_NO");
                            break;
                        case "TallBuildings":
                            conceptName = queryResult.getString("NAME");
                            break;
                        case "Munistops":
                            conceptName = queryResult.getString("STOPNAME");
                            break;
                        case "Streets":
                            conceptName = queryResult.getString("STREETNAME");
                            break;
                        case "FilmSites":
                            conceptName = queryResult.getString("ID");
                            break;
                    }

                    translator.declareConcept(domainSpec.getName(), conceptName);

                    //Each mapping in the context must be translated, bringing the data into the ontology
                    for(String attribute : mappings.keySet()){
                        //System.out.println("DOMAINSPEC: " + domainSpec.getName() + " KEY: " + attribute + " AND VALUE: " + retContext.getMaps().get(domainSpec).get(attribute).split("\\.")[1]);
                        if(ic.isDomainSpec(mappings.get(attribute).split("\\.")[1])){
                            if(domainSpec.getName().equals("RealEstateInventory"))
                                translator.relateConceptToConcept(domainSpec.getName(), queryResult.getString("APP_NO"), attribute, queryResult.getString("STREET"), mappings.get(attribute).split("\\.")[1]);
                            if(domainSpec.getName().equals("Munistops")) {
                                    translator.relateConceptToConcept(domainSpec.getName(), queryResult.getString("STOPNAME"), attribute, queryResult.getString(attribute),mappings.get(attribute).split("\\.")[1]);
                            }
                            if(domainSpec.getName().equals("TallBuildings")) {
                                translator.relateConceptToConcept(domainSpec.getName(), queryResult.getString("NAME"), attribute, queryResult.getString(attribute),mappings.get(attribute).split("\\.")[1]);
                            }
                            if(domainSpec.getName().equals("FilmSites")) {
                                translator.relateConceptToConcept(domainSpec.getName(), queryResult.getString("ID"), attribute, queryResult.getString("LOCATIONS"));
                            }
                            //translator.relateConceptToConcept(domainSpec.getName(), queryResult.getString("APP_NO"), attribute, queryResult.getString("STREET"));
                        }
                        else {
                            translator.relateConceptToData(domainSpec.getName(), conceptName, attribute, domainSpec.getType(attribute), queryResult.getString(mappings.get(attribute).split("\\.")[1]));
                        }

                    }
                    //System.out.printf(queryResult.getString("STREET") + " " + queryResult.getString("PCI_Score") + " " + queryResult.getString("SPEEDLIMIT") + " " + queryResult.getString("MODEL6_VOL") + "%n");
                }
            }catch(Exception e){
                e.printStackTrace();
            }

        }

        //Create the axioms that exist for a specific context
        switch(context){
            case "infrastructure":
                translator.createAxiom("DireStreet");
                translator.createAxiom("SmallPoorPark");
                translator.createAxiom("MaintainableArea");
                break;
            case "realestate":
                translator.createAxiom("HouseWithMuni");
                translator.createAxiom("HouseWithTallBuilding");
                translator.createAxiom("HouseWithTallBuildingAndMuni");
                break;
            case "tourism":
                translator.createAxiom("FamousLocation");
                translator.createAxiom("AntManTour");
                translator.createAxiom("TourWithPark");
                break;
        }




        translator.saveOntology();
        
    }

    
    private void doReason(String context, int query){

        ReasoningEngine re = new ReasoningEngine(translator.getManager());
        re.initReasoning();
        
        List<String> result = null;

        //Number corresponds to the query selected from the UI
        switch(context){
            case "infrastructure":
                switch(query) {
                    case 0:
                        result = re.getStreetsForRepaving();
                        break;
                    case 1:
                        result = re.getParksForFixing();
                        break;
                    case 2:
                        result = re.getCityAreasForInspection();
                        break;
                }
                break;
            case "realestate":
                switch(query){
                    case 0:
                        result = re.getTwoBedroomAnswer();
                        break;
                    case 1:
                        result = re.getCastroHouses();
                        break;
                    case 2:
                        result = re.getBusyHouses();
                        break;
                }
                break;
            case "tourism":
                switch(query){
                    case 0:
                        result = re.getAntManTour();
                        break;
                    case 1:
                        result = re.getFamousSites();
                        break;
                    case 2:
                        result = re.getFamousParks();
                        break;
                }
                break;
        }
        
        ri.updateText(result);
        
        cc.closeConnection();
    }

}
