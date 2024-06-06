package Gradiente;
import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import java.util.*;

public class SimpleAgent extends Agent {
    private boolean ocupado = false; // Variable de estado
  protected void setup() {
    System.out.println("Agent "+getLocalName()+" started.");
      addBehaviour(new Gradiente());
      // Register the pso service in the yellow pages
      DFAgentDescription dfd = new DFAgentDescription();
      dfd.setName(getAID());
      ServiceDescription sd = new ServiceDescription();
      sd.setType("Optimizacion");
      sd.setName("Gradiente");
      dfd.addServices(sd);
      try {
          DFService.register(this, dfd);
      }
      catch (FIPAException fe) {
          fe.printStackTrace();
      }
      // Add the behaviour serving queries from buyer agents
      addBehaviour(new Gradiente());
    }

     private class Gradiente extends CyclicBehaviour{
         double [] dataSet;
          public void action() {
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
                  double[] x = new double[datosX.length];
                  double[] y = new double[datosY.length];
                  for (int i = 0; i < datosX.length; i++) {
                      x[i] = Double.parseDouble(datosX[i]);
                      y[i] = Double.parseDouble(datosY[i]);
                  }
                  // crear un DataSet con los datos recibidos
                  DataSet dataSet = new DataSet(x,y);
                  GradientDescent gradienteD = new GradientDescent(0.0007);
                  String mgs = gradienteD.gradiente(x, y, 90000);
                  ACLMessage reply = mensaje.createReply();
                  reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
                  reply.setContent(mgs);
                  System.out.println("Resultado: "+ mgs);
                  myAgent.send(reply);
                  ocupado = false;
                  }
              } else {
                  // Si no se recibió ningún mensaje, bloquea el comportamiento
                  block();
              }
          }
        public int onEnd() {
            myAgent.doDelete();
            return super.onEnd();
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
        System.out.println("Gradiente-agent "+getAID().getName()+" terminating.");
    }
}
