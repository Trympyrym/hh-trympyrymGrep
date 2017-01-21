public class Main {

    public static void main(String[] args) {
	// write your code here
        KeywordTree tree = new KeywordTree();
        tree.add("abc");
        tree.add("cccb");
        tree.add("bcdc");
        tree.add("bcdd");
        tree.add("bbbc");
        tree.findAllPatterns("abcdcbcddbbbcccbbbcccbb");
    }
}
