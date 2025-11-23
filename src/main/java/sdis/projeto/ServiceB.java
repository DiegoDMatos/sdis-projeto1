package sdis.projeto;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.rabbitmq.client.*;

public class ServiceB {

    private static final String FILA_PEDIDO = "fila_search";
    private static final String FILA_RESPOSTA = "fila_respostas";




    public static void main(String[] args) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(FILA_PEDIDO, false, false, false, null);
        channel.queueDeclare(FILA_RESPOSTA, false, false, false, null);

        System.out.println("SERVIÇO B AGUARDANDO MENSAGENS...");

        DeliverCallback callback = (consumerTag, delivery) -> {

            System.out.println("Mensagem recebida no Serviço B!");

            SearchWord sw = new SearchWord();
            sw.run();

            Gson gson = new Gson();
            JsonElement swHandler = null;
            String resposta = gson.toJson(swHandler);
            channel.basicPublish("", FILA_RESPOSTA, null, resposta.getBytes());
        };

        channel.basicConsume(FILA_PEDIDO, true, callback, consumerTag -> { });
    }
}


