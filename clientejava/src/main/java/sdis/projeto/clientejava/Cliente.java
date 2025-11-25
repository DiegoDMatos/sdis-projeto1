package sdis.projeto.clientejava;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Cliente {

    private static final String FILA_FULL = "fila_full";
    private static final String FILA_SEARCH = "fila_search";

    public static void main(String[] args) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("rabbitmq");
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("admin");
        // Connection connection = factory.newConnection();

        Connection connection = null;
        while (connection == null) {
            try {
                connection = factory.newConnection();
            } catch (Exception e) {
                System.out.println("RabbitMQ não pronto, tentando novamente...");
                Thread.sleep(2000);
            }
        }
        
        Channel channel = connection.createChannel();

        channel.queueDeclare(FILA_FULL, false, false, false, null);
        channel.queueDeclare(FILA_SEARCH, false, false, false, null);

        String msgFull = "start_full";
        channel.basicPublish("", FILA_FULL, null, msgFull.getBytes());
        System.out.println("Cliente: enviei pedido de FULL TRANSCRIPT!");


        String msgSearch = "start_search";
        channel.basicPublish("", FILA_SEARCH, null, msgSearch.getBytes());
        System.out.println("Cliente: enviei pedido de SEARCH WORD!");

        channel.close();
        connection.close();
    }
}
