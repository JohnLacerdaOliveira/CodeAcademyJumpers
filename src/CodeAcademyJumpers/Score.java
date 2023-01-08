package CodeAcademyJumpers;

import java.io.*;

public class Score {

    private int maxScore;

    private int score;


    private void loadFile() throws IOException {
        FileReader reader = new FileReader("score.txt");
        BufferedReader bReader = new BufferedReader(reader);

        String line = bReader.readLine();
        this.maxScore = (line == null || line.length() == 0) ? 0 : Integer.parseInt(line) ;

        bReader.close();
        reader.close();
    }


    public void updateFile() throws IOException {
        if (this.score > this.maxScore) {
            FileWriter writer = new FileWriter("score.txt");
            BufferedWriter bWriter = new BufferedWriter(writer);

            bWriter.write(String.valueOf(this.score));

            bWriter.flush();
            bWriter.close();
        }
    }


    public Score() throws IOException {
        this.loadFile();
    }

    public int getScore() {
        return this.score;
    }

    public int getMaxScore() {
        return this.maxScore;
    }

    public void upScore() {
        this.score+=1000;
    }
}
