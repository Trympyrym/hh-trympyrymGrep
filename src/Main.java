import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
	// write your code here
        if (args.length < 2)
        {
            System.out.println("not enough arguments");
            return;
        }
        String pattern = args[0];
        List<String> filenames = new ArrayList<>();
        for (int i = 1; i < args.length; i++)
        {
            filenames.add(args[i]);
        }

        KeywordTree tree = new KeywordTree();
        tree.add(pattern);

        FileChannel channel = new FileInputStream(filenames.get(0)).getChannel();
        byte[] byteArray = new byte[128000];
        ByteBuffer buffer = ByteBuffer.wrap(byteArray);
        int count;
        while ((count = channel.read(buffer)) != -1)
        {
            for (int i = 0; i < count; i++)
            {
                if (tree.next((char)byteArray[i]))
                    {
                        System.out.println(filenames.get(0) + ":" + tree.getCurrentRowNumber());
                    }
                    buffer.clear();
            }
        }


        //System.out.println(tree.checkString("right light.... In my opinion perpetual peace is possible but--I do not"));

//        for (String filename : filenames)
//        {
//            BufferedReader reader = new BufferedReader(new FileReader(filename));
//            String currentLine = reader.readLine();
//            int lineCounter = 1;
//            while (currentLine != null)
//            {
//                if (tree.checkString(currentLine))
//                {
//                    System.out.println(filename + " " + lineCounter);
//                }
//                currentLine = reader.readLine();
//                lineCounter++;
//            }

        //AtomicBoolean eofReached = new AtomicBoolean(false);
        //ByteBuffer buffer = ByteBuffer.allocate(1024);
        //Thread thread = new Thread(new Task(buffer, eofReached));
        //thread.start();

//        for (String filename : filenames)
//        {
//            Path filepath = Paths.get(filename);
//            try (ByteChannel channel = Files.newByteChannel(filepath))
//            {
//                int count;
//                do {
//                    count = channel.read(buffer);
//                    if (count != -1)
//                    {
//                        buffer.rewind();
//                        for (int i = 0; i < count; i++)
//                            if (tree.next((char)buffer.get()))
//                            {
//                                System.out.println(filename + ":" + tree.getCurrentRowNumber());
//                            }
//                    }
//                } while (count != -1);
//            }
//        }
    }
}
