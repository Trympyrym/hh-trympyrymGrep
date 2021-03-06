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

    private KeywordTreeVertex currentVertex;
    private int currentRowNumber = 1;
    private boolean patternFound = false;

    public void resetRowCounter()
    {
        currentRowNumber = 1;
    }

    public boolean next(char currentChar)
    {
        if (currentChar == '\n')
        {
            currentVertex = root;
            currentRowNumber++;
            patternFound = false;
            return false;
        }
        if (patternFound)
        {
            return false;
        }
        currentVertex = currentVertex.getAutoMove(currentChar);
        boolean result = check(currentVertex);
        if (result)
        {
            patternFound = true;
            return true;
        }
        else
        {
            return false;
        }
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
