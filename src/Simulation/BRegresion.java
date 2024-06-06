package Simulation;
import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class BRegresion extends Agent {
    //String datos ="23,26,30,34,43,48,52,57,58;651,762,856,1063,1190,1298,1421,1440,1518";  //genetic
    String datos ="1,2,3,4,5,6,7,8,9,10;10,9,8,7,6,5,4,3,2,1";  //simple
  //String datos= "-3,-2,-1,0,1,2,3;7.5,3,0.5,1,3,6,14"; // gradiente
    //pso
  /* String datos = "53,67,98,123,88,109,145,77,94,136,111,102,83,120,146,70,64,137,95,112,130,75,104,99,142,86,69,118,"+
            "132,106,139,54,89,127,93,143,91,71,126,135,105,85,140,113,128,147,78,107,92,121,65,116,129,134,76,90,148,"+
            "138;58,70,102,126,92,115,149,82,99,139,113,105,88,124,150,75,69,141,98,116,133,78,108,104,145,91,74,121,"+
            "134,110,142,57,93,130,97,146,95,73,129,137,109,89,143,117,132,151,79,111,96,125,68,120,131,136,77,94,152,140";
*/
    private AID[] ClasificaAgents;
    public void setup() {
        // Printout a welcome message
        System.out.println("Hello! Search regression "+getAID().getName()+" is ready.");
        addBehaviour(new TickerBehaviour(this, 5000) {
            protected void onTick() {
                System.out.println("Buscando...");
                // Update the list of clasifica agents
                DFAgentDescription template = new DFAgentDescription();
                ServiceDescription sd = new ServiceDescription();
                sd.setType("Clasificacion");
                template.addServices(sd);
                try {
                    DFAgentDescription[] result = DFService.search(myAgent, template);
                    System.out.println("Found the following clacifica agents:");
                    ClasificaAgents = new AID[result.length];
                    for (int i = 0; i < result.length; ++i) {
                        ClasificaAgents[i] = result[i].getName();
                        System.out.println(ClasificaAgents[i].getName());
                    }
                } catch (FIPAException fe) {
                    fe.printStackTrace();
                }
                // Perform the request
                myAgent.addBehaviour(new RequestPerformer());
            }
        });
    }

protected void takeDown() {
    // Printout a dismissal message
    System.out.println("Search regresion-agent "+getAID().getName()+" terminating.");
}
private class RequestPerformer extends Behaviour{
    private void enviarMensaje(String contenido){
        AID targetAgent = new AID(contenido, AID.ISLOCALNAME);
        ACLMessage msg = new ACLMessage(ACLMessage.CFP);
        msg.addReceiver(targetAgent);
        msg.setContent(datos);
        msg.setConversationId("data-classification");
        msg.setReplyWith("cfp" + System.currentTimeMillis());
        myAgent.send(msg);
    }
    private AID bestClassifier; // El agente que esta disponible
    private int refusesCnt = 0; // El contador de respuestas refusas de los agentes clasificadores
    private MessageTemplate mt,met; // La plantilla para recibir respuestas
    private int step = 0; // El paso actual en el comportamient

    public void action() {
        switch (step) {
            case 0:
                // Enviar un mensaje de consulta a todos los agentes clasificadores
                ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
                for (AID clasificaAgent : ClasificaAgents) {
                    cfp.addReceiver(clasificaAgent);
                }
                cfp.setContent(datos);
                cfp.setConversationId("data-classification");
                cfp.setReplyWith("cfp" + System.currentTimeMillis());
                myAgent.send(cfp);
                mt = MessageTemplate.and(MessageTemplate.MatchConversationId("data-classification"),
                        MessageTemplate.MatchInReplyTo(cfp.getReplyWith()));
                step = 1;
                break;
            case 1:
                ACLMessage reply = myAgent.receive(mt);
                if (reply != null) {
                    if (reply.getPerformative() == ACLMessage.REFUSE) {
                        refusesCnt++;
                    } else if (reply.getPerformative() == ACLMessage.ACCEPT_PROPOSAL) {
                        // Enviar datos al agente especificado en la respuesta
                        String response = reply.getContent();
                        System.out.println("recibido: "+ response);
                        if (response.equalsIgnoreCase("Simple")){
                            enviarMensaje("Simple");
                        }else if (response.equalsIgnoreCase("Multiple")){
                            enviarMensaje("Multiple");
                        } else if (response.equalsIgnoreCase("Polinomial")) {
                            enviarMensaje("Polinomial");
                        }else if (response.equalsIgnoreCase("Genetic")) {
                            enviarMensaje("Genetic");
                        }else if (response.equalsIgnoreCase("PSO")) {
                            enviarMensaje("PSO");
                        }  else if (response.equalsIgnoreCase("Gradiente")) {
                            enviarMensaje("Gradiente");
                        }
                    }
                    step = 2;
                } else {
                    block(50);
                }
                break;
            case 2:
                ACLMessage devuelto = myAgent.receive(met);
                if (devuelto != null) {
                    if (devuelto.getPerformative() == ACLMessage.REFUSE) {
                        step = 1;  // Reintentar si se recibe un REFUSE
                    } else if (devuelto.getPerformative() == ACLMessage.ACCEPT_PROPOSAL) {
                        // Obtener la respuesta que contiene la ecuación
                        String responde = devuelto.getContent();
                            String[] parts = responde.split(",");
                            double[] betas = new double[parts.length];
                            for (int i = 0; i < parts.length; i++) {
                                if (i == 0) {
                                    betas[i] = Double.parseDouble(parts[i]); // b0
                                } else {
                                    // Eliminar solo el último carácter "x"
                                    String betaValue = parts[i].substring(0, parts[i].length() - 1);
                                    betas[i] = Double.parseDouble(betaValue);
                                }
                            }
                            StringBuilder equation = new StringBuilder("La ecuación es: y = " + betas[0]);
                            for (int i = 1; i < betas.length; i++) {
                                    equation.append(" + ").append(betas[i]).append("x").append(i);
                            }
                            System.out.println(equation);
                        // Hacer 5 predicciones
                        double[] inputs = {10,20,30,50,70};  // Ejemplo de entradas
                        double[] predictions = new double[inputs.length];
                        for (int i = 0; i < inputs.length; i++) {
                            double y = betas[0];  // Empezar con b0
                            for (int j = 1; j < betas.length; j++) {
                                y += betas[j] * inputs[i];
                            }
                            predictions[i] = y;
                        }
                        // Imprimir las predicciones
                        System.out.println("Predicciones:");
                        for (int i = 0; i < inputs.length; i++) {
                            System.out.println("Predicción para x=" + inputs[i] + ": y=" + predictions[i]);
                        }
                        myAgent.doDelete();
                        step = 3; // Actualizar el paso a 3 después de realizar las predicciones
                    }
                } else {
                    block();
                }
                break;
        }
    }

    public boolean done() {
        // Terminar cuando el paso es 3
        return step == 3;
    }
}
}
