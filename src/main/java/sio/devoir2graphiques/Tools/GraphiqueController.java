package sio.devoir2graphiques.Tools;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class GraphiqueController
{
    private Connection cnx;
    private PreparedStatement ps;
    private ResultSet rs;

    public GraphiqueController() {
        cnx = ConnexionBDD.getCnx();
    }

    public Double getAllSalaires()
    {
        ArrayList<Double> lesSalaires = new ArrayList<>();
        try {
            ps = cnx.prepareStatement("SELECT employe.salaireEmp FROM employe");
            rs = ps.executeQuery();
            while (rs.next())
            {
                lesSalaires.add(rs.getDouble(1));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lesSalaires;
    }

    public ArrayList<String> getSexe()
    {
        ArrayList<String> lesSexes = new ArrayList<>();
        try {
            ps = cnx.prepareStatement("select COUNT(employe.sexe)\n" +
                    "from employe \n" +
                    "group by employe.sexe;\n");
            rs = ps.executeQuery();
            while (rs.next())
            {
                lesSexes.add(rs.getString(1));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lesSexes;
    }
    public Object getAllAges()
    {
        ArrayList<String> lesAges = new ArrayList<>();
        try {
            ps = cnx.prepareStatement("SELECT employe.ageEmp FROM employe");
            rs = ps.executeQuery();
            while (rs.next())
            {
                lesAges.add(rs.getString(1));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lesAges;
    }

    public HashMap<Integer, Double> getGraphique1(Double salaire) throws SQLException
    {
        HashMap<Integer, Double> lesDatas = new HashMap<>();

        try
        {
            ps = cnx.prepareStatement("SELECT employe.ageEmp, employe.salaireEmp FROM employe");
            //2) L'éxecuter
            rs = ps.executeQuery();

            //3) Récupérer les données
            while (rs.next())
            {
                lesDatas.put(rs.getInt(1), rs.getDouble(2));
            }
            rs.close();
            ps.close();


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lesDatas;

    }

    public HashMap<String, ArrayList<Integer>> getGraphique2(String sexe, int age) throws SQLException
    {
        HashMap<String, ArrayList<Integer>> lesDatas = new HashMap<>();


        ps = cnx.prepareStatement("SELECT employe.ageEmp, employe.salaireEmp\n" +
                "FROM employe");
        // ='"+nomAction+"'");

        rs = ps.executeQuery();
        //3) Récupérer les données
        while (rs.next())
        {
            if(!lesDatas.containsKey(rs.getString(1)))
            {
                ArrayList<Integer> lesTranchesAges= new ArrayList<>();
                lesTranchesAges.add(rs.getInt(2));
                lesTranchesAges.add(rs.getInt(3));
                lesDatas.put(rs.getString(1),lesTranchesAges );
            }
            else
            {
                lesDatas.get(rs.getString(1)).add(rs.getInt(2));
                lesDatas.get(rs.getString(1)).add(rs.getInt(3));
            }
        }

        ps.close();
        rs.close();

        return lesDatas;
    }


    public ArrayList<String> getGraphique3(String sexe) throws SQLException {

        ArrayList<String> lesDatas = new ArrayList<>();

        ps = cnx.prepareStatement("select employe.sexe, count(employe.sexe) * 100.0 / " +
                "(select count(*) from employe) from employe group by employe.sexe");


        rs = ps.executeQuery();
        while (rs.next()) {
            lesDatas.add(rs.getString(1));
        }

        ps.close();
        rs.close();

        return lesDatas;
    }

}
