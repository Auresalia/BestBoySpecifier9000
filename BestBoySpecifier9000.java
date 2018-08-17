import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;

public class BestBoySpecifier9000 {
	
	//I went with LocalDateTime instead of LocalDate because I wanted a clock in there.
	//It wasn't really useful other than proving that you could essentially leave it open and it would update once a second.
	private static LocalDateTime currentDate;
	
	//Shut up I'm aware that the padding JLabels are inefficient, I just really hate using BoxLayout more than I have to.
	//Normally I wouldn't define all these up here, but nameLabel would break if I didn't (see getBestBoysName())
	//and I didn't like having one that was defined with the rest being undefined.
	private static JLabel headerPadding = new JLabel(" ");
	private static JLabel footerPadding = new JLabel(" ");
	private static JLabel header = new JLabel();
	private static JLabel nameLabel = new JLabel();
	private static JLabel footer = new JLabel("is best boy!");
	
	//Why not define fonts we'll only use once? it does improve readablility slightly, I guess
	//but why the fuck do I care about that?
	//Arial was chosen because every fucking computer under the sun has had it for decades now.
	private static Font hfFont = new Font("Arial", Font.PLAIN, 30);
	private static Font qFont = new Font("Arial", Font.BOLD, 105);
	private static Font aFont = new Font("Arial", Font.BOLD, 200);

	public static void main(String[] args) throws InterruptedException {
		
		//Clearly my priorities are in order
		//I could have done something productive with the time I spent doing this.
		JFrame window = new JFrame("Best Boy Specifier 9000");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		currentDate = LocalDateTime.now();
		
		//Mostly offloading this onto other methods for readability.
		//It was giving me a headache staring at my own code spaghetti.
		//It ended up being useful since I decided to let this code update itself every second.
		header.setText(getHeaderText());;
		nameLabel.setText(getBestBoysName());
		
		//I chose this color because fuck you I like it.
		nameLabel.setForeground(new Color(164, 14, 14));
		
		//Make everything slightly less cancerous by aligning them all horizontally.
		header.setAlignmentX(Component.CENTER_ALIGNMENT);
		nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		footer.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		//Oh wait we used that one twice I guess.
		//Technically I guess I saved myself like a second and a half.
		header.setFont(hfFont);
		footer.setFont(hfFont);
		
		//I hate java swing
		Container container = window.getContentPane();
		
		//Putting everything in a vertical line.
		//This vertical glue bullshit just means that all the spare space needs to be split 
		//between the space above and below the displayed name.
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		container.add(headerPadding);
		container.add(header);
		container.add(Box.createVerticalGlue());
		container.add(nameLabel);
		container.add(Box.createVerticalGlue());		
		container.add(footer);
		container.add(footerPadding);
		
		//1200x400 was chosen because it's big enough to actually display IntrospectiveInquisitor's long ass name
		//in a decent sized font. Even then, it's still almost half the size of what my name's font size is.
		window.setSize(1200, 400);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		//Yeah fuck off with saying that "true while loops" are bad, I don't care.
		//Also fuck off with telling me that I shouldn't use Thread.sleep with swing.
		//You're not my fucking boss or my professor so you can take your good coding practices and shove them up your ass.
		//Basically, this shit says to refresh the time and date, along with the name of the person who is best boy for that day.
		//Theoretically having it sleep for exactly for one second could eventually cause it to skip one extra second on the clock
		//but this isn't my fucking thesis get off my back you troglodyte.
		while(true) {
			Thread.sleep(1000);
			currentDate = LocalDateTime.now();
			header.setText(getHeaderText());
			nameLabel.setText(getBestBoysName());
		}

	}
	
	
	//Return how many days it has been since post was made and rules established.
	//Days are expressed in long format because fuck you.
	//Actually, it's in long format because that's what Oracle programmed it to be and I am not Oracle.
	//It's going to be cast as an int when it's called so fucking shut it.
	public static long getDaysSince() {
		
		LocalDate originDate = LocalDate.of(2018, Month.MAY, 7);
		return ChronoUnit.DAYS.between(originDate, currentDate);
		
	}
	
	//This fucking shit.
	public static String getBestBoysName() {
		
		//So basically, IntrospectiveInquisitor demanded that we split the title of best boy between us on a rotating 4/3 daily schedule
		//that swaps every two weeks. That means it's a recurring pattern that repeats itself every 28 days. So, using modular arithmetic
		//we can determine what day in the pattern we are in, which would determine who is best boy that day.
		int turnValue = (int) getDaysSince() % 28;
		
		//This felt easier to write than a quadruple (pentuple?) nested if/else tree, so I went with a switch-case conditional
		//The pattern assumes that IntrospectiveInquisitor is the first best boy, and that his shift began with 4 days.
		//I only had to mark down which days were his, then put at the bottom that if the current day wasn't his time, then it would
		//by default be mine.
		//So the reason nameLabel had to be initialized before any methods used it was the setFont() call here. The names have different
		//sizes to keep the window size the same, so it had to be changed before the text was set. But instead of introducing more variables
		//I didn't need, I basically put the setFont() call before the method returned. The drawback was that if I called this on an 
		//uninitialized JLabel [using new JLabel(getBestBoysName()), I would be trying to set the font to text that didn't exist yet.
		//So I just initialized it and washed my hands of it.
		//Also fuck you I put in two return statments because I feel like it and also why the fuck do I need to make more variables.
		switch(turnValue) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 7:
			case 8:
			case 9:
			case 10:
			case 14:
			case 15:
			case 16:
			case 21:
			case 22:
			case 23:
				nameLabel.setFont(qFont);
				return "IntrospectiveInquisitor";
			default:
				nameLabel.setFont(aFont);
				return "Auresalia";
		}
		
	}
	
	//name is explanatory
	//really this is just some shitty formatting
	//the only notable thing was making the clock have every unit have two digits (so it looks like a fucking clock)
	//and doing some fucky substring shit on the month because if I didn't the month would have returned in all caps
	//and all caps are aesthetically displeasing and also I just wanted it to look a tad bit better
	public static String getHeaderText() {
		return String.format("It is currently %02d:%02d:%02d on %s %d, %d, meaning", currentDate.getHour(), currentDate.getMinute(), currentDate.getSecond(), currentDate.getMonth().toString().substring(0, 1) + currentDate.getMonth().toString().substring(1).toLowerCase(), currentDate.getDayOfMonth(), currentDate.getYear());
	}

}
