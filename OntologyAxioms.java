package pac.knowledgecoordinator;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.vocab.OWLFacet;
import uk.ac.manchester.cs.owl.owlapi.OWLOntologyManagerImpl;

import java.util.ArrayList;
import java.util.List;

public class OntologyAxioms {

    //Each axiom is unique to a context. It is a concept that is defined using operators such as exists, union, intersection, etc.
    //It is currently manually done as the prototype does not have a grammar for the axioms that could be made into a general function

    static void createDireStreet(OWLDataFactory df, OWLOntology o, OWLOntologyManager m){
        //Get concepts and data properties necessary for the axiom
        OWLClass maintainableStreet = df.getOWLClass(IRI.create("http://www.co-ode.org/ontologies/sfo.owl#MaintainableStreet"));

        OWLDataProperty speedLimit = df.getOWLDataProperty(IRI.create("http://www.co-ode.org/ontologies/sfo.owl#MaintainableStreetHasspeedLimit"));
        OWLDataProperty pci = df.getOWLDataProperty(IRI.create("http://www.co-ode.org/ontologies/sfo.owl#MaintainableStreetHasPCI"));
        OWLClass direStreet = df.getOWLClass(IRI.create("http://www.co-ode.org/ontologies/sfo.owl#DireStreet"));

        //Create datatypes that axiom will be using
        OWLDatatype typing = df.getOWLDatatype(IRI.create("http://www.w3.org/2001/XMLSchema#integer"));


        //Create restrictions the axiom will be using
        OWLFacet min = OWLFacet.MIN_INCLUSIVE;
        OWLFacet max = OWLFacet.MAX_EXCLUSIVE;
        OWLFacetRestriction speedrestrictionMIN = df.getOWLFacetRestriction(min, 15);
        OWLFacetRestriction speedrestrictionMAX = df.getOWLFacetRestriction(max, 35);


        OWLFacetRestriction pcirestrictionMAX = df.getOWLFacetRestriction(max, 60);


        //Declare the axioms
        OWLObjectIntersectionOf ax1 = df.getOWLObjectIntersectionOf(
                df.getOWLDataSomeValuesFrom(pci, df.getOWLDatatypeRestriction(typing, pcirestrictionMAX)),
                df.getOWLDataSomeValuesFrom(speedLimit, df.getOWLDatatypeRestriction(typing, speedrestrictionMIN, speedrestrictionMAX)));

        OWLClassAxiom axiom2 = df.getOWLEquivalentClassesAxiom(direStreet, ax1);

        OWLSubClassOfAxiom subClass = df.getOWLSubClassOfAxiom(direStreet, maintainableStreet);

        //Apply the change
        m.applyChange(new AddAxiom(o, subClass));

        m.applyChange(new AddAxiom(o, axiom2));
    }

    static void createSmallPoorPark(OWLDataFactory df, OWLOntology o, OWLOntologyManager m){
        //Get concepts and data properties necessary for the axiom
        OWLClass maintainablePark = df.getOWLClass(IRI.create("http://www.co-ode.org/ontologies/sfo.owl#MaintainablePark"));

        OWLDataProperty parkArea = df.getOWLDataProperty(IRI.create("http://www.co-ode.org/ontologies/sfo.owl#MaintainableParkHassquarefeet"));
        OWLDataProperty parkScore = df.getOWLDataProperty(IRI.create("http://www.co-ode.org/ontologies/sfo.owl#MaintainableParkHasscore"));
        OWLClass PoorScorePark = df.getOWLClass(IRI.create("http://www.co-ode.org/ontologies/sfo.owl#PoorScorePark"));

        //Create datatypes that axiom will be using
        OWLDatatype typing = df.getOWLDatatype(IRI.create("http://www.w3.org/2001/XMLSchema#float"));


        //Create restrictions the axiom will be using
        OWLFacet maxSquareFeet = OWLFacet.MAX_EXCLUSIVE;
        OWLFacet maxScore = OWLFacet.MAX_EXCLUSIVE;

        OWLFacetRestriction squareFeetMAX = df.getOWLFacetRestriction(maxSquareFeet, Float.parseFloat("100000"));
        OWLFacetRestriction scoreMAX = df.getOWLFacetRestriction(maxScore, Float.parseFloat("80"));


        //Declare the axioms
        OWLObjectIntersectionOf ax1 = df.getOWLObjectIntersectionOf(
                df.getOWLDataSomeValuesFrom(parkArea, df.getOWLDatatypeRestriction(typing, squareFeetMAX)),
                df.getOWLDataSomeValuesFrom(parkScore, df.getOWLDatatypeRestriction(typing, scoreMAX)));

        OWLClassAxiom axiom2 = df.getOWLEquivalentClassesAxiom(PoorScorePark, ax1);

        OWLSubClassOfAxiom subClass = df.getOWLSubClassOfAxiom(PoorScorePark, maintainablePark);

        //Apply the change
        m.applyChange(new AddAxiom(o, subClass));

        m.applyChange(new AddAxiom(o, axiom2));

    }

    static void createMaintainableArea(OWLDataFactory df, OWLOntology o, OWLOntologyManager m){
        OWLClass maintainableArea = df.getOWLClass(IRI.create("http://www.co-ode.org/ontologies/sfo.owl#MaintainableArea"));


        OWLClass maintainablePark = df.getOWLClass(IRI.create("http://www.co-ode.org/ontologies/sfo.owl#MaintainablePark"));
        OWLClass maintainableStreet = df.getOWLClass(IRI.create("http://www.co-ode.org/ontologies/sfo.owl#DireStreet"));


        OWLClassExpression maint = df.getOWLObjectUnionOf(maintainablePark, maintainableStreet);

        OWLAxiom axiom =  df.getOWLEquivalentClassesAxiom(maintainableArea, maint);

        m.applyChange(new AddAxiom(o, axiom));
    }

    static void createHouseWithMuniStop(OWLDataFactory df, OWLOntology o, OWLOntologyManager m){
        OWLClass streetWithMuni = df.getOWLClass(IRI.create("http://www.co-ode.org/ontologies/sfo.owl#StreetWithMuni"));
        OWLClass houseWithMuni = df.getOWLClass(IRI.create("http://www.co-ode.org/ontologies/sfo.owl#HouseWithMuni"));
        OWLClass street = df.getOWLClass(IRI.create("http://www.co-ode.org/ontologies/sfo.owl#Streets"));

        OWLClass house = df.getOWLClass(IRI.create("http://www.co-ode.org/ontologies/sfo.owl#RealEstateInventory"));
        OWLClass muniStop = df.getOWLClass(IRI.create("http://www.co-ode.org/ontologies/sfo.owl#Munistops"));

        OWLObjectProperty hasAddress = df.getOWLObjectProperty(IRI.create("http://www.co-ode.org/ontologies/sfo.owl#RealEstateInventoryHasaddress"));
        OWLObjectProperty hasOnStreet = df.getOWLObjectProperty(IRI.create("http://www.co-ode.org/ontologies/sfo.owl#MunistopsHasonStreet"));
        OWLObjectProperty hasAtStreet = df.getOWLObjectProperty(IRI.create("http://www.co-ode.org/ontologies/sfo.owl#MunistopsHasatStreet"));
        OWLObjectProperty hasMuniStop = df.getOWLObjectProperty(IRI.create("http://www.co-ode.org/ontologies/sfo.owl#StreetsHasmuniStop"));
        OWLObjectProperty hasHome = df.getOWLObjectProperty(IRI.create("http://www.co-ode.org/ontologies/sfo.owl#StreetsHasRealEstateInventory"));
        OWLObjectProperty hasMuni = df.getOWLObjectProperty(IRI.create("http://www.co-ode.org/ontologies/sfo.owl#RealEstateInventoryHasMuni"));

        m.applyChange(new AddAxiom(o,df.getOWLInverseObjectPropertiesAxiom(hasOnStreet,hasMuniStop)));
        m.applyChange(new AddAxiom(o,df.getOWLInverseObjectPropertiesAxiom(hasAtStreet,hasMuniStop)));
        m.applyChange(new AddAxiom(o,df.getOWLInverseObjectPropertiesAxiom(hasAddress,hasHome)));

        OWLAxiom axiom2 = df.getOWLEquivalentClassesAxiom(streetWithMuni,df.getOWLObjectIntersectionOf(df.getOWLObjectSomeValuesFrom(hasMuniStop,muniStop),df.getOWLObjectSomeValuesFrom(hasHome,house)));
        m.applyChange(new AddAxiom(o, axiom2));
        m.applyChange(new AddAxiom(o, df.getOWLSubClassOfAxiom(streetWithMuni, street)));

        List<OWLObjectProperty> chain = new ArrayList<>();
        chain.add(hasAddress);
        chain.add(hasMuniStop);

        m.applyChange(new AddAxiom(o,df.getOWLSubPropertyChainOfAxiom(chain, hasMuni)));

        OWLAxiom axiom3 = df.getOWLEquivalentClassesAxiom(houseWithMuni,df.getOWLObjectSomeValuesFrom(hasMuni,muniStop));
        m.applyChange(new AddAxiom(o, axiom3));

        m.applyChange(new AddAxiom(o, df.getOWLSubClassOfAxiom(houseWithMuni, house)));
    }

    static void createHouseWithTallBuilding(OWLDataFactory df, OWLOntology o, OWLOntologyManager m){
        OWLClass houseWithTallBuilding = df.getOWLClass(IRI.create("http://www.co-ode.org/ontologies/sfo.owl#HouseWithTallBuilding"));
        OWLClass tallBuilding = df.getOWLClass(IRI.create("http://www.co-ode.org/ontologies/sfo.owl#TallBuildings"));
        OWLClass house = df.getOWLClass(IRI.create("http://www.co-ode.org/ontologies/sfo.owl#RealEstateInventory"));

        OWLObjectProperty hasAddress = df.getOWLObjectProperty(IRI.create("http://www.co-ode.org/ontologies/sfo.owl#RealEstateInventoryHasaddress"));
        OWLObjectProperty hasStreet = df.getOWLObjectProperty(IRI.create("http://www.co-ode.org/ontologies/sfo.owl#TallBuildingsHasstreet"));
        OWLObjectProperty hasTallBuilding = df.getOWLObjectProperty(IRI.create("http://www.co-ode.org/ontologies/sfo.owl#StreetsHastallBuilding"));
        OWLObjectProperty houseHasTallBuilding = df.getOWLObjectProperty(IRI.create("http://www.co-ode.org/ontologies/sfo.owl#RealEstateInventoryHastallBuilding"));

        m.applyChange(new AddAxiom(o,df.getOWLInverseObjectPropertiesAxiom(hasStreet,hasTallBuilding)));

        List<OWLObjectProperty> chain = new ArrayList<>();
        chain.add(hasAddress);
        chain.add(hasTallBuilding);

        m.applyChange(new AddAxiom(o,df.getOWLSubPropertyChainOfAxiom(chain, houseHasTallBuilding)));

        OWLAxiom axiom3 = df.getOWLEquivalentClassesAxiom(houseWithTallBuilding,df.getOWLObjectSomeValuesFrom(houseHasTallBuilding,tallBuilding));
        m.applyChange(new AddAxiom(o, axiom3));

        m.applyChange(new AddAxiom(o, df.getOWLSubClassOfAxiom(houseWithTallBuilding, house)));
    }

    static void createHouseWithMuniAndTallBuilding(OWLDataFactory df, OWLOntology o, OWLOntologyManager m){
        OWLClass houseWithTallBuilding = df.getOWLClass(IRI.create("http://www.co-ode.org/ontologies/sfo.owl#HouseWithTallBuilding"));
        OWLClass houseWithMuni = df.getOWLClass(IRI.create("http://www.co-ode.org/ontologies/sfo.owl#HouseWithMuni"));
        OWLClass houseWithTallBuildingAndMuni = df.getOWLClass(IRI.create("http://www.co-ode.org/ontologies/sfo.owl#HouseWithTallBuildingAndMuni"));

        m.applyChange(new AddAxiom(o,
                df.getOWLEquivalentClassesAxiom(houseWithTallBuildingAndMuni,df.getOWLObjectIntersectionOf(houseWithTallBuilding,houseWithMuni))));

    }

    static void createFamousLocation(OWLDataFactory df, OWLOntology o, OWLOntologyManager m){
        //Get concepts and data properties necessary for the axiom
        OWLClass filmPark = df.getOWLClass(IRI.create("http://www.co-ode.org/ontologies/sfo.owl#Park"));
        OWLClass filmStreets = df.getOWLClass(IRI.create("http://www.co-ode.org/ontologies/sfo.owl#Streets"));

        OWLClass filmLocation = df.getOWLClass(IRI.create("http://www.co-ode.org/ontologies/sfo.owl#FilmSites"));
        OWLClass location = df.getOWLClass(IRI.create("http://www.co-ode.org/ontologies/sfo.owl#Location"));

        m.applyChange(new AddAxiom(o, df.getOWLEquivalentClassesAxiom(location, df.getOWLObjectUnionOf(filmPark,filmStreets))));

        OWLObjectProperty filmSiteLocation = df.getOWLObjectProperty(IRI.create("http://www.co-ode.org/ontologies/sfo.owl#FilmSitesHaslocation"));
        OWLClass FamousLocation = df.getOWLClass(IRI.create("http://www.co-ode.org/ontologies/sfo.owl#FamousLocation"));

        OWLObjectProperty hasFilm = df.getOWLObjectProperty(IRI.create("http://www.co-ode.org/ontologies/sfo.owl#LocationHasFilm"));

        m.applyChange(new AddAxiom(o,df.getOWLInverseObjectPropertiesAxiom(filmSiteLocation,hasFilm)));

        //Declare the axioms
        OWLObjectIntersectionOf ax1 = df.getOWLObjectIntersectionOf(
                location, df.getOWLObjectSomeValuesFrom(hasFilm,filmLocation));

        OWLClassAxiom axiom2 = df.getOWLEquivalentClassesAxiom(FamousLocation, ax1);

        //Apply the change

        m.applyChange(new AddAxiom(o, axiom2));

    }

    static void createAntManTour(OWLDataFactory df, OWLOntology o, OWLOntologyManager m){
        //Get concepts and data properties necessary for the axiom
        OWLClass filmLocation = df.getOWLClass(IRI.create("http://www.co-ode.org/ontologies/sfo.owl#FilmSites"));

        OWLDataProperty filmSiteName = df.getOWLDataProperty(IRI.create("http://www.co-ode.org/ontologies/sfo.owl#FilmSitesHasfilm"));
        OWLClass AntManTour = df.getOWLClass(IRI.create("http://www.co-ode.org/ontologies/sfo.owl#AntManTour"));

        //Create datatypes that axiom will be using
        OWLDatatype typing = df.getOWLDatatype(IRI.create("http://www.w3.org/2001/XMLSchema#string"));

        OWLLiteral lit = df.getOWLLiteral("AntMan");

        //Declare the axioms
        OWLObjectIntersectionOf ax1 = df.getOWLObjectIntersectionOf(
                filmLocation, df.getOWLDataHasValue(filmSiteName, lit));

        OWLClassAxiom axiom2 = df.getOWLEquivalentClassesAxiom(AntManTour, ax1);

        //Apply the change

        m.applyChange(new AddAxiom(o, axiom2));
    }

    static void createTourWithPark(OWLDataFactory df, OWLOntology o, OWLOntologyManager m){
        OWLClass filmPark = df.getOWLClass(IRI.create("http://www.co-ode.org/ontologies/sfo.owl#Park"));
        OWLClass filmLocation = df.getOWLClass(IRI.create("http://www.co-ode.org/ontologies/sfo.owl#FilmSites"));

        OWLClass FamousPark = df.getOWLClass(IRI.create("http://www.co-ode.org/ontologies/sfo.owl#FamousPark"));

        OWLObjectProperty hasFilm = df.getOWLObjectProperty(IRI.create("http://www.co-ode.org/ontologies/sfo.owl#LocationHasFilm"));

        OWLObjectIntersectionOf ax1 = df.getOWLObjectIntersectionOf(
                filmPark, df.getOWLObjectSomeValuesFrom(hasFilm,filmLocation));

        OWLClassAxiom axiom2 = df.getOWLEquivalentClassesAxiom(FamousPark, ax1);

        //Apply the change

        m.applyChange(new AddAxiom(o, axiom2));
    }
}
