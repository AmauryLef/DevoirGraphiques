package sio.devoir2graphiques;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import sio.devoir2graphiques.Tools.ConnexionBDD;
import sio.devoir2graphiques.Tools.GraphiqueController;

import java.net.URL;
import java.sql.*;
import java.util.HashMap;
import java.util.ResourceBundle;

public class Devoir2GraphiquesController implements Initializable
{


    ConnexionBDD maCnx;
    GraphiqueController graphiqueController;

    XYChart.Series<String,Number> serieGraph1;
    XYChart.Series<String,Double> serieGraph3;
    @FXML
    private Button btnGraph1;
    @FXML
    private Button btnGraph2;
    @FXML
    private Button btnGraph3;
    @FXML
    private AnchorPane apGraph1;
    @FXML
    private LineChart graph1;
    @FXML
    private Label lblTitre;
    @FXML
    private AnchorPane apGraph2;
    @FXML
    private AnchorPane apGraph3;
    @FXML
    private PieChart graph3;
    @FXML
    private BarChart graph2;

    String sexe = graphiqueController.getSexe();

    Double salaire = graphiqueController.getAllSalaires();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblTitre.setText("Devoir : Graphique n°1");
        apGraph1.toFront();

            try {
                maCnx = new ConnexionBDD();
                graphiqueController = new GraphiqueController();

                serieGraph1 = new XYChart.Series<>();
                serieGraph1.setName("Nom des Traders");



                graph1.getData().add(serieGraph1);

            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }




    @FXML
    public void menuClicked(Event event) throws SQLException {
        if(event.getSource() == btnGraph1)
        {
            lblTitre.setText("Devoir : Graphique n°1");
            apGraph1.toFront();

           HashMap<Integer, Double> lesDatasDuGraph1 = graphiqueController.getGraphique1(salaire);

            serieGraph1 = new XYChart.Series<>();
            serieGraph1.setName("Moyenne des Salaires par Age");
            for (Integer age : graphiqueController.getGraphique1(salaire).keySet())
            {
                serieGraph1.getData().add(new XYChart.Data<>(age, graphiqueController.getGraphique1(salaire).get()));
            }

            graph1.getData().add(serieGraph1);
            for (XYChart.Data<String, Number> entry : serieGraph1.getData())
            {
                Tooltip t = new Tooltip(entry.getYValue().toString()+ ":" + entry.getXValue().toString());
                t.setStyle("-fx-background-color: #3D9ADA");
                Tooltip.install(entry.getNode(), t);
            }

        }
        else if(event.getSource() == btnGraph2)
        {
            lblTitre.setText("Devoir : Graphique n°2");
            apGraph2.toFront();

            HashMap<String, Integer> lesDatasDuGraph2 = graphiqueController.getGraphique2(sexe);

            graph3.getData().clear();
            serieGraph3 = new XYChart.Series<>();
            for (String tranche : lesDatasDuGraph2.keySet())
            {
                serieGraph3.setName(tranche);
                for(int i = 0; i<lesDatasDuGraph2.get(tranche); i+=2)
                {
                    serieGraph3.getData().add(new XYChart.Data<>(lesDatasDuGraph2.get(tranche).get(i).toString(),
                            Integer.parseInt(lesDatasDuGraph2.get(sexe).get(i+1))));

                }
                graph3.getData().add(serieGraph3);
                serieGraph3 = new XYChart.Series<>();

            }

        }
        else
        {
            lblTitre.setText("Devoir : Graphique n°3");
            apGraph3.toFront();

            ObservableList serieGraph2 = FXCollections.observableArrayList();

            for (String sexe : graphiqueController.getGraphique3().keySet())
            {
                serieGraph2.add(new PieChart.Data(sexe,Double.parseDouble(graphiqueController.getGraphique3().get(sexe))));
            }
            graph2.setData(serieGraph2);

            for (PieChart.Data entry : graph2.getData())
            {
                Tooltip t = new Tooltip(entry.getName().toString()+ ":" + entry.getPieValue());
                t.setStyle("-fx-background-color: #3D9ADA");
                Tooltip.install(entry.getNode(), t);
            }

        }



    }
}