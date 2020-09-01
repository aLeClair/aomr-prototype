package pac.knowledgecoordinator;




import aterm.ATermAppl;
import com.clarkparsia.pellet.owlapiv3.PelletReasoner;
import com.clarkparsia.owlapiv3.OWL;
import com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory;


import java.util.*;


import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.InferenceType;
import org.semanticweb.owlapi.reasoner.OWLReasoner;



public class ReasoningEngine {

    OWLOntology o;
    PelletReasoner reasoner;
    OWLDataFactory df;

    //The reasoning engine that is made can be paramterized if multiple reasoning engines want to be made available
    //Currently we only use Pellet
    ReasoningEngine(OWLOntologyManager m){
        for(OWLOntology ontz : m.getOntologies())
            o = ontz;

        reasoner = PelletReasonerFactory.getInstance().createReasoner(o);
        df = reasoner.getManager().getOWLDataFactory();
    }

    public void initReasoning() {
        reasoner.prepareReasoner();

        reasoner.precomputeInferences(InferenceType.CLASS_ASSERTIONS,
                InferenceType.CLASS_HIERARCHY,
                InferenceType.DATA_PROPERTY_ASSERTIONS,
                InferenceType.DATA_PROPERTY_HIERARCHY,
                InferenceType.OBJECT_PROPERTY_ASSERTIONS,
                InferenceType.OBJECT_PROPERTY_HIERARCHY,
                InferenceType.DIFFERENT_INDIVIDUALS,
                InferenceType.DISJOINT_CLASSES,
                InferenceType.SAME_INDIVIDUAL);

    }


    public void getAnswer(){

        for(ATermAppl clazz : reasoner.getKB().getClasses()){
            System.out.println("CLASS: " + clazz.getName());
            if(clazz.getName().equals("http://www.co-ode.org/ontologies/sfo.owl#RealEstateInventory")) {
                for (ATermAppl indz : reasoner.getKB().getInstances(clazz)) {
                    System.out.println("IND: " + indz.getName());
                }
            }
        }




        System.out.println("Completed Reasoning");


    }

    public List<String> getTwoBedroomAnswer(){
        OWLDataProperty hasNeighborhood = df.getOWLDataProperty(IRI.create("http://www.co-ode.org/ontologies/sfo.owl#RealEstateInventoryHasneighborhood"));
        OWLDataProperty hasListing = df.getOWLDataProperty(IRI.create("http://www.co-ode.org/ontologies/sfo.owl#RealEstateInventoryHaslisting"));

        OWLClass RealEstateInventory = df.getOWLClass(IRI.create("http://www.co-ode.org/ontologies/sfo.owl#RealEstateInventory"));
        String TwoBedroom = "\"2_FAMILY_DWELLING\"^^xsd:string";

        List<String> result = new LinkedList<>();
        result.add("The houses with two bedrooms are ...");

        for(OWLIndividual indz : RealEstateInventory.getIndividuals(o)){
            for(OWLLiteral dataz : indz.getDataPropertyValues(hasListing,o)) {
                if (dataz.toString().contains(TwoBedroom)) {
                    for (OWLLiteral datazz : indz.getDataPropertyValues(hasNeighborhood, o)) {
                        if(!result.contains(datazz.toString().split("\"")[1]))
                            result.add(datazz.toString().split("\"")[1]);
                    }
                }
            }
        }

        return result;
    }


    //Currently, the reasoning tasks are specified to the query. This is to make a more personalized experience, and to ensure the output is only what the user asked for
    //Each reasoning task follows the same steps: get all individuals and concepts that can be found via reasoning, narrow down this set to only the concepts or individuals
    //that were requested, and return those
    public List<String> getCastroHouses(){
        List<String> result = new LinkedList<>();

        OWLObjectProperty hasAddress = df.getOWLObjectProperty(IRI.create("http://www.co-ode.org/ontologies/sfo.owl#RealEstateInventoryHasaddress"));
        String address = "<http://www.co-ode.org/ontologies/sfo.owl#Castro>";

        OWLDataProperty hasNeighborhood = df.getOWLDataProperty(IRI.create("http://www.co-ode.org/ontologies/sfo.owl#RealEstateInventoryHasneighborhood"));
        OWLDataProperty hasListing = df.getOWLDataProperty(IRI.create("http://www.co-ode.org/ontologies/sfo.owl#RealEstateInventoryHaslisting"));

        OWLClass RealEstateInventory = df.getOWLClass(IRI.create("http://www.co-ode.org/ontologies/sfo.owl#RealEstateInventory"));

        result.add("The information about houses on Castro St. is ...");

        //Format the output so it is readable
        for(OWLIndividual indz : RealEstateInventory.getIndividuals(o)){
            for(OWLIndividual indzz : indz.getObjectPropertyValues(hasAddress,o)) {
                if (indzz.toString().equals(address)) {
                    result.add("House Inventory Number: " + indz.toStringID().split("#")[1]);
                    result.add("Address: " + indzz.toString().split("#")[1].split(">")[0]);
                    for (OWLLiteral datazz : indz.getDataPropertyValues(hasNeighborhood, o)) {
                        if(!result.contains(datazz.toString().split("\"")[1]))
                            result.add("Neighborhood: " + datazz.toString().split("\"")[1]);
                    }
                    for (OWLLiteral datazz : indz.getDataPropertyValues(hasListing, o)) {
                        if(!result.contains(datazz.toString().split("\"")[1]))
                            result.add("Listing: " + datazz.toString().split("\"")[1]);
                    }
                }
            }
        }

        return result;
    }

    public List<String> getBusyHouses(){
        List<String> result = new LinkedList<>();

        result.add("The houses with both a tall building and a muni stop on the same street are ...");
        for(ATermAppl clazz : reasoner.getKB().getClasses()){
            if(clazz.getName().equals("http://www.co-ode.org/ontologies/sfo.owl#HouseWithTallBuildingAndMuni")) {
                for (ATermAppl indz : reasoner.getKB().getInstances(clazz)) {
                    if(!result.contains(indz.toString().split("#")[1]))
                        result.add(indz.toString().split("#")[1]);
                }
            }
        }
        return result;
    }

    public List<String> getStreetsForRepaving(){
        List<String> result = new LinkedList<>();
        result.add("The streets that need repaving are ...");
        for(ATermAppl clazz : reasoner.getKB().getClasses()){
            if(clazz.getName().equals("http://www.co-ode.org/ontologies/sfo.owl#DireStreet")) {
                for (ATermAppl indz : reasoner.getKB().getInstances(clazz)) {
                    if(!result.contains(indz.toString().split("#")[1]))
                        result.add(indz.toString().split("#")[1]);
                }
            }
        }
        return result;
    }

    public List<String> getParksForFixing(){
        List<String> result = new LinkedList<>();
        result.add("The parks that require maintenance are ...");
        for(ATermAppl clazz : reasoner.getKB().getClasses()){
            if(clazz.getName().equals("http://www.co-ode.org/ontologies/sfo.owl#PoorScorePark")) {
                for (ATermAppl indz : reasoner.getKB().getInstances(clazz)) {
                    if(!result.contains(indz.toString().split("#")[1]))
                        result.add(indz.toString().split("#")[1]);
                }
            }
        }
        return result;
    }

    public List<String> getCityAreasForInspection(){
        List<String> result = new LinkedList<>();
        result.add("All areas that require maintenance are ...");
        for(ATermAppl clazz : reasoner.getKB().getClasses()){
            if(clazz.getName().equals("http://www.co-ode.org/ontologies/sfo.owl#MaintainableArea")) {
                for (ATermAppl indz : reasoner.getKB().getInstances(clazz)) {
                    if(!result.contains(indz.toString().split("#")[1]))
                        result.add(indz.toString().split("#")[1]);
                }
            }
        }
        return result;
    }

    public List<String> getAntManTour(){
        List<String> result = new LinkedList<>();
        result.add("All sites in an Ant Man tour route ...");
        for(ATermAppl clazz : reasoner.getKB().getClasses()){
            if(clazz.getName().equals("http://www.co-ode.org/ontologies/sfo.owl#AntManTour")) {
                for (ATermAppl indz : reasoner.getKB().getInstances(clazz)) {
                    if(!result.contains(indz.toString().split("#")[1]))
                        result.add(indz.toString().split("#")[1]);
                }
            }
        }
        return result;
    }

    public List<String> getFamousSites(){
        List<String> result = new LinkedList<>();
        result.add("All famous sites are ...");
        for(ATermAppl clazz : reasoner.getKB().getClasses()){
            if(clazz.getName().equals("http://www.co-ode.org/ontologies/sfo.owl#FamousLocation")) {
                for (ATermAppl indz : reasoner.getKB().getInstances(clazz)) {
                    if(!result.contains(indz.toString().split("#")[1]))
                        result.add(indz.toString().split("#")[1]);
                }
            }
        }
        return result;
    }

    public List<String> getFamousParks(){
        List<String> result = new LinkedList<>();
        result.add("All famous parks are ...");
        for(ATermAppl clazz : reasoner.getKB().getClasses()){
            if(clazz.getName().equals("http://www.co-ode.org/ontologies/sfo.owl#FamousPark")) {
                for (ATermAppl indz : reasoner.getKB().getInstances(clazz)) {
                    if(!result.contains(indz.toString().split("#")[1]))
                        result.add(indz.toString().split("#")[1]);
                }
            }
        }
        return result;
    }

}
