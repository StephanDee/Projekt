package nessi.main.HallOfFameList;

public class User {

	private String name;
	private int id;
	private int rank;

	public User(String name, int id, int rank) {

		this.name = name;
		this.id = id;
		this.rank = rank;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

}