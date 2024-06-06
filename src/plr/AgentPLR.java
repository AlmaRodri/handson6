package plr;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import java.util.*;
public class AgentPLR extends  Agent{
    private boolean ocupado = false; // Variable de estado
    protected void setup() {
        System.out.println("Agent "+getLocalName()+" started.");
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("Regresion");
        sd.setName("Polinomial");
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        }
        catch (FIPAException fe) {
            fe.printStackTrace();
        }
        addBehaviour(new APLR());
    }
    private class APLR extends CyclicBehaviour{
        public void action(){
            // Recibir el mensaje
            MessageTemplate met = MessageTemplate.MatchPerformative(ACLMessage.CFP);
            ACLMessage mensaje = myAgent.receive(met);
            if (mensaje != null) {
                if (ocupado) {
                    // Enviar mensaje de rechazo
                    ACLMessage reply = mensaje.createReply();
                    reply.setPerformative(ACLMessage.REFUSE);
                    reply.setContent("Agent is busy. Please try again later.");
                    myAgent.send(reply);
                } else{
                    ocupado = true;
                    System.out.println("recibiendo datos: ");
                // Procesar el mensaje y extraer los datos
                String contenido = mensaje.getContent();
                String[] partes = contenido.split(";");
                String[] datosX = partes[0].split(",");
                String[] datosY = partes[1].split(",");

                // Convertir los datos de cadena a arrays de double
                double[] xValues = new double[datosX.length];
                double[] yValues = new double[datosY.length];
                for (int i = 0; i < datosX.length; i++) {
                    xValues[i] = Double.parseDouble(datosX[i]);
                }
                for (int i = 0; i < datosY.length; i++) {
                    yValues[i] = Double.parseDouble(datosY[i]);
                }

                // Crear la matriz X con las potencias necesarias para regresión polinómica
                double[][] x = new double[xValues.length][4]; // Asumiendo regresión cúbica (1, x, x^2, x^3)
                for (int i = 0; i < xValues.length; i++) {
                    x[i][0] = 1;
                    x[i][1] = xValues[i];
                    x[i][2] = Math.pow(xValues[i], 2);
                    x[i][3] = Math.pow(xValues[i], 3);
                }
                // crear un DataSet con los datos recibidos
                DataSet dS = new DataSet(x, yValues);
                DiscretMath disMath = new DiscretMath();
                PLR plr = new PLR(dS, disMath);
                double [] betas = plr.getBetas();
                    StringBuilder equation = new StringBuilder();
                    for (int i = 0; i < betas.length; i++) {
                        if (i == 0) {
                            equation.append(betas[i]);
                        } else {
                            equation.append(",").append(betas[i]).append("x");
                        }
                    }
                    String mensj = equation.toString();
                    ACLMessage reply = mensaje.createReply();
                    reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
                    reply.setContent(mensj);
                    System.out.println("resultado: " + mensj);
                    myAgent.send(reply);
                    ocupado = false;
                }
            }
        }
    }
    protected void takeDown() {
        // Deregister from the yellow pages
        try {
            DFService.deregister(this);
        }
        catch (FIPAException fe) {
            fe.printStackTrace();
        }
        // Printout a dismissal message
        System.out.println("PLR-agent "+getAID().getName()+" terminating.");
    }
}
