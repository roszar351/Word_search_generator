import java.util.*;
import java.io.*;

///////////////////////////////////////////////////////////////////////////////////////////
//                            	Word Search                                           //
///////////////////////////////////////////////////////////////////////////////////////////
// -> Read words from file.                                                              //
// -> Decide direction of the word(8 directions).                                        //
// -> If words fails to place X num of times stop generating. X = 1,000,000              //
// -> Fill the board with random letters AFTER all words have been placed.               //
// -> Default board 10x10, represented by a char array.                                  //
///////////////////////////////////////////////////////////////////////////////////////////

public class WordSearch
{
	public static char[][] board = new char[10][10];
	public static ArrayList<String> words = new ArrayList<>();
	
	public static void main(String[] args) throws IOException
	{
		Scanner in = new Scanner(System.in);
		int h = 0, w = 0;
		
		System.out.print("Input heigh: ");
		h = Integer.parseInt(in.nextLine());
		System.out.print("Input width: ");
		w = Integer.parseInt(in.nextLine());
		customBoard(h, w);
		loadFile();
		if(fillBoard())
		{
			finishBoard();
			showBoard();
		}
	}
	
	public static void loadFile() throws IOException
	{
		File aFile = new File("DataFiles/words.txt");
		Scanner in;
		String[] elements;
		
		if(aFile.exists())
		{
			in = new Scanner(aFile);
			elements = in.nextLine().split(",");
			for(int i = 0; i < elements.length; i++)
				words.add(elements[i]);
			in.close();
		}
	}
	
	public static void customBoard(int height, int width)
	{
		if(height != 0 && width != 0)
		{
			board = new char[height][width];
		}
	}
	
	public static boolean fillBoard()
	{
		String currentWord = "";
		int posX = 0, posY = 0, direction = 0, tries = 0;
		char[][] boardCopy = board.clone();
		boolean stop = false;
		
		for(int i = 0; i < words.size(); i++)
		{
			tries++;
			stop = false;
			boardCopy = board.clone();
			currentWord = words.get(i);
			
			while(!stop)
			{
				posX = (int) (Math.random() * board.length);
				posY = (int) (Math.random() * board[0].length);
				direction = (int) (Math.random() * 8);
				
				for(int j = 0; j < currentWord.length() && !stop; j++)
				{
					if(posX >= 0 && posX < board.length && posY >= 0 && posY < board[0].length)
					{
						if(board[posX][posY] == '\u0000' || board[posX][posY] == currentWord.charAt(j))
						{
							board[posX][posY] = currentWord.charAt(j);
							if(direction == 2 || direction == 3 || direction == 4)
								posX++;
							else if(direction == 0 || direction == 7 || direction == 6)
								posX--;
							if(direction == 0 || direction == 1 || direction == 2)
								posY++;
							else if(direction == 6 || direction == 5 || direction == 4)
								posY--;
						}
						else
						{
							board = boardCopy.clone();
							stop = true;
							i--;
						}
					}
					else
					{
						board = boardCopy.clone();
						stop = true;
						i--;
					}
				}
				stop = true;
			}
			if(tries % (words.size() * 20) == 0)
			{
				board = new char[board.length][board[0].length];
				i = -1;
			}
			else if(tries > 1000000)
			{
				System.out.println("OVER 1,000,000 TRIES: WORD SEARCH COULD NOT BE CREATED.");
				return false;
			}
		}
		return true;
	}
	
	public static void finishBoard()
	{
		char ch = ' ';
		
		for(int i = 0; i < board.length; i++)
		{
			for(int j = 0; j < board[i].length; j++)
			{
				if(board[i][j] == '\u0000')
				{
					ch = (char) ((int) (Math.random() * 26 + 65));
					board[i][j] = ch;
				}
			}
		}
	}
	
	public static void showBoard() throws IOException
	{
		File aFile = new File("DataFiles/search.txt");
		PrintWriter out = new PrintWriter(new FileWriter(aFile, true));
		int f = board[0].length / 6;
		
		for(int i = 0; i < board.length; i++)
		{
			for(int j = 0; j < board[i].length; j++)
			{
				System.out.print(board[i][j] + " ");
				out.print(board[i][j] + " ");
			}
			System.out.println();
			out.println();
		}
		for(int i = 1; i <= words.size(); i++)
		{
			System.out.printf("%2d. %-10s", i, words.get(i-1));
			out.printf("%2d. %-10s", i, words.get(i-1));
			if(i % f == 0)
			{
				System.out.println();
				out.println();
			}
		}
		out.println();
		out.close();
	}
}