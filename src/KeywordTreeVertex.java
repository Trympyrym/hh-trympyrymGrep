import java.util.HashMap;
import java.util.Map;

/**
 * Created by Trympyrym on 16.01.2017.
 */
public class KeywordTreeVertex {

    public KeywordTreeVertex()
    {
        this(null, null);
    }

    public KeywordTreeVertex(KeywordTreeVertex parent, Character parentSymbol)
    {
        this.patternNumber = 0;
        this.parent = parent;
        this.parentSymbol = parentSymbol;
    }

    public Boolean hasChild(Character argSymbol)
    {
        return children.containsKey(argSymbol);
    }

    public void makeChild(Character argSymbol)
    {
        children.put(argSymbol, new KeywordTreeVertex(this, argSymbol));
    }

    public KeywordTreeVertex getNext(Character argSymbol)
    {
        return children.get(argSymbol);
    }

    public void setPatternNumber(Integer patternNumber) {
        this.patternNumber= patternNumber;
    }


    private int patternNumber = 0;
    public boolean isPattern()
    {
        return patternNumber > 0;
    }
    public int getPatternNumber()
    {
        return patternNumber;
    }

    private KeywordTreeVertex parent = null;
    public boolean isRoot()
    {
        return (this.parent == null);
    }

    private Character parentSymbol = null;

    Map<Character, KeywordTreeVertex> children = new HashMap<>();
    Map<Character, KeywordTreeVertex> autoMove = new HashMap<>();

    private KeywordTreeVertex suffixLink = null;
    private boolean suffixLinkCalculated()
    {
        return suffixLink != null;
    }

    private KeywordTreeVertex goodSuffixLink = null;
    private boolean goodSuffixLinkCalculated()
    {
        return goodSuffixLink != null;
    }

    private KeywordTreeVertex getSuffixLink() {
        if (!suffixLinkCalculated())
        {
            initSuffixLink();
        }
        return suffixLink;
    }

    public KeywordTreeVertex getGoodSuffixLink() {
        if (!goodSuffixLinkCalculated())
        {
            initGoodSuffixLink();
        }
        return goodSuffixLink;
    }

    public KeywordTreeVertex getAutoMove(Character argSymbol)
    {
        if (!autoMove.containsKey(argSymbol))
        {
            initAutoMove(argSymbol);
        }
        return autoMove.get(argSymbol);
    }

    private void initSuffixLink()
    {
        if (isRoot())
        {
            suffixLink = this;
        }
        else
        {
            if (parent.isRoot())
            {
                suffixLink = parent;
            }
            else
            {
                suffixLink = parent.getSuffixLink().getAutoMove(parentSymbol);
            }
        }
    }

    private void initAutoMove(Character argSymbol)
    {
        if (children.containsKey(argSymbol))
        {
            autoMove.put(argSymbol, children.get(argSymbol));
        }
        else
        {
            if (isRoot())
            {
                autoMove.put(argSymbol, this);
            }
            else
            {
                autoMove.put(argSymbol, getSuffixLink().getAutoMove(argSymbol));
            }
        }
    }

    private void initGoodSuffixLink()
    {
        KeywordTreeVertex localSuffixLink = getSuffixLink();
        //System.out.println(this + " " + localSuffixLink + " " + localSuffixLink.isRoot());
        if (localSuffixLink.isRoot())
        {
            goodSuffixLink = localSuffixLink;
        }
        else
        {
            goodSuffixLink = localSuffixLink.isPattern() ? localSuffixLink : localSuffixLink.getGoodSuffixLink();
        }
    }
}
