package sandbox.interview.hospital;

import java.util.Optional;
import java.util.Stack;

class MagicStack extends Stack<Integer> {
    private Stack<Integer> minimums = new Stack<>();

    public synchronized Optional<Integer> minimumValue() {
        return Optional.ofNullable(this.minimums.peek());
    }

    @Override
    public synchronized Integer push(Integer item) {
        super.push(item);
        Integer minimumSoFar = minimums.peek();
        if (item < minimumSoFar) {
            minimums.push(item);
        } else {
            minimums.push(minimumSoFar);
        }
        return item;
    }

    @Override
    public synchronized Integer pop() {
        this.minimums.pop();
        return super.pop();
    }
}
