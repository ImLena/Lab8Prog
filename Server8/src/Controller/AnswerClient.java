package Controller;

import Other.Answer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.RecursiveAction;
import java.util.logging.Logger;

public class AnswerClient extends RecursiveAction {
    private SocketChannel channel;
    private Answer msg;
    private Logger log = Logger.getLogger(AnswerClient.class.getName());

    public AnswerClient(SocketChannel channel, Answer msg) {
        this.channel = channel;
        this.msg = msg;
    }

    @Override
    protected void compute() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(msg);
            byte[] data = baos.toByteArray();
            channel.write(ByteBuffer.wrap(data));
            log.info("Answer sent");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
