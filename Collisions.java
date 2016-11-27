import java.util.ArrayList;

/**
 * Created by gmaed on 11/23/2016.
 */
public class Collisions{

    Game game = new Game();

    public synchronized void tick() {

            ArrayList<Bullet> bullets = game.getPlayer().getBullets();
            ArrayList<Enemy> enemies = game.getLevelHandler().getEnemies();
            if(bullets.size() > 0)  {
                for(int i = 0; i < bullets.size(); i++) {
                    if(bullets.get(i).getRectangle() != null) {
                        for(int j = 0; j < enemies.size(); j++) {
                            if(enemies.get(j).getRectangle() != null) {
                                if(bullets.get(i).getRectangle().intersects(enemies.get(j).getRectangle())) {
                                    enemies.get(j).reduceHealth(game.getPlayer().getBulletDamage());
                                    if(enemies.get(j).isDead()) {
                                        game.getPlayer().addScore(enemies.get(j).getValue());
                                        enemies.remove(j);

                                    }
                                }
                            }
                        }
                    }
                }
            }

            enemies = game.getLevelHandler().getEnemies();
            if(enemies.size() > 0) {
                for (int i = 0; i < enemies.size(); i++) {
                    if(enemies.get(i).getRectangle() != null && game.getPlayer().getRectangle() != null) {
                        if (enemies.get(i).getRectangle().getBounds().intersects(game.getPlayer().getRectangle())) {

                            game.getPlayer().reduceHealth(enemies.get(i).getDamage());
                            game.getLevelHandler().getEnemies().remove(i);

                            //trigger animation
                        }


                    }
                }
            }
    }



}
