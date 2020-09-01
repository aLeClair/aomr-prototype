package pac.contextcoordinator;


public class uploadData {

    //This class takes a row and will automatically create the SQL insert query for the dataset

    public static String insertSpeedLimits(String[] row) {
        //THIS IS ONLY FOR THE SPEEDLIMITS DATASET. MAKE ONE PER DATASET
        //This is to check for stupid streets that have apostrophes in it
        if (row[1].contains("'"))
            row[1] = row[1].replace("'","''");
        if (row[4].contains("'"))
            row[4] = row[4].replace("'","''");
        if (row[5].contains("'"))
            row[5] = row[5].replace("'","''");
        //Some speed limits are empty?? Make them 0.
        if(row[6].isEmpty())
            row[6] = "0";
        for(int i = 15; i < row.length; i++)
            row[14] = row[14]+ "," + row[i];
        String sql = "INSERT INTO speedlimits VALUES (" + row[0] + ",'" + row[1] + "','" + row[2] + "','" + row[3] + "','" + row[4] +
                "','" + row[5] + "'," + row[6] + ",'" + row[7] + "','" + row[8] + "','" + row[9] + "','" + row[10] + "','" + row[11] +
                "','" + row[12] + "','" + row[13] + "','" + row[14] + "')";
        System.out.println(sql);
        return sql;

    }

    public static String insertPedestrianVolume(String[] row) {
        String sql = "INSERT INTO pedestrianvolume VALUES (" + row[0] + "," + row[1] + ",'" + row[2] + "','" + row[3] + "','" + row[4] +
                "','" + row[5] + "','" + row[6] + "','" + row[7] + "','" + row[8] + "','" + row[9] + "','" + row[10] + "'," + row[11] +
                "," + row[12] + "," + row[13] + "," + row[14] + "," + row[15] + "," + row[16] + "," + row[17]
                + "," + row[18] + "," + row[19] + ",'" + row[20]+ "')";
        System.out.println(sql);
        return sql;

    }

    public static String insertPavingScore(String[] row) {
        if(row[1].contains("'")){
            String[] newValue = row[1].split("'");
            row[1] = newValue[0] + newValue[1];
        }
        if(row[3].contains("'")){
            String[] newValue = row[3].split("'");
            row[3] = newValue[0] + newValue[1];
        }
        if(row[5].isEmpty())
            row[5] = "01/01/0001";

        if(row.length == 8) {
            String sql = "INSERT INTO pavingscore VALUES (" + row[0] + ",'" + row[1] + "'," + row[2] + ",'" + row[3] + "','" + row[4] +
                    "','" + row[5] + "','" + row[6] + "','" + row[7] + "','')";
            System.out.println(sql);
            return sql;
        }
        else{
            String sql = "INSERT INTO pavingscore VALUES (" + row[0] + ",'" + row[1] + "'," + row[2] + ",'" + row[3] + "','" + row[4] +
                    "','" + row[5] + "','" + row[6] + "','" + row[7] + "','" + row[8] + "')";
            System.out.println(sql);
            return sql;
        }

    }


    public static String insertParkProperty(String[] row) {
        for(int i = 0; i < row.length; i++){
            if(row[i].contains("'")){
                String[] newValue = row[i].split("'");
                row[i] = newValue[0] + newValue[1];
            }
            if(row[i].contains("&")){
                row[i] = row[i].replace("&","and");
            }
            if(row[i].contains("#")){
                row[i] = row[i].replace("#","");
            }
            if(row[i].contains(" ")){
                row[i] = row[i].replace(" ", "");
            }
            if(row[i].isEmpty())
            row[i] = "-1";
        }


        String sql = "INSERT INTO parkproperties VALUES (" + row[0] + ",'" + row[1] + "'," + row[2] + "," + row[3] + "," + row[4] +
                "," + row[5] + ",'" + row[6] + "','" + row[7] + "'," + row[8] + ",'" + row[9] + "'," + row[10] + "," + row[11] +
                "," + row[12] + ",'" + row[13] + "','" + row[14] + "','" + row[15] + "','" + row[16] + "'," + row[17]
                + ",'" + row[18] + "','" + row[19] + "','" + row[20]+ "','" +row[21] + "'," + row[22] + ",'" + row[23] + "','" + row[24] + "','" +
                row[25] + "','" + row[26] + "','" + row[27] + "','" + row[28] + "','" + row[29] + "','" + row[30] + "')";
        System.out.println(sql);
        return sql;
    }

    public static String insertParkScore(String[] row) {
        for(int i = 0; i < row.length; i++){
            if(row[i].contains("'")){
                String[] newValue = row[i].split("'");
                row[i] = newValue[0] + newValue[1];
            }
            if(row[i].contains("&")){
                row[i] = row[i].replace("&","and");
            }
            if(row[i].contains(" ")){
                row[i] = row[i].replace(" ", "");
            }
        }
        String sql = "INSERT INTO parkscores VALUES ('" + row[0] + "','" + row[1] + "'," + row[2] + ",'" + row[3] + "'," + row[4] +
                ")";
        System.out.println(sql);
        return sql;
    }

    public static String insertHousingInventory(String[] row) {
        for(int i = 0; i < row.length; i++){
            if(row[i].contains("'")){
                String[] newValue = row[i].split("'");
                row[i] = newValue[0] + newValue[1];
            }
            if(row[i].contains("&")){
                row[i] = row[i].replace("&","and");
            }
            if(row[i].contains("\"")){
                row[i] = row[i].replace("\"", "");
            }
            if(row[i].contains(" ")){
                row[i] = row[i].replace(" ", "_");
            }
        }
        if(row[15].isEmpty())
            row[15] = "-1";
        if(row[17].isEmpty())
            row[17] = "-1";
        String sql = "INSERT INTO realestateinventory VALUES ('" + row[0] + "','" + row[1] + "','" + row[2] + "','" + row[3] + "','" + row[4] +
                "','" + row[5] + "','"+ row[6] + "','" + row[7] + "','" + row[8] + "','" + row[9] + "','" + row[10] + "','" + row[11] +
                "','" + row[13] + "','" + row[14] + "'," + row[15] + ",'" + row[16] + "'," + row[17]
                + "," + row[18] + ",'" + row[19] + "','" + row[20]+ "','" +row[21] + "','" + row[22] + "')";
        System.out.println(sql);
        return sql;
    }

    public static String insertNeighborhood(String[] row) {
        for(int i = 0; i < row.length; i++){
            if(row[i].contains("'")){
                String[] newValue = row[i].split("'");
                row[i] = newValue[0] + newValue[1];
            }
            if(row[i].contains("&")){
                row[i] = row[i].replace("&","and");
            }
            if(row[i].contains("\"")){
                row[i] = row[i].replace("\"", "");
            }
            if(row[i].contains(" ")){
                row[i] = row[i].replace(" ", "_");
            }
        }
        if(row[0].equals("None")){
            String sql = "INSERT INTO neighborhoods VALUES ('" + row[0] + "','" + row[row.length-1] +"','null')";
            System.out.println(sql);
            return sql;
        }
        else {
            String sql = "INSERT INTO neighborhoods VALUES ('" + row[0] + "','" + row[row.length - 2] + "','" + row[row.length - 1] + "')";
            System.out.println(sql);
            return sql;
        }
    }

    public static String insertTallBuilding(String[] row) {
        for(int i = 0; i < row.length; i++){
            if(row[i].contains("&")){
                row[i] = row[i].replace("&","and");
            }
            if(row[i].contains("\"")){
                row[i] = row[i].replace("\"", "");
            }
            if(row[i].contains(" ")){
                row[i] = row[i].replace(" ", "_");
            }
        }
        if(row[11].isEmpty()){
            row[11] = "-1";
        }

        String street = row[3].split("_")[1];

            String sql = "INSERT INTO tallbuildings VALUES (" + row[0] + ",'" + row[2] + "','" + row[3] + "','" + row[4] + "','" + row[5] +
                    "','" + row[6] + "','" + row[7] + "','" + row[8] + "'," + row[9] + "," + row[10] + "," + row[11] + ",'" + row[12] + "','" +
                    street + "')";
            System.out.println(sql);
            return sql;
    }

    public static String insertMuniStop(String[] row) {
        for(int i = 0; i < row.length; i++){
            if(row[i].contains("'")){
                String[] newValue = row[i].split("'");
                row[i] = newValue[0] + newValue[1];
            }
            if(row[i].contains("&")){
                row[i] = row[i].replace("&","and");
            }
            if(row[i].contains("\"")){
                row[i] = row[i].replace("\"", "");
            }
            if(row[i].contains(" ")){
                row[i] = row[i].replace(" ", "_");
            }
        }

        if ("1".equals(row[13])) {
            row[13] = "true";
        } else {
            row[13] = "false";
        }
        if(row[7].isEmpty()){
            row[7] = "-1";
        }

        row[8] = row[8].split("_")[0];
        row[9] = row[9].split("_")[0];

        String sql = "INSERT INTO munistops VALUES (" + row[0] + ",'" + row[1] + "','" + row[2] + "','" + row[3] + "'," + row[4] +
                "," + row[5] + "," + row[6] + "," + row[7] + ",'" + row[8] + "','" + row[9] + "','" + row[10] + "','" + row[11] +
                "','" + row[12] + "'," + row[13] + ")";
        System.out.println(sql);
        return sql;
    }

    public static String insertStreet(String[] row) {
        for(int i = 0; i < row.length; i++){
            if(row[i].contains("'")){
                String[] newValue = row[i].split("'");
                row[i] = newValue[0] + newValue[1];
            }
            if(row[i].contains("&")){
                row[i] = row[i].replace("&","and");
            }
            if(row[i].contains("\"")){
                row[i] = row[i].replace("\"", "");
            }
            if(row[i].contains(" ")){
                row[i] = row[i].replace(" ", "_");
            }
        }


        String street = row[1].split("_")[0];

        String sql = "INSERT INTO streets VALUES (" + row[0] + ",'" + street + "')";
        System.out.println(sql);
        return sql;
    }

    public static String insertFilmSite(String[] row) {
        for(int i = 0; i < 4; i++) {
            if (row[i].contains("'")) {
                String[] newValue = row[i].split("'");
                row[i] = newValue[0] + newValue[1];
            }
            if (row[i].contains("&")) {
                row[i] = row[i].replace("&", "and");
            }
            if (row[i].contains("\"")) {
                row[i] = row[i].replace("\"", "");
            }
            if (row[i].contains(" ")) {
                row[i] = row[i].replace(" ", "");
            }
            if (row[i].contains("-")) {
                row[i] = row[i].replace("-","");
            }
        }

        String sql = "INSERT INTO filmsites VALUES (" + row[0] + ",'" + row[1] + "'," + row[2] + ",'" + row[3] + "')";
        System.out.println(sql);
        return sql;
    }
}
