package pac.contextcoordinator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;

public class CSVReaderToSQL {
    Connection conn;
    BufferedReader br = null;
    String line = "";
    String cvsSplitBy = ",";
    HashSet<String> streets;

    public CSVReaderToSQL(){
        streets = new HashSet<String>();
    }

    public void uploadDataSet(String table, Connection connection) throws SQLException {
        this.conn = connection;
        Statement stmt = conn.createStatement();
        try {
            int i = 0;
            switch(table){
                case "speedlimits":
                    br = new BufferedReader(new FileReader("C:\\Users\\Ned\\Desktop\\MVCTool-master\\data\\Speed_Limits_TRUNC.csv"));
                    while((line = br.readLine()) != null) {
                        if (i == 0) {
                            i++;
                        } else {
                            String[] row = line.split(cvsSplitBy);
                            stmt.executeUpdate(uploadData.insertSpeedLimits(row));
                        }
                    }
                    break;
                case "pedestrianvolume":
                    br = new BufferedReader(new FileReader("C:\\Users\\Ned\\Desktop\\MVCTool-master\\data\\Pedestrian_Volume_Model_TRUNC.csv"));
                    while((line = br.readLine()) != null) {
                        if (i == 0) {
                            i++;
                        } else {
                            String[] row = line.split(cvsSplitBy);
                            stmt.executeUpdate(uploadData.insertPedestrianVolume(row));
                        }
                    }
                    break;
                case "pavingscore":
                    br = new BufferedReader(new FileReader("C:\\Users\\Ned\\Desktop\\MVCTool-master\\data\\Streets_Data_-_Pavement_Condition_Index__PCI__Scores_TRUNC.csv"));
                    while((line = br.readLine()) != null) {
                        if (i == 0) {
                            i++;
                        } else {
                            String[] row = line.split(cvsSplitBy);
                            stmt.executeUpdate(uploadData.insertPavingScore(row));
                        }
                    }
                    break;
                case "parkproperties":
                    br = new BufferedReader(new FileReader("C:\\Users\\Ned\\Desktop\\MVCTool-master\\data\\Recreation_and_Parks_Properties.csv"));
                    while((line = br.readLine()) != null) {
                        if (i == 0) {
                            i++;
                        } else {
                            String[] row = line.split(cvsSplitBy);
                            stmt.executeUpdate(uploadData.insertParkProperty(row));
                        }
                    }
                    break;
                case "parkscores":
                    br = new BufferedReader(new FileReader("C:\\Users\\Ned\\Desktop\\MVCTool-master\\data\\Park_Evaluation_Scores_starting_Fiscal_Year_2015.csv"));
                    while((line = br.readLine()) != null) {
                        if (i == 0) {
                            i++;
                        } else {
                            String[] row = line.split(cvsSplitBy);
                            stmt.executeUpdate(uploadData.insertParkScore(row));
                        }
                    }
                        break;
                case "realestateinventory":
                    br = new BufferedReader(new FileReader("C:\\Users\\Ned\\Desktop\\MVCTool-master\\data\\Housing_Inventories_2005_to_2018.csv"));
                    while((line = br.readLine()) != null) {
                        if (i == 0) {
                            i++;
                        } else {
                            String[] row = line.split(cvsSplitBy);
                            stmt.executeUpdate(uploadData.insertHousingInventory(row));
                        }
                    }
                    break;

                case "neighborhoods":
                    br = new BufferedReader(new FileReader("C:\\Users\\Ned\\Desktop\\MVCTool-master\\data\\Realtor_Neighborhoods.csv"));
                    while((line = br.readLine()) != null) {
                        if (i == 0) {
                            i++;
                        } else {
                            String[] row = line.split(cvsSplitBy);
                            stmt.executeUpdate(uploadData.insertNeighborhood(row));
                        }
                    }
                    break;
                case "tallbuildings":
                    br = new BufferedReader(new FileReader("C:\\Users\\Ned\\Desktop\\MVCTool-master\\data\\Tall_Building_Inventory.csv"));
                    while((line = br.readLine()) != null) {
                        if (i == 0) {
                            i++;
                        } else {
                            String[] row = line.split(cvsSplitBy);
                            stmt.executeUpdate(uploadData.insertTallBuilding(row));
                        }
                    }
                    break;

                case "munistops":
                    br = new BufferedReader(new FileReader("C:\\Users\\Ned\\Desktop\\MVCTool-master\\data\\Muni_Stops.csv"));
                    while((line = br.readLine()) != null) {
                        if (i == 0) {
                            i++;
                        } else {
                            String[] row = line.split(cvsSplitBy);
                            stmt.executeUpdate(uploadData.insertMuniStop(row));
                        }
                    }
                    break;
                case "streets":
                    br = new BufferedReader(new FileReader("C:\\Users\\Ned\\Desktop\\MVCTool-master\\data\\Muni_Stops.csv"));
                    while((line = br.readLine()) != null) {
                        if (i == 0) {
                            i++;
                        } else {
                            String[] row = line.split(cvsSplitBy);
                            if(!streets.contains(row[1])) {
                                stmt.executeUpdate(uploadData.insertStreet(row));
                                streets.add(row[1]);
                            }
                        }
                    }
                    break;
                case "filmsites":
                    br = new BufferedReader(new FileReader("C:\\Users\\Ned\\Desktop\\MVCTool-master\\data\\Film_Locations_in_San_Francisco.csv"));
                    while((line = br.readLine()) != null) {
                        if (i == 0) {
                            i++;
                        } else {
                            String[] row = line.split(cvsSplitBy);
                            if(!streets.contains(row[1])) {
                                stmt.executeUpdate(uploadData.insertFilmSite(row));
                                streets.add(row[1]);
                            }
                        }
                    }
                    break;

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
