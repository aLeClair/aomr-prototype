package pac.knowledgecoordinator;

import org.semanticweb.owlapi.vocab.OWLFacet;
import pac.interpretationscoord.DomainSpec;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.util.AutoIRIMapper;
import pac.interpretationscoord.InterpretationsCoordinator;

import java.io.*;

public class OWLTranslator {
    OWLOntologyManager m;
    OWLOntology o = null;
    OWLDataFactory df = null;
    final IRI my_iri = IRI.create("http://www.co-ode.org/ontologies/sfo.owl");
    File file;
    OutputStream out;
    InterpretationsCoordinator ic;


    //The most general framework for translating to OWL is in this file

    public OWLTranslator() {
        try {
            m = OWLManager.createOWLOntologyManager();
            //It will be output to a file
            file = new File("C:\\Users\\Ned\\Desktop\\MVCTool-master\\data\\materializedOntology.owl");
            fileExists();
            out = new FileOutputStream(file);

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void fileExists(){
        if (file.exists() && file.isFile())
            file.delete();
        try {
            file.createNewFile();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void translateOntology() {
    try {
        m.addIRIMapper(new AutoIRIMapper(
                new File("C:\\Users\\Ned\\Desktop\\MVCTool-master\\data\\materializedOntology"), true));
        o = m.createOntology(my_iri);
        df = o.getOWLOntologyManager().getOWLDataFactory();
        ic = InterpretationsCoordinator.getInstance();



    } catch (Exception e) {
        e.printStackTrace();
    }
    }

    public void translateDS(DomainSpec DS) {
            //Declare the DS as a class
            OWLClass dsConcept = df.getOWLClass(IRI.create("http://www.co-ode.org/ontologies/sfo.owl#" + DS.getName()));
            m.applyChange(new AddAxiom(o, df.getOWLDeclarationAxiom(dsConcept)));


            String[][] tempAttrs = DS.getAttrsNTypes();

            for(int i = 0; i < DS.numAttrs(); i++){
                //Declare each attribute within the DS as a dataproperty axiom
                if(!ic.isDomainSpec(tempAttrs[i][1])){
                    OWLDataProperty dataProp = df.getOWLDataProperty(IRI.create("http://www.co-ode.org/ontologies/sfo.owl#" + DS.getName() + "Has" + tempAttrs[i][0]));
                    m.applyChange(new AddAxiom(o, df.getOWLDeclarationAxiom(dataProp)));
                    m.applyChange(new AddAxiom(o, df.getOWLDataPropertyDomainAxiom(dataProp, dsConcept)));
                    m.applyChange(new AddAxiom(o, df.getOWLDataPropertyRangeAxiom(dataProp, getDataType(tempAttrs[i][1]))));
                }
            }


    }

    private OWLDataRange getDataType(String attributeType){
        OWLDataRange result;
        //Currently only support the basic datatypes, but more could be added. User-defined could also be added if needed
        switch(attributeType){
            case "Int":
                result = df.getOWLDatatype(IRI.create("http://www.w3.org/2001/XMLSchema#integer"));
                break;
            case "String":
                result = df.getOWLDatatype(IRI.create("http://www.w3.org/2001/XMLSchema#string"));
                break;
            case "Float":
                result = df.getOWLDatatype(IRI.create("http://www.w3.org/2001/XMLSchema#float"));
                break;
            case "Boolean":
                result = df.getOWLDatatype(IRI.create("http://www.w3.org/2001/XMLSchema#boolean"));
                break;
            default:
                result = df.getOWLDatatype(IRI.create("http://www.w3.org/2001/XMLSchema#string"));
        }
        return result;
    }

    public void declareConcept(String domainSpec, String name){
        //Each domainspec becomes a concept, with the name of the domainspec as the name of the concept
        OWLNamedIndividual tempIndividual = df.getOWLNamedIndividual(IRI.create("http://www.co-ode.org/ontologies/sfo.owl#"+name));
        OWLClass dsConcept = df.getOWLClass(IRI.create("http://www.co-ode.org/ontologies/sfo.owl#"+domainSpec));

        m.applyChange(new AddAxiom(o, df.getOWLDeclarationAxiom(tempIndividual)));

        OWLClassAssertionAxiom assertion = df.getOWLClassAssertionAxiom(dsConcept, tempIndividual);
        m.applyChange(new AddAxiom(o, assertion));


    }

    public void relateConceptToData(String domainSpec, String conceptName, String attribute, String type, String value){
        //The concept of the domainspec will be related to a list of individuals that belongs to it from the dataset
        //The relation between the concept and individuals is named after the domainspec and attribute of the domainspec
        OWLDataProperty property = df.getOWLDataProperty(IRI.create("http://www.co-ode.org/ontologies/sfo.owl#"+domainSpec+"Has"+attribute));
        OWLNamedIndividual tempIndividual = df.getOWLNamedIndividual(IRI.create("http://www.co-ode.org/ontologies/sfo.owl#"+conceptName));

        if(type.matches("Int"))
            m.applyChange(new AddAxiom(o, df.getOWLDataPropertyAssertionAxiom(property, tempIndividual,Integer.parseInt(value))));
        else if(type.matches("Float"))
            m.applyChange(new AddAxiom(o, df.getOWLDataPropertyAssertionAxiom(property, tempIndividual,Float.parseFloat(value))));
        else if(type.matches("Boolean"))
            m.applyChange(new AddAxiom(o, df.getOWLDataPropertyAssertionAxiom(property, tempIndividual,Boolean.parseBoolean(value))));
        else
            m.applyChange(new AddAxiom(o, df.getOWLDataPropertyAssertionAxiom(property, tempIndividual,String.valueOf(value))));
    }

    public void relateConceptToConcept(String domainSpec, String individual, String attribute, String toDomainSpec){

        //Domainspec to domainspec just involves concept to concept relation. The process for naming the relation is the same for the concept to individual
        if(!toDomainSpec.isEmpty()) {
            OWLObjectProperty property = df.getOWLObjectProperty(IRI.create("http://www.co-ode.org/ontologies/sfo.owl#" + domainSpec + "Has" + attribute));
            OWLNamedIndividual from = df.getOWLNamedIndividual(IRI.create("http://www.co-ode.org/ontologies/sfo.owl#" + individual));

            if(!domainSpec.equals("FilmSites")) {
                String firstLetter = toDomainSpec.substring(0, 1).toUpperCase();
                String restLetters = toDomainSpec.substring(1).toLowerCase();
                toDomainSpec = firstLetter + restLetters;
            }

            OWLNamedIndividual to = df.getOWLNamedIndividual(IRI.create("http://www.co-ode.org/ontologies/sfo.owl#" + toDomainSpec));

            for(OWLClassExpression clazz : to.getTypes(o)){
                System.out.println("CLASS OF " + to + " " + clazz.asOWLClass());
            }



            OWLPropertyAssertionAxiom assertionAx = df.getOWLObjectPropertyAssertionAxiom(property, from, to);
            m.applyChange(new AddAxiom(o, assertionAx));
        }
    }

    public void relateConceptToConcept(String domainSpec, String individual, String attribute, String toValue, String toDomainSpec){
        if(!toValue.isEmpty()) {
            OWLClass toValueToClass = df.getOWLClass(IRI.create("http://www.co-ode.org/ontologies/sfo.owl#" + toDomainSpec));
            OWLObjectProperty property = df.getOWLObjectProperty(IRI.create("http://www.co-ode.org/ontologies/sfo.owl#" + domainSpec + "Has" + attribute));
            OWLNamedIndividual from = df.getOWLNamedIndividual(IRI.create("http://www.co-ode.org/ontologies/sfo.owl#" + individual));

            if(!domainSpec.equals("FilmSites")) {
                String firstLetter = toValue.substring(0, 1).toUpperCase();
                String restLetters = toValue.substring(1).toLowerCase();
                toValue = firstLetter + restLetters;
            }

            OWLNamedIndividual to = df.getOWLNamedIndividual(IRI.create("http://www.co-ode.org/ontologies/sfo.owl#" + toValue));

            OWLClassAssertionAxiom assertionAxiom = df.getOWLClassAssertionAxiom(toValueToClass, to);
            m.applyChange(new AddAxiom(o, assertionAxiom));

            OWLPropertyAssertionAxiom assertionAx = df.getOWLObjectPropertyAssertionAxiom(property, from, to);
            m.applyChange(new AddAxiom(o, assertionAx));
        }
    }

    public void createAxiom(String axiom){

        switch(axiom){
            case "DireStreet":
                OntologyAxioms.createDireStreet(df, o, m);
                break;
            case "SmallPoorPark":
                OntologyAxioms.createSmallPoorPark(df, o, m);
                break;
            case "MaintainableArea":
                OntologyAxioms.createMaintainableArea(df,o,m);
                break;
            case "HouseWithMuni":
                OntologyAxioms.createHouseWithMuniStop(df,o,m);
                break;
            case "HouseWithTallBuilding":
                OntologyAxioms.createHouseWithTallBuilding(df,o,m);
                break;
            case "HouseWithTallBuildingAndMuni":
                OntologyAxioms.createHouseWithMuniAndTallBuilding(df,o,m);
                break;
            case "FamousLocation":
                OntologyAxioms.createFamousLocation(df,o,m);
                break;
            case "AntManTour":
                OntologyAxioms.createAntManTour(df,o,m);
                break;
            case "TourWithPark":
                OntologyAxioms.createTourWithPark(df,o,m);
        }

    }


    public void saveOntology(){
        try{
            m.saveOntology(o, out);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public OWLOntologyManager getManager(){
        return m;
    }

    public OWLDataFactory getCurrentDataFactory(){
        return df;
    }


}
