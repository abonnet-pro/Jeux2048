package jeu2048;

public enum Couleur 
{
	WHITE("src/images/case0.png"),
	GREY("src/images/case2.png"),
	RED("src/images/case4.png"),
	ORANGE("src/images/case8.png"),
	YELLOW("src/images/case16.png"),
	GREEN("src/images/case32.png"),
	BLUE("src/images/case64.png"),
	PURPLE("src/images/case128.png"),
	BROWN("src/images/case256.png"),
	PINK("src/images/case512.png"),
	GOLD("src/images/case1024.png"),
	SILVER("src/images/case2048.png"),
	NONE("src/images/case0.png");
	
	private String name = "";
	
	Couleur(String name)
	{
		this.name = name;
	}
	
	public String toString()
	{
		return name;
	}
}
