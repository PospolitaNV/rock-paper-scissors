package com.github.pospolitanv.rock_paper_scissors.strategy;

import com.github.pospolitanv.rock_paper_scissors.moves.Move;
import com.github.pospolitanv.rock_paper_scissors.moves.MovesGameMode;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.TextTerminal;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * CLI strategy for Human players with Console UI
 */
public class CommandLineInputStrategy implements HumanStrategy {

    private final TextIO textIO;
    private final List<Move> availableMoves;

    public CommandLineInputStrategy(MovesGameMode gameMode, TextTerminal textTerminal) {
        textIO = new TextIO(textTerminal);
        this.availableMoves = Arrays.stream(Move.values())
                .filter(move -> move.getOrder() <= gameMode.getMovesCount())
                .collect(Collectors.toList());
    }

    @Override
    public Move nextMove(MovesGameMode gameMode) {
        return textIO.newEnumInputReader(Move.class)
                .withNumberedPossibleValues(availableMoves)
                .withInputMasking(true)
                .read("What is your choice? Be wise 🧙‍");
    }
}
