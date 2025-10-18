package projects.VargaBarbara.gameforboredom.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public interface Character {

    void takeDamage();
    void doDamage();
    void move();
    void dodge();

}
