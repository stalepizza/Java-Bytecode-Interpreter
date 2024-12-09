# **Bytecode Interpreter**

A simple yet powerful **Bytecode Interpreter** implemented in Java. This program interprets a set of bytecode instructions, simulating basic arithmetic operations, stack manipulation, control flow, and conditional branching. It's designed to demonstrate the inner workings of interpreters like those used in virtual machines.

---

## **Features**

- 🚀 **Arithmetic Operations**: Perform addition, subtraction, multiplication, and division.
- 🧩 **Stack Manipulation**: Push and pop constants, load values from local variables, and store results.
- 🔄 **Control Flow**:
  - Conditional jumps (`if_icmpeq`, `if_icmpne`).
  - Unconditional jumps (`goto`).
- 🛠️ **Basic Debugging**: Tracks the state of the stack, local variables, and the instruction pointer during execution.

---

## **How It Works**

The program executes a sequence of bytecode instructions. Each instruction is parsed and executed step by step, maintaining the state of:
1. **Operand Stack**: Holds intermediate results for computations.
2. **Local Variables Table**: Stores variables for quick access.
3. **Instruction Pointer**: Tracks the current instruction being executed.

---

## **Bytecode Instructions**

### **Stack Manipulation**
```text
bipush  [value]  : Push a byte value onto the stack
iconst  [value]  : Push a constant integer onto the stack
iload   [index]  : Load value from a local variable onto the stack
istore  [index]  : Store value from the stack into a local variable
```
### **Arithmetic Operations**
```text
iadd            : Add the top two values on the stack
isub            : Subtract the top two values on the stack
imul            : Multiply the top two values on the stack
idiv            : Divide the top two values on the stack (checks for division by zero)
```
### **Control Flow**
```text
if_icmpeq [target] : Compare top two stack values; jump if equal
if_icmpne [target] : Compare top two stack values; jump if not equal
goto      [target] : Unconditional jump to the given instruction
ireturn            : Return the top value on the stack
```
## **Sample Bytecode**

Here’s an example of bytecode to compute `(10 + 20)` and return `1` if the result equals `30`:

```text
iconst 10       # Push 10 onto the stack
iconst 20       # Push 20 onto the stack
iadd            # Add the two numbers (10 + 20)
istore 0        # Store result (30) in local variable 0
iload 0         # Load the result back onto the stack
iconst 30       # Push 30 onto the stack
if_icmpeq 10    # If 30 == 30, jump to instruction 10
iconst 0        # Push 0 (skipped if condition is true)
goto 11         # Unconditional jump to instruction 11
iconst 1        # Push 1 (branch target)
ireturn         # Return the result
```
## **Output**

```text
Instruction Pointer: 0
Current Instruction: iconst 10
Operand Stack: []
Local Variables: [0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
...
Return value: 1
```

## **How to Run**
1. Clone this repository or download the .java file.
2. Compile the program:
 ```text
javac BytecodeInterpreter.java
  ```
3. Run the program:
  ```text
java BytecodeInterpreter
  ```
Have fun playing around with the basics of Java as much as I did!





