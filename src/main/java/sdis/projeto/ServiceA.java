package sdis.projeto;

import com.rabbitmq.client.*;

public class ServiceA {
    private static final String FILA_PEDIDO = "fila_full";
    private static final String FILA_RESPOSTA = "fila_respostas";

    public static void main(String[] args) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost"); 
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(FILA_PEDIDO, false, false, false, null);
        channel.queueDeclare(FILA_RESPOSTA, false, false, false, null);

        System.out.println("SERVIÇO A AGUARDANDO MENSAGENS...");

        DeliverCallback callback = (consumerTag, delivery) -> {

            System.out.println("Mensagem recebida no Serviço A!");

            FullTranscript ft = new FullTranscript();
            ft.run(); //

            String resposta = ft.ftHandler.getText();


            channel.basicPublish("", FILA_RESPOSTA, null, resposta.getBytes());
        };

        channel.basicConsume(FILA_PEDIDO, true, callback, consumerTag -> { });
    }
}

