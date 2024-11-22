package A_Card_Game;

public class Menu {
    ActionHandler aHandler = new ActionHandler(this);
    MenuUI ui = new MenuUI(this);

    public Menu() {
    }

    // change in frontend of menu, rule, guide, and game play
    private void statusMenu(boolean status) {
        ui.menu_canvas.setVisible(status);
        ui.guideButton.setVisible(status);
        ui.introButton.setVisible(status);
        ui.startButton.setVisible(status);
    }

    private void statusGuide(boolean status) {
        ui.guide_canvas.setVisible(status);
        ui.backGuideButton.setVisible(status);
    }

    private void statusIntro(boolean status) {
        ui.intro_canvas.setVisible(status);
        ui.backIntroButton.setVisible(status);
    }

    public void menuToIntro() {
        statusMenu(false); // turn off menu screen
        statusIntro(true); // turn on rule screen
    }

    public void menuToGuide() {
        statusMenu(false); // turn off menu screen
        statusGuide(true); // turn on guide screen
    }

    public void backtoMenu() {
        statusGuide(false); // turn off guide screen
        statusIntro(false); // turn off rule screen
        statusMenu(true); // turn on menu screen
    }

    public void menuToGame() throws Exception {
        ui.dispose(); // turn off menu screen

        try {
            PlayPage.resetStaticVariables();
            PlayPage.startGame();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
