/*Changelog:
 * 
 */

		
			/*font.draw(batch,
				"Restitution: " + body.getFixtureList().first().getRestitution(), -Gdx.graphics.getWidth() / 2,
			Gdx.graphics.getHeight() / 2); */
			
			/*font.draw(batch,
					"X: " + (Gdx.input.getAccelerometerX() -9.8), -Gdx.graphics.getWidth() / 4,
				Gdx.graphics.getHeight() / 4);
			font.draw(batch,
					"Y: " + Gdx.input.getAccelerometerY(), -Gdx.graphics.getWidth() / 4+50,
				Gdx.graphics.getHeight() / 4+50);
			font.draw(batch,
					"Z: " + Gdx.input.getAccelerometerZ(), -Gdx.graphics.getWidth() / 4+100,
				Gdx.graphics.getHeight() / 4+100);
			
			font.draw(batch,
					"AZIMUTH: " + Gdx.input.getAzimuth(), -Gdx.graphics.getWidth() / 4,
				Gdx.graphics.getHeight() / 4-50);
			font.draw(batch,
					"Pitch: " + Gdx.input.getPitch(), -Gdx.graphics.getWidth() / 4-100,
				Gdx.graphics.getHeight() / 4-100);
			font.draw(batch,
					"Roll: " + Gdx.input.getRoll(), -Gdx.graphics.getWidth() / 4-150,
				Gdx.graphics.getHeight() / 4-150);*/
	

package trial;

import com.badlogic.gdx.Game;
import com.cs102.screens.Splash;

public class GameProject extends Game {
    public static final String TITLE="Game Project"; 
    public static final int WIDTH=480,HEIGHT=800; // used later to set window size
    
    @Override
    public void create() {
            setScreen(new Splash());
    }
    
    
}
