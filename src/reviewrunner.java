import java.util.Scanner;

public class reviewrunner {
    public static void main(String[] args) {
        Scanner sb = new Scanner(System.in);

        Review review = new Review();
//        double num = review.sentimentVal("good");
//        System.out.print(num);

        double total_score = review.totalSentiment("review.txt");
//        System.out.println(total_score);
        int star_rating = review.starRating("review.txt");
//        System.out.print(star_rating);

        String new_review = review.fakeReview("review.txt");
        System.out.print(new_review);
}}
