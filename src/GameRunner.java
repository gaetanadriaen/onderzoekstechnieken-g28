import java.util.Scanner;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
//programma blackjack

public class GameRunner{
    public static void main(String[] args) {
        Workbook workbook;
        Sheet sheet1;
	int i = 0;
        int rowSpelletje = 0;
        int playerWins = 0;
        int dealerWins = 0;
        workbook = new HSSFWorkbook();
        sheet1 = workbook.createSheet();
        Scanner sc = new Scanner(System.in);
        System.out.printf("\nGeef het aantal decks in: ");
        int aantalDecks = sc.nextInt();
        System.out.printf("\nWil je dat de dealer bij 17 hit of stop doet? Typ 'hit' of 'stop': ");
        int strategyDealer = 0;
        
        if(sc.next().equals("stop")){
            strategyDealer = 17;
        }else{
            strategyDealer = 18;
        }
        
        
        do{
                Row row = sheet1.createRow(rowSpelletje);
                Cell cell = row.createCell(0);
                cell.setCellValue(i);
		// inits
		
		Deck theDeck = new Deck(aantalDecks, true);
		
		// init player objects
		Player me = new Player("Speler");
		Player dealer = new Player("Dealer");
		
		// deal two cards to each player
		me.addCard(theDeck.dealNextCard());
		dealer.addCard(theDeck.dealNextCard());
		me.addCard(theDeck.dealNextCard());
		dealer.addCard(theDeck.dealNextCard());
		
		// print initial hands for both players
		System.out.println("Cards are dealt\n");
		me.printHand(true);
		dealer.printHand(false);
		System.out.println("\n");
		
		// flags for when each player is finished hitting
		boolean meDone = false;
		boolean dealerDone = false;
		String ans;
		
		while(!meDone || !dealerDone) {
			
			// player's turn
			if(!meDone){
				System.out.print("Hit or Stay? (Enter H or S): ");
//				ans = sc.next();
//				System.out.println();
				
				// if the player hits
				if(me.getHandSum() <= 11 || ((me.getHandSum() == 12) && ((dealer.getVisibleCard() != 4) && (dealer.getVisibleCard() != 5) && (dealer.getVisibleCard() != 6))) || ((me.getHandSum() >= 13) && (me.getHandSum() < 17)) && (!((dealer.getVisibleCard() >= 2) && (dealer.getVisibleCard() <= 6))) || (!(me.getHandSum() >= 17))) {
					System.out.printf("\nThe player hits\n");
					// add next card and store whether we've busted
					meDone = !me.addCard(theDeck.dealNextCard());
					me.printHand(true);
				}
				else{
                                    System.out.printf("\nThe player stays\n");
					meDone = true;
				}
			}
			
			// dealer's turn
			if(!dealerDone) {
				if(dealer.getHandSum() < strategyDealer){
					System.out.println("The Dealer hits\n");
					dealerDone = !dealer.addCard(theDeck.dealNextCard());
					dealer.printHand(false);
				}
				else {
					System.out.println("The Dealer stays\n");
					dealerDone = true;
				}
			}
			
			System.out.println();
		}
		
		// close scanner
		sc.close();
		
		// print final hands
		me.printHand(true);
		dealer.printHand(true);
		
		int mySum = me.getHandSum();
		int dealerSum = dealer.getHandSum();
		if(!(mySum > 21 && dealerSum > 21)){
                    if((mySum > dealerSum && mySum <= 21) || dealerSum > 21  ){
                        
			System.out.println("You win!");
                        Row rowPlayer1 = sheet1.createRow(rowSpelletje++);
                        Row rowDealer1 = sheet1.createRow(rowSpelletje++);
                        Card[] playerCards = me.getCards();
                        Cell cellWhoWins = rowPlayer1.createCell(0);
                        Cell cellTotalPlayer = rowPlayer1.createCell(1);
                        cellWhoWins.setCellValue("Player wins");
                        cellTotalPlayer.setCellValue(me.getHandSum());
                        for(int j = 0; j < me.getNumberCards(); j++){
                            Cell cellPlayer = rowPlayer1.createCell(j+2);
                            if(playerCards[j].getNumber() > 10){
                                cellPlayer.setCellValue(10);
                            }
                            else{
                                cellPlayer.setCellValue(playerCards[j].getNumber());
                            }
                            
                        }
                        Card[] dealerCards = dealer.getCards();
                        Cell cellTotalDealer = rowDealer1.createCell(1);
                        cellTotalDealer.setCellValue(dealer.getHandSum());
                        for(int k = 0; k < dealer.getNumberCards(); k++){
                            Cell cellDealer = rowDealer1.createCell(k+2);
                            if(dealerCards[k].getNumber() > 10){
                                cellDealer.setCellValue(10);
                            }
                            else{
                                cellDealer.setCellValue(dealerCards[k].getNumber());
                            }
                            
                        }
                        System.out.printf("\n\n\n\nNumber of cards van player : %d",me.getNumberCards());
                        for(int a = 0; a < me.getNumberCards(); a++){
                            System.out.printf("\nCard %d: %s / Number : %d", a, me.getCards()[a], me.getCards()[a].getNumber());
                        }
                        System.out.printf("\nSom van alle kaarten: %d\n", me.getHandSum());
                        
                        playerWins++;
		}
		else{
			System.out.println("Dealer wins!");
                        Row rowPlayer1 = sheet1.createRow(rowSpelletje++);
                        Row rowDealer1 = sheet1.createRow(rowSpelletje++);
                        Card[] playerCards = me.getCards();
                        Cell cellWhoWins = rowPlayer1.createCell(0);
                        Cell cellTotalPlayer = rowPlayer1.createCell(1);
                        cellWhoWins.setCellValue("Dealer wins");
                        cellTotalPlayer.setCellValue(me.getHandSum());
                        for(int j = 0; j < me.getNumberCards(); j++){
                            Cell cellPlayer = rowPlayer1.createCell(j+2);
                            if(playerCards[j].getNumber() > 10){
                                cellPlayer.setCellValue(10);
                            }
                            else{
                                cellPlayer.setCellValue(playerCards[j].getNumber());
                            }
                            
                        }
                        Cell cellTotalDealer = rowDealer1.createCell(1);
                        cellTotalDealer.setCellValue(dealer.getHandSum());
                        Card[] dealerCards = dealer.getCards();
                        for(int k = 0; k < dealer.getNumberCards(); k++){
                            Cell cellDealer = rowDealer1.createCell(k+2);
                            if(dealerCards[k].getNumber() > 10){
                                cellDealer.setCellValue(10);
                            }
                            else{
                                cellDealer.setCellValue(dealerCards[k].getNumber());
                            }
                            
                        }
                        System.out.printf("\n\n\n\nNumber of cards van player : %d",me.getNumberCards());
                        for(int a = 0; a < me.getNumberCards(); a++){
                            System.out.printf("\nCard %d: %s / Number : %d", a, me.getCards()[a], me.getCards()[a].getNumber());
                        }
                        System.out.printf("\nSom van alle kaarten: %d\n", me.getHandSum());
                        dealerWins++;
		}
                }
                else{
                    System.out.println("Dealer wins!");
                        Row rowPlayer1 = sheet1.createRow(rowSpelletje++);
                        Row rowDealer1 = sheet1.createRow(rowSpelletje++);
                        Card[] playerCards = me.getCards();
                        Cell cellWhoWins = rowPlayer1.createCell(0);
                        Cell cellTotalPlayer = rowPlayer1.createCell(1);
                        cellWhoWins.setCellValue("Dealer wins");
                        cellTotalPlayer.setCellValue(me.getHandSum());
                        for(int j = 0; j < me.getNumberCards(); j++){
                            Cell cellPlayer = rowPlayer1.createCell(j+2);
                            if(playerCards[j].getNumber() > 10){
                                cellPlayer.setCellValue(10);
                            }
                            else{
                                cellPlayer.setCellValue(playerCards[j].getNumber());
                            }
                            
                        }
                        Cell cellTotalDealer = rowDealer1.createCell(1);
                        cellTotalDealer.setCellValue(dealer.getHandSum());
                        Card[] dealerCards = dealer.getCards();
                        for(int k = 0; k < dealer.getNumberCards(); k++){
                            Cell cellDealer = rowDealer1.createCell(k+2);
                            if(dealerCards[k].getNumber() > 10){
                                cellDealer.setCellValue(10);
                            }
                            else{
                                cellDealer.setCellValue(dealerCards[k].getNumber());
                            }
                            
                        }
                        System.out.printf("\n\n\n\nNumber of cards van player : %d",me.getNumberCards());
                        for(int a = 0; a < me.getNumberCards(); a++){
                            System.out.printf("\nCard %d: %s / Number : %d", a, me.getCards()[a], me.getCards()[a].getNumber());
                        }
                        System.out.printf("\nSom van alle kaarten: %d\n", me.getHandSum());
                        dealerWins++;
                }
                
                i++;
                rowSpelletje++;
        }while(i < 10000);
        System.out.printf("\nDe speler heeft %d keer gewonnen.\n", playerWins);
        System.out.printf("\nDe deler heeft %d keer gewonnen.\n", dealerWins);
        Row row = sheet1.createRow(rowSpelletje++);
        Cell cell = row.createCell(0);
        Cell cell2 = row.createCell(1);
        
        cell.setCellValue(playerWins);
        cell.setCellValue(dealerWins);
        try{
            FileOutputStream output = new FileOutputStream("Data.xls");
            workbook.write(output);
            output.close();
        }catch(Exception ex){
            ex.printStackTrace();
        }

	}
}
