import java.util.Stack;
import java.util.Arrays;

public class BytecodeInterpreter {
    private int[] localVariables; // Local variables table
    private Stack<Integer> operandStack; // Operand stack
    private int instructionPointer; // Instruction pointer

    public BytecodeInterpreter() {
        this.localVariables = new int[10]; // Initialize 10 local variables
        this.operandStack = new Stack<>(); // Initialize operand stack
        this.instructionPointer = 0; // Start at instruction 0
    }

    // Helper method to check stack size
    private void ensureStackSize(int requiredSize) {
        if (operandStack.size() < requiredSize) {
            throw new IllegalStateException(
                    "Stack underflow: Expected at least " + requiredSize +
                            " operands but found " + operandStack.size() +
                            " at instruction " + instructionPointer);
        }
    }

    // Execute the given bytecode
    public void execute(String[] bytecode) {
        while (instructionPointer < bytecode.length) {
            // Debugging outputs
            System.out.println("Instruction Pointer: " + instructionPointer);
            System.out.println("Current Instruction: " + bytecode[instructionPointer]);
            System.out.println("Operand Stack: " + operandStack);
            System.out.println("Local Variables: " + Arrays.toString(localVariables));

            // Get current instruction and split into parts
            String instruction = bytecode[instructionPointer];
            String[] parts = instruction.split(" ");
            String opcode = parts[0];

            switch (opcode) {
                case "bipush": // Push a byte value onto the stack
                    int value = Integer.parseInt(parts[1]);
                    operandStack.push(value);
                    break;

                case "iconst": // Push a constant integer value onto the stack
                    int constant = Integer.parseInt(parts[1]);
                    operandStack.push(constant);
                    break;

                case "iload": // Load value from local variable onto the stack
                    int loadIndex = Integer.parseInt(parts[1]);
                    operandStack.push(localVariables[loadIndex]);
                    break;

                case "istore": // Store value from stack into a local variable
                    ensureStackSize(1);
                    int storeIndex = Integer.parseInt(parts[1]);
                    localVariables[storeIndex] = operandStack.pop();
                    break;

                case "iadd": // Add top two values on the stack
                    ensureStackSize(2);
                    int addValue1 = operandStack.pop();
                    int addValue2 = operandStack.pop();
                    operandStack.push(addValue1 + addValue2);
                    break;

                case "isub": // Subtract top two values on the stack
                    ensureStackSize(2);
                    int subValue1 = operandStack.pop();
                    int subValue2 = operandStack.pop();
                    operandStack.push(subValue1 - subValue2);
                    break;

                case "imul": // Multiply top two values on the stack
                    ensureStackSize(2);
                    int mulValue1 = operandStack.pop();
                    int mulValue2 = operandStack.pop();
                    operandStack.push(mulValue1 * mulValue2);
                    break;

                case "idiv": // Divide top two values on the stack
                    ensureStackSize(2);
                    int divValue1 = operandStack.pop();
                    int divValue2 = operandStack.pop();
                    if (divValue2 == 0) {
                        throw new ArithmeticException("Division by zero");
                    }
                    operandStack.push(divValue1 / divValue2);
                    break;

                case "if_icmpeq": // Compare top two values; jump if equal
                    ensureStackSize(2);
                    int eqValue1 = operandStack.pop();
                    int eqValue2 = operandStack.pop();
                    int eqTarget = Integer.parseInt(parts[1]);
                    if (eqValue1 == eqValue2) {
                        instructionPointer = eqTarget;
                        continue; // Skip pointer increment
                    }
                    break;

                case "if_icmpne": // Compare top two values; jump if not equal
                    ensureStackSize(2);
                    int neValue1 = operandStack.pop();
                    int neValue2 = operandStack.pop();
                    int neTarget = Integer.parseInt(parts[1]);
                    if (neValue1 != neValue2) {
                        instructionPointer = neTarget;
                        continue;
                    }
                    break;

                case "goto": // Unconditional jump
                    int gotoTarget = Integer.parseInt(parts[1]);
                    instructionPointer = gotoTarget;
                    continue;

                case "ireturn": // Return the top value on the stack
                    ensureStackSize(1);
                    System.out.println("Return value: " + operandStack.pop());
                    return;

                default: // Handle invalid instructions
                    throw new IllegalArgumentException("Unknown opcode: " + opcode);
            }

            // Move to the next instruction
            instructionPointer++;
        }
    }

    public static void main(String[] args) {
        BytecodeInterpreter interpreter = new BytecodeInterpreter();

        // Example bytecode
        String[] bytecode = {
                "iconst 10", // Push 10 onto the stack
                "iconst 20", // Push 20 onto the stack
                "iadd", // Add 10 + 20 (result: 30)
                "istore 0", // Store result in local variable 0
                "iload 0", // Load 30 back onto the stack
                "iconst 30", // Push 30 onto the stack
                "if_icmpeq 10", // If 30 == 30, jump to instruction 10
                "iconst 0", // Push 0 (skipped if condition is true)
                "goto 11", // Jump to instruction 11
                "iconst 1", // Push 1 (branch target)
                "ireturn" // Return the result
        };

        interpreter.execute(bytecode); // Should print: Return value: 1
    }
}
