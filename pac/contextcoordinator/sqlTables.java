package pac.contextcoordinator;

public class sqlTables {

    static String speed_limits = "CREATE TABLE speedlimits (CNN int, STREET varchar(128), ST_TYPE varchar(128), " +
            "STREET_GC varchar(128), FROM_ST varchar(128), TO_ST varchar(128), SPEEDLIMIT int, " +
            "SCHOOLZONE varchar(128), SCHOOLZONE_LIMIT varchar(128), MTAB_DATE varchar(128), " +
            "MTAB_MOTION varchar(128), MTAB_RESO_TEXT varchar(128), STATUS varchar(128), " +
            "WORKORDER varchar(128), shape varchar(2048))";


    static String pedestrian_volume = "CREATE TABLE pedestrianvolume (OBJECTID int primary key, CNN int, CNNTEXT varchar(128)," +
            "ST_NAME1 varchar(128), ST_TYPE1 varchar(128), ST_NAME2 varchar(128), ST_TYPE2 varchar(128), " +
            "ST_NAME3 varchar(128), ST_TYPE3 varchar(128), ST_NAME4 varchar(128), ST_TYPE4 varchar(128), " +
            "TOTEMP2 int, UNIVPROX int, SIGNALIZED int, PKGMETERS int, MAXPCTSLPE int, MODEL6_VOL int, HH_PEDMODE int," +
            "PCOL_04_09 int, PCOL_RATE int, POINT varchar(128))";

    static String paving_score = "CREATE TABLE pavingscore (CNN int, Street_Name varchar(128), " +
            "PCI_Score int, From_Street varchar(128), To_Street varchar(128), PCI_Change_Date date, " +
            "Treatment_Or_Survey varchar(128), Street_Accepted_For_Maintenance varchar(128), " +
            "Functional_Class varchar(128))";


    static String maintainable_street = "CREATE TABLE maintainablestreet (CNN int, STREET varchar(128), " +
            "PCI_Score int, SPEEDLIMIT int, MODEL6_VOL int)";

    static String park_properties = "CREATE TABLE parkproperties (OBJECTID int, Map_Label varchar(128), Longitude real," +
            "Latitude real, Acres real, TMA_PropertyID bigint, GlobalID varchar(128), created_user varchar(128), created_date bigint," +
            "last_edited_user varchar(128), last_edited_date bigint, SquareFeet real, PerimeterLength real, PropertyType varchar(128)," +
            "Address varchar(128), City varchar(128), State varchar(128), Zipcode int, Complex varchar(128), PSA varchar(128)," +
            "SupDist varchar(128), Ownership varchar(128), Land_ID bigint, GGP_Section varchar(128), State_Senate varchar(128), " +
            "MONS_Neighborhood varchar(128), Police_District varchar(128), US_Congress varchar(128), Realtor_Neighborhood varchar(128)," +
            "State_Assembly varchar(128), Planning_Neighborhood varchar(128))";

    static String park_scores = "CREATE TABLE parkscores (Park varchar(128), Park_Type varchar(128), Park_Site_Score real, PSA varchar(128)," +
            "Supervisor_District int)";

    static String maintainable_park = "CREATE TABLE maintainablepark(Map_Label varchar(128), Longitude real, Latitude real, SquareFeet real, Park_Site_Score real)";

    static String real_estate_inventory = "CREATE TABLE realestateinventory (ACTDT varchar(128), YEAR_COMP varchar(128), YR_QTR varchar(128), ACTION varchar(128)," +
            "APP_NO varchar(128), FM varchar(128), NUMBER varchar(128), STREET varchar(128), ST_TYPE varchar(128), BLOCK varchar(128), LOT varchar(128)," +
            "BLOCKLOT varchar(128), EXT_USE varchar(128), PROP_USE varchar(128), AFF_HSG int, AFF_TARGET varchar(128), UNITS int," +
            "NETUNITS int, PLANNING_DISTRICT_NUMBER varchar(128), PLANNING_DISTRICT varchar(128), SUPERVISOR_DISTRICT varchar(128), ANALYSIS_NEIGHBORHOOD varchar(128)" +
            ")";

    static String neighborhoods = "CREATE TABLE neighborhoods (sfar_distr varchar(128), nbrhood varchar(128), nid varchar(128))";

    static String tall_buildings = "CREATE TABLE tallbuildings (OBJECTID int, NAME varchar(128), ADDRESS varchar(128), MAPBLOCKLOT varchar(128)," +
            "MBL_UNIQUE varchar(128), DATE varchar(128), RETROFIT_DATE varchar(128), DESCRIPTION varchar(128), HEIGHT_FT real, STORIES_ABOVE_GRADE int," +
            "STORIES_BELOW_GRADE int, OCCUPANCY varchar(128), STREET varchar(128))";

    static String muni_stops = "CREATE TABLE munistops (OBJECTID int, STOPNAME varchar(128), TRAPEZESTOPABBR varchar(128), RUCUSSTOPABBR varchar(128)," +
            "STOPID int, LATITUDE real, LONGITUDE real, ACCESSIBILITYMASK int, ATSTREET varchar(128), ONSTREET varchar(128), POSITION varchar(128)," +
            "ORIENTATION varchar(128), SERVICEPLANNINGSTOPTYPE varchar(128), SHELTER boolean)";

    static String streets = "CREATE TABLE streets (CNN int, streetname varchar(128))";

    static String film_sites = "CREATE TABLE filmsites (ID int, TITLE varchar(128), RELEASEYEAR int, LOCATIONS varchar(128))";


    public sqlTables() {

    }

}
