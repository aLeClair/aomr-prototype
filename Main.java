
import pac.contextcoordinator.ContextCoordinator;
import pac.contextcoordinator.DataComponent;
import pac.interpretationscoord.Archetype;
import pac.interpretationscoord.DomainSpec;
import pac.interpretationscoord.InterpretationsCoordinator;
import pac.knowledgecoordinator.KnowledgeCoordinator;
import pac.contextcoordinator.Context;

import javax.ws.rs.core.MultivaluedMap;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

public class Main
{
    //public static void main(String[] args) throws OWLOntologyCreationException {
    public static void main(String[] args) throws InterruptedException, SQLException {
        KnowledgeCoordinator kc = KnowledgeCoordinator.getInstance();
        ContextCoordinator cc = ContextCoordinator.getInstance();


        InterpretationsCoordinator ic = InterpretationsCoordinator.getInstance();


        //Here is the hard-coding of each domain-specific concept
        //It can be made to be an interactive process, but for the purpose of this prototype, it is hard-coded
        String archArea = "Area";
        String[] areaAttrs = {"name", "location"};
        ic.createArch(archArea, areaAttrs);

        String archBuilding = "Building";
        String[] buildingAttrs = {"name","address"};
        ic.createArch(archBuilding, buildingAttrs);

        String archRoute = "Route";
        String[] routeAttrs = {"name","path"};
        ic.createArch(archRoute,routeAttrs);

        Archetype areaArchetype = ic.getArch(archArea);

        String maintStreet = "MaintainableStreet";

        ic.createDomainSpec(maintStreet, areaArchetype);

        DomainSpec d = ic.getDS(maintStreet);

        d.modifyAttrType("name", "String");
        d.modifyAttrType("location","Street");
        d.addAttr("PCI","Int");
        d.addAttr("speedLimit","Int");
        d.addAttr("pedestrianVolume","Int");

        String maintPark = "MaintainablePark";

        ic.createDomainSpec(maintPark, areaArchetype);

        DomainSpec d2 = ic.getDS(maintPark);

        d2.modifyAttrType("name", "String");
        d2.modifyAttrType("location","Float");
        d2.addAttr("squarefeet", "Float");
        d2.addAttr("score","Float");


        // String[][] attrsNTypesNValues = d.getAttrsNTypesNValues();
        // for (int i = 0; i < attrsNTypesNValues.length; i++) {
        //     System.out.printf("Attr %s and type %s and value %s\n", attrsNTypesNValues[i][0], attrsNTypesNValues[i][1], attrsNTypesNValues[i][2]);
        // }



        DataComponent dc = DataComponent.getInstance();

        cc.createContext();

        //Test map the Maintainable Street
        cc.map(d, "location", "speedlimits","STREET");
        cc.map(d, "PCI", "pavingscore", "PCI_Score");
        cc.map(d, "speedLimit", "speedlimits", "SPEEDLIMIT");
        cc.map(d, "pedestrianVolume", "pedestrianvolume", "MODEL6_VOL");

        //Test map the Park
        cc.map(d2, "name", "parkproperties", "Map_Label");
        cc.map(d2, "location", "parkproperties", "Longitude");
        cc.map(d2, "squarefeet", "parkproperties", "SquareFeet");
        cc.map(d2, "score", "parkscores","Park_Site_Score");

        cc.saveContext("infrastructure");


        Archetype building = ic.getArch(archBuilding);
        Archetype route = ic.getArch(archRoute);

        String house = "RealEstateInventory";

        ic.createDomainSpec(house, building);

        DomainSpec domainHouse = ic.getDS(house);
        domainHouse.modifyAttrType("name", "String");
        domainHouse.modifyAttrType("address", "Streets");
        domainHouse.addAttr("neighborhood", "String");
        domainHouse.addAttr("listing", "String");

        String tallBuilding = "TallBuildings";

        ic.createDomainSpec(tallBuilding, building);

        DomainSpec domainTallBuilding = ic.getDS(tallBuilding);
        domainTallBuilding.modifyAttrType("name", "String");
        domainTallBuilding.modifyAttrType("address", "Address");
        domainTallBuilding.addAttr("height", "Float");
        domainTallBuilding.addAttr("constructedYear", "Int");
        domainTallBuilding.addAttr("street", "String");

        String muniStop = "Munistops";

        ic.createDomainSpec(muniStop, areaArchetype);

        DomainSpec domainMuni = ic.getDS(muniStop);
        domainMuni.modifyAttrType("name", "String");
        domainMuni.modifyAttrType("location", "Float");
        domainMuni.addAttr("atStreet", "Streets");
        domainMuni.addAttr("onStreet", "Streets");
        domainMuni.addAttr("hasShelter","Boolean");

        String street = "Streets";

        ic.createDomainSpec(street, route);

        DomainSpec domainStreet = ic.getDS(street);
        domainStreet.modifyAttrType("name","String");
        domainStreet.modifyAttrType("path","Int");


        cc.createContext();
        //Test map the House
        cc.map(domainHouse, "name", "realestateinventory","APP_NO");
        cc.map(domainHouse, "address", "realestateinventory", "Streets");
        cc.map(domainHouse, "neighborhood", "realestateinventory", "ANALYSIS_NEIGHBORHOOD");
        cc.map(domainHouse, "listing", "realestateinventory", "PROP_USE");

        //Test map the TallBuildings
        cc.map(domainTallBuilding, "name", "tallbuildings","NAME");
        cc.map(domainTallBuilding, "address", "tallbuildings", "ADDRESS");
        cc.map(domainTallBuilding, "height", "tallbuildings", "HEIGHT_FT");
        cc.map(domainTallBuilding, "constructedYear", "tallbuildings", "DATE");
        cc.map(domainTallBuilding, "street", "tallbuildings", "Streets");

        //Test map the MuniStop
        cc.map(domainMuni, "name", "munistops","STOPNAME");
        cc.map(domainMuni, "location", "munistops", "LATITUDE");
        cc.map(domainMuni, "atStreet", "munistops", "Streets");
        cc.map(domainMuni, "onStreet", "munistops", "Streets");
        cc.map(domainMuni, "hasShelter", "munistops", "SHELTER");

        cc.map(domainStreet, "name", "streets", "STREETNAME");
        cc.map(domainStreet, "path", "streets", "CNN");


        cc.saveContext("realestate");


        cc.createContext();

        String filmSite = "FilmSites";
        ic.createDomainSpec(filmSite, areaArchetype);

        DomainSpec domainFilmSite = ic.getDS(filmSite);
        domainFilmSite.modifyAttrType("name", "Int");
        domainFilmSite.modifyAttrType("location", "Location");
        domainFilmSite.addAttr("film", "String");
        domainFilmSite.addAttr("yearFilmed", "Int");

        String filmPark = "Park";

        ic.createDomainSpec(filmPark, areaArchetype);

        DomainSpec domainPark = ic.getDS(filmPark);

        domainPark.modifyAttrType("name", "String");
        domainPark.modifyAttrType("location","Float");

        String filmLocation = "Location";

        ic.createDomainSpec(filmLocation, areaArchetype);

        DomainSpec domainLocation = ic.getDS(filmLocation);
        domainLocation.modifyAttrType("name","String");
        domainLocation.modifyAttrType("location","String");

        String filmStreet = "Streets";

        ic.createDomainSpec(street, route);

        DomainSpec domainFilmStreet = ic.getDS(filmStreet);
        domainFilmStreet.modifyAttrType("name","String");
        domainFilmStreet.modifyAttrType("path","Int");

        cc.map(domainFilmSite, "name", "filmsites","ID");
        cc.map(domainFilmSite, "location", "filmsites","Location");
        cc.map(domainFilmSite, "film", "filmsites","TITLE");
        cc.map(domainFilmSite, "yearFilmed", "filmsites","RELEASEYEAR");


        cc.map(domainPark, "name", "parkproperties", "Map_Label");
        cc.map(domainPark, "location", "parkproperties", "Longitude");

        cc.map(domainFilmStreet, "name", "streets", "STREETNAME");
        cc.map(domainFilmStreet, "path", "streets", "CNN");


        cc.saveContext("tourism");

        //dc.getTableNames();

        //String query = "INSERT INTO maintainablestreet SELECT speedlimits.CNN, STREET, PCI_Score, SPEEDLIMIT, MODEL6_VOL FROM speedlimits, pavingscore, pedestrianvolume WHERE speedlimits.CNN = pavingscore.CNN AND speedlimits.STREET = pedestrianvolume.ST_NAME1";
        //String query = "INSERT INTO maintainablepark SELECT parkproperties.Map_Label, parkproperties.Longitude, parkproperties.Latitude, parkproperties.SquareFeet, parkscores.Park_Site_Score FROM parkproperties, parkscores WHERE parkproperties.Map_Label = parkscores.Park";
        //dc.exeQuery(query);

        //String query = "SELECT * FROM largepoorparks";
        //dc.doQuery(query);


        //kc.reason("realestate",1);

        kc.createInterface();


        //dc.dropDataSet("temp");

    }

}



      //  ArchRepository ar = new ArchRepository();

      //  Archetype a = new Archetype("test");

      //  System.out.println(a.getName());

        /*
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {                                           
                UIView view = new UIView();
                view.newUI();
                DataComponent dataStore = new DataComponent();
                ArchetypesComponent archComponent = new ArchetypesComponent();
                DSComponent dsComponent = new DSComponent();
                KnowledgeCoordinator knowledgeCoord = new KnowledgeCoordinator(dataStore, archComponent, dsComponent);
                UIController controller = new UIController(view,knowledgeCoord);
                controller.control();
            }
        });  
    	*/
    	
    	//
    	//
    	//	The parts dedicated to the testing of OWL API
    	//
    	//
    	/*
    	final IRI my_iri = IRI.create("http://www.co-ode.org/ontologies/wine.owl");
    	OWLOntologyManager m = OWLManager.createOWLOntologyManager();
    	m.addIRIMapper(new AutoIRIMapper(
    			new File("materializedOntologies"), true));
		OWLOntology o = m.createOntology(my_iri);
		
		OWLDataFactory df = o.getOWLOntologyManager().getOWLDataFactory();
    	
		OWLClass clsGrape = df.getOWLClass(IRI.create(my_iri + "#Grape"));
		OWLClass clsRegion = df.getOWLClass(IRI.create(my_iri + "#Region"));
		OWLClass clsCARegion = df.getOWLClass(IRI.create(my_iri + "#CanadaRegion"));
		OWLClass clsCanadianRegion = df.getOWLClass(IRI.create(my_iri + "#CanadianRegion"));
		
		OWLAxiom sax = df.getOWLSubClassOfAxiom(clsCARegion, clsRegion);
		m.applyChange(new AddAxiom(o, sax));
		
		OWLIndividual grape1 = df.getOWLNamedIndividual(IRI.create(my_iri + "#G1"));
		OWLIndividual grape2 = df.getOWLNamedIndividual(IRI.create(my_iri + "#G2"));
		OWLIndividual grape3 = df.getOWLNamedIndividual(IRI.create(my_iri + "#G3"));
		OWLIndividual grape4 = df.getOWLNamedIndividual(IRI.create(my_iri + "#G4"));
		
		OWLIndividual region1 = df.getOWLNamedIndividual(IRI.create(my_iri + "#R1"));
		OWLIndividual region2 = df.getOWLNamedIndividual(IRI.create(my_iri + "#R2"));
		OWLIndividual region3 = df.getOWLNamedIndividual(IRI.create(my_iri + "#R3"));
		
		OWLAxiom assertion1 = df.getOWLClassAssertionAxiom(clsRegion, region1);
		OWLAxiom assertion2 = df.getOWLClassAssertionAxiom(clsRegion, region2);
		OWLAxiom assertion3 = df.getOWLClassAssertionAxiom(clsCARegion, region3);
		
		OWLAxiom assertion4 = df.getOWLClassAssertionAxiom(clsGrape, grape1);
		OWLAxiom assertion5 = df.getOWLClassAssertionAxiom(clsGrape, grape2);
		OWLAxiom assertion6 = df.getOWLClassAssertionAxiom(clsGrape, grape3);
		OWLAxiom assertion7 = df.getOWLClassAssertionAxiom(clsGrape, grape4);
		
		m.applyChange(new AddAxiom(o, assertion1));
		m.applyChange(new AddAxiom(o, assertion2));
		m.applyChange(new AddAxiom(o, assertion3));
		
		m.applyChange(new AddAxiom(o, assertion4));
		m.applyChange(new AddAxiom(o, assertion5));
		m.applyChange(new AddAxiom(o, assertion6));
		m.applyChange(new AddAxiom(o, assertion7));
		
		OWLObjectProperty hasRegion = df.getOWLObjectProperty(IRI.create(my_iri + "#hasRegion"));
		df.getOWLObjectPropertyDomainAxiom(hasRegion, clsGrape);
		df.getOWLObjectPropertyRangeAxiom(hasRegion, clsRegion);

		OWLAxiom assertion8 = df.getOWLObjectPropertyAssertionAxiom(hasRegion, grape1, region1);
		OWLAxiom assertion9 = df.getOWLObjectPropertyAssertionAxiom(hasRegion, grape2, region2);
		OWLAxiom assertion10 = df.getOWLObjectPropertyAssertionAxiom(hasRegion, grape3, region3);
		OWLAxiom assertion11 = df.getOWLObjectPropertyAssertionAxiom(hasRegion, grape4, region3);
		
		m.applyChange(new AddAxiom(o, assertion8));
		m.applyChange(new AddAxiom(o, assertion9));
		m.applyChange(new AddAxiom(o, assertion10));
		m.applyChange(new AddAxiom(o, assertion11));
		
		OWLClassExpression CanadianRegion = df.getOWLObjectSomeValuesFrom(hasRegion, clsCARegion);
		
		OWLAxiom ax = df.getOWLEquivalentClassesAxiom(CanadianRegion, clsCanadianRegion);
		m.applyChange(new AddAxiom(o, ax));
		
		
		OWLClass Region = df.getOWLClass(IRI.create(my_iri + "#Region"));
		OWLClass RegionCanada = df.getOWLClass(IRI.create(my_iri + "#RegionCanada"));
		OWLClass Wine = df.getOWLClass(IRI.create(my_iri + "#Wine"));
		OWLClass DryWine = df.getOWLClass(IRI.create(my_iri + "#DryWine"));
		OWLClass RedWine = df.getOWLClass(IRI.create(my_iri + "#RedWine"));
		OWLClass TableWine = df.getOWLClass(IRI.create(my_iri + "#TableWine"));
		OWLClass CanadianWines = df.getOWLClass(IRI.create(my_iri + "#CanadianWines"));
		
		OWLIndividual wine1 = df.getOWLNamedIndividual(IRI.create(my_iri + "#Wine1"));
		OWLIndividual wine2 = df.getOWLNamedIndividual(IRI.create(my_iri + "#Wine2"));
		OWLIndividual wine3 = df.getOWLNamedIndividual(IRI.create(my_iri + "#Wine3"));
		OWLIndividual wine4 = df.getOWLNamedIndividual(IRI.create(my_iri + "#Wine4"));
		OWLIndividual wine5 = df.getOWLNamedIndividual(IRI.create(my_iri + "#Wine5"));
		OWLIndividual wine6 = df.getOWLNamedIndividual(IRI.create(my_iri + "#Wine6"));
		OWLIndividual wine7 = df.getOWLNamedIndividual(IRI.create(my_iri + "#Wine7"));
		
		OWLIndividual region1 = df.getOWLNamedIndividual(IRI.create(my_iri + "#France"));
		OWLIndividual region2 = df.getOWLNamedIndividual(IRI.create(my_iri + "#Canada"));
		OWLIndividual region3 = df.getOWLNamedIndividual(IRI.create(my_iri + "#Italy"));
		
		OWLAxiom assertion1 = df.getOWLClassAssertionAxiom(Region, region1);
		OWLAxiom assertion2 = df.getOWLClassAssertionAxiom(RegionCanada, region2);
		OWLAxiom assertion3 = df.getOWLClassAssertionAxiom(Region, region3);
			
		OWLAxiom assertion4 = df.getOWLClassAssertionAxiom(Wine, wine1);
		OWLAxiom assertion5 = df.getOWLClassAssertionAxiom(Wine, wine2);
		OWLAxiom assertion6 = df.getOWLClassAssertionAxiom(RedWine, wine3);
		OWLAxiom assertion7 = df.getOWLClassAssertionAxiom(DryWine, wine4);
		OWLAxiom assertion8 = df.getOWLClassAssertionAxiom(RedWine, wine4);
		OWLAxiom assertion9 = df.getOWLClassAssertionAxiom(RedWine, wine5);
		OWLAxiom assertion10 = df.getOWLClassAssertionAxiom(DryWine, wine5);
		OWLAxiom assertion11 = df.getOWLClassAssertionAxiom(DryWine, wine6);
		OWLAxiom assertion12 = df.getOWLClassAssertionAxiom(DryWine, wine7);

		m.applyChange(new AddAxiom(o, assertion1));
		m.applyChange(new AddAxiom(o, assertion2));
		m.applyChange(new AddAxiom(o, assertion3));
		
		m.applyChange(new AddAxiom(o, assertion4));
		m.applyChange(new AddAxiom(o, assertion5));
		m.applyChange(new AddAxiom(o, assertion6));
		m.applyChange(new AddAxiom(o, assertion7));
		m.applyChange(new AddAxiom(o, assertion8));
		m.applyChange(new AddAxiom(o, assertion9));
		m.applyChange(new AddAxiom(o, assertion10));
		m.applyChange(new AddAxiom(o, assertion11));
		m.applyChange(new AddAxiom(o, assertion12));
		
		
		m.applyChange(new AddAxiom(o, df.getOWLSubClassOfAxiom(RedWine, Wine)));
		m.applyChange(new AddAxiom(o, df.getOWLSubClassOfAxiom(DryWine, Wine)));
		
		OWLClassExpression TableWine1 = df.getOWLObjectIntersectionOf(RedWine, DryWine);
			
		//GET THE DOMAIN TO GET VALUES FROM REGIONCANADA!!
		OWLAxiom ax3 = df.getOWLEquivalentClassesAxiom(TableWine, TableWine1);
		m.applyChange(new AddAxiom(o, ax3));
		
		OWLObjectProperty produces = df.getOWLObjectProperty(IRI.create(my_iri + "#Produces"));
		//df.getOWLObjectPropertyDomainAxiom(produces, RegionCanada);
		m.applyChange(new AddAxiom(o, df.getOWLObjectPropertyRangeAxiom(produces, Wine)));
		
		//OWLAxiom assertion13 = df.getOWLObjectPropertyAssertionAxiom(produces, region1, wine1);
		//OWLAxiom assertion14 = df.getOWLObjectPropertyAssertionAxiom(produces, region1, wine2);
		//OWLAxiom assertion15 = df.getOWLObjectPropertyAssertionAxiom(produces, region1, wine3);
		OWLAxiom assertion16 = df.getOWLObjectPropertyAssertionAxiom(produces, region2, wine5);
		OWLAxiom assertion17 = df.getOWLObjectPropertyAssertionAxiom(produces, region2, wine6);
		OWLAxiom assertion18 = df.getOWLObjectPropertyAssertionAxiom(produces, region2, wine7);
		//OWLAxiom assertion19 = df.getOWLObjectPropertyAssertionAxiom(produces, region3, wine5);
		//OWLAxiom assertion20 = df.getOWLObjectPropertyAssertionAxiom(produces, region3, wine3);
		//OWLAxiom assertion21 = df.getOWLObjectPropertyAssertionAxiom(produces, region3, wine4);
		
		//m.applyChange(new AddAxiom(o, assertion13));
		//m.applyChange(new AddAxiom(o, assertion14));
		//m.applyChange(new AddAxiom(o, assertion15));
		m.applyChange(new AddAxiom(o, assertion16));
		m.applyChange(new AddAxiom(o, assertion17));
		m.applyChange(new AddAxiom(o, assertion18));
		//m.applyChange(new AddAxiom(o, assertion19));
		//m.applyChange(new AddAxiom(o, assertion20));
		//m.applyChange(new AddAxiom(o, assertion21));
		
		OWLClassExpression CanadianWine1 = df.getOWLObjectSomeValuesFrom(produces, TableWine);
		OWLClassExpression CanadianWine2 = df.getOWLObjectIntersectionOf(CanadianWine1, RegionCanada);
		
		OWLAxiom ax2 = df.getOWLEquivalentClassesAxiom(CanadianWines, CanadianWine2);
		m.applyChange(new AddAxiom(o, ax2));
		
		Reasoner hermit = new Reasoner(o);
		
		hermit.precomputeSameAsEquivalenceClasses();
		
		hermit.precomputeInferences(InferenceType.CLASS_ASSERTIONS, 
				InferenceType.OBJECT_PROPERTY_ASSERTIONS,
				InferenceType.CLASS_HIERARCHY);
		
		hermit.printHierarchies(new PrintWriter(System.out), true, true, true);
		//System.out.println(hermit.getInstances(CanadianWines, true));
		//System.out.println(hermit.getSuperClasses(CanadianWines, true));

		//System.out.println(hermit.getInstances(TableWine,true));
		
		for(OWLNamedIndividual i : hermit.getInstances(CanadianWines, true).getFlattened()){
			//System.out.println(hermit.getObjectPropertyValues(i, produces));
		
			for(OWLNamedIndividual ii : hermit.getObjectPropertyValues(i, produces).getFlattened()){
				
				if(hermit.getInstances(TableWine, true).containsEntity(ii))
					System.out.println(ii);
			}
			//System.out.println(hermit.getTypes(i, true));
			
		}
		
		*/
