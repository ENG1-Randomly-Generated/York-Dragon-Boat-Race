package com.mygdx.dragonboatgame.screens;

import com.badlogic.gdx.Screen;
import com.mygdx.dragonboatgame.game.Game;

import java.util.HashMap;

public class ScreenManager {

    public final GameManager gameManager;
    private HashMap<GAMESTATE, AbstractScreen> screens;
    private AbstractScreen currentScreen;

    public enum GAMESTATE {
        StartMenu,
        BoatSelection,
        TeamNameInput,
        DragonBoatGame,
        LegResult,
        Finalists,
        Medals
    }

    public ScreenManager(final GameManager gameManager) {
        this.gameManager = gameManager;
        initScreens();
    }

    /**
     * Create instances of all screens required for the game
     *  Each screen will handle their creation and have an instance of this gamemanager for requesting a switch
     */
    private void initScreens() {
        this.screens = new HashMap<GAMESTATE, AbstractScreen>();
        this.screens.put(GAMESTATE.StartMenu, new StartMenu(gameManager));
        this.screens.put(GAMESTATE.BoatSelection, new BoatSelectionScreen(gameManager));
        this.screens.put(GAMESTATE.TeamNameInput, new TeamNameInputScreen(gameManager));
        this.screens.put(GAMESTATE.DragonBoatGame, new Game(gameManager));
        this.screens.put(GAMESTATE.LegResult, new LegResultScreen(gameManager));
        this.screens.put(GAMESTATE.Finalists, new FinalistsScreen(gameManager));
        this.screens.put(GAMESTATE.Medals, new MedalScreen(gameManager));
    }

    /**
     * Set the game's screen to the one for the given GAMESTATE
     *
     * @param gamestate GAMESTATE for the next screen
     */
    public void setScreen(GAMESTATE gamestate) {
        currentScreen = screens.get(gamestate);
        gameManager.setScreen(currentScreen);
    }

    public AbstractScreen getScreen(){ return currentScreen; }

    public void dispose(){
        for(AbstractScreen screen : screens.values()){
            if (screen != null){
                screen.dispose();
            }
        }
    }
}
