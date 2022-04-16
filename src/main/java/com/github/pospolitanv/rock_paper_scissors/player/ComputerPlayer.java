package com.github.pospolitanv.rock_paper_scissors.player;

import com.github.pospolitanv.rock_paper_scissors.moves.Move;
import com.github.pospolitanv.rock_paper_scissors.moves.MovesGameMode;
import com.github.pospolitanv.rock_paper_scissors.strategy.ComputerStrategy;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class ComputerPlayer implements Player {

    private final String name;
    private final ComputerStrategy computerStrategy;

    /**
     * Randomized Computer creation for fun.
     * @param gameMode
     */
    public ComputerPlayer(MovesGameMode gameMode) {
        this.name = getRandomName();
        this.computerStrategy = getRandomStrategy(gameMode);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Move getMove(MovesGameMode gameMode, Map<Player, List<Move>> history) {
        return computerStrategy.nextMove(this, gameMode, history);
    }

    protected ComputerStrategy getStrategy() {
        return this.computerStrategy;
    }

    private String getRandomName() {
        List<String> computerNames =
                Arrays.asList("Bender", "Tik-Tok", "Tin Can", "R2-D2", "C-3PO", "Optimus Prime", "WALL-E");
        return computerNames.get(ThreadLocalRandom.current().nextInt(0, computerNames.size()));
    }

    /**
     * We create random strategy using reflection. It's a good way to extend, because now I just implement new {@link
     * ComputerStrategy} to seamless add new strategy into the game, decoupling code. For example, Spring provides it
     * out-of-the box.
     *
     * @param gameMode
     */
    private ComputerStrategy getRandomStrategy(MovesGameMode gameMode) {
        Reflections reflections = new Reflections("com.github.pospolitanv.rock_paper_scissors.strategy");

        List<? extends ComputerStrategy> strategies =
                new ArrayList<>(reflections.getSubTypesOf(ComputerStrategy.class))
                        .stream()
                        .map(this::getComputerStrategy)
                        .filter(Objects::nonNull)
                        .filter(computerStrategy -> computerStrategy.isApplicable(gameMode))
                        .collect(Collectors.toList());

        return strategies.get(ThreadLocalRandom.current().nextInt(0, strategies.size()));
    }

    private ComputerStrategy getComputerStrategy(Class<? extends ComputerStrategy> strategy) {
        try {
            return strategy.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}
