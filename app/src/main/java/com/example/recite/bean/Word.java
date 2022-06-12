package com.example.recite.bean;

import java.util.ArrayList;

public class Word {
    private int wordID;
    private String wordHead = null;
    private String usphone = null;
    private ArrayList<PartOfSpeech> partOfSpeeches = null;
    private ArrayList<PartOfSpeech> FalsePartOfSpeeches = null;
    private ArrayList<Sentence> sentences= null;
    private boolean isFinished;

    public Word(int wordID, String wordHead, String usphone) {
        this.wordID = wordID;
        this.wordHead = wordHead;
        this.usphone = usphone;

        partOfSpeeches = new ArrayList<>();
        FalsePartOfSpeeches = new ArrayList<>();
        sentences = new ArrayList<>();
    }

    public class PartOfSpeech {
        public String character;
        public String mean;

        public PartOfSpeech(String character, String mean) {
            this.character = character;
            this.mean = mean;
        }
    }

    public class Sentence {
        public String sContent;
        public String sCn;

        public Sentence(String sContent, String sCn) {
            this.sContent = sContent;
            this.sCn = sCn;
        }
    }

    public int getWordID() {
        return wordID;
    }

    public void setWordID(int wordID) {
        this.wordID = wordID;
    }

    public String getWordHead() {
        return wordHead;
    }

    public void setWordHead(String wordHead) {
        this.wordHead = wordHead;
    }

    public String getUsphone() {
        return usphone;
    }

    public void setUsphone(String usphone) {
        this.usphone = usphone;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public ArrayList<PartOfSpeech> getPartOfSpeeches() {
        return partOfSpeeches;
    }

    public void setPartOfSpeeches(ArrayList<PartOfSpeech> partOfSpeeches) {
        this.partOfSpeeches = partOfSpeeches;
    }

    public ArrayList<Sentence> getSentences() {
        return sentences;
    }

    public void setSentences(ArrayList<Sentence> sentences) {
        this.sentences = sentences;
    }

    public ArrayList<PartOfSpeech> getFalsePartOfSpeeches() {
        return FalsePartOfSpeeches;
    }

    public void setFalsePartOfSpeeches(ArrayList<PartOfSpeech> falsePartOfSpeeches) {
        FalsePartOfSpeeches = falsePartOfSpeeches;
    }

    public void addPartOfSpeeches(String character, String mean) {
        PartOfSpeech partOfSpeech = new PartOfSpeech(character, mean);
        partOfSpeeches.add(partOfSpeech);
    }
    public void addSentence(String sContent, String sCn) {
        Sentence sentence = new Sentence(sContent, sCn);
        sentences.add(sentence);
    }

    public void addFalsePartOfSpeeches(String character, String mean) {
        PartOfSpeech partOfSpeech = new PartOfSpeech(character, mean);
        FalsePartOfSpeeches.add(partOfSpeech);
    }

}
