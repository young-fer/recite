package com.example.recite.db;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.recite.bean.Word;
import com.example.recite.tool.Tool;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLOutput;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class DBTool {
    private final String DB_NAME = "word.db";
    private SQLiteDatabase myDatabase;
    private Context context;
    private String[] characters = new String[] {"n", "vt", "vi", "v", "adj", "adv", "prep", "conj", "int"};

    //定义类的方法
    public DBTool(Context context) {
        this.context = context;
    }

    // 复制和加载区域数据库中的数据
    public String CopyDBile() throws IOException {

        // 在第一次运行应用程序时，加载数据库到data/data/当前包的名称/database/数据库名字
        //获取准确的路径,context.getPackageName()得到包名
        File dir = new File("data/data/" + context.getPackageName() + "/databases");
        //如果文件夹不存在，则创建指定的文件
        if (!dir.exists() || !dir.isDirectory()) {
            dir.mkdir();
        }
        //文件声明
        File file = new File(dir, DB_NAME);
        //输入流
        InputStream inputStream = null;
        //输出流
        OutputStream outputStream = null;
        //若不存在，通过IO流的方式，将assets目录下的数据库文件，写入到项目模拟手机中，当开启模拟
        //器时，会将数据库文件写入到模拟手机的内存中
        if (!file.exists()) {
            try {
                //创建文件
                file.createNewFile();
                //加载文件
                inputStream = context.getClass().getClassLoader().getResourceAsStream("assets/" + DB_NAME);
                //输出到文件
                outputStream = new FileOutputStream(file);

                byte[] buffer = new byte[1024];
                int len;
                //按字节写入
                while ((len = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, len);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                //关闭资源
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            }
        }
        return file.getPath();
    }

    @SuppressLint("Range")
    public List<Word> selectWord() {
        List<Word> words = new LinkedList<>();

        MyDBOpenHelper myDBOpenHelper = new MyDBOpenHelper(context, "word.db", null, 1);
        SQLiteDatabase sqLiteDatabase = myDBOpenHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query("kaoyan", new String[] { "wordID",
                "wordHead", "usphone" }, null, null, null, null, null);
        int wordID;
        String wordhead = null;
        String usphone = null;
        while (cursor.moveToNext()) {
            wordID = cursor.getInt(cursor.getColumnIndex("wordID"));
            wordhead = cursor.getString(cursor.getColumnIndex("wordHead"));
            usphone = cursor.getString(cursor.getColumnIndex("usphone"));
            words.add(new Word(wordID, wordhead, usphone));
        }
        sqLiteDatabase.close();

        return words;
    }

    @SuppressLint("Range")
    public List<Word> getStuWords() {
        List<Word> words = new LinkedList<>();
        Word word = null;

        MyDBOpenHelper myDBOpenHelper = new MyDBOpenHelper(context, "word.db", null, 1);
        SQLiteDatabase sqLiteDatabase = myDBOpenHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT study.wordID as wordID, wordHead, usphone, state, review, reviewCnt From study, kaoyan " +
                "where study.wordID = kaoyan.wordID and state='study' AND bookID = ? AND userID = ? LIMIT 10", new String[]{String.valueOf(Tool.bookID), String.valueOf(Tool.userID)});

        int wordID, reviewCnt;
        String state, wordHead, usphone;
        Cursor meanCursor;
        while (cursor.moveToNext()) {
            wordID = cursor.getInt(cursor.getColumnIndex("wordID"));
            reviewCnt = cursor.getInt(cursor.getColumnIndex("reviewCnt"));
            state = cursor.getString(cursor.getColumnIndex("state"));
            wordHead = cursor.getString(cursor.getColumnIndex("wordHead"));
            usphone = cursor.getString(cursor.getColumnIndex("usphone"));
            word = new Word(wordID, wordHead, usphone);
            word.setFinished(false);
            setWordMean(word);
            setFalseMeanByWord(word);
            words.add(word);
        }
        cursor.close();

        sqLiteDatabase.close();
        return words;
    }

    @SuppressLint("Range")
    public List<Word> getReviewWords() {
        List<Word> words = new LinkedList<>();
        Word word = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = null;
        Calendar calendar = Calendar.getInstance();

        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
        date = sdf.format(calendar.getTime());

        MyDBOpenHelper myDBOpenHelper = new MyDBOpenHelper(context, "word.db", null, 1);
        SQLiteDatabase sqLiteDatabase = myDBOpenHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT study.wordID as wordID, wordHead, usphone, state, review, reviewCnt From study, kaoyan " +
                "where study.wordID = kaoyan.wordID and state='hasStudy' and review <= ? LIMIT 10", new String[]{date});

        int wordID, reviewCnt;
        String state, wordHead, usphone;
        Cursor meanCursor;
        while (cursor.moveToNext()) {
            wordID = cursor.getInt(cursor.getColumnIndex("wordID"));
            reviewCnt = cursor.getInt(cursor.getColumnIndex("reviewCnt"));
            state = cursor.getString(cursor.getColumnIndex("state"));
            wordHead = cursor.getString(cursor.getColumnIndex("wordHead"));
            usphone = cursor.getString(cursor.getColumnIndex("usphone"));
            word = new Word(wordID, wordHead, usphone);
            word.setFinished(false);
            setWordMean(word);
            setFalseMeanByWord(word);
            words.add(word);
        }
        cursor.close();

        sqLiteDatabase.close();
        return words;
    }

    @SuppressLint("Range")
    public void setWordMean(Word word) {
        int wordID = word.getWordID();
        MyDBOpenHelper myDBOpenHelper = new MyDBOpenHelper(context, "word.db", null, 1);
        SQLiteDatabase sqLiteDatabase = myDBOpenHelper.getWritableDatabase();
        Cursor meanCursor = sqLiteDatabase.rawQuery("SELECT * FROM partofspeech where wordID = ?", new String[]{String.valueOf(wordID)});
        if(meanCursor.moveToFirst())
        {
            List<String> word_part_of_speech = new ArrayList<>();
            for(int i = 0; i < characters.length; ++i) {
                word_part_of_speech.add(meanCursor.getString(meanCursor.getColumnIndex(characters[i])));
            }

            for(int i = 0; i < characters.length; ++i) {
                if (word_part_of_speech.get(i) != null) {
                    word.addPartOfSpeeches(characters[i], word_part_of_speech.get(i));
                }
            }
        }
        meanCursor.close();
    }

    @SuppressLint("Range")
    public void setFalseMeanByWord(Word word) {
        Word falseWord;
        int wordID = word.getWordID();
        MyDBOpenHelper myDBOpenHelper = new MyDBOpenHelper(context, "word.db", null, 1);
        SQLiteDatabase sqLiteDatabase = myDBOpenHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM KAOYAN WHERE wordID != ? ORDER BY Random()LIMIT 3 ", new String[]{String.valueOf(wordID)});

        int falseWordID;
        String falseWordHead, falseWordUsphone;
        while (cursor.moveToNext()) {
            falseWordID = cursor.getInt(cursor.getColumnIndex("wordID"));
            falseWordHead = cursor.getString(cursor.getColumnIndex("wordHead"));
            falseWordUsphone = cursor.getString(cursor.getColumnIndex("usphone"));
            falseWord = new Word(falseWordID, falseWordHead, falseWordUsphone);

            System.out.println("设置falseWordMean");
            setWordMean(falseWord);
            String falseCharacter = falseWord.getPartOfSpeeches().get(0).character;
            String falseMean = falseWord.getPartOfSpeeches().get(0).mean;
            word.addFalsePartOfSpeeches(falseCharacter, falseMean);
        }
        cursor.close();

        sqLiteDatabase.close();

    }

    @SuppressLint("Range")
    public void setHasStudy(Word word) {
        int wordID = word.getWordID();
        MyDBOpenHelper myDBOpenHelper = new MyDBOpenHelper(context, "word.db", null, 1);
        SQLiteDatabase sqLiteDatabase = myDBOpenHelper.getWritableDatabase();
        sqLiteDatabase.execSQL("UPDATE study SET state = 'hasStudy' where wordID = ? AND bookID = ? AND userID = ?", new String[] {String.valueOf(wordID), String.valueOf(Tool.bookID) , String.valueOf(Tool.userID)});
        sqLiteDatabase.close();
    }

    @SuppressLint("Range")
    public int getHasStuWordCnt() {
        int cnt = 0;
        MyDBOpenHelper myDBOpenHelper = new MyDBOpenHelper(context, "word.db", null, 1);
        SQLiteDatabase sqLiteDatabase = myDBOpenHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT count(*) FROM study WHERE state = 'study' AND userID = ? AND bookID = ?", new String[]{String.valueOf(Tool.userID), String.valueOf(Tool.bookID)});
        if(cursor.moveToFirst()){
            cnt = cursor.getInt(0);
        }
        return cnt;
    }

    @SuppressLint("Range")
    public int getNotStuWordCnt() {
        int cnt = 0;
        MyDBOpenHelper myDBOpenHelper = new MyDBOpenHelper(context, "word.db", null, 1);
        SQLiteDatabase sqLiteDatabase = myDBOpenHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT count(*) FROM study WHERE state != 'study' AND userID = ?", new String[]{String.valueOf(Tool.userID)});
        if(cursor.moveToFirst()){
            cnt = cursor.getInt(0);
        }
        return cnt;
    }

    @SuppressLint("Range")
    public int getReviewWordCnt() {
        int cnt = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = null;
        Calendar calendar = Calendar.getInstance();

        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
        date = sdf.format(calendar.getTime());

        MyDBOpenHelper myDBOpenHelper = new MyDBOpenHelper(context, "word.db", null, 1);
        SQLiteDatabase sqLiteDatabase = myDBOpenHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT count(*) FROM study WHERE state == 'hasStudy' AND userID = ? AND review <= ?", new String[]{String.valueOf(Tool.userID), date});
        if(cursor.moveToFirst()){
            cnt = cursor.getInt(0);
        }
        return cnt;
    }

    public void updateReviewDate(Word word) {
        int reviewCnt = 0;
        String date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();



//        Calendar calendar = Calendar.getInstance();
//        try {
//            calendar.setTime(sdf.parse("2021-09-20"));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        System.out.println("修改日期后的日历时间是：" +  sdf.format(calendar.getTime()));
//        calendar.set(Calendar.MONTH,Calendar.JANUARY);
//        System.out.println("修改月份后的日历时间是：" + sdf.format(calendar.getTime()));

        MyDBOpenHelper myDBOpenHelper = new MyDBOpenHelper(context, "word.db", null, 1);
        SQLiteDatabase sqLiteDatabase = myDBOpenHelper.getReadableDatabase();
        // 获取已经复习的次数
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT reviewCnt FROM study WHERE wordID = ? AND userID = ? AND bookID = ?", new String[]{String.valueOf(word.getWordID()), String.valueOf(Tool.userID), String.valueOf(Tool.bookID)});
        if(cursor.moveToFirst()){
            reviewCnt = cursor.getInt(0);
        }
        cursor.close();

        if (reviewCnt == 0) {
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE)+1);
        }else if(reviewCnt == 1) {
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE)+2);
        }else if(reviewCnt == 2) {
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE)+4);
        }else if(reviewCnt == 3) {
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE)+8);
        }else if(reviewCnt == 4) {
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE)+16);
        }else if(reviewCnt > 4) {
            if(reviewCnt % 2 == 1) {
                calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE)+8);
            }else {
                calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE)+16);
            }
        }

        sqLiteDatabase.execSQL("UPDATE study SET reviewCnt = ? WHERE wordID = ? ", new String[]{String.valueOf(reviewCnt+1), String.valueOf(word.getWordID())});
        date = sdf.format(calendar.getTime());
        sqLiteDatabase.execSQL("UPDATE study SET review = ? WHERE wordID = ? ", new String[]{date, String.valueOf(word.getWordID())});
        sqLiteDatabase.close();

    }

}