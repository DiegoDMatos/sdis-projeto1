package sdis.projeto.servicea;

import com.rabbitmq.client.*;

public class ServiceA {
    private static final String FILA_PEDIDO = "fila_full";
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
                System.out.println("RabbitMQ ainda não pronto, tentando novamente");
                Thread.sleep(2000);
            }
        }
        
        Channel channel = connection.createChannel();

        channel.queueDeclare(FILA_PEDIDO, false, false, false, null);
        channel.queueDeclare(FILA_RESPOSTA, false, false, false, null);

        System.out.println("SERVIÇO A AGUARDANDO MENSAGENS...");

        DeliverCallback callback = (consumerTag, delivery) -> {

            System.out.println("Mensagem recebida no Serviço A!");

            FullTranscript ft = new FullTranscript();
            ft.run();

            String resposta = ft.ftHandler.getText();

            channel.basicPublish("", FILA_RESPOSTA, null, resposta.getBytes());
        };

        channel.basicConsume(FILA_PEDIDO, true, callback, consumerTag -> { });
    }
}
