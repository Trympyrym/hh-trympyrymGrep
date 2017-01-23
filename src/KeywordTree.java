import java.util.ArrayList;
import java.util.List;

/**
 * Created by Trympyrym on 16.01.2017.
 */
public class KeywordTree {
    private KeywordTreeVertex root;

    private List<String> patterns = new ArrayList<>();

    public KeywordTree() {
        this.root = new KeywordTreeVertex();
        currentVertex = root;
    }

    public void add(String argString) {
        KeywordTreeVertex currentVertex = root;
        for (char currentChar : argString.toCharArray()) {
            if (!currentVertex.hasChild(currentChar)) {
                currentVertex.makeChild(currentChar);
            }
            currentVertex = currentVertex.getNext(currentChar);
        }

        patterns.add(argString);
        currentVertex.setPatternNumber(patterns.size());
    }

    private Boolean contains(String argString) {
        KeywordTreeVertex currentVertex = root;
        for (char currentChar : argString.toCharArray()) {
            if (!currentVertex.hasChild(currentChar)) {
                return false;
            }
            currentVertex = currentVertex.getNext(currentChar);
        }
        return true;
    }

//    public void findAllPatterns(String argString)
//    {
//        KeywordTreeVertex currentVertex = root;
//        int size = argString.length();
//        for (int i = 0; i < size; i++)
//        {
//            char currentChar = argString.toCharArray()[i];
//            currentVertex = currentVertex.getAutoMove(currentChar);
//            check(currentVertex, i + 1);
//        }
//    }

    public boolean checkString(String argString)
    {
        KeywordTreeVertex currentVertex = root;
        for (char currentChar : argString.toCharArray())
        {
            currentVertex = currentVertex.getAutoMove(currentChar);
            if (check(currentVertex))
            {
                return true;
            }
        }
        return false;
    }

    private KeywordTreeVertex currentVertex;
    private int currentRowNumber = 1;

    public boolean next(char currentChar)
    {
        if (currentChar == '\n')
        {
            currentVertex = root;
            currentRowNumber++;
            return false;
        }
        currentVertex = currentVertex.getAutoMove(currentChar);
        return check(currentVertex);
    }

    public int getCurrentRowNumber()
    {
        return currentRowNumber;
    }

    private boolean check(KeywordTreeVertex vertex)
    {
        //System.out.println("check entered " + (vertex.getGoodSuffixLink() == null));
        for (KeywordTreeVertex currentVertex = vertex; !currentVertex.isRoot(); currentVertex = currentVertex.getGoodSuffixLink())
        {
            if (currentVertex.isPattern())
            {
                return true;
            }
        }
        return false;
    }
}
