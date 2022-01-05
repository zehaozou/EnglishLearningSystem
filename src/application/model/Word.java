package application.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author 永世の青空
 * @Description Model class for a word
 * @CreateTime 2021-07-10 18:10
 */
public class Word implements Comparable,Cloneable{
    /* 数据域 */
    private final StringProperty word;
    private final StringProperty meaning;
    private final StringProperty sentence;
    private final StringProperty sentenceMeaning;
    private final StringProperty ranking;
    private final StringProperty partOfSpeech;

    /**
     * Default constructor.
     */
    public Word(){ this(null,null,null,null,null,null);}

    /**
     * Constructor with initial data.
     */
    public Word(String word, String meaning,String partOfSpeech ,String sentence,String sentence_meaning, String ranking) {
        this.word = new SimpleStringProperty(word);
        this.meaning = new SimpleStringProperty(meaning);
        this.partOfSpeech = new SimpleStringProperty(partOfSpeech);
        this.sentence = new SimpleStringProperty(sentence);
        this.sentenceMeaning=new SimpleStringProperty(sentence_meaning);
        this.ranking=new SimpleStringProperty(ranking);
    }

    public void setWord(String word){ this.word.set(word); }

    public void setMeaning(String meaning) { this.meaning.set(meaning); }

    public void setPartOfSpeech(String partOfSpeech) { this.partOfSpeech.set(partOfSpeech); }

    public void setRanking(String ranking) { this.ranking.set(ranking); }

    public void setSentence(String sentence) { this.sentence.set(sentence); }

    public void setSentenceMeaning(String sentenceMeaning) { this.sentenceMeaning.set(sentenceMeaning); }

    public String getWord() { return word.get(); }

    public String getMeaning() { return meaning.get(); }

    public String getPartOfSpeech() { return partOfSpeech.get(); }

    public String getSentence() { return sentence.get(); }

    public String getSentenceMeaning() { return sentenceMeaning.get(); }

    public String getRanking() { return ranking.get(); }

    public StringProperty wordProperty(){ return word;}

    public StringProperty meaningProperty(){return meaning;}

    public StringProperty partOfSpeechProperty(){return partOfSpeech;}

    public StringProperty sentenceProperty(){return sentence;}

    public StringProperty sentenceMeaningProperty(){return sentenceMeaning;}

    public StringProperty rankingProperty(){return ranking;}

    @Override
    public String toString(){
        return "word{" +
                "word='" + word.get() + '\'' +
                ", meaning='" + meaning.get() + '\'' +
                ", partOfSpeech'"+ partOfSpeech.get() +'\''+
                ", sentence='" + sentence.get() + '\'' +
                ", sentence_meaning='" + sentenceMeaning.get() + '\'' +
                ", ranking='" + ranking.get() + '\'';
    }

    @Override
    public int compareTo(Object o) {
        Word temp = (Word) o;
        return this.getWord().compareToIgnoreCase(temp.getWord());
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
