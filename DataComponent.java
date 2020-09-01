package pac.contextcoordinator;

import java.sql.*;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;



public class DataComponent {
    private static DataComponent instance = null;
    private CSVReaderToSQL csv;

    private DataComponent(){
        csv = new CSVReaderToSQL();

        //These functions only need to be run once: to create the dataset, and to load the data into it
        //Since we are not dealing with live datasets, they were not made into their own functions with the need to update


        //dropAllDataSets();
        //createAllDataSets();
        //uploadAllDataSets();

        //dropDataSet("speedlimits");
        //dropDataSet("pedestrianvolume");
        //dropDataSet("pavingscore");
        //dropDataSet("maintainablestreet");
        //dropDataSet("parkproperties");
        //dropDataSet("parkscores");
        //dropDataSet("maintainablepark");
        //dropDataSet("realestateinventory");
        //dropDataSet("neighborhoods");
        //dropDataSet("tallbuildings");
        //dropDataSet("munistops");
        //dropDataSet("streets");
        //dropDataSet("filmsites");

        //createDataSet("speedlimits");
        //createDataSet("pedestrianvolume");
        //createDataSet("pavingscore");
        //createDataSet("maintainablestreet");
        //createDataSet("parkproperties");
        //createDataSet("parkscores");
        //createDataSet("maintainablepark");
        //createDataSet("realestateinventory");
        //createDataSet("neighborhoods");
        //createDataSet("tallbuildings");
        //createDataSet("munistops");
        //createDataSet("streets");
        //createDataSet("filmsites");

        //uploadDataSet("speedlimits");
        //uploadDataSet("pedestrianvolume");
        //uploadDataSet("pavingscore");
        //uploadDataSet("parkproperties");
        //uploadDataSet("parkscores");
        //uploadDataSet("realestateinventory");
        //uploadDataSet("neighborhoods");
        //uploadDataSet("tallbuildings");
        //uploadDataSet("munistops");
        //uploadDataSet("streets");
        //uploadDataSet("filmsites");
    }

    public static DataComponent getInstance(){
        if (instance == null)
            instance = new DataComponent();
        return instance;
    }

    //  RELEVANT FOR EMBEDDED SQL
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:derby:pacdb;create=true");
    }

    public void doQuery(String query){
        try(Connection conn = getConnection()) {
            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(query);

            while(result.next()){
            //    System.out.printf(result.getString("STREET") + " " + result.getString("PCI_Score") + " " + result.getString("SPEEDLIMIT") + " " + result.getString("MODEL6_VOL") +"%n"); // MaintainableStreet check
            //    System.out.printf(result.getString("OBJECTID") + " " + result.getString("ST_NAME1") + "%n"); //Pedestrianvolume check
            //    System.out.printf(result.getString("Map_Label") + " " + result.getString("Longitude") + " " + result.getString("Latitude") + " " + result.getString("Acres") + " " + result.getString("Park_Site_Score") +"%n");
            }

        }catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public void exeQuery(String query){
        try(Connection conn = getConnection()) {
            Statement statement = conn.createStatement();
            statement.execute(query);

        }catch (SQLException ex) {
            ex.printStackTrace();
        }

    }


    public ResultSet getData(String domainSpec){
        String query = "SELECT * FROM " + domainSpec;
        try(Connection conn = getConnection()) {
            Statement statement = conn.createStatement();
            return statement.executeQuery(query);
        }catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }

    public void uploadDataSet(String table){
        try(Connection conn = getConnection()) {
            csv.uploadDataSet(table, conn);
            System.out.println("Uploaded data into "+table);
        }catch (SQLException ex) {
            ex.printStackTrace();
        }

    }


    public void createDataSet(String name) {
        try(Connection conn = getConnection()) {
            Statement statement = conn.createStatement();
            if (!doesTableExists(name, conn)) {
                String sql = "";
                switch(name) {
                    case "speedlimits":
                        sql = sqlTables.speed_limits;
                        break;
                    case "pedestrianvolume":
                        sql = sqlTables.pedestrian_volume;
                        break;
                    case "pavingscore":
                        sql = sqlTables.paving_score;
                        break;
                    case "maintainablestreet":
                        sql = sqlTables.maintainable_street;
                        break;
                    case "parkproperties":
                        sql = sqlTables.park_properties;
                        break;
                    case "parkscores":
                        sql = sqlTables.park_scores;
                        break;
                    case "maintainablepark":
                        sql = sqlTables.maintainable_park;
                        break;
                    case "realestateinventory":
                        sql = sqlTables.real_estate_inventory;
                        break;
                    case "neighborhoods":
                        sql = sqlTables.neighborhoods;
                        break;
                    case "tallbuildings":
                        sql = sqlTables.tall_buildings;
                        break;
                    case "munistops":
                        sql = sqlTables.muni_stops;
                        break;
                    case "streets":
                        sql = sqlTables.streets;
                        break;
                    case "filmsites":
                        sql = sqlTables.film_sites;
                        break;
                }

                statement.execute(sql);
                System.out.println("Created table " + name + ".");

            }

        }catch (SQLException ex) {
                    ex.printStackTrace();
        }
    }

    public void getTableNames(){
        try(Connection conn = getConnection()) {
            DatabaseMetaData dbMetaData = conn.getMetaData();
            String[] types = {"TABLE"};

            ResultSet rs = dbMetaData.getTables(null,null,"%",types);
            while(rs.next()){
                String tableCatalog = rs.getString(1);
                String tableSchema = rs.getString(2);
                String tableName = rs.getString(3);

                System.out.printf("%s - %s - %s%n", tableCatalog, tableSchema,tableName);

                ResultSet columns = dbMetaData.getColumns(null,null,tableName,null);
                while(columns.next()){
                    String columnName = columns.getString("COLUMN_NAME");
                    System.out.println(columnName);
                }
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dropDataSet(String table){
        try(Connection conn = getConnection()) {
            Statement statement = conn.createStatement();
            String query = "DROP TABLE "+table;
            statement.execute(query);
            System.out.println("Dropped table" + table +".");
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void closeConnection()  {
        try {
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        } catch (SQLException ex) {
            if (ex.getSQLState().equals("XJ015")) {
                System.out.println("Derby shutdown normally");
            } else {
                ex.printStackTrace();
            }
        }
    }

    private static boolean doesTableExists(String tableName, Connection conn)
            throws SQLException {
        DatabaseMetaData meta = conn.getMetaData();
        ResultSet result = meta.getTables(null, null, tableName.toUpperCase(), null);

        return result.next();
    }

}
