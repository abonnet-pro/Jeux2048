package jeu2048;
import java.awt.event.*;
import java.util.*;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.*;

public class Jeu extends JFrame implements KeyListener
{
	private JPanel panelJeu;
	
	private static final int COTE = 4;
    private int[][] tableauJeu;
    private boolean partieTermine = false;
    
    private List<JLabel> cases = new ArrayList<JLabel>();
    
    Jeu()
    {
    	initialiserJeu();
    }
        
    public void initialiserJeu()
    {
    	panelJeu = new JPanel();
    	
    	addKeyListener(this);
    	
    	this.setTitle("2048 Game !");
    	this.setSize(500, 528);
    	setResizable(false); 
    	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	
    	GridLayout gl = new GridLayout(COTE, COTE);
    	panelJeu.setLayout(gl);
    	
    	intialiserCases();
    	
        this.setContentPane(panelJeu);
        this.setVisible(true);
        
        commencerJeu();
        dessinerPlateau(); 
    }

	private void intialiserCases() 
	{
		for(int i = 0; i < COTE*COTE; i++)
		{
			JLabel casePlateau = new JLabel();
			cases.add(casePlateau);
			panelJeu.add(casePlateau);
		}
	}
    
    private void dessinerPlateau()
    {       
    	int casePlateau = 0;
    	
        for(int i = 0; i < COTE; i++)
        {
            for(int j = 0; j < COTE; j++)
            {
                changerCouleurCases(casePlateau, tableauJeu[i][j]);
                casePlateau++;
            }
        }
    }
    
    private void changerCouleurCases(int casePlateau, int valeur)
    {
        Couleur couleur;
        couleur = getCouleurParValeur(valeur);
             
        cases.get(casePlateau).setIcon(new ImageIcon(couleur.toString()));
    }
    
    private Couleur getCouleurParValeur(int valeur)
    {
        switch(valeur)
        {
            case 0: return Couleur.WHITE;
            case 2: return Couleur.GREY;
            case 4: return Couleur.RED; 
            case 8: return Couleur.ORANGE; 
            case 16: return Couleur.YELLOW; 
            case 32: return Couleur.GREEN; 
            case 64: return Couleur.BLUE; 
            case 128: return Couleur.PURPLE; 
            case 256: return Couleur.BROWN; 
            case 512: return Couleur.PINK; 
            case 1024: return Couleur.GOLD; 
            case 2048: return Couleur.SILVER; 
            default: return Couleur.NONE;
        }
    }
    
    private void commencerJeu()
    {
        tableauJeu = new int[COTE][COTE];
    
        creerNouveauNombre();
        creerNouveauNombre();
    }
    
    private void creerNouveauNombre()
    {
        if(recupererNombreMaxPlateau() == 2048)
        {
            gagner();
        }
        
        int x = 0;
        int y = 0;
        
        do
        {
            x = creerNombreAleatoire(COTE);
            y = creerNombreAleatoire(COTE);
        }
        while(tableauJeu[x][y] != 0);
        
        if(creerNombreAleatoire(10) == 9)
        {
            tableauJeu[x][y] = 4;
        }
        else
        {
            tableauJeu[x][y] = 2;
        }
    }
    
    private int creerNombreAleatoire(int nombre)
    {
    	Random random = new Random();
    	return random.nextInt(nombre);
    }
    
    private void gagner()
    {
        partieTermine = true;
        JOptionPane.showMessageDialog(this, "Felicitations !\nPour rejouer Press \"Espace");
    }
    
    private void perdue()
    {
        partieTermine = true;
        JOptionPane.showMessageDialog(this, "Dommage !\nPour rejouer Press \"Espace");
    }
    
    private void rotationMatriceAntiHoraire()
    {
        int[][] tab = new int[COTE][COTE]; 
        for(int i = 0; i < COTE; i++)
        {
            for(int j = 0; j < COTE; j++)
            {
                tab[j][COTE-1 - i] = tableauJeu[i][j];
            }
        }
        tableauJeu = tab;
    }
    
    private int recupererNombreMaxPlateau()
    {
        int maxi = 0;
        for(int[] rangee : tableauJeu)
        {
            for(int valeur : rangee)
            {
                if(valeur > maxi)
                {
                    maxi = valeur;
                }
            }
        }
        return maxi;
    }
              
    private boolean verifieUtilisateurMouvement()
    {
       boolean mouvementGauche = false;

       for (int i = 0 ; i < COTE; i++)
       {
           for (int j = 0 ; j<COTE; j++)
           {
               if (tableauJeu[i][j] == 0)
               {
                   mouvementGauche = true;
               }

               if((i-1) > 0 && (tableauJeu[i][j] == tableauJeu[i-1][j]))
               {
                   mouvementGauche = true;
               }

               if ((i+1) < COTE && (tableauJeu[i][j] == tableauJeu[i+1][j]))
               {
                   mouvementGauche = true;
               }

               if ((j+1) < COTE && (tableauJeu[i][j] == tableauJeu[i][j+1]))
               {
                   mouvementGauche = true;
               }

               if ((j-1) > 0 && (tableauJeu[i][j] == tableauJeu[i][j-1]))
               {
                   mouvementGauche = true;
               }

           }
        }
           
        return mouvementGauche;
    }
    
    private void bougerGauche()
    {
        boolean mouvementGauche = false;
        for(int i = 0; i < COTE; i++)
        {
            if(compresserRangee(tableauJeu[i]))
            {
                mouvementGauche = true;
            }
            if(fusionnerRangee(tableauJeu[i]))
            {
                mouvementGauche = true;
            }
            if(compresserRangee(tableauJeu[i]))
            {
                mouvementGauche = true;
            }
        }
        
        if(mouvementGauche)
        {
            creerNouveauNombre();
        }
    }
    
    private void bougerDroite()
    {
        rotationMatriceAntiHoraire();
        rotationMatriceAntiHoraire();
        bougerGauche();
        rotationMatriceAntiHoraire();
        rotationMatriceAntiHoraire();
    }
    
    private void bougerHaut()
    {
        rotationMatriceAntiHoraire();
        rotationMatriceAntiHoraire();
        rotationMatriceAntiHoraire();
        bougerGauche();
        rotationMatriceAntiHoraire();
    }
    
    private void bougerBas()
    {
        rotationMatriceAntiHoraire();
        bougerGauche();
        rotationMatriceAntiHoraire();
        rotationMatriceAntiHoraire();
        rotationMatriceAntiHoraire();
    }
    
    private boolean compresserRangee(int[] rangee)
    {
        boolean mouvement = false;
        
        for(int i = 0; i < rangee.length; i++)
        {
            for(int j = i; j < rangee.length; j++)
            {
                if(rangee[i] == 0)
                {
                    if(rangee[j] != 0)
                    {
                        rangee[i] = rangee[j];
                        rangee[j] = 0;
                        mouvement = true;
                        break;
                    }
                }
            }
            
        }
        
        return mouvement;
    }
    
    private boolean fusionnerRangee(int[] rangee)
    {
        boolean fusion = false;
        
        for(int i = 0; i < rangee.length - 1; i++)
        {
            if( rangee[i] != 0 && rangee[i] == rangee[i + 1] )
            {
                rangee[i] += rangee[i + 1];
                rangee[i + 1] = 0;
                fusion = true;
            }
        }
        
        return fusion;
    }
    
    public void KeyReleased(KeyEvent key)
    {
    	return;
    }
    
    public void KeyTyped(KeyEvent key)
    {
    	return;
    }
        
    public void keyPressed(KeyEvent key)
    {
        if(!partieTermine)
        {
            if(!verifieUtilisateurMouvement())
            {
                perdue();
                return;
            }
            
            if(key.getKeyCode() == KeyEvent.VK_LEFT)
            {
                bougerGauche();
                dessinerPlateau();
            }
            else if(key.getKeyCode() == KeyEvent.VK_RIGHT)
            {
                bougerDroite();
                dessinerPlateau();
            }
            else if(key.getKeyCode() == KeyEvent.VK_DOWN)
            {
                bougerBas();
                dessinerPlateau();
            }
            else if(key.getKeyCode() == KeyEvent.VK_UP)
            {
                bougerHaut();
                dessinerPlateau();
            }
        }
        
        if(partieTermine && key.getKeyCode() == KeyEvent.VK_SPACE)
        {
            partieTermine = false;
            commencerJeu();
            dessinerPlateau();
        }
    }
}
