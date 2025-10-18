package projects.VargaBarbara.gameforboredom.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FireMagician implements Character{

    private int baseHealth = 200;
    private int baseAttack = 60;
    private int baseSpeed = 30;
    private int baseMana = 10;
    private int baseDefense = 40;
    private int positionX;
    private int positionY;

    @Override
    public void takeDamage() {

    }

    @Override
    public void doDamage() {

    }

    @Override
    public void move() {

    }

    @Override
    public void dodge() {

    }
}
