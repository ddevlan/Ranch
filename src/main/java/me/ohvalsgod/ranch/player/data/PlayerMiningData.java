package me.ohvalsgod.ranch.player.data;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerMiningData {

    private int diamonds, iron, lapis, gold, redstone, stone, coal, quartz;

    public PlayerMiningData()  {
        diamonds = 0;
        iron = 0;
        lapis = 0;
        gold = 0;
        redstone = 0;
        coal = 0;
        stone = 0;
        quartz = 0;
    }

}
