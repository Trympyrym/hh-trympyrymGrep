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

        byte[] byteArray = new byte[128000];
        ByteBuffer buffer = ByteBuffer.wrap(byteArray);
        long time1 = 0;
        long time2 = 0;
        long time3 = 0;
        for (String filename: filenames)
        {
            long start1 = System.nanoTime();
            tree.resetRowCounter();
            try (FileChannel channel = new FileInputStream(filename).getChannel())
            {
                int count;
                while ((count = channel.read(buffer)) != -1)
                {
                    long start2 = System.nanoTime();
                    for (int i = 0; i < count; i++)
                    {
                        if (tree.next((char)byteArray[i]))
                        {
                            long start3 = System.nanoTime();
                            System.out.println(filename + ":" + tree.getCurrentRowNumber());
                            time3 += System.nanoTime() - start3;
                        }
                        buffer.clear();
                    }
                    time2 += System.nanoTime() - start2;
                }
            }
            time1 += System.nanoTime() - start1;
        }
        System.out.println("1:" + time1);
        System.out.println("2:" + time2);
        System.out.println("3:" + time3);
    }
}
