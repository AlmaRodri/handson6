package mlr;
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
public class AgMLR extends Agent {
    private boolean ocupado = false; // Variable de estado
    protected void setup() {
     System.out.println("Agent "+getLocalName()+" started.");
    DFAgentDescription dfd = new DFAgentDescription();
      dfd.setName(getAID());
    ServiceDescription sd = new ServiceDescription();
      sd.setType("Regresion");
      sd.setName("Multiple");
      dfd.addServices(sd);
      try {
        DFService.register(this, dfd);
    }
      catch (FIPAException fe) {
        fe.printStackTrace();
    }
    addBehaviour(new ReMLR());
}
private class ReMLR extends CyclicBehaviour{

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
              // Procesar el mensaje y extraer los datos
              String contenido = mensaje.getContent();
              String[] partes = contenido.split(";");
              String[] datosX = partes[0].split(",");
              String[] datosY = partes[1].split(",");

              // Convertir los datos de cadena a arrays
              double[] x = new double[datosX.length];
              double[] y = new double[datosY.length];
              for (int i = 0; i < datosX.length; i++) {
                  x[i] = Double.parseDouble(datosX[i]);
                  y[i] = Double.parseDouble(datosY[i]);
              }
              // crear un DataSet con los datos recibidos
                  System.out.println("datos a dataset");
              DataSet dS = new DataSet(x, y);
          DiscreteMaths disMath = new DiscreteMaths();
          MLR mlr = new MLR(dS, disMath);
              String b0 = String.valueOf(mlr.calculateSlope2());
              String b1 = String.valueOf(mlr.calculateSlope1());
              String b2 = String.valueOf(mlr.calculateIntersection());
              String resultado =b2+","+b1+"x,"+b0+"x";
                  System.out.println("resultado: "+ resultado);
              ACLMessage reply = mensaje.createReply();
                  reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
                  reply.setContent(resultado);
                  myAgent.send(reply);
              ocupado = false;
              }
      }else {
              block();
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
        System.out.println("MLR-agent "+getAID().getName()+" terminating.");
    }
}
