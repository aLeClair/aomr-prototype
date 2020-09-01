package pac.knowledgecoordinator;

import javax.swing.*;

import pac.contextcoordinator.Context;
import pac.contextcoordinator.ContextCoordinator;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Set;

public class ReasoningInterface {
    private JPanel mainInterface;
    private JComboBox<String> contextBox;
    private JComboBox queryBox;
    private JComboBox reasonerBox;
    private JButton submitButton;
    private JTextPane reasoningResults;
    private ContextCoordinator cc = ContextCoordinator.getInstance();
    private KnowledgeCoordinator kc;

    public ReasoningInterface() {

        contextBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String selected = contextBox.getSelectedItem().toString();
                populateQueries(selected);
            }
        });
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String context = contextBox.getSelectedItem().toString();
                int query = queryBox.getSelectedIndex();
                kc.reason(context,query);
            }
        });
    }

    public void setupInterface(){
        createUIComponents();
    }


    public void createInterface(KnowledgeCoordinator kc) {
        JFrame frame = new JFrame("ReasoningInterface");
        frame.setContentPane(mainInterface);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        setupInterface();

        frame.setVisible(true);

        this.kc = kc;
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        populateContexts();
        populateReasoner();

        reasoningResults.setEditable(false);
        reasoningResults.setContentType("text/html");
    }

    private void populateContexts() {
        String[] contexts = cc.getContexts().toArray(new String[cc.getContexts().size()]);
        ComboBoxModel<String> model = new DefaultComboBoxModel<>(contexts);
        contextBox.setModel(model);
    }

    private void populateQueries(String context) {
        String[] temp = null;
        switch(context){
            case "realestate":
                temp = new String[]{"Which neighborhoods have 2-bedroom houses?", "Tell me about the houses on Castro St.", "Which houses have a tall building and a muni stop on the same street?"};
                break;
            case "infrastructure":
                temp = new String[]{"Which streets require repaving?", "Which parks need to be maintained?", "Which areas of the city need to be inspected?"};
                break;
            case "tourism":
                temp = new String[]{"What is a route for an Ant-Man tour?", "Where are famous San Francisco sites?", "Which parks were featured in a film?"};
                break;
        }
        String[] queries = temp;
        ComboBoxModel<String> model = new DefaultComboBoxModel<String>(queries);
        queryBox.setModel(model);
    }

    private void populateReasoner(){
        String[] reasoners = {"Pellet"};
        ComboBoxModel<String> model = new DefaultComboBoxModel<>(reasoners);
        reasonerBox.setModel(model);
        }

        public void updateText(List<String> text){
        String toAdd = "";
            for(String s : text){
                if(s.contains("..."))
                    s = "<b>"+s+"</b>";
                s = s +"<br/";
                toAdd = toAdd + s;
            }
            reasoningResults.setText(toAdd);
        }
}
