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
        Podium,
        Medals
    }

    public ScreenManager(final GameManager gameManager) {
        this.gameManager = gameManager;
        initScreens();
    }

    private void initScreens() {
        this.screens = new HashMap<GAMESTATE, AbstractScreen>();
        this.screens.put(GAMESTATE.StartMenu, new StartMenu(gameManager));
        this.screens.put(GAMESTATE.BoatSelection, new BoatSelectionScreen(gameManager));
        this.screens.put(GAMESTATE.TeamNameInput, new TeamNameInputScreen(gameManager));
        this.screens.put(GAMESTATE.DragonBoatGame, new Game(gameManager));
        this.screens.put(GAMESTATE.LegResult, new LegResultScreen(gameManager));
        this.screens.put(GAMESTATE.Finalists, new FinalistsScreen(gameManager));
        this.screens.put(GAMESTATE.Medals, new MedalScreen(gameManager));
        //add all other screens to this
    }

    public void setScreen(GAMESTATE nextScreen) {
        currentScreen = screens.get(nextScreen);
        gameManager.setScreen(currentScreen);
    }

    public AbstractScreen getScreen(){return currentScreen;}

    public void dispose(){
        for(AbstractScreen screen : screens.values()){
            if (screen != null){
                screen.dispose();
            }
        }
    }
}
