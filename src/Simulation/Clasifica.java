package Simulation;
import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.MessageTemplate;
import java.util.*;

public class Clasifica extends Agent{
    private boolean ocupado = false; // Variable de estado

    protected void setup(){
        // Register the clasifica service in the yellow pages
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("Clasificacion");
        sd.setName("Clasifica");
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        }
        catch (FIPAException fe) {
            fe.printStackTrace();
        }
        // Add the behaviour serving queries from buyer agents
        addBehaviour(new OfferRequestsServer());
    }
    // Put agent clean-up operations here
    protected void takeDown() {
        // Deregister from the yellow pages
        try {
            DFService.deregister(this);
        }
        catch (FIPAException fe) {
            fe.printStackTrace();
        }
        // Printout a dismissal message
        System.out.println("clasifica-agent "+getAID().getName()+" terminating.");
    }
    private class OfferRequestsServer extends CyclicBehaviour{
        public void action() {
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);
            ACLMessage mensaje = myAgent.receive(mt);
            if (mensaje != null) {
                if (ocupado) {
                    // Enviar mensaje de rechazo
                    ACLMessage reply = mensaje.createReply();
                    reply.setPerformative(ACLMessage.REFUSE);
                    reply.setContent("Agent is busy. Please try again later.");
                    myAgent.send(reply);
                } else {
                    ocupado = true;
                    // Procesar el mensaje y clasificar los datos
                    String contenido = mensaje.getContent();
                    ACLMessage reply = mensaje.createReply();
                    String resultado = clasificarDatos(contenido);
                    // Enviar el resultado de la clasificación al agente solicitante
                    reply.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
                    reply.setContent(resultado);
                    System.out.println("resultado " + resultado);
                    myAgent.send(reply);
                    ocupado = false;
                }
            } else {
                block();
            }
        }
    }
    private String clasificarDatos(String contenido) {
        String[] partes = contenido.split(";");// Separamos las partes del mensaje
        int numeroArrays = partes.length;
        double sumaX = 0;
        double sumaY = 0;
        double sumaCuadradosX = 0;
        double sumaCuadradosY = 0;
        int totalDatos = 0;
        // Si el mensaje está vacío o no contiene arrays, retornamos un mensaje de error
        if (numeroArrays == 0) {
            return "Error: No se han proporcionado datos";
        }
        for (String array : partes) {
            String[] datos = array.split(","); // Separamos los datos dentro del array
            int numeroDatos = datos.length;
            totalDatos += numeroDatos;
            // Calculamos la suma y los cuadrados de los datos en X y en Y
            for (String datoStr : datos) {
                double dato = Double.parseDouble(datoStr);
                sumaX += dato;
                sumaY += dato;
                sumaCuadradosX += Math.pow(dato, 2);
                sumaCuadradosY += Math.pow(dato, 2);
            }
        }
        double mediaX = sumaX / totalDatos;
        double mediaY = sumaY / totalDatos;
        double varianzaX = (sumaCuadradosX / totalDatos) - Math.pow(mediaX, 2);
        double varianzaY = (sumaCuadradosY / totalDatos) - Math.pow(mediaY, 2);
        double desviacionEstandarX = Math.sqrt(varianzaX);
        double desviacionEstandarY = Math.sqrt(varianzaY);

        // el máximo entre la desviación estándar de X y Y
        double ordenExponencial = Math.max(desviacionEstandarX, desviacionEstandarY);

        // Decidir si utilizar regresión u optimización
        if (totalDatos < 50 && (varianzaX < 10 && varianzaY < 10)) {
            // Decidir tipo de regresión
            if (varianzaX < 10 && varianzaY < 10) {
                return "Simple"; // Baja varianza para regresión simple
            } else if (varianzaX < 20 && varianzaY < 20) {
                return "Multiple"; // Varianza moderada para regresión múltiple
            } else {
                return "Polinomial"; // Alta varianza para regresión polinomial
            }
        } else if (totalDatos >= 50) {
            // Decidir tipo de optimización
            return "PSO"; // Muchos datos para PSO
        } else if (ordenExponencial > 5 || (varianzaX > 100 || varianzaY > 100)) {
            return "Genetic"; // Alta varianza para algoritmo genético
        } else {
            return "Gradiente"; // Casos generales para gradiente descendiente
        }
    }
}
