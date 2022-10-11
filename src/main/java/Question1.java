public class Question1 {
    /**
     *
     */
    private class Solution {
        <T> T solve() {
            return null;
        }
    }

    public static void main(String[] args) {
        var solution = new Question1().new Solution();
        var result = solution.<Object>solve();
        System.out.println(
            result
        );
    }

}
