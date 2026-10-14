package io.github.techtastic.ocforth.forth;

import io.github.techtastic.ocforth.forth.words.LiteralWord;
import io.github.techtastic.ocforth.forth.words.comment.CommentEndWord;
import io.github.techtastic.ocforth.forth.words.comment.CommentStartWord;
import io.github.techtastic.ocforth.forth.words.compile.DOCOLEndWord;
import io.github.techtastic.ocforth.forth.words.compile.DOCOLWord;
import io.github.techtastic.ocforth.forth.words.logic.*;
import io.github.techtastic.ocforth.forth.words.math.*;
import io.github.techtastic.ocforth.forth.words.stack.DropWord;
import io.github.techtastic.ocforth.forth.words.stack.DuplicateWord;
import io.github.techtastic.ocforth.forth.words.stack.OverWord;
import io.github.techtastic.ocforth.forth.words.stack.SwapWord;
import io.github.techtastic.ocforth.forth.words.temp.PrintWord;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ForthVM {
    public static final Map<String, Word> BASE_DICTIONARY;

    private final int capacity;

    private final ArrayDeque<Long> data;
    private final HashMap<String, Word> dictionary = new HashMap<>();

    private State state = State.INTERPRET;
    private String definition;
    private List<String> body;

    private State previousState = State.COMPILE;

    public ForthVM(int capacity) {
        this.capacity = capacity;
        this.data = new ArrayDeque<>(capacity);
    }

    public void push(long x) {
        if (this.data.size() >= this.capacity)
            throw new RuntimeException("stack overflow");
        this.data.push(x);
    }

    public long pop() {
        try {
            return this.data.pop();
        } catch (NoSuchElementException ignored) {
            throw new RuntimeException("stack underflow");
        }
    }

    public long peek(int index) {
        try {
            return  this.data.stream().toList().get(index);
        } catch (IndexOutOfBoundsException ignored) {
            if (index >= this.data.size())
                throw new RuntimeException("stack underflow");
            else
                throw new RuntimeException("stack overflow");
        }
    }

    @Nullable
    public Word getWord(String definition) {
        if (this.dictionary.containsKey(definition))
            return this.dictionary.get(definition);
        if (BASE_DICTIONARY.containsKey(definition))
            return BASE_DICTIONARY.get(definition);
        return null;
    }

    public void addWord(String key, Word value) {
        this.dictionary.put(key, value);
    }

    public State getState() {
        return this.state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public State getPreviousState() {
        return this.previousState;
    }

    public void setPreviousState(State state) {
        this.previousState = state;
    }

    public String getCurrentDefinition() {
        return this.definition;
    }

    public List<String> getCurrentDefinitionBody() {
        return this.body;
    }

    public void clearCurrentDefinition() {
        this.definition = null;
        this.body = null;
    }

    public void interpret(String token) {
        if (this.getWord(token) instanceof Word word)
            word.execute(this);
        else
            try {
                this.push(Long.parseLong(token));
            } catch (NumberFormatException ignored) {
                throw new RuntimeException(token + " ?");
            }
    }

    public void compile(String token) {
        if (this.definition == null) {
            this.definition = token;
            return;
        }
        if (this.body == null)
            this.body = new ArrayList<>();

        if (this.getWord(token) instanceof Word word) {
            switch (word) {
                case DOCOLWord start -> {}
                case DOCOLEndWord end -> end.execute(this);
                case CommentStartWord start -> start.execute(this);
                default -> {
                    this.body.add(token);
                    if (word.immediate())
                        word.execute(this);
                }
            }
        } else
            try {
                this.body.add(token);
            } catch (NumberFormatException ignored) {
                this.clearCurrentDefinition();
                this.setState(State.INTERPRET);
                throw new RuntimeException(token + " ?");
            }
    }
    
    public void execute(String script) {
        if (script.trim().isEmpty())
            throw new RuntimeException("Cannot process empty Forth script!");

        String[] tokens = script.trim().split("\\s+");
        for (String token : tokens) {
            switch (this.state) {
                case INTERPRET -> this.interpret(token.toLowerCase(Locale.ROOT));
                case COMPILE -> this.compile(token.toLowerCase(Locale.ROOT));
                case COMMENT -> {
                    if (getWord(token.toLowerCase(Locale.ROOT)) instanceof CommentEndWord word) word.execute(this);
                }
            }
        }
    }

    public ForthVM copy(int capacity) {
        ForthVM vm = new ForthVM(capacity);

        Iterator<Long> it = this.data.descendingIterator();
        while (it.hasNext())
            vm.data.push(it.next());
        this.dictionary.forEach(vm::addWord);
        vm.state = this.state;
        vm.definition = this.definition;
        if (this.body != null && !this.body.isEmpty())
            vm.body = new ArrayList<>(this.body);

        return vm;
    }

    @FunctionalInterface
    public interface Word {
        void execute(ForthVM vm);

        default boolean immediate() {
            return false;
        }
    }

    public enum State {
        INTERPRET,
        COMPILE,
        COMMENT
    }

    static {
        HashMap<String, Word> words = new HashMap<>();
        words.put("+", new AddWord());
        words.put("-", new SubtractWord());
        words.put("*", new MultiplyWord());
        words.put("/", new DivideWord());
        words.put("%", new ModWord());
        words.put("^", new PowWord());

        words.put("&&", new AndComparsionWord());
        words.put("||", new OrComparsionWord());
        words.put("not", new NotBooleanWord());
        words.put(">", new GreaterBooleanWord());
        words.put("<", new LesserBooleanWord());
        words.put("=", new EqualsBooleanWord());
        words.put("true", new BooleanLiteralWord(true));
        words.put("false", new BooleanLiteralWord(false));

        words.put("dup", new DuplicateWord());
        words.put("swap", new SwapWord());
        words.put("drop", new DropWord());
        words.put("over", new OverWord());

        words.put("(", new CommentStartWord());
        words.put(")", new CommentEndWord());

        words.put(":", new DOCOLWord());
        words.put(";", new DOCOLEndWord());

        words.put(".", new PrintWord());

        BASE_DICTIONARY = Map.copyOf(words);
    }
}
