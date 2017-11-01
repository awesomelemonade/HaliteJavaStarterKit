package hlt;

import java.io.PrintWriter;

public class Networking {
	private static final PrintWriter writer;
	private static final char UNDOCK_KEY = 'u';
	private static final char DOCK_KEY = 'd';
	private static final char THRUST_KEY = 't';
	static {
		writer = new PrintWriter(System.out);
	}
	public static void sendMoves(Iterable<Move> moves) {
		StringBuilder moveString = new StringBuilder();
		for (Move move : moves) {
			switch (move.getType()) {
			case NOOP:
				continue;
			case UNDOCK:
				moveString.append(UNDOCK_KEY).append(' ').append(move.getShip().getId()).append(' ');
				break;
			case DOCK:
				moveString.append(DOCK_KEY).append(' ').append(move.getShip().getId()).append(' ')
						.append(((DockMove) move).getDestinationId()).append(' ');
				break;
			case THRUST:
				if(((ThrustMove)move).getThrust()!=0){
					moveString.append(THRUST_KEY).append(' ').append(move.getShip().getId()).append(' ')
							.append(((ThrustMove) move).getThrust()).append(' ')
							.append((int)Math.round(Math.toDegrees(((ThrustMove) move).getRoundedAngle()))).append(' ');
				}
				break;
			}
		}
		writer.println(moveString);
		writer.flush();
	}
	private static String readLine() {
		try {
			StringBuilder builder = new StringBuilder();
			int buffer;
			while ((buffer = System.in.read()) >= 0) {
				if (buffer == '\n') {
					break;
				}
				if (buffer == '\r') {
					// Ignore carriage return if on windows for manual testing.
					continue;
				}
				builder = builder.append((char) buffer);
			}
			return builder.toString();
		} catch (Exception e) {
			return null;
		}
	}
	public static Metadata readLineIntoMetadata() {
		return new Metadata(readLine().trim());
	}
	public static GameMap initialize() {
		int myId = Integer.parseInt(readLine());
		Metadata inputStringMapSize = readLineIntoMetadata();
		int width = Integer.parseInt(inputStringMapSize.pop());
		int height = Integer.parseInt(inputStringMapSize.pop());
		GameMap gameMap = new GameMap(width, height, myId);
		Metadata inputStringMetadata = readLineIntoMetadata();
		gameMap.updateMap(inputStringMetadata);
		return gameMap;
	}
	public static void finalizeInitialization(String botName) {
		writer.println(botName);
		writer.flush();
	}
}
