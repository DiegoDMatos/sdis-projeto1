package sdis.projeto.clientejava;

import com.rabbitmq.client.*;

public class Cliente {

    private static final String FILA_FULL = "fila_full";
    private static final String FILA_SEARCH = "fila_search";
    private static final String FILA_RESPOSTA = "fila_resposta";

    public static void main(String[] args) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("rabbitmq");
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("admin");

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
        channel.queueDeclare(FILA_RESPOSTA, false, false, false, null);

        AMQP.BasicProperties props = new AMQP.BasicProperties.Builder()
                .replyTo(FILA_RESPOSTA)
                .build();

        channel.basicPublish("", FILA_FULL, props, "start_full".getBytes());
        System.out.println("Cliente: enviei pedido de FULL TRANSCRIPT!");

        channel.basicPublish("", FILA_SEARCH, props, "start_search".getBytes());
        System.out.println("Cliente: enviei pedido de SEARCH WORD!");

        System.out.println("Cliente aguardando respostas...");

        channel.basicConsume(FILA_RESPOSTA, true, (consumerTag, message) -> {
            String resposta = new String(message.getBody());
            System.out.println("nova resposta recebida: " + resposta);
        }, consumerTag -> {});
    }
}
