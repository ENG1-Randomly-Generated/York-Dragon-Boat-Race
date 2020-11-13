package com.mygdx.dragonboatgame.screens;

import com.badlogic.gdx.Screen;

import java.util.HashMap;

public class ScreenManager {

    public final GameManager gameManager;
    private HashMap<GAMESTATE ,AbstractScreen> screens;
    private AbstractScreen currentScreen;

    public enum GAMESTATE {
        StartMenu,
        BoatSelection,
        TeamNameInput,
        DragonBoatGame,
        LegResult,
        Finalists,
        Podium
    }

    public ScreenManager(final GameManager gameManager) {
        this.gameManager = gameManager;
        initScreens();
        setScreen(GAMESTATE.StartMenu);
    }

    private void initScreens() {
        this.screens = new HashMap<GAMESTATE, AbstractScreen>();
        this.screens.put(GAMESTATE.StartMenu, new StartMenu(gameManager));
        this.screens.put(GAMESTATE.BoatSelection, new BoatSelectionScreen(gameManager));
        this.screens.put(GAMESTATE.DragonBoatGame, new DragonBoatGame(gameManager));
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
