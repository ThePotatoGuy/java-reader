
/**
 * I, Andre Ponce, pledge that this program is my own independent work and conforms to Oxford Academy's 
 * Academic Honesty Guidelines
 */
import java.io.FileReader;
import java.io.File;
import java.io.FileWriter;
import javax.swing.*;
import java.io.IOException;
// import java.util.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JavaReader
{
    private ArrayList<String> theBook;
    private String[] words; // all the words in the book
    private JTextArea displayArea;
    private static JFrame frame;
    private JScrollPane scrollPane;
    private JPanel panel;
    private JLabel chapterLabel, pageLabel, pageNumberLabel;
    private JTextField chapterField, pageField, pageDisplayField;
    private JButton nextPageButton, prevPageButton, gotoPageButton, gotoChapterButton, chooseBookButton, printUniqueWordsButton;;
    public static final int FIELD_HEIGHT = 40;
    public static final int FIELD_WIDTH = 60;
    public static final int FRAME_HEIGHT = 800;
    public static final int FRAME_WIDTH = 700;
    public static final int TEXT_FIELD_WIDTH = 3;
    public static final int PAGE_LENGTH = 300;
    public String text = "";
    public int startPage, endPage, chapterCount, pageNumber;
    public String data;
    //public int MIN_VALUE;
    JavaReader()
    {
        theBook = new ArrayList<String>();
        startPage = 0;
        endPage = startPage+1;
        data = "Welcome to the E-Reader! Choose a book to begin." +
            "\n" + "Copyright Andre Ponce, 2010, 2011."+
            "\n" + "Use for private applications only."+
            "\n" + "Unlawful distriubtion and/or commercialization of this program is" +
            "\n" + "subject to a perjury in court, along with federal charges" +
            "\n" + "against the individual in question. Happy Reading!";
        displayArea = new JTextArea(data, FIELD_HEIGHT, FIELD_WIDTH);
        displayArea.setEditable(false);  
        displayArea.setLineWrap(true);
        displayArea.setWrapStyleWord(true);
        
        pageNumber = 0;
        
        createPrintUniqueWordsButton();
        createNextPageButton(); 
        createPrevPageButton();
        createGotoPageButton();
        createGotoChapterButton();
        createChooseBookButton();
        createPageDisplayField();
        createChapterField();
        createPageField();
        createPanel();
        createFrame();
        displayArea.setSelectionStart(0);
        displayArea.setSelectionEnd(0);
        /*
        try
        {
            readInBookByLine();
            
            
            
        }
        catch(IOException e)
        {
            System.out.println(e);
        }//*/
    }
    private void createFrame()
    {
        JFrame frame = new JFrame();
        frame.setSize(FRAME_WIDTH,FRAME_HEIGHT);
        frame.setTitle("E-Reader");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);    
        frame.add(panel);
    }
    private void createPanel()
    {
        panel = new JPanel();
        panel.add(chooseBookButton);
        panel.add(chapterLabel);
        panel.add(chapterField);
        panel.add(gotoChapterButton);
        panel.add(pageLabel);
        panel.add(pageField);
        panel.add(gotoPageButton);
        //panel.add(pageNumberLabel);
        
        panel.add(printUniqueWordsButton);
        JScrollPane scrollPane = new JScrollPane(displayArea);
        //JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        //JViewport jv = scrollPane.getViewport();  
        //jv.setViewPosition(new Point(0,0));  
        //MIN_VALUE = scrollPane.getVerticalScrollBar().getMinimum();
        //scrollPane.getVerticalScrollBar().setValue(MIN_VALUE);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        
        panel.add(scrollPane);
        
        
        panel.add(prevPageButton);
        panel.add(pageDisplayField);
        panel.add(nextPageButton);
        
    }
    /*private void resetScrollBar()
    {
        MIN_VALUE = scrollPane.getVerticalScrollBar().getMinimum();
        scrollPane.getVerticalScrollBar().setValue(MIN_VALUE);
    }//*/
    private void createNextPageButton()
    {
        nextPageButton = new JButton("Next Page");
        
        class NextPage implements ActionListener
        {
            public void actionPerformed(ActionEvent event)
            {
                startPage=endPage;
                endPage+=1;
                pageNumber++;
                if(calcPageIndex(startPage)>theBook.size())
                {
                    noPage();
                    endPage=startPage;
                    startPage-=1;
                    pageNumber--;
                }
                else
                    text = getPages(startPage, endPage);
                replaceDataRange(text);
                replacePageNumber(pageNumber);
                //resetScrollBar();
                //scrollPane.getVerticalScrollBar().setValue(MIN_VALUE);
            }
        }
        ActionListener nextPageLis = new NextPage();
        nextPageButton.addActionListener(nextPageLis);
    }
    private void createChooseBookButton()
    {
        chooseBookButton = new JButton("Choose Book");
        class Book implements ActionListener
        {
            public void actionPerformed(ActionEvent event)
            {
                try
                {
                    readInBookByLine();
                    startPage = 0;
                    endPage = startPage+1;
                    pageNumber = 0;
                }
                catch(IOException e)
                {
                    JOptionPane.showMessageDialog(null, e);
                }
                replaceDataRange(text);
                replacePageNumber(pageNumber);
                //scrollPane.getVerticalScrollBar().setValue(0);
            }
        }
        ActionListener chooseBookLis = new Book();
        chooseBookButton.addActionListener(chooseBookLis);
    }
    private void createPrevPageButton()
    {
        prevPageButton = new JButton("Prev Page");
        class PrevPage implements ActionListener
        {
            public void actionPerformed(ActionEvent event)
            {
                endPage=startPage;
                startPage-=1;
                pageNumber--;
                if(startPage<=0)
                {
                    noPage();
                    startPage=1;
                    endPage=2;
                    pageNumber=1;
                }
                else
                    text=getPages(startPage,endPage);
                replaceDataRange(text);
                replacePageNumber(pageNumber);
                //scrollPane.getVerticalScrollBar().setValue(0);
            }
        }
        ActionListener prevPageLis = new PrevPage();
        prevPageButton.addActionListener(prevPageLis);
    }
    private void createGotoChapterButton()
    {
        gotoChapterButton = new JButton("Go to Chapter");
        class Chapter implements ActionListener
        {
            public void actionPerformed(ActionEvent event)
            {
                int chapter;
                try
                {
                    chapter = Integer.parseInt(chapterField.getText());
                    
                    if(chapter>chapterCount)
                        JOptionPane.showMessageDialog(null,"Chapter does not exist.");
                    else
                        text=getChapter(chapter);
                }
                catch(NumberFormatException e)
                {
                    JOptionPane.showMessageDialog(null,"Chapter does not exist.");
                }
                replacePageNumber(pageNumber);
                replaceDataRange(text);
                //scrollPane.getVerticalScrollBar().setValue(0);
            }
        }
        ActionListener chapterLis = new Chapter();
        gotoChapterButton.addActionListener(chapterLis);
    }
    private void createGotoPageButton()
    {
        gotoPageButton = new JButton("Go to page");
        class Page implements ActionListener
        {
            public void actionPerformed(ActionEvent event)
            {
                int page;
                try
                {
                    page = Integer.parseInt(pageField.getText());
                    startPage = page;
                    endPage = page+1;
                    pageNumber = page;
                    if(calcPageIndex(startPage)>theBook.size())
                        noPage();
                    else
                        text=getPages(startPage,endPage);
                        
                }
                catch(NumberFormatException e)
                {
                    noPage();
                }
                replaceDataRange(text);
                replacePageNumber(pageNumber);
                //scrollPane.getVerticalScrollBar().setValue(0);
            }
        }
        ActionListener listener = new Page();
        gotoPageButton.addActionListener(listener);
    }    
    private void createPrintUniqueWordsButton()
    {
        printUniqueWordsButton = new JButton("Print unique words");
        class UniqueWords implements ActionListener
        {
            public void actionPerformed(ActionEvent event)
            {
                //JOptionPane.showMessageDialog(null, "Please wait, this will take a while.");
                convertToArray();
                text = text +"\n"+printUniqueWordsString();
                replaceDataRange(text);
            }
        }
        ActionListener list5 = new UniqueWords();
        printUniqueWordsButton.addActionListener(list5);
    }
    private void noPage()
    {
        JOptionPane.showMessageDialog(null,"Page does not exist.");
    }
    private void createPageDisplayField()
    {
        pageNumberLabel = new JLabel("Current Page Number: ");
        pageDisplayField = new JTextField(TEXT_FIELD_WIDTH);
        pageDisplayField.setText(Integer.toString(pageNumber));
        pageDisplayField.setEditable(false);
    }
    private void createChapterField()
    {
        chapterLabel = new JLabel("Chapter: ");
        
        chapterField = new JTextField(TEXT_FIELD_WIDTH);
        chapterField.setText("");
    }
    private void createPageField()
    {
        pageLabel = new JLabel("Page: ");
        pageField = new JTextField(TEXT_FIELD_WIDTH);
        pageField.setText("");
        
    }
    public void cleanUpWordsArray()
    {
        // removes punctuation marks. except hyphens in between words
        // use cleanWord method
        for(int i = 0; i < words.length;i++)
        {
            words[i] = cleanWord(words[i]);
        }
        //System.out.println("All dirty words cleaned up!");
        
    }
    
    public static String cleanWord(String dirtyWord)
    {  
        // -"space-ship."!
        if(dirtyWord == null || dirtyWord.length() == 0)
            return dirtyWord;
        int start = 0;
        int end = dirtyWord.length()-1;
        // while loop from front to find start index
        while(start < dirtyWord.length() && 
        !Character.isLetterOrDigit(dirtyWord.charAt(start)))
            start++;
        while(end >= 0 && 
        !Character.isLetterOrDigit(dirtyWord.charAt(end)))
            end--;
        // use Character.isLetterOrDigit(...)
        // loop from back to find end index
     
        if(start > end)
            return dirtyWord;
        //System.out.print(dirtyWord + " : ");
        // System.out.println(dirtyWord.substring(start, end+1));
        return dirtyWord.substring(start, end+1);
    }
    
    public void sortWords()
    {
        Arrays.sort(words);  //*/
        //Collections.sort(theBook);
        //System.out.println("Words sorted.");
        text = text + "\n"+"Words sorted.";
    }
    
    
    
    public void readInBookByLine() throws IOException 
    {
        
        FileReader in = null;
        JFileChooser chooser = new JFileChooser();
        if(chooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION)
        {
            theBook.clear();
            File selectedFile = chooser.getSelectedFile();
            FileReader fin = new FileReader(selectedFile);
            StringTokenizer strTok;  // newly added
            String line = new String();  // changed name from input to line
            String wrd;  // newly added
            Scanner src = new Scanner(fin);
            while (src.hasNextLine()) 
            {
                line = src.nextLine();
                strTok = new StringTokenizer(line);
                //System.out.println(input); // echo book to console
                while(strTok.hasMoreTokens())
                {
                    wrd = strTok.nextToken();
                    theBook.add(wrd);
                }
                theBook.add("\n");   // that's a backslash
            } 
            //System.out.println("Book size: " + theBook.size());
            //convertToArray();
            chapterCount=0;
            for(String word: theBook)
            {
                if(word.equals("CHAPTER"))
                    chapterCount++;
                    
            }
            //System.out.println("There are "+count+" chapters.");
            text="Book size: " + theBook.size() + ". There are "+chapterCount+" chapters. Press next page button to begin.";
        }
        
    }
       
    public void convertToArray()
    // precondition: arraylist theBook is filled
    {
    
        // count real words in theBook
        // create an array "words" to the wordCount
        
        // then copy each valid word from theBook to "words" array
        
        // in an array, you access using [i]
        // in an arraylist, you access items using .get(i)
        
        int wordCount = 0;
        String blah = new String();
        for(String word: theBook)
        {
            if(isAWordOrNumber(word) == true)
                wordCount++;
        }
        int n = 0;
        String newWords[] = new String[wordCount];
        for(String word: theBook)
        {
            if(isAWordOrNumber(word) == true)
            {
                newWords[n] = word;
                n++;
            }
        }
        words = newWords;
        //Object words = theBook.toArray();
        //System.out.println("IT WORKED! Words converted to Array");
        text = "Converted words to array.";
    }
    
    public boolean isAWordOrNumber(String wrd)
    {
        //for(int i = 0; i < wrd.length(); i++)
        
        if(Character.isLetterOrDigit(wrd.charAt(0)))
            return true;
    
        return false;
    }
    
    
    
    /*
    public void readInBook() throws IOException 
    {
    FileReader fin = new FileReader("Rendezvous with Rama.txt");
    
    String input = new String();
    
    Scanner src = new Scanner(fin);
    while (src.hasNext()) 
    {
        input = src.next();
    
        System.out.println(input); // echo book to console
        theBook.add(input);
    } 
    System.out.println("book size: " + theBook.size());
    
    words = new String[theBook.size()];
    words = theBook.toArray();
    cleanUpWordsArray();
    
    }
    
    public void quadSortWords()
    {
        // this sorts all the words in "words" array
     
    }//*/
    public void displayWords()
    {  
        // displays all the words in the words array
        
        for(int i = 0; i < words.length; i++)
        {
            System.out.println(i+": "+words[i]);
        }
        
        
    }
    public void displayBook()
    {
        // displays the book with original line spacing, except
        // tab stops at the beginning of each paragraph.
        
        for(String word: theBook)
            System.out.print(word+" ");
            
    }
    public boolean bookContains(String word)
    {
        // tests whether the book contains a certain word
        for(String word2: words)
        {
            if(word2.equalsIgnoreCase(word))
                return true;
        }
        return false;
    }
    
    public void lowercaseWordsInWords()
    { 
        // makes all the words in words array lowercase
        for(int i = 0; i < words.length;i++)
        {
            
            words[i] = words[i].toLowerCase();
        }
        //eSystem.out.println("The words has been lowercased.");
    }
     
    
    public int frequencyOfWord(String word)
    { 
        // using the words array, counts the frequency of the
        // word in the book
        int count = 0;
        for(String word2: words)
        {
            if(word2.equalsIgnoreCase(word))
                count++;
        }
        return count;
    }
    
    public void printPages(int start, int end)
    {  
        // use the getPages(... ) method to print the page to
        // the console
        System.out.println(getPages(start,end));
    }
    
    public ArrayList<String> getAllUniqueWords()
    // precondition: array called words is filled
    //               words is cleaned up (including making it all lowercase)
    //               words is sorted
    {
        ArrayList<String> uniqueWords = new ArrayList<String>();
        cleanUpWordsArray();
        lowercaseWordsInWords();
        sortWords();
        
        for(String word: words)
        {
            if(!uniqueWords.contains(word))
                uniqueWords.add(word);
            
        }
        //System.out.println("Author used " + uniqueWords.size() + " unique words");
        
        return uniqueWords;
    }
    public String printUniqueWordsString()
    {
        ArrayList<String> uniqueWords = getAllUniqueWords();
        String uniqueWordsEnd = "";
        int iNumber = 0;
        for(int i = 0; i < uniqueWords.size(); i++)
        {
            iNumber = i +1;
            uniqueWordsEnd = uniqueWordsEnd + iNumber +": "+uniqueWords.get(i)+" : "+frequencyOfWord(uniqueWords.get(i))+
                " time(s)." + "\n";
        }
        if(uniqueWords.size()==0)
            uniqueWordsEnd = "No unique words exist.";
        return uniqueWordsEnd;
    }
    
    public void printUniqueWords()
    {  
        // uses the getAllUniqueWords() method to retrieve
        // all unique words and prints it.
        ArrayList<String> uniqueWords = getAllUniqueWords();
        for(int i = 0; i < uniqueWords.size(); i++)
        {
            System.out.println(i+1+": "+uniqueWords.get(i)+" : "+frequencyOfWord(uniqueWords.get(i))+" time(s).");
        }
    }
    /*public void printUniqueWordsTest()
    {
        System.out.println(getAllUniqueWords());
    }//*/
    public void clearBook()
    {
        theBook.clear();
    }
    
    public void replaceDataRange(String input)
    {
        displayArea.replaceRange(input,0,data.length());
        data=input;
        //scrollPane.getVerticalScrollBar().setValue(0);
        displayArea.setSelectionStart(0);
        displayArea.setSelectionEnd(0);
    }
    public void replacePageNumber(int page)
    {
        pageDisplayField.setText(Integer.toString(page));
        pageNumber = page;
    }
    public int calcPageIndex(int pageStart)
    {
        return (pageStart*PAGE_LENGTH)-PAGE_LENGTH;
    }
    public int calcPage(int pageIndex)
    {
        return (pageIndex + PAGE_LENGTH)/PAGE_LENGTH;
    }
    
    
    
    
    public String getPages(int startPage, int endPage)
    {
        // 5 to 7 1000th word to 1200th word
        // lets assume each page contains 300 words
        String pages = new String();
        
        int index = calcPageIndex(startPage);
        int endIndex;
        if(calcPageIndex(endPage)>theBook.size())
            endIndex = theBook.size();
        else
            endIndex = calcPageIndex(endPage);
        
        for(int i = index; i < endIndex-1; i++)
        {
            pages = pages + theBook.get(i)+" ";
            
        }
        
        return pages; // so it compiles
    }
    
    public String getChapter(int chapter)
    {
    
        
        
        int chapterIndex = 0;
        int nextChapterIndex =0;
        
        String chap = "";
        int count=0;
       
        for(int i = 0; i < theBook.size();i++)
        {
            if(theBook.get(i).equals("CHAPTER"))
            {
                count++;
                if(count==chapter)
                    chapterIndex=i;
                else if(count==chapter+1)
                {
                    nextChapterIndex=i;
                    break;
                }       
            }
            else if(i==theBook.size()-1)
                    nextChapterIndex=i;         
        }
        pageNumber = calcPage(chapterIndex);
        for(int i = chapterIndex; i < nextChapterIndex; i++)
            chap = chap + theBook.get(i) + " ";//*/
        return  chap;
    }
    
    
    
    
    
    public void printChapter(int chapter)
    { 
        // use the getChapter(...) method to help print the chapter
        System.out.println(getChapter(chapter));
    }

}
