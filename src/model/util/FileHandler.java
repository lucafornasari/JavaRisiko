package model.util;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.StringTokenizer;

import model.entities.Card;
import model.entities.Continent;
import model.entities.FIGURE;
import model.entities.Mission;
import model.entities.RisikoGame;
import model.entities.Territory;

public class FileHandler {
	private ArrayList<Territory> list;
	private ArrayList<Card> card;
	private ArrayList<Mission> missions;
	private ArrayList<Continent> continents;
	private ArrayList<Pixel> pixels;
	private int nLine = 0;
	private int nLineC=0;
	private int nLineM=0;
	private int nLineCont=0;
	
	/**
	 * Creates an array list containing all territories through a file
	 * @param path is the path of the file
	 * @return list
	 * @throws NumberFormatException in case the parseInt operation isn't successful
	 * @throws IOException in case the file has an error
	 */
	public ArrayList<Territory> genTerritories(String path) throws NumberFormatException, IOException {
		
		BufferedReader in = new BufferedReader(new FileReader(path));
		String line;
		int n = Integer.parseInt(in.readLine());
		
		list = new ArrayList<Territory>();
		
		String name;
		String continent;
		int code;
		String color;
			
		for(int i = 0; i<n; i++) {
			
			line = in.readLine();
			code = Integer.parseInt(line.substring(0,2));
			StringTokenizer st = new StringTokenizer (line.substring(4));
			color = st.nextToken();
			int k=Integer.parseInt(line.substring(22, 23));
			int j=k*3;
			StringTokenizer s = new StringTokenizer (line.substring(j+23));
			name = s.nextToken();
			continent = s.nextToken();
			
			if(!addTerritory(new Territory(name, code, continent, color))) {
				break;
			}
		}
		in.close();
		return list;
	}
	
	/**
	 * Adds a territory to the list
	 * @param t is the territory added
	 * @return boolean
	 */
	public boolean addTerritory(Territory t) {
		if(nLine <= list.size()) {
			list.add(t);
			nLine++;
			return true;
		}
		return false;
	}
	
	/**
	 * Adds the borders to a territory through a file
	 * @param list is the array containing all the territories to add
	 * @param path is the path of the file
	 * @return list
	 * @throws NumberFormatException in case the parseInt operation isn't successful
	 * @throws IOException in case the file has an error
	 */
	public ArrayList<Territory> addConfinanti(ArrayList<Territory> list, String path) throws NumberFormatException, IOException{
		
		BufferedReader in = new BufferedReader(new FileReader(path));
		String line;
		int n = Integer.parseInt(in.readLine());
		
		int id;
		int n_confini;
		
		ArrayList<Territory> tempList;
		
		for(int i = 0; i<n; i++) {
			
			tempList = new ArrayList<Territory>();
			line = in.readLine();
			id = Integer.parseInt(line.substring(0,2));
			n_confini = Integer.parseInt(line.substring(22, 23));
			StringTokenizer st = new StringTokenizer (line.substring(23));
			
			for(int k = 0; k<n_confini; k++) {
				
				tempList.add(list.get(Integer.parseInt(st.nextToken())-1));
			}
			
			list.get(id-1).setConfinanti(tempList);
		}
		in.close();
		return list;	
	}
	
	/**
	 * Creates a deck of cards through a file
	 * @param list is the array of territories corresponding to the cards
	 * @param path is the path of the file
	 * @return card
	 * @throws NumberFormatException in case the parseInt operation isn't successful
	 * @throws IOException in case the file has an error
	 */
	public ArrayList<Card> genCards(ArrayList<Territory> list, String path) throws NumberFormatException, IOException{
		
		BufferedReader in= new BufferedReader(new FileReader(path));
		String line;
		
		card=new ArrayList<Card>();
		int n=Integer.parseInt(in.readLine()) + 2;
		
		Territory territory=null;
		FIGURE figure=null;		// Capire se lasciarlo di tipo String o trovare il modo per farlo leggere direttamente come tipo della ENUM
		int code;
		
		for(int i=0; i<n; i++) {
			line=in.readLine();
			code=Integer.parseInt(line.substring(0, 2));
			StringTokenizer st=new StringTokenizer(line.substring(2));
			int k=Integer.parseInt(st.nextToken());
			switch (k) {
				case 1:
					figure=FIGURE.JOLLY;
					territory=null;
					break;
				case 2:
					figure=FIGURE.FANTE;
					if(code<43)
						territory=list.get((code)-1);
					break;
				case 3:
					figure=FIGURE.CANNONE;
					if(code<43)
						territory=list.get((code)-1);
					break;
				case 4:
					figure=FIGURE.CAVALIERE;
					if(code<43)
						territory=list.get((code)-1);
					break;
			}
	
			if(!addCard(new Card(figure, territory, code))) {
				break;
			}
		}
		in.close();
		return card;
	}
	
	/**
	 * Adds a new card and verifies if the operation is successful
	 * @param c is the card added
	 * @return boolean
	 */
	public boolean addCard(Card c) {
		if(nLineC<=card.size()) {
			card.add(c);
			nLineC++;
			return true;
		}
		return false;
	}
	
	/**
	 * Creates a list of continents through a file
	 * @param path is the path of the file
	 * @return continents
	 * @throws NumberFormatException in case the parseInt operation isn't successful
	 * @throws IOException in case the file has an error
	 */
	public ArrayList<Continent> genContinents (String path) throws NumberFormatException, IOException {
		
		BufferedReader in = new BufferedReader(new FileReader(path));
		String line;
		int n = Integer.parseInt(in.readLine());
		
		continents = new ArrayList<Continent>();
		
		String name;
		int bonus;
		int code;
			
		for(int i = 0; i<n; i++) {
			
			line = in.readLine();
			code = Integer.parseInt(line.substring(0,2));
			StringTokenizer st = new StringTokenizer (line.substring(2));
			name = st.nextToken();
			bonus = Integer.parseInt(st.nextToken());
			
			if(!addContinents(new Continent(code, name, bonus))) {
				break;
			}
		}
		in.close();
		return continents;
	}
	
	/**
	 * Adds a new continent to the list of continents and verifies if the operation was successful
	 * @param c is the new continent
	 * @return boolean
	 */
	public boolean addContinents(Continent c) {
		if(nLineCont <= list.size()) {
			continents.add(c);
			nLineCont++;
			return true;
		}
		return false;
	}
	
	/**
	 * Creates a list of missions through a file
	 * @param path is the path of the file
	 * @param continents is the list of continents for the mission
	 * @return missions
	 * @throws NumberFormatException in case the parseInt operation isn't successful
	 * @throws IOException in case the file has an error
	 */
	public ArrayList<Mission> genMissions(String path, ArrayList<Continent> continents) throws NumberFormatException, IOException{
		
		BufferedReader in= new BufferedReader(new FileReader(path));
		String line;
		int n=Integer.parseInt(in.readLine());
		
		missions=new ArrayList<Mission>();
		
		int nty;
		int ntk;
		Continent cont1 = null;
		Continent cont2 = null;
		int codeMission;
		int typeMission;
		int code;
		int code2;
		int code3;
		boolean temp;
		
		for(int i=0; i<n; i++) {
			line=in.readLine();
			typeMission=Integer.parseInt(line.substring(0, 1));
			StringTokenizer st=new StringTokenizer(line.substring(1));
			codeMission=Integer.parseInt(st.nextToken());
			switch(typeMission) {
				case 1:
					nty=Integer.parseInt(st.nextToken());
					ntk=Integer.parseInt(st.nextToken());
					if(!addMission(new Mission(nty, ntk, codeMission))) {
						break;
					}
					break;
				case 2:
					code=Integer.parseInt(st.nextToken());
					code2=Integer.parseInt(st.nextToken());
					code3=Integer.parseInt(st.nextToken());
					if(code3==1)
						temp=true;
					else
						temp=false;
					cont1=continents.get((code)-1);
					cont2=continents.get((code2)-1);
					if(!addMission(new Mission(cont1, cont2, temp, codeMission))) {
						break;
					}
					break;
			}	
		}
		in.close();
		return missions;
	}
	
	/**
	 * Adds a mission to the list of missions and verifies if the operation was successful
	 * @param m is the mission added
	 * @return boolean
	 */
	public boolean addMission(Mission m) {
		if(nLineM<=missions.size()) {
			missions.add(m);
			nLineM++;
			return true;
		}
		return false;
	}

	public ArrayList<Pixel> addPosizione(String path) throws NumberFormatException, IOException{
		
		BufferedReader in = new BufferedReader(new FileReader(path));
		String line;
		int n = Integer.parseInt(in.readLine());
		
		pixels=new ArrayList<Pixel>();
		int id;
		int x=0;
		int y=0;
		Color color=new Color(0);		
		
		for(int i = 0; i<n; i++) {
			
			line = in.readLine();
			id = Integer.parseInt(line.substring(0,2));
			StringTokenizer st = new StringTokenizer (line.substring(11));
			x = Integer.parseInt(st.nextToken());
			y = Integer.parseInt(st.nextToken());
			
			if(!addPixel(new Pixel(x, y, color))) {
				break;
			}
		}
		in.close();
		return pixels;
	}
	
	private boolean addPixel(Pixel p) {
		int nLineP=0;
		// TODO Auto-generated method stub
		if(nLineP<=pixels.size()) {
			pixels.add(p);
			nLineP++;
			return true;
		}
		return false;
	}
	
}